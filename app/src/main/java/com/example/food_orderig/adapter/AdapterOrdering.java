package com.example.food_orderig.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.helper.Tools;
import com.example.food_orderig.model.Product;

import java.util.List;

public class AdapterOrdering extends RecyclerView.Adapter<AdapterOrdering.ViewHolder> {

    List<Product> list ;
    Context context ;
    DatabaseHelper database;
    ProductDao dao;
    Product product;
    private int numberorder = 1  ;
    public Listener listener;

    public AdapterOrdering(List<Product> list , Context context , Listener listener){
        this.list = list;
        this.context = context;
        this.listener = listener ;
    }

    public interface Listener{
        void onAdded(int pos);
        void onRemove(int pos);
    }

    @Override
    public AdapterOrdering.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_product_ordering,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterOrdering.ViewHolder holder,@SuppressLint("RecyclerView") int position) {

        product = list.get(position);
        holder.txtname.setText(product.name);
        holder.txtprise.setText(product.price);
        holder.txtcategory.setText(product.category);
        holder.imageView_food.setImageURI(Uri.parse(product.picture));
        holder.txt_price.setText(Tools.getForamtPrice(Tools.convertToPrice(product.price) * product.amount +""));
        holder.title.setText(product.amount+"");
        holder.add.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                listener.onAdded(position);
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRemove(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtname ;
        TextView txtprise;
        TextView txtcategory;
        TextView txt_price;
        TextView title;
        ImageView imageView_food;
        CardView add , remove ;


        public ViewHolder(View itemView) {
            super(itemView);
            txtname =itemView.findViewById(R.id.name_product_ordering);
            txtprise = itemView.findViewById(R.id.price_product_ordering);
            txtcategory = itemView.findViewById(R.id.category_ordering);
            add =itemView.findViewById(R.id.add);
            remove = itemView.findViewById(R.id.remove);
            imageView_food = itemView.findViewById(R.id.image_product);
            txt_price = itemView.findViewById(R.id.price_product_ordering);
            title =itemView.findViewById(R.id.number_pro);
        }
    }
}
