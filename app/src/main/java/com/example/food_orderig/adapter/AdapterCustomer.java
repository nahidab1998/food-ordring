package com.example.food_orderig.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.food_orderig.R;
import com.example.food_orderig.activity.customer.ActivityAddOrEditCostomer;
import com.example.food_orderig.activity.customer.ActivityCustomer;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.CustomerDao;
import com.example.food_orderig.database.dao.DetailOrderDao;
import com.example.food_orderig.database.dao.SavedOrderDao;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.helper.Permition;
import com.example.food_orderig.model.Customer;
import com.example.food_orderig.model.Order;
import com.example.food_orderig.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.ViewholderCustomer> {

    private List<Customer> list;
    private Context context;
    private Listener listener;
    private LinearLayout edite_customer , delete_customer;
    private CustomerDao dao_customer;
    private SavedOrderDao savedOrderDao;
    private DatabaseHelper database_customer;
    private DetailOrderDao detailOrderDao ;
    private List<Customer> list_search;
    private String text;
    private Activity activity;


    public AdapterCustomer(List<Customer> list , Context context , Listener listener , Activity activity){
        this.list_search = list;
        this.context = context;
        this.listener = listener;
        this.list = new ArrayList<>(list_search);
        this.activity = activity;


    }

    public interface Listener{
        void onClickListener(Customer customer, int pos , String name);
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
//        String call = customer.phone;
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Permition permition;
                permition = new Permition(200,context , activity) {
                    
                    @Override
                    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    }
                };
                if (permition.checkPermission()){
//                    Toast.makeText(context, "call", Toast.LENGTH_SHORT).show();
                    String number_for_phone = list.get(position).phone;
                    Intent call = new Intent(Intent.ACTION_VIEW);
                    call.setData(Uri.parse("tel:" + number_for_phone));
                    context.startActivity(call);

                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onClickListener(customer , position , list.get(position).name);
//                showBottomSheetDialogclick(position);
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

    public void showBottomSheetDialogclick (int pos , String name , int id) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.btnsheet_deleteedite);

        delete_customer = bottomSheetDialog.findViewById(R.id.delerebtn);
        delete_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatabase();
                setTextDialog(id);
                showAlertDialog(id , bottomSheetDialog , pos , name);
            }
        });
        edite_customer = bottomSheetDialog.findViewById(R.id.editbtn);
        edite_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ActivityAddOrEditCostomer.class);
                intent.putExtra("Customer",new Gson().toJson(list.get(pos)));
                context.startActivity(intent);
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }

    private void initDatabase(){
        database_customer = App.getDatabase();
        dao_customer = database_customer.customerDao();
        savedOrderDao = database_customer.savedOrderDao();
        detailOrderDao = database_customer.detailOrderDao();
        List<Order> listOrder = new ArrayList<>();
        listOrder.addAll(savedOrderDao.getOrderList()) ;
    }

    public void showAlertDialog(int id , Dialog bottomSheetDialog , int pos , String name){

        new AlertDialog.Builder(context)
                .setTitle("حذف")
                .setIcon(R.drawable.delete_image_dialog)
                .setMessage(text)
                .setPositiveButton("تأیید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//
                        if (savedOrderDao.getid(id) != null){
                            detailOrderDao.deleteOneOrderDetail(savedOrderDao.getid(id).unit_code);
                            savedOrderDao.deteteID(id);
                            deleteOneItem(bottomSheetDialog , pos , name);

                        }else {
                            deleteOneItem(bottomSheetDialog , pos , name);
                        }

                    }
                })
                .setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        bottomSheetDialog.dismiss();
                    }
                })
                .create()
                .show();
        bottomSheetDialog.dismiss();

    }

    public void deleteOneItem(Dialog bottomSheetDialog , int pos , String name){
        dao_customer.deleteCustomer(list.get(pos));
        list.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos,list.size());
        notifyDataSetChanged();
        bottomSheetDialog.dismiss();
        Toast.makeText(context, name +" با موفقیت حذف شد ", Toast.LENGTH_LONG).show();
    }

    private void setTextDialog(int id){
        if(savedOrderDao.getid(id) != null){
            text = " این کاربر دارای سفارش هست ، ایا مایلید این مورد را حذف کنید؟ ";
        }else {
            text = " ایا مایلید این مورد را حذف کنید؟";
        }
    }

    public Filter getFilter() {
        return newsFilter;
    }
    private final Filter newsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Customer> filterdNewList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filterdNewList.addAll(list_search);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Customer customer : list_search){

                    if(customer.name.toLowerCase().contains(filterPattern))
                        filterdNewList.add(customer);

                    if(customer.phone.toLowerCase().contains(filterPattern))
                        filterdNewList.add(customer);
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


    public void addList(List<Customer> arrayList){
        list_search.clear();
        list.clear();
        list_search.addAll(arrayList);
        list.addAll(list_search);
        notifyDataSetChanged();
    }

//    private void edit(int pos){
//
//        Intent intent = new Intent(context, ActivityAddOrEditCostomer.class);
//        intent.putExtra("Customer",new Gson().toJson(list.get(pos)));
//        context.startActivity(intent);
//
//    }

}
