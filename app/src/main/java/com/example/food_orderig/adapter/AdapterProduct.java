package com.example.food_orderig.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.product.ActivityAddOrEditProduct;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewholderProduct> {

    private List<Product> list;
    private Context context;
    private Listener listener;
    private DatabaseHelper database;
    private ProductDao dao;
    private List<Product> list_search;
    private LinearLayout edite_product , delete_product;




    public AdapterProduct(List<Product> list , Context context ,  Listener listener){

        this.list_search = list;
        this.context = context;
        this.listener = listener;
        this.list = new ArrayList<>(list_search);

    }

    public interface Listener{
        void onClick(Product product , int pos);
    }

    @Override
    public AdapterProduct.ViewholderProduct onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =LayoutInflater.from(parent. getContext());
        View view = layoutInflater.inflate(R.layout.list_product,parent,false);
        ViewholderProduct viewholderProduct =new ViewholderProduct(view);
        return viewholderProduct;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(AdapterProduct.ViewholderProduct holder, @SuppressLint("RecyclerView") int position) {

        Product product = list . get(position);
        holder.name_food.setText(product.name);
        holder.category_food.setText(product.category);
        holder.price_food.setText(product.price);
        Log.e("qqqq", "onBindViewHolder: " + product.picture );
        try{
            final int takeFlags =  (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            // Check for the freshest data.
            context.getContentResolver().takePersistableUriPermission(Uri.parse(product.picture), takeFlags);
            // convert uri to bitmap
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(product.picture));
            // set bitmap to imageview
            holder.imageViewfood.setImageBitmap(bitmap);
        }
        catch (Exception e){
            //handle exception
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                showBottomSheetDialogclick(position);
                listener.onClick(product , position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewholderProduct extends RecyclerView.ViewHolder {
        TextView name_food , category_food , price_food;
        ImageView imageViewfood;

        public ViewholderProduct(View itemView) {
            super(itemView);
            name_food=itemView.findViewById(R.id.name_product);
            category_food=itemView.findViewById(R.id.category_product);
            imageViewfood=itemView.findViewById(R.id.image_product);
            price_food = itemView.findViewById(R.id.price_product);

        }
    }

        public void showBottomSheetDialogclick (int pos) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.btnsheet_deleteedite);

       Product product = list . get(pos);
        delete_product = bottomSheetDialog.findViewById(R.id.delerebtn);
        delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("حذف")
                        .setIcon(R.drawable.delete_image_dialog)
                        .setMessage("آیا از حذف کامل این محصول اطمینان دارید؟")
                        .setPositiveButton("تأیید", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                database = App.getDatabase();
                                dao = database.productDao();
                                dao.deleteProduct(product);
                                list.remove(pos);
                                notifyItemRemoved(pos);
                                notifyItemRangeChanged(pos,list.size());
                                notifyDataSetChanged();
                                Toast.makeText(context, product.name + " با موفقیت حذف شد ", Toast.LENGTH_LONG).show();

                            }
                        })
                        .setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                bottomSheetDialog.dismiss();
                            }
                        })
                        .create()
                        .show();
                bottomSheetDialog.dismiss();

            }
        });

        edite_product = bottomSheetDialog.findViewById(R.id.editbtn);
        edite_product.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ActivityAddOrEditProduct.class);
                intent.putExtra("product",new Gson().toJson(list.get(pos)));
                context.startActivity(intent);
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }


    public Filter getFilter() {
        return newsFilter;
    }

    private final Filter newsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Product> filterdNewList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterdNewList.addAll(list_search);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Product product : list_search){

                    if(product.name.toLowerCase().contains(filterPattern))
                        filterdNewList.add(product);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterdNewList;
            results.count = filterdNewList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public void addList(List<Product> arrayList){
        list_search.clear();
        list_search.addAll(arrayList);
//        list.clear();
        list.addAll(list_search);
        notifyDataSetChanged();
    }

    public void add(Product product){
        list.add(list.size(),product);
        notifyItemInserted(list.size());
    }

    public void update(Product product, int pos){
//        list.get(0) = product;
        notifyItemChanged(pos,product);
    }

    public void remove(int pos){
        list.remove(pos);
        notifyItemRemoved(pos);
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }


}
