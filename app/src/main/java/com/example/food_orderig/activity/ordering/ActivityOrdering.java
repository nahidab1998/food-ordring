package com.example.food_orderig.activity.ordering;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.customer.ActivityCustomer;
import com.example.food_orderig.activity.product.ActivityProduct;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.DetailOrderDao;
import com.example.food_orderig.adapter.AdapterOrdering;
import com.example.food_orderig.database.dao.SavedOrderDao;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.helper.Tools;
import com.example.food_orderig.model.Customer;
import com.example.food_orderig.model.DetailOrder;
import com.example.food_orderig.model.Order;
import com.example.food_orderig.model.Product;
import com.google.gson.Gson;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import ir.hamsaa.persiandatepicker.util.PersianCalendarUtils;


public class ActivityOrdering extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterOrdering adapterOrdering;
    private DatabaseHelper db;
    private List<Product> orderDetailList;
    private DetailOrderDao dao_detailorder;
    private SavedOrderDao dao_savedorder;
    private Customer customer;
    private LinearLayout box_customer;
    private CardView record_order,add_order;
    private CardView cardView_pay;
    private TextView txtname;
    private TextView name_customer;
    private TextView number_order;
    private TextView total ;
    private TextView save_order ;
    private TextView txt_calender , txt_time ;
    private CardView calender , houre ;
    private CardView card_numberorder;
    private ImageView delete_ordering;
    private LinearLayout lottieAnimationView;
    private String CODE = String.valueOf(System.currentTimeMillis());
    private PersianDatePickerDialog picker;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

//        Log.e("bbbbb", "onCreate: " + System.currentTimeMillis() );
        initDatabase();
        initID();
        initBoxCustomer();
        initBoxProduct();
        initSaveOrdeing();
        delete_ordering();
        setCalender();
        setTime();
        setDefultCalender();
        setDefultTime();

        orderDetailList = new ArrayList<>();
        adapterOrdering = new AdapterOrdering(orderDetailList, this, new AdapterOrdering.Listener() {
            @Override
            public void onAdded(int pos) {
                orderDetailList.get(pos).amount = orderDetailList.get(pos).amount + 1;
                adapterOrdering.notifyItemChanged(pos);
                initCounter();
            }

            @Override
            public void onRemove(int pos) {
                if (orderDetailList.get(pos).amount > 1){
                    orderDetailList.get(pos).amount = orderDetailList.get(pos).amount - 1;
                    adapterOrdering.notifyItemChanged(pos);
                }else {
                    orderDetailList.remove(pos);
                    adapterOrdering.notifyDataSetChanged();
                }
                initCounter();
            }
        });
        recyclerView.setAdapter(adapterOrdering);
        recyclerView.setHasFixedSize(true);
    }

    private void initDatabase() {
        db = App.getDatabase();
        dao_savedorder = db.savedOrderDao();
        dao_detailorder = db.detailOrderDao();
    }

    private void setTime() {
        houre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PersianCalendar now = new PersianCalendar();
                TimePickerDialog tpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                        String time = hourOfDay+":"+minute;
                        txt_time.setText(time);
                    }
                },
                now.get(PersianCalendar.HOUR_OF_DAY),
                now.get(PersianCalendar.MINUTE),
                true);
                tpd.show(getFragmentManager(),"tpd");
            }
        });
    }


    private void setDefultCalender() {
        PersianCalendar now = new PersianCalendar();
        String currentDate = now.getPersianShortDate();
        txt_calender.setText(currentDate);
    }

    private void setCalender() {
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picker = new PersianDatePickerDialog(ActivityOrdering.this)
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setMinYear(1300)
                        .setMaxYear(1450)
                        .setMaxMonth(12)
                        .setMaxDay(31)
//                        .setBackgroundColor(Color.RED)
                        .setInitDate(PersianDatePickerDialog.THIS_DAY, PersianDatePickerDialog.THIS_MONTH, PersianDatePickerDialog.THIS_DAY)
                        .setActionTextColor(Color.GRAY)
                        .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                        .setShowInBottomSheet(true)
                        .setListener(new PersianPickerListener() {
                            @Override
                            public void onDateSelected(PersianPickerDate persianPickerDate) {
                                Log.d(TAG, "onDateSelected: " + persianPickerDate.getTimestamp());//675930448000
                                Log.d(TAG, "onDateSelected: " + persianPickerDate.getGregorianDate());//Mon Jun 03 10:57:28 GMT+04:30 1991
                                Log.d(TAG, "onDateSelected: " + persianPickerDate.getPersianLongDate());// دوشنبه  13  خرداد  1370
                                Log.d(TAG, "onDateSelected: " + persianPickerDate.getPersianMonthName());//خرداد
                                Log.d(TAG, "onDateSelected: " + PersianCalendarUtils.isPersianLeapYear(persianPickerDate.getPersianYear()));//true
                                txt_calender.setText(persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay());
                            }
                            @Override
                            public void onDismissed() {
                            }
                        });
                picker.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case 100:
                    String json_customer = data.getExtras().getString("json_customer");
                    customer = new Gson().fromJson(json_customer,Customer.class);
                    txtname.setText(customer.name);
                    txtname.setTextColor(getResources().getColor(R.color.matn));

                    break;
                case 200:
                    String json_product = data.getExtras().getString("json_product");
                    Product product = new Gson().fromJson(json_product,Product.class);
                    insertToOrderList(product);
                    break;
            }
        }
    }

    private void initID(){
        add_order = findViewById(R.id.add_order);
        box_customer = findViewById(R.id.box_custome);
        name_customer=findViewById(R.id.txtname);
        total = findViewById(R.id.total);
        save_order = findViewById(R.id.save_order);
        number_order = findViewById(R.id.text_number_of_order);
        recyclerView = findViewById(R.id.recycler_ordering);
        card_numberorder = findViewById(R.id.card_numberorder);
        txtname = findViewById(R.id.txtname);
        delete_ordering = findViewById(R.id.delete_add_order);
        cardView_pay = findViewById(R.id.cardvie_pay);
        lottieAnimationView = findViewById(R.id.lottie_shop);
        calender = findViewById(R.id.calender);
        houre = findViewById(R.id.houre);
        txt_calender = findViewById(R.id.calender_txt);
        txt_time = findViewById(R.id.time_txt);
    }

    private void initBoxCustomer(){
        box_customer.setOnClickListener(v ->{
            Intent intent = new Intent(this, ActivityCustomer.class);
            intent.putExtra("for_order", true);
            startActivityForResult(intent,100);
        });
    }

    private void initBoxProduct(){
        add_order.setOnClickListener(v ->{
            Intent intent = new Intent(this, ActivityProduct.class);
            intent.putExtra("for_order", true);
            startActivityForResult(intent,200);
        });


    }

    private void initCounter(){
        if (orderDetailList.size() > 0){
            lottieAnimationView.setVisibility(View.GONE);
            cardView_pay.setVisibility(View.VISIBLE);
            card_numberorder.setVisibility(View.VISIBLE);
            number_order.setText(orderDetailList.size()+"");
        }else {
            cardView_pay.setVisibility(View.GONE);
            card_numberorder.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.VISIBLE);
        }
        total.setText(Tools.getForamtPrice(getTotalPrice()+""));
    }

    private void insertToOrderList(Product product){
        for (int i = 0; i < orderDetailList.size(); i++) {
            if (orderDetailList.get(i).id == product.id){
                orderDetailList.get(i).amount = orderDetailList.get(i).amount + 1;
                adapterOrdering.notifyDataSetChanged();
                initCounter();
                return;
            }
        }
        orderDetailList.add(product);
        adapterOrdering.notifyDataSetChanged();
        initCounter();
    }

    private Integer getTotalPrice(){
        int p = 0;
        for (int i = 0; i < orderDetailList.size(); i++) {
            p = p + (Tools.convertToPrice(orderDetailList.get(i).price) * orderDetailList.get(i).amount);
        }
        return p;
    }

    private void delete_ordering(){
        delete_ordering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initSaveOrdeing(){
        save_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customer == null){
                    Toast.makeText(ActivityOrdering.this, "مشتری را انتخاب کنید", Toast.LENGTH_SHORT).show();
                }else {
                    dao_savedorder.insertOrder(new Order(customer.name , CODE , customer.id , 1 , total.getText()+"" , "با تمام مخلفات" , txt_time.getText().toString(), txt_calender.getText().toString()));
                    for (int i = 0; i < orderDetailList.size(); i++) {
                        dao_detailorder.insertDetailOrder(new DetailOrder(orderDetailList.get(i).name , orderDetailList.get(i).price , orderDetailList.get(i).category ,
                                orderDetailList.get(i).amount , CODE ,txt_time.getText().toString(), txt_calender.getText().toString() , orderDetailList.get(i).picture));
                    }
                    finish();
                    Toast.makeText(ActivityOrdering.this, "سفارش "+ name_customer.getText().toString() +" با موفقیت ثبت شد  ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setDefultTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String datetime = dateFormat.format(c.getTime());
        txt_time.setText(datetime);

    }
}