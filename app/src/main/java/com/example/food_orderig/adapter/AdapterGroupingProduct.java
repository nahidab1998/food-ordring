package com.example.food_orderig.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;
import com.example.food_orderig.model.Grouping;

import java.util.List;

public class AdapterGroupingProduct extends RecyclerView.Adapter<AdapterGroupingProduct.ViewHolder> {

    List<Grouping> list;
    Context context;
    Grouping grouping;
    AdapterProduct adapterProduct;

    public AdapterGroupingProduct(List<Grouping> list , Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public AdapterGroupingProduct.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_grouping_product,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( AdapterGroupingProduct.ViewHolder holder, int position) {

        grouping = list.get(position);
        holder.textgrouping_product.setText(grouping.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Toast.makeText(context.getApplicationContext(),grouping.name + "ها ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textgrouping_product;

        public ViewHolder(View itemView) {
            super(itemView);

            textgrouping_product = itemView.findViewById(R.id.textgrouping_product);
        }
    }
}
