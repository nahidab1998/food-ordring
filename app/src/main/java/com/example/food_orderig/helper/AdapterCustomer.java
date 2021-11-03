package com.example.food_orderig.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;
import com.example.food_orderig.model.Customer;
import com.example.food_orderig.model.Grouping;

import java.util.List;

public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.ViewholderCustomer> {

    List<Customer> list;
    Context context;

    public AdapterCustomer(List<Customer> list , Context context ){
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewholderCustomer onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =LayoutInflater.from(parent. getContext());
        View view = layoutInflater.inflate(R.layout.list_customer,parent,false);
        ViewholderCustomer viewholderCustomer = new ViewholderCustomer(view);
        return viewholderCustomer;

    }

    @Override
    public void onBindViewHolder(ViewholderCustomer holder, int position) {
        Customer customer = list . get(position);
        holder.name_customer.setText(customer.name);
        holder.phone_customer.setText(customer.phone);
        holder.address_customer.setText(customer.address);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewholderCustomer extends RecyclerView.ViewHolder {

        TextView name_customer,phone_customer ,address_customer;

        public ViewholderCustomer(View itemView) {
            super(itemView);

            name_customer=itemView.findViewById(R.id.name_customer);
            phone_customer=itemView.findViewById(R.id.phone_customer);
            address_customer=itemView.findViewById(R.id.address_customer);

        }
    }

    public void addList(List<Customer> arrayList){
        list.clear();
        list.addAll(arrayList);
        notifyDataSetChanged();
    }

}
