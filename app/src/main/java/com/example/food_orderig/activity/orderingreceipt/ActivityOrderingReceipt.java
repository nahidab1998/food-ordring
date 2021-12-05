package com.example.food_orderig.activity.orderingreceipt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.food_orderig.R;
import com.example.food_orderig.adapter.AdapterReceipt;
import com.example.food_orderig.adapter.AdaptersavedOrdering;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.CustomerDao;
import com.example.food_orderig.database.dao.DetailOrderDao;
import com.example.food_orderig.database.dao.SavedOrderDao;

public class ActivityOrderingReceipt extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterReceipt adapterReceipt;

    DatabaseHelper db;
    DetailOrderDao dao_detailOrder;
    SavedOrderDao dao_savedOrderDao;
    CustomerDao dao_customerDao;
    String code, customername , total_detail;
    private int customerID;
    TextView name , phone , address , total;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_receipt);

        if(getIntent().getExtras() != null){
            code = getIntent().getStringExtra("code");
            customerID = getIntent().getIntExtra("customerid" ,0 );
            customername = getIntent().getStringExtra("name");
            total_detail = getIntent().getStringExtra("total");
        }

        initDatabase();
        initId();
        initRecycler();
        initSetCustomer();
        initSetTotal();
    }

    private void initDatabase(){

        db = DatabaseHelper.getInstance(getApplicationContext());
        dao_detailOrder = db.detailOrderDao();
        dao_savedOrderDao = db.savedOrderDao();
        dao_customerDao = db.customerDao();
    }

    private void initId(){

        name = findViewById(R.id.name_receipt);
        phone = findViewById(R.id.phone_receipt);
        address = findViewById(R.id.address_receipt);
        total = findViewById(R.id. total_receipt);
        recyclerView = findViewById(R.id.recycle_receipt);
    }

    private void initRecycler(){
        recyclerView.setHasFixedSize(true);
        adapterReceipt = new AdapterReceipt(dao_detailOrder.getSpecificOrder(code),this);
        recyclerView.setAdapter(adapterReceipt);
    }
    private  void initSetCustomer(){
        name.setText(customername);
        phone.setText(dao_customerDao.getID(customerID).phone);
        address.setText(dao_customerDao.getID(customerID).address);

    }
    private  void initSetTotal(){

        total.setText(total_detail);

    }
}