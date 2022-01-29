package com.example.food_orderig.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.grouping.ActivityAddOrEditGrouping;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.GroupingDao;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.model.Grouping;
import com.example.food_orderig.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdapterGrouping extends RecyclerView.Adapter<AdapterGrouping.ViewholderGrouping> {

    private List<Grouping> list;
    private Context context;
    private LinearLayout edite_grouping , delete_grouping;
    private DatabaseHelper database;
    private GroupingDao groupingDao;
    private ProductDao productDao;
    private List<Grouping> list_search;
    private int count;
    private String text;




    public AdapterGrouping(List<Grouping> list , Context context ){
        this.list_search = list;
        this.context = context;
        this.list = new ArrayList<>(list_search);
    }

    @Override
    public ViewholderGrouping onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent. getContext());
        View view = layoutInflater.inflate(R.layout.list_grouping,parent,false);
        ViewholderGrouping viewholderGrouping = new ViewholderGrouping(view);
        return viewholderGrouping;
    }

    @Override
    public void onBindViewHolder(ViewholderGrouping holder, @SuppressLint("RecyclerView") int position) {

        Grouping grouping = list . get(position);
        holder.textView_showname_grouping.setText(grouping.name);
//        holder.imageView_grouping.setImageURI(Uri.parse(grouping.picture));
//        try{
//            final int takeFlags =  (Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//            // Check for the freshest data.
//            context.getContentResolver().takePersistableUriPermission(Uri.parse(grouping.picture), takeFlags);
//            // convert uri to bitmap
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(grouping.picture));
//            // set bitmap to imageview
//            holder.imageView_grouping.setImageBitmap(bitmap);
//        }
//        catch (Exception e){
//            //handle exception
//            e.printStackTrace();
//        }
        try {
            if (new File(grouping.picture).exists() && !grouping.picture.isEmpty()){
                Picasso.with(context).load(new File(grouping.picture)).into(holder.imageView_grouping);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialogclick(position , list.get(position).name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewholderGrouping extends RecyclerView.ViewHolder {

        TextView textView_showname_grouping;
        ImageView imageView_grouping;

        public ViewholderGrouping(View itemView) {
            super(itemView);
            textView_showname_grouping=itemView.findViewById(R.id.name_grouping);
            imageView_grouping=itemView.findViewById(R.id.img_grouping);
        }
    }

    private void showBottomSheetDialogclick (int pos , String name) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.btnsheet_deleteedite);

        Grouping grouping = list . get(pos);
        delete_grouping = bottomSheetDialog.findViewById(R.id.delerebtn);
        delete_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDataBase(name);
                setTextAlertDialog();
                showAlertDialog(name , pos , bottomSheetDialog);

            }
        });

        edite_grouping = bottomSheetDialog.findViewById(R.id.editbtn);
        edite_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityAddOrEditGrouping.class);
                intent.putExtra("Grouping",new Gson().toJson(list.get(pos)));
                context.startActivity(intent);
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    private void initDataBase(String name){

        database = App.getDatabase();
        groupingDao = database.groupingDao();
        productDao = database.productDao();
        count = productDao.get_product_by_category(name).size();
    }

    private void setTextAlertDialog(){
        if(count >= 1 ){
            text =  " این دسته بندی "+ " ( "+ count + " ) "+ " محصول دارد ؛ ایا مایلید این مورد را حذف کنید؟ ";
        }else {
            text = " ایا مایلید این مورد را حذف کنید؟";
        }
    }

    private void showAlertDialog(String name , int pos , Dialog bottomSheetDialog){
        new AlertDialog.Builder(context)
                .setTitle("حذف")
                .setIcon(R.drawable.delete_image_dialog)
                .setMessage(text)
                .setPositiveButton("تأیید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(count >=1){
                            productDao.deleteProductByCategory(name);
                            deletOneItem(pos, bottomSheetDialog,name);
                        }else {
                            deletOneItem(pos, bottomSheetDialog,name);
                        }
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

    private void deletOneItem(int pos , Dialog bottomSheetDialog , String name){
        groupingDao.deleteGrouping(list.get(pos));
        list.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos,list.size());
        notifyDataSetChanged();
        Toast.makeText(context, name +" با موفقیت حذف شد ", Toast.LENGTH_LONG).show();
    }


    public Filter getFilter() {
        return newsFilter;
    }

    private final Filter newsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Grouping> filterdNewList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterdNewList.addAll(list_search);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Grouping grouping : list_search){

                    if(grouping.name.toLowerCase().contains(filterPattern))
                        filterdNewList.add(grouping);
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


    public void addList(List<Grouping> arrayList){
        list_search.clear();
        list_search.addAll(arrayList);
        list = new ArrayList<>(list_search);
        notifyDataSetChanged();
    }
}
