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
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.LinkMovementMethod;
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
import com.uncopt.android.widget.text.justify.JustifiedTextView;

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
    private JustifiedTextView textView1 , textView2 , textView3 , textView4 , textView5 , textView6 , textView7 , textView8;
    private ImageView email , telegram , instagram ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatabase();
        initID();
        initSetName();
        initIntents();
        setNavigationDrawer();
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
                textView5 = dialog_guid.findViewById(R.id.txt5);
                textView6 = dialog_guid.findViewById(R.id.txt6);
                textView7 = dialog_guid.findViewById(R.id.txt7);
                textView8 = dialog_guid.findViewById(R.id.txt8);

                mydrawer.closeDrawer(GravityCompat.END);
                dialog_guid.show();
                dialog_guid.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog_guid.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView vv = (TextView)textView5.findViewById(R.id.search_src_text);
                TextView mm = (TextView)textView6.findViewById(R.id.search_src_text);
                TextView oo = (TextView)textView7.findViewById(R.id.search_src_text);
                TextView pp = (TextView)textView8.findViewById(R.id.search_src_text);

                Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "font/iran_sans.ttf");

                textView5.setTypeface(myCustomFont);
                textView6.setTypeface(myCustomFont);
                textView7.setTypeface(myCustomFont);
                textView8.setTypeface(myCustomFont);

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
                textView1 =dialog_about.findViewById(R.id.txt1);
                textView2 = dialog_about.findViewById(R.id.txt2);
                textView3 = dialog_about.findViewById(R.id.txt3);
                textView4 = dialog_about.findViewById(R.id.txt4);
                email = dialog_about.findViewById(R.id.textlink);
                telegram = dialog_about.findViewById(R.id.telegram);
                instagram = dialog_about.findViewById(R.id.instagram);

                mydrawer.closeDrawer(GravityCompat.END);
                dialog_about.show();
                dialog_about.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog_about.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView tt = (TextView)textView1.findViewById(R.id.search_src_text);
                TextView aa = (TextView)textView2.findViewById(R.id.search_src_text);
                TextView bb = (TextView)textView3.findViewById(R.id.search_src_text);
                TextView cc = (TextView)textView4.findViewById(R.id.search_src_text);
                Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "font/iran_sans.ttf");

                textView1.setTypeface(myCustomFont);
                textView2.setTypeface(myCustomFont);
                textView3.setTypeface(myCustomFont);
                textView4.setTypeface(myCustomFont);

                email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String[] TO = {"n.alibagheri1998@gmail.com"};
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setData(Uri.parse("n.alibagheri1998@gmail.com"));
                        intent.putExtra(Intent.EXTRA_EMAIL, TO);
//                        intent.putExtra(Intent.EXTRA_CC, new String[]{"n.alibagheri1998@gmail.com"});
//                        intent.putExtra(Intent.EXTRA_BCC, new String[]{"n.alibagheri1998@gmail.com"});

//                        intent.putExtra(Intent.EXTRA_SUBJECT, "hi");
//                        intent.putExtra(Intent.EXTRA_TEXT, message);

                        intent.setType("message/rfc822");
                        startActivity(Intent.createChooser(intent, "Select email"));

                    }
                });

                telegram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Semicolon_Developers"));
                        startActivity(intent);
                    }
                });

                instagram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse("http://instagram.com/_u/semicolon_developers");
                        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                        likeIng.setPackage("com.instagram.android");

                        try {
                            startActivity(likeIng);
                        } catch (ActivityNotFoundException e) {
//                            startActivity(new Intent(Intent.ACTION_VIEW,
//                                    Uri.parse("http://instagram.com/xxx")));
                        }
                    }
                });

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
        dailyIncome.addAll(dao_savedorder.dailyTotal(Tools.getCurrentDate()));
        int j = 0;
        try {
            for (int i=0 ; i< dao_savedorder.getOrderList().size() ; i++){
                String a = dailyIncome.get(i);
                j = j + Tools.convertToPrice(a);
            }

        }catch (Exception e){
            e.printStackTrace();
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
            Toast.makeText(this, "برای خروج ، دکمه بازگشت را دوباره بزنید", Toast.LENGTH_SHORT).show();
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