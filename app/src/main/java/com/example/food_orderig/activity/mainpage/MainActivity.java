package com.example.food_orderig.activity.mainpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.food_orderig.R;
import com.example.food_orderig.activity.customer.ActivityCustomer;
import com.example.food_orderig.activity.grouping.ActivityGrouping;
import com.example.food_orderig.activity.ordering.ActivityOrdering;
import com.example.food_orderig.activity.orderingdetails.ActivityOrderingDetails;
import com.example.food_orderig.activity.product.ActivityProduct;
import com.example.food_orderig.activity.settings.ActivitySettings;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.CustomerDao;
import com.example.food_orderig.database.dao.GroupingDao;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.adapter.AdapterProduct;
import com.example.food_orderig.database.dao.SavedOrderDao;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.helper.Tools;
import com.example.food_orderig.model.ChartModel;
import com.example.food_orderig.model.Grouping;
import com.example.food_orderig.model.Order;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CardView cardViewproduct,cardViewcustomer , cardViewprouping;
    private ImageView add_shop , munu;
    private TextView number_product, number_customer , number_groping , month ,week ,day , txt_name_restaurant , dayName , monthName , totall;
    private AdapterProduct adapterProduct;
    private DatabaseHelper db;
    private ProductDao dao_product;
    private GroupingDao dao_grouping;
    private CustomerDao dao_customer;
    private SavedOrderDao dao_savedorder;
    private int count_product;
    private int count_customer;
    private int count_grouping;
    private CardView saved;
    private CombinedChart combinedChart;
    private ArrayList<ChartModel> chartModels;
    private String date = String.valueOf(System.currentTimeMillis()- 604800000);
    private LottieAnimationView lottie;
    private BarChart barChart;
    private static final int STORAGE_PERMISSION_CODE = 101;
    public static final int CALL_PERMISSION_CODE = 100;
    private DrawerLayout mydrawer;
    private LinearLayout linearLayout_setting , linearLayout_about , linearLayout_guid , linearLayout_signup ;
    private TextView close_about , close_guid;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatabase();
        initID();
        initSetName();
        initIntents();
        setNavigationDrawer();
        checkPermission();
        setLinear_setting();
        setLinear_about();
        setLinear_guid();
        setLinear_signup();
    }

    private void setLinear_signup() {
        linearLayout_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }

    private void setLinear_guid() {

        linearLayout_guid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog_guid = new Dialog(MainActivity.this);
                dialog_guid.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_guid.setContentView(R.layout.dialog_guid);

                close_guid = dialog_guid.findViewById(R.id.close_dialog_guid);
                mydrawer.closeDrawer(GravityCompat.END);
                dialog_guid.show();
                dialog_guid.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog_guid.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                close_guid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_guid.dismiss();
                    }
                });

            }
        });

    }

    private void setLinear_about() {

        linearLayout_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog_about = new Dialog(MainActivity.this);
                dialog_about.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_about.setContentView(R.layout.dialog_about);

                close_about = dialog_about.findViewById(R.id.close_dialog_about);
                mydrawer.closeDrawer(GravityCompat.END);
                dialog_about.show();
                dialog_about.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog_about.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                close_about.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_about.dismiss();
                    }
                });

            }
        });

    }

    private void setLinear_setting() {

        linearLayout_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , ActivitySettings.class);
                startActivity(intent);
                mydrawer.closeDrawer(GravityCompat.END);
            }
        });
    }

    private void setNavigationDrawer() {

        munu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mydrawer.openDrawer((int) GravityCompat.END);
            }
        });

    }


    @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == STORAGE_PERMISSION_CODE ) {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "مجوز دوربین داده شد", Toast.LENGTH_SHORT).show();

                }
            }
        }

        public Boolean checkPermission() {
            String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE};
            int requestCode = STORAGE_PERMISSION_CODE;
            int requestCode_call = CALL_PERMISSION_CODE;
            if (ContextCompat.checkSelfPermission(this, permission[0]) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[] { permission[0] }, requestCode);
            }else if (ContextCompat.checkSelfPermission(this, permission[1]) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{permission[1]}, requestCode);
            }else if (ContextCompat.checkSelfPermission(this, permission[2]) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{permission[2]}, requestCode_call);
            }
            else {
                return true;
            }
            return false;
        }


    private void initIntents() {
        cardViewproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, ActivityProduct.class);
                startActivity(i);
            }
        });

        cardViewcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent q= new Intent(MainActivity.this, ActivityCustomer.class);
                startActivity(q);
            }
        });
        cardViewprouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r= new Intent(MainActivity.this, ActivityGrouping.class);
                startActivity(r);
            }
        });

        add_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a= new Intent(MainActivity.this, ActivityOrdering.class);
                startActivity(a);
            }
        });

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityOrderingDetails.class);
                startActivity(intent);
            }
        });
    }

    private void initSetName() {

        Intent intent = getIntent();
        String a = intent.getStringExtra("name_restaurant");
        txt_name_restaurant.setText(a);
    }

    private void initDatabase() {
        db = App.getDatabase();
        dao_product = db.productDao();
        dao_customer = db.customerDao();
        dao_grouping = db.groupingDao();
        dao_savedorder = db.savedOrderDao();
    }

    private void initID() {
        cardViewproduct=findViewById(R.id.products);
        cardViewcustomer=findViewById(R.id.customer);
        cardViewprouping=findViewById(R.id.grouping);
        saved = findViewById(R.id.saved);
        month = findViewById(R.id.month_profit);
        week = findViewById(R.id.week_profit);
        day = findViewById(R.id.today_profit);
        totall = findViewById(R.id.total_profit1);
        add_shop = findViewById(R.id.add_shop);
        number_product = findViewById(R.id.number_of_product);
        number_customer = findViewById(R.id.number_of_customer);
        number_groping = findViewById(R.id.number_of_grouping);
        txt_name_restaurant = findViewById(R.id.name_restaurant);
        dayName = findViewById(R.id.day_name);
        monthName = findViewById(R.id.month_name);
        lottie = findViewById(R.id.lottie_chart);
        barChart = findViewById(R.id.chart_bar);
        munu = findViewById(R.id.munu);
        mydrawer = findViewById(R.id.mydrawer);
        linearLayout_setting = findViewById(R.id.setting_nav);
        linearLayout_about = findViewById(R.id.about_nav);
        linearLayout_guid = findViewById(R.id.guid_nav);
        linearLayout_signup = findViewById(R.id.signup_nav);



    }

    public void countsizeRecycler(){

        count_product = dao_product.getList().size();
        if (count_product == 0){
            number_product.setText("");
        }else {
            number_product.setText(Integer.toString(count_product));
        }

        count_customer= dao_customer.getList().size();
        if (count_customer == 0) {
            number_customer.setText("");
        }else {
            number_customer.setText(Integer.toString(count_customer));
        }

        count_grouping = dao_grouping.getList().size();
        if (count_grouping == 0){
            number_groping.setText("");
        }else {

            number_groping.setText(Integer.toString(count_grouping));
        }
    }

    public void setDailyIncome(){

        List<String> dailyIncome =new ArrayList<>();
        dailyIncome.addAll(dao_savedorder.alldate());
        int j = 0;
        for (int i=0 ; i< dao_savedorder.getOrderList().size() ; i++){
            String a = dailyIncome.get(i);
            j = j + Tools.convertToPrice(a);
        }
        if ( j == 0){
            day.setText("_");
        }else {
            day.setText(Tools.getForamtPrice(String.valueOf(j)));
        }
    }

    private void setWeeklyIncome(){
        List<Order> weeklyIncome = new ArrayList<>();
        weeklyIncome.addAll(dao_savedorder.getOrderListDate(Tools.dayAgo()));
        int j = 0 ;
        for (int i = 0 ; i<weeklyIncome.size() ; i++){
            String a = weeklyIncome.get(i).total;
            j = j + Tools.convertToPrice(a);
        }
        if (j == 0 ){
            week.setText("_");
        }else {
            week.setText(Tools.getForamtPrice(String.valueOf(j)));
        }
    }

    private void setMonthIncome(){
        List<Order> monthIncome = new ArrayList<>();
        monthIncome.addAll(dao_savedorder.getOrderListDate(Tools.getThirtyDaysAgo()));
        int j = 0;
        for (int i = 0; i < monthIncome.size(); i++) {
            String aa = monthIncome.get(i).total;
            j = j + Tools.convertToPrice(aa);
        }
        if ( j == 0){
            month.setText("_");
        }else {
            month.setText(Tools.getForamtPrice(String.valueOf(j)));
        }
    }

    private void setNameDayMonth(){
        dayName.setText("( " + Tools.getDayName()+ " )");
        monthName.setText("( " + Tools.getMonthName() + " )");
    }

    private void setTotalIncome(){

        List<String> totalIncome = new ArrayList<>();
        totalIncome.addAll(dao_savedorder.getAllTotal());
        int t = 0;
        for (int i = 0; i < totalIncome.size(); i++) {
            String aaa = totalIncome.get(i);
            t = t + Tools.convertToPrice(aaa);
        }
        if (t == 0){
            totall.setText("_");
        }else {
            totall.setText(Tools.getForamtPrice(String.valueOf(t)));
        }

    }

    private void populateChart(){
        Log.e("qqqqmain", "populateChart: started" );
        ArrayList<Order> list = new ArrayList<>(dao_savedorder.getOrderList());
        chartModels = new ArrayList<>();

        Log.e("qqqqmain", "populateChart: "+list.size() + "-"+ chartModels.size() );

        for (int i = 0; i < list.size(); i++) {

            Log.e("qqqqmain2", "populateChart: for list is started \n"
                    + list.get(i).date
            );

            if (chartModels.size() == 0){
                Log.e("qqqqmain", "populateChart: chartModels.size() == 0" );
                chartModels.add(new ChartModel(list.get(i).date,Tools.convertToPrice(list.get(i).total)));
//                lottie.setVisibility(View.GONE);
//                barChart.setVisibility(View.VISIBLE);

            }else {
                Log.e("qqqqmain", "populateChart: chartModels.size() > 0" );

                for (int c = 0; c < chartModels.size(); c++) {
                    Log.e("qqqqmain2", "populateChart: for chart model is started\n"
                            + chartModels.get(c).getDate() );

                    if (list.get(i).date.equals(chartModels.get(c).getDate())){
                        chartModels.get(c).setTotal(chartModels.get(c).getTotal() + Tools.convertToPrice(list.get(i).total));
//                        return;
                    }else {
                        chartModels.add(new ChartModel(list.get(i).date,Tools.convertToPrice(list.get(i).total)));
                    }
                }
            }
        }
    }

    public void create_chart22() {
        BarChart bar_chart = findViewById(R.id.chart_bar);
        ArrayList<BarEntry> visitor = new ArrayList<>();

        for (int i = 0; i < chartModels.size(); i++) {
            visitor.add(new BarEntry( i+1, (int) chartModels.get(i).getTotal() ));
        }

        BarDataSet barDataSet = new BarDataSet(visitor, "");
        barDataSet.setColors(Color.rgb(241, 92, 65));
        barDataSet.setValueTextSize(0f);

        BarData barData = new BarData(barDataSet);
        bar_chart.setFitBars(true);
        bar_chart.setData(barData);
        bar_chart.getDescription().setText("");
        bar_chart.animateY(1000);

        XAxis xAxis = bar_chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
    }

    private void sessionchart(){
        if (dao_savedorder.getOrderList().size() >= 1){
            int count = dao_savedorder.getOrderList().size();
            lottie.setVisibility(View.GONE);
            barChart.setVisibility(View.VISIBLE);
        }else {
            lottie.setVisibility(View.VISIBLE);
            barChart.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {        // to prevent irritating accidental logouts
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "برای خروج دکمه بازگشت را دوباره بزنید", Toast.LENGTH_SHORT).show();
            mydrawer.closeDrawer(GravityCompat.END);
        } else {    // this guy is serious
            // clean up
            super.onBackPressed();       // bye
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        countsizeRecycler();
        setDailyIncome();
        setWeeklyIncome();
        setMonthIncome();
        setTotalIncome();
        setNameDayMonth();
        populateChart();
        create_chart22();
        sessionchart();
    }

}