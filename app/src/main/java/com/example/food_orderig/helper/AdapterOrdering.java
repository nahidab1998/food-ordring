package com.example.food_orderig.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.model.Product;

import java.util.List;

public class AdapterOrdering extends RecyclerView.Adapter<AdapterOrdering.ViewHolder> {

    List<Product> list ;
    Context context ;
    DatabaseHelper database;
    ProductDao dao;
    Product product;

    public AdapterOrdering(List<Product> list , Context context){
        this.list = list;
        this.context = context;

    }

    @Override
    public AdapterOrdering.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_product_ordering,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterOrdering.ViewHolder holder, int position) {

        product = list.get(position);
        holder.txtname.setText(product.name);
        holder.txtprise.setText(product.price);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtname ;
        TextView txtprise;

        public ViewHolder(View itemView) {
            super(itemView);
            txtname =itemView.findViewById(R.id.name_product_ordering);
            txtprise = itemView.findViewById(R.id.price_product_ordering);
        }
    }
}
