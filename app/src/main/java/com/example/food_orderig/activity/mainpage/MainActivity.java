package com.example.food_orderig.activity.mainpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
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
import com.github.mikephil.charting.charts.CombinedChart;
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
    private TextView number_product, number_customer , number_groping;
    private AdapterProduct adapterProduct;
    private DatabaseHelper db;
    private ProductDao dao_product;
    private GroupingDao dao_grouping;
    private CustomerDao dao_customer;
    private SavedOrderDao dao_savedorder;
    int count_product;
    int count_customer;
    int count_grouping;
    private CardView saved;
    private CombinedChart combinedChart;
    private TextView month ,week ,day ;
    private TextView txt_name_restaurant ;
    private String date = String.valueOf(System.currentTimeMillis()- 604800000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatabase();
        initID();
        initGraph();
        initSetName();

        // Intents 
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
        setdate();
    }

    private void initSetName() {

        Intent intent = getIntent();
        String a = intent.getStringExtra("name_restaurant");
        txt_name_restaurant.setText(a);
    }

    private void initGraph() {

//        LineGraphSeries<DataPoint> bgseries= new LineGraphSeries<>(new DataPoint[]{
//
//                new DataPoint(1, 0),
//                new DataPoint(2, 8000),
//                new DataPoint(3, 7000),
//                new DataPoint(4, 10000),
//        });


//        CombinedData data = new CombinedData();
//
//        data.setData(generateLineData());
//        data.setData(generateBarData());
//        data.setData(generateBubbleData());
//        data.setData(generateScatterData());
//        data.setData(generateCandleData());
//
//        combinedChart.setData(data);
//        combinedChart.invalidate();



        // styling series
//        bgseries.setTitle("Random Curve 1");
//        bgseries.setColor(Color.GREEN);
//        bgseries.setDrawDataPoints(false);
//        bgseries.setDataPointsRadius(10);
//        bgseries.setThickness(6);


        combinedChart.setBackgroundColor(getResources().getColor(android.R.color.background_light));
//        combinedChart.getViewport().setScalable(true);
//        combinedChart.getViewport().setScrollable(true);
//
//        combinedChart.setTitleColor(getResources().getColor(android.R.color.white));
////        bgseries.setThickness(6);
//        bgseries.setDrawBackground(true);
//        bgseries.setBackgroundColor(Color.rgb(248,243,247));
////        graph.getLegendRenderer().setVisible(true);
//        combinedChart.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
//        combinedChart.addSeries(bgseries);

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
        add_shop = findViewById(R.id.add_shop);
        combinedChart =findViewById(R.id.chart);
        number_product = findViewById(R.id.number_of_product);
        number_customer = findViewById(R.id.number_of_customer);
        number_groping = findViewById(R.id.number_of_grouping);
        txt_name_restaurant = findViewById(R.id.name_restaurant);
    }

    private void setdate(){
        Calendar c = Calendar.getInstance() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy ");
        String datetime = dateFormat.format(c.getTime());
//        test.setText(datetime);
    }


    public void countsizeRecycler(){

        count_product = dao_product.getList().size();
        number_product.setText(Integer.toString(count_product));

        count_customer= dao_customer.getList().size();
        number_customer.setText(Integer.toString(count_customer));

        count_grouping = dao_grouping.getList().size();
        number_groping.setText(Integer.toString(count_grouping));
    }

    public void setDailyIncome(){

        List<String> dailyIncome =new ArrayList<>();
        dailyIncome.addAll(dao_savedorder.alldate());
        int j = 0;
        for (int i=0 ; i< dao_savedorder.getOrderList().size() ; i++){
            String a = dailyIncome.get(i);
            j = j + Tools.convertToPrice(a);
        }
        day.setText(Tools.getForamtPrice(String.valueOf(j)));
    }


    @Override
    protected void onResume() {
        super.onResume();
        countsizeRecycler();
        setDailyIncome();
    }

}