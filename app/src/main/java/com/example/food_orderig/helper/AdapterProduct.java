package com.example.food_orderig.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.grouping.ActivityAddOrEditGrouping;
import com.example.food_orderig.activity.product.ActivityAddOrEditProduct;
import com.example.food_orderig.activity.product.ActivityProduct;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.model.Grouping;
import com.example.food_orderig.model.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewholderProduct> {

    List<Product> list;
    Context context;

    DatabaseHelper database;
    ProductDao dao;
    Product product;
    ActivityProduct activityProduct;


    public AdapterProduct(List<Product> list , Context context){

        this.list = list;
        this.context = context;
    }

    @Override
    public AdapterProduct.ViewholderProduct onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =LayoutInflater.from(parent. getContext());
        View view = layoutInflater.inflate(R.layout.list_product,parent,false);
        ViewholderProduct viewholderProduct =new ViewholderProduct(view);
        return viewholderProduct;
    }

    @Override
    public void onBindViewHolder(AdapterProduct.ViewholderProduct holder, @SuppressLint("RecyclerView") int position) {

        product = list . get(position);
        holder.name_food.setText(product.name);
        holder.category_food.setText(product.category);
        holder.price_food.setText(product.price);

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                delete(position);
//                return true;
//            }
//        });
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(position);
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
        ImageView ivCheckBox;
        TextView tvEmpty;

        public ViewholderProduct(View itemView) {
            super(itemView);

            name_food=itemView.findViewById(R.id.name_product);
            ivCheckBox = itemView.findViewById(R.id.iv_check_Box);
            category_food=itemView.findViewById(R.id.category_product);
            imageViewfood=itemView.findViewById(R.id.image_product);
            price_food = itemView.findViewById(R.id.price_product);
            tvEmpty = itemView.findViewById(R.id.tv_empty);

        }
    }

    class Anim extends Animation{

        private int width,startwidth;
        private View view;

        public Anim(int width , View view){
            this.width = width;
            this.view = view;
            this.startwidth = view.getWidth();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newwidth = startwidth + (int) ((width-startwidth) * interpolatedTime);
            view.getLayoutParams().width=newwidth;

            super.applyTransformation(interpolatedTime, t);
        }

        @Override
        public boolean willChangeBounds() {

            return true;
        }
    }

    private void edit(int pos){

        Intent intent = new Intent(context, ActivityAddOrEditProduct.class);
        intent.putExtra("product",new Gson().toJson(list.get(pos)));
        context.startActivity(intent);

    }

    private void delete(int pos){


        database = DatabaseHelper.getInstance(context.getApplicationContext());
        dao = database.productDao();
        dao.deleteProduct(product);
        list.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos,list.size());
        notifyDataSetChanged();
        Toast.makeText(context, "با موفقیت حذف شد", Toast.LENGTH_LONG).show();

    }

    public void addList(List<Product> arrayList){
        list.clear();
        list.addAll(arrayList);
        notifyDataSetChanged();
    }
}
