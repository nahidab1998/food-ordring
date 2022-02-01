package com.example.food_orderig.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;
import com.example.food_orderig.model.Grouping;

import java.util.List;

public class AdapterGroupingProduct extends RecyclerView.Adapter<AdapterGroupingProduct.ViewHolder> {

    private List<Grouping> list;
    private Context context;
    private Listener listener;
    private int row_index = 0;
    private String categoryIsChosen = "";

    public AdapterGroupingProduct(List<Grouping> list , Context context, Listener listener){
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    public interface Listener{
        void onClick(int pos, Grouping category);
    }

    @Override
    public AdapterGroupingProduct.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_grouping_product,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterGroupingProduct.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Grouping grouping = list.get(position);
        holder.textgrouping_product.setText(grouping.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position,grouping);
                row_index = position ;
                categoryIsChosen = "";
                notifyDataSetChanged();
            }
        });

        if(row_index == position || grouping.name.equals(categoryIsChosen)){
            holder.cardView_back.setCardBackgroundColor(Color.parseColor("#595959"));
            holder.textgrouping_product.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.cardView_back.setCardBackgroundColor(Color.parseColor("#ffffff"));
            holder.textgrouping_product.setTextColor(Color.parseColor("#595959"));

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textgrouping_product;
        CardView cardView_back;

        public ViewHolder(View itemView) {
            super(itemView);

            textgrouping_product = itemView.findViewById(R.id.textgrouping_product);
            cardView_back = itemView.findViewById(R.id.cardView_back_grouping);
        }

    }
}
