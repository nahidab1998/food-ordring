package com.example.food_orderig.activity.mainpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
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
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {

    CardView cardViewproduct,cardViewcustomer , cardViewprouping;
    ImageView add_shop;
    TextView number_product, number_customer , number_groping;
    AdapterProduct adapterProduct;
    DatabaseHelper db;
    ProductDao dao_product;
    GroupingDao dao_grouping;
    CustomerDao dao_customer;
    int count_product;
    int count_customer;
    int count_grouping;
    CardView saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardViewproduct=findViewById(R.id.products);
        cardViewcustomer=findViewById(R.id.customer);
        cardViewprouping=findViewById(R.id.grouping);
        saved = findViewById(R.id.saved);

        add_shop = findViewById(R.id.add_shop);

        db = DatabaseHelper.getInstance(getApplicationContext());

        final GraphView graph = (GraphView) findViewById(R.id.graf);
        LineGraphSeries<DataPoint> bgseries= new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 1000),
                new DataPoint(1, 8000),
                new DataPoint(2, 3500),
                new DataPoint(3, 24000),
                new DataPoint(4, 60000),
                new DataPoint(5, 1300),
                new DataPoint(6, 5300),
                new DataPoint(7, 3500),
                new DataPoint(8, 80000),
                new DataPoint(9, 3500),
                new DataPoint(10, 1300),
                new DataPoint(11, 5300),
                new DataPoint(12, 80000),
                new DataPoint(13, 3500),
                new DataPoint(14, 60000),
                new DataPoint(15, 5300),
                new DataPoint(16, 8000),
                new DataPoint(17, 1000),
                new DataPoint(18, 5300),
                new DataPoint(19, 60000),
                new DataPoint(20, 5300),
                new DataPoint(21, 1300),
                new DataPoint(22, 60000),
                new DataPoint(23, 3500),
                new DataPoint(24, 1300),
                new DataPoint(25, 1000),
                new DataPoint(26, 8000),
                new DataPoint(27, 5300),
                new DataPoint(28, 1300),
                new DataPoint(29, 60000),
                new DataPoint(30, 5300),
                new DataPoint(31, 80000),
        });

        graph.addSeries(bgseries);
        graph.setBackgroundColor(getResources().getColor(android.R.color.background_light));
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
//        graph.setTitle("نمودار رشد سرمایه");
        graph.setTitleColor(getResources().getColor(android.R.color.white));
//        bgseries.setTitle("foo");
        bgseries.setThickness(5);
        bgseries.setDrawBackground(true);
        bgseries.setColor(Color.rgb(241,92,65));
        bgseries.setBackgroundColor(Color.rgb(248,243,247));
//        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);


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

    public void count(){

        number_product = findViewById(R.id.number_of_product);
        number_customer = findViewById(R.id.number_of_customer);
        number_groping = findViewById(R.id.number_of_grouping);

        dao_product = db.productDao();
        count_product = dao_product.getList().size();
        number_product.setText(Integer.toString(count_product));

//        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_customer = db.customerDao();
        count_customer= dao_customer.getList().size();
        number_customer.setText(Integer.toString(count_customer));

//        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_grouping = db.groupingDao();
        count_grouping = dao_grouping.getList().size();
        number_groping.setText(Integer.toString(count_grouping));

    }

    @Override
    protected void onResume() {
        super.onResume();
        count();
    }

}