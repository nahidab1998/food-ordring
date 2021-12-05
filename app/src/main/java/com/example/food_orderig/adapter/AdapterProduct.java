package com.example.food_orderig.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.food_orderig.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.List;


public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewholderProduct> {

    List<Product> list;
    Context context;
    Listener listener;
    DatabaseHelper database;
    ProductDao dao;
    Toolbar toolbardelete;
    LinearLayout edite_product , delete_product;



    public AdapterProduct(List<Product> list , Context context ,  Listener listener){

        this.list = list;
        this.context = context;
        this.listener = listener;

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

//    private String converText(String text){
//        StringBuilder stringBuilder = new StringBuilder(text);
//        for (int i = stringBuilder.length() - 3 ; i >0 ; i -= 3){
//            stringBuilder.insert( i , ",");
//        }
//        return stringBuilder.toString();
//    }

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
                        .setMessage("آیا از حذف کامل این محصول اطمینان دارید؟")
                        .setPositiveButton("تأیید", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                database = DatabaseHelper.getInstance(context.getApplicationContext());
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

    public void addList(List<Product> arrayList){
        list.clear();
        list.addAll(arrayList);
        notifyDataSetChanged();
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }


}
