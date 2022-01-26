package com.example.food_orderig.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.orderingreceipt.ActivityOrderingReceipt;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.SavedOrderDao;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.helper.Tools;
import com.example.food_orderig.model.Grouping;
import com.example.food_orderig.model.Order;

import java.util.ArrayList;
import java.util.List;

public class AdaptersavedOrdering extends RecyclerView.Adapter<AdaptersavedOrdering.ViewHolder> {

    private List<Order> list;
    private Context context;
    private DatabaseHelper database;
    private SavedOrderDao dao;
    private List<Order> list_search;


    public AdaptersavedOrdering(Context context , List<Order> list){
        this.context = context;
        this.list_search = list;
        this.list = new ArrayList<>(list_search);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_saved_ordering , parent , false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdaptersavedOrdering.ViewHolder holder,@SuppressLint("RecyclerView") int position) {

        Order order = list.get(position);
        Log.e("qqqq", "onBindViewHolder - order time is: " + order.date);
        holder.name.setText(order.name);
        holder.status.setText(String.valueOf(order.status));
        holder.total.setText(order.total);
        holder.explain.setText(order.description);
        holder.date.setText(order.date);
        holder.time.setText(order.time);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent = new Intent(context, ActivityOrderingReceipt.class);
                intent.putExtra("code" , list.get(position).unit_code);
                intent.putExtra("customerid" , list.get(position).customer_id);
                intent.putExtra("name" , list.get(position).name);
                intent.putExtra("total" , list.get(position).total);
                intent.putExtra("time" , list.get(position).time);
                intent.putExtra("date" , list.get(position).date);
                context.startActivity(intent);
//                Toast.makeText(context, "جزییات سفارش " + order.name, Toast.LENGTH_SHORT).show();


            }
        });

        holder.deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("حذف")
                        .setIcon(R.drawable.delete_image_dialog)
                        .setMessage("آیا از لغو کامل این سفارش اطمینان دارید؟")
                        .setPositiveButton("تأیید", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                database = App.getDatabase();
                                dao = database.savedOrderDao();
                                dao.deleteOrder(order);
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,list.size());
                                notifyDataSetChanged();
                                Toast.makeText(context, "سفارش "+order.name +" با موفقیت حذف شد ", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name , status , total , explain ;
        TextView deleteitem;
//        TextView houre;
        TextView date , time;

        public ViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            name = itemView.findViewById(R.id.name_seved);
            status = itemView.findViewById(R.id.status);
            total = itemView.findViewById(R.id.total_saved);
            explain = itemView.findViewById(R.id.explain);
            deleteitem = itemView.findViewById(R.id.delete_ordering);
//            houre = itemView.findViewById(R.id.houre);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);

        }
    }

    public Filter getFilter() {
        return newsFilter;
    }

    private final Filter newsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Order> filterdNewList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterdNewList.addAll(list_search);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Order order : list_search){
                    if(order.name.toLowerCase().contains(filterPattern))
                        filterdNewList.add(order);
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

    public void addList(List<Order> arrayList){
        list_search.clear();
        list_search.addAll(arrayList);
        list = new ArrayList<>(list_search);
        notifyDataSetChanged();
    }

}
