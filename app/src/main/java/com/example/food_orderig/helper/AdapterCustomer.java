package com.example.food_orderig.helper;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.customer.ActivityAddOrEditCostomer;
import com.example.food_orderig.activity.customer.ActivityCustomer;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.CustomerDao;
import com.example.food_orderig.model.Customer;
import com.example.food_orderig.model.Grouping;

import java.util.ArrayList;
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
    public void onBindViewHolder(ViewholderCustomer holder, @SuppressLint("RecyclerView") int position) {
        Customer customer = list . get(position);
        holder.name_customer.setText(customer.name);
        holder.phone_customer.setText(customer.phone);
        holder.address_customer.setText(customer.address);
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

    public class ViewholderCustomer extends RecyclerView.ViewHolder {

        TextView name_customer,phone_customer ,address_customer;
        LinearLayout call;



        public ViewholderCustomer(View itemView) {
            super(itemView);

            name_customer=itemView.findViewById(R.id.name_customer);
            phone_customer=itemView.findViewById(R.id.phone_customer);
            address_customer=itemView.findViewById(R.id.address_customer);
            call = itemView.findViewById(R.id.call);

        }

    }


    public void addList(List<Customer> arrayList){
        list.clear();
        list.addAll(arrayList);
        notifyDataSetChanged();
    }

    private void edit(int pos){

        Intent intent = new Intent(context, ActivityAddOrEditCostomer.class);
        intent.putExtra("namecustomer",list.get(pos).name);
        intent.putExtra("phonecustomer",list.get(pos).phone);
        intent.putExtra("addresscustomer",list.get(pos).address);
        context.startActivity(intent);

    }

}
