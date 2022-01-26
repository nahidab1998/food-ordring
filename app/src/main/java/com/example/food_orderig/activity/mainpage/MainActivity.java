package com.example.food_orderig.activity.mainpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.customer.ActivityCustomer;
import com.example.food_orderig.activity.grouping.ActivityGrouping;
import com.example.food_orderig.activity.ordering.ActivityOrdering;
import com.example.food_orderig.activity.orderingdetails.ActivityOrderingDetails;
import com.example.food_orderig.activity.product.ActivityProduct;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.CustomerDao;
import com.example.food_orderig.database.dao.GroupingDao;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.adapter.AdapterProduct;
import com.example.food_orderig.database.dao.SavedOrderDao;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.helper.Tools;
import com.example.food_orderig.model.ChartModel;
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
    private ImageView add_shop;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatabase();
        initID();
        initSetName();
        initIntents();

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
        totalIncome.addAll(dao_savedorder.alldate());
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
            visitor.add(new BarEntry( i, (int) chartModels.get(i).getTotal() ));
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
    }

}