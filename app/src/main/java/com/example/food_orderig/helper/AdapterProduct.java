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
import com.example.food_orderig.model.Grouping;
import com.example.food_orderig.model.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewholderProduct> {

    List<Product> list;
    Context context;
    boolean isEnable = false;
    boolean isSelectAll = false;
    ArrayList<String> arrayList;
    TextView tvEmpty;
    Activity activity;
    ArrayList<String> selectList = new ArrayList<>();

    MainViewModel mainViewModel;

    public AdapterProduct(List<Product> list , Context context , ArrayList<String> arrayList , TextView tvEmpty , Activity activity){

        this.list = list;
        this.context = context;
        this.arrayList = arrayList;
        this.tvEmpty =tvEmpty;
        this.activity = activity;
    }

    @Override
    public AdapterProduct.ViewholderProduct onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =LayoutInflater.from(parent. getContext());
        View view = layoutInflater.inflate(R.layout.list_product,parent,false);
        ViewholderProduct viewholderProduct =new ViewholderProduct(view);

        mainViewModel = ViewModelProviders.of((FragmentActivity) activity)
                .get(MainViewModel.class);
        return viewholderProduct;
    }

    @Override
    public void onBindViewHolder(AdapterProduct.ViewholderProduct holder, @SuppressLint("RecyclerView") int position) {

        Product product = list . get(position);
        holder.name_food.setText(product.name);
        holder.category_food.setText(product.category);
        holder.price_food.setText(product.price);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (!isEnable){

                    ActionMode.Callback callback = new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

                            MenuInflater menuInflater = actionMode.getMenuInflater();
                            menuInflater.inflate(R.menu.menu,menu);

                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {

                            isEnable = true;
                            ClickItem(holder);

                            mainViewModel.getText1().observe((LifecycleOwner) activity, new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    actionMode.setTitle(String.format("%s Selected" , s));

                                }
                            });
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                            int id = menuItem.getItemId();

                            switch (id){
                                case R.id.menu_delete:
                                    for (String s : selectList){
                                        arrayList.remove(s);
                                    }
                                    if (arrayList.size() == 0 ){
                                        tvEmpty.setVisibility(View.VISIBLE);
                                    }
                                    actionMode.finish();
                                    break;
                                case R.id.select_all:
                                    if (selectList.size()==arrayList.size()){
                                        isSelectAll = false;
                                        selectList.clear();
                                    }else {
                                        isSelectAll = true;
                                        selectList.clear();
                                        selectList.addAll(arrayList);
                                    }
                                    mainViewModel.setText1(String.valueOf(selectList.size()));
                                    notifyDataSetChanged();
                                    break;
                            }
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode actionMode) {
                            isEnable=false;
                            isSelectAll = false;
                            selectList.clear();
                            notifyDataSetChanged();
                        }
                    };
                    ((AppCompatActivity) v.getContext()).startActionMode(callback);
                }else {
                    ClickItem(holder);
                }

                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnable){

                    ClickItem(holder);

                }else {

                    edit(position);
                }
            }
        });
        if (isSelectAll){
            holder.ivCheckBox.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }else {
            holder.ivCheckBox.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void ClickItem(ViewholderProduct holder) {

        String s = arrayList.get(holder.getAdapterPosition());
        if (holder.ivCheckBox.getVisibility() == View.GONE){
            holder.ivCheckBox.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.LTGRAY);
            selectList.add(s);
        }else {
            holder.ivCheckBox.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            selectList.remove(s);
        }
        //
        mainViewModel.setText1(String.valueOf(selectList.size()));
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

    private void edit(int pos){

        Intent intent = new Intent(context, ActivityAddOrEditProduct.class);
        intent.putExtra("product",new Gson().toJson(list.get(pos)));
        context.startActivity(intent);

    }

    public void addList(List<Product> arrayList){
        list.clear();
        list.addAll(arrayList);
        notifyDataSetChanged();
    }
}
