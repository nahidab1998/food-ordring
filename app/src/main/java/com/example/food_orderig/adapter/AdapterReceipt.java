package com.example.food_orderig.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.DetailOrderDao;
import com.example.food_orderig.model.DetailOrder;
import com.example.food_orderig.model.Order;

import java.util.List;

public class AdapterReceipt extends RecyclerView.Adapter<AdapterReceipt.ViewHolder> {

    private List<DetailOrder> list;
    private Context context;
    private DatabaseHelper db;
    private DetailOrderDao dao;

    public AdapterReceipt (List<DetailOrder> list , Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =LayoutInflater.from(parent. getContext());
        View view = layoutInflater.inflate(R.layout.list_receipt,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder ;
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {

        DetailOrder detailOrder = list.get(position);
        holder.name.setText(detailOrder.name);
        holder.category.setText(detailOrder.category);
        holder.amont.setText(String.valueOf(detailOrder.amant));
        holder.price.setText(detailOrder.price);
        holder.picture_food.setImageURI(Uri.parse(detailOrder.picture));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView amont , name , price , category;
        ImageView picture_food;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_food);
            amont = itemView.findViewById(R.id.amont);
            price = itemView.findViewById(R.id.price_receipt);
            category = itemView.findViewById(R.id.category_receipt);
            picture_food = itemView.findViewById(R.id.pic_food);

        }
    }
}
