package com.example.food_orderig.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.example.food_orderig.R;
import com.example.food_orderig.activity.customer.ActivityAddOrEditCostomer;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.CustomerDao;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.model.Customer;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import java.util.List;

public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.ViewholderCustomer> {

    List<Customer> list;
    Context context;
    Listener listener;
    LinearLayout edite_customer , delete_customer;
    CustomerDao dao_customer;
    DatabaseHelper database_customer;


    public AdapterCustomer(List<Customer> list , Context context , Listener listener){
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    public interface Listener{
        void onClickListener(Customer customer, int pos);
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

//                Toast.makeText(context, "call", Toast.LENGTH_SHORT).show();
                    String number_for_phone = list.get(position).phone;
                    Intent call = new Intent(Intent.ACTION_VIEW);
                    call.setData(Uri.parse("tel:" + number_for_phone));
                    context.startActivity(call);


            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onClickListener(customer , position);
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

    public void showBottomSheetDialogclick (int pos) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.btnsheet_deleteedite);

        Customer customer = list.get(pos);
        delete_customer = bottomSheetDialog.findViewById(R.id.delerebtn);

        delete_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("حذف")
                        .setMessage("آیا از حذف کامل این مشتری اطمینان دارید؟")
                        .setPositiveButton("تأیید", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                database_customer = App.getDatabase();
                                dao_customer = database_customer.customerDao();
                                dao_customer.deleteCustomer(customer);
                                list.remove(pos);
                                notifyItemRemoved(pos);
                                notifyItemRangeChanged(pos,list.size());
                                notifyDataSetChanged();
                                bottomSheetDialog.dismiss();
                                Toast.makeText(context, customer.name +" با موفقیت حذف شد ", Toast.LENGTH_LONG).show();
//                                Toast.makeText(context, list.get(pos).name+" با موفقیت حذف شد ", Toast.LENGTH_LONG).show();
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


    public void addList(List<Customer> arrayList){
        list.clear();
        list.addAll(arrayList);
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
