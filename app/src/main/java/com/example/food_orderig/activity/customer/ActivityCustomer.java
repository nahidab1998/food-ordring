package com.example.food_orderig.activity.customer;

import static android.media.CamcorderProfile.get;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.CustomerDao;
import com.example.food_orderig.adapter.AdapterCustomer;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.model.Customer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ActivityCustomer extends AppCompatActivity {

    private FloatingActionButton fab_customer;
    private CustomerDao dao_customer;
    private DatabaseHelper db;
    private AdapterCustomer adapterCustomer;
    private RecyclerView recyclerView_customer;
    private LinearLayout call;
    private TextView name,phone,address;
    private Toolbar toolbar;
    private boolean for_order = false;
    private TextView textView_customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        initDataBase();
        initID();
        set_toolBar();
        set_recyclerview();
        hideFloat();
        onClickFloat();

        if (getIntent() != null){
            for_order = getIntent().getBooleanExtra("for_order",false);
        }

//        recyclerView_customer.setLayoutManager(new GridLayoutManager(this,2));
    }

    private void set_toolBar() {
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.text));
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_customer, menu);
        MenuItem item = menu.findItem(R.id.searchCustomer);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setBackground(getResources().getDrawable(R.drawable.ripple_all));
        TextView searchText = (TextView) searchView.findViewById(R.id.search_src_text);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"font/iran_sans.ttf");
        searchText.setTypeface(myCustomFont);
        searchText.setTextSize(14);

        // for remove icon hint
        EditText searchEdit = ((EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        searchEdit.setTextColor(getResources().getColor(R.color.text));
        searchEdit.setHintTextColor(getResources().getColor(R.color.text));
        searchEdit.setHint("نام مشتری را وارد کنید...");

        // for underline
//        View v = searchView.findViewById(androidx.appcompat.R.id.search_plate);
//        v.setBackgroundColor(Color.parseColor("#f15c41"));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterCustomer.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void onClickFloat() {
        fab_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent add_new_customer = new Intent(ActivityCustomer.this,ActivityAddOrEditCostomer.class);
                startActivity(add_new_customer);
            }
        });
    }

    private void hideFloat() {
        recyclerView_customer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy >0 ){
                    fab_customer.hide();

                }else {

                    fab_customer.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initID() {
        name =findViewById(R.id.name_customer);
        phone =findViewById(R.id.phone_customer);
        address = findViewById(R.id.address_customer);
        fab_customer=findViewById(R.id.fab_customer);
        call = findViewById(R.id.call);
        recyclerView_customer = findViewById(R.id.recycle_customer);
        toolbar = findViewById(R.id.toolbar_customer);
//        textView_customer =findViewById(R.id.txt_customer);
    }

    private void initDataBase() {
        db = App.getDatabase();
        dao_customer = db.customerDao();
    }

    public void set_recyclerview(){
        recyclerView_customer.setHasFixedSize(true);
        recyclerView_customer.setLayoutManager(new LinearLayoutManager(this));
        adapterCustomer = new AdapterCustomer(new ArrayList<>(), this, new AdapterCustomer.Listener(){
            @Override
            public void onClickListener(Customer customer, int pos , String name) {
                if (for_order){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("json_customer", new Gson().toJson(customer));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }else {
                    adapterCustomer.showBottomSheetDialogclick(pos , name , customer.id);

                }
            }
        } , ActivityCustomer.this);
        recyclerView_customer.setAdapter(adapterCustomer);
//        Collections.reverse(arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView_customer.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapterCustomer != null){
            adapterCustomer.addList(dao_customer.getList());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (db != null) db.close();
    }

//    private void setCont(){
//        if (adapterCustomer != null) {
//            if(dao_customer.getList().size() < 0 ) {
//                textView_customer.setVisibility(View.VISIBLE);
//            }else {
//                textView_customer.setVisibility(View.GONE);
//                adapterCustomer.addList(dao_customer.getList());
//            }
//        }
//    }


}