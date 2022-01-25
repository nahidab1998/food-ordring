package com.example.food_orderig.activity.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.product.mvp.ProductModel;
import com.example.food_orderig.activity.product.mvp.ProductView;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.GroupingDao;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.adapter.AdapterGroupingProduct;
import com.example.food_orderig.adapter.AdapterProduct;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.model.Grouping;
import com.example.food_orderig.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ActivityProduct extends AppCompatActivity implements ProductView {


    private RecyclerView recyclerView_product;
    private RecyclerView recyclerView_category;
    private FloatingActionButton floatingActionButton_product;
    private ProductDao dao_product;
    private GroupingDao dao_grouping;
    private DatabaseHelper db;
    private AdapterProduct adapterProduct = null;
    private AdapterGroupingProduct adapterGroupingProduct;
    private String category;
    private LinearLayout lotieProduct;
    private Boolean for_order = false ;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initDataabase();
        initID();
        set_toolBar();
        set_recycle_grouping_product();
        set_RecyclerView_product();
        hideFloat();
        onClickFloat();

        if(getIntent().getExtras() != null){
            for_order = getIntent().getBooleanExtra("for_order",false);
        }
    }

    private void initDataabase(){

        db= App.getDatabase();
        dao_product= db.productDao();
        dao_grouping = db.groupingDao();
    }

    private void initID() {
        floatingActionButton_product = findViewById(R.id.fab_product);
        lotieProduct = findViewById(R.id.lottie_product);
        recyclerView_product=findViewById(R.id.recycle_product);
        recyclerView_category =findViewById(R.id.recycler_grouping_product);
        toolbar = findViewById(R.id.toolbar_product);
    }

    private void onClickFloat() {

        floatingActionButton_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_new_product = new Intent(ActivityProduct.this, ActivityAddOrEditProduct.class);
                startActivity(add_new_product);
            }
        });
    }

    private void hideFloat(){
        recyclerView_product.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy >0 ){
                    floatingActionButton_product.hide();
                }else {
                    floatingActionButton_product.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }

        });
    }


    public void set_toolBar(){
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.text));
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_product, menu);
        MenuItem item = menu.findItem(R.id.searchProduct);
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
        searchEdit.setHint("");

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
                adapterProduct.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListProduct();
        set_recycle_grouping_product();
    }

    public  void set_RecyclerView_product(){

        recyclerView_product.setHasFixedSize(true);
        adapterProduct = new AdapterProduct(new ArrayList<>(), this, new AdapterProduct.Listener() {
            @Override
            public void onClick(Product product , int pos) {

                if(for_order){
                    for_order = getIntent().getBooleanExtra("for_order",false);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("json_product", new Gson().toJson(product));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }else {

                    adapterProduct.showBottomSheetDialogclick(pos);
                }
            }
        });
        recyclerView_product.setAdapter(adapterProduct);
        adapterProduct.notifyDataSetChanged();
    }

    public void set_recycle_grouping_product(){

//        if(dao_product.getList().size() > 0 ) {
            ArrayList<Grouping> groupingArrayList = new ArrayList<>();
            groupingArrayList.add(0,new Grouping("همه محصولات",""));
            groupingArrayList.addAll(dao_grouping.getList());

            recyclerView_category.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this ,LinearLayoutManager.HORIZONTAL , false);
            recyclerView_category.setLayoutManager(layoutManager);
            adapterGroupingProduct = new AdapterGroupingProduct(groupingArrayList, this, new AdapterGroupingProduct.Listener() {
                @Override
                public void onClick(int pos, Grouping c) {
                    if (pos == 0){

                        category = null;

                    }else {
                        category = c.name;

                    }
                    initListProduct();
                }
            });
            recyclerView_category.setAdapter(adapterGroupingProduct);
//        }
    }

    private void initListProduct(){
        Log.e("qqqq", "initListProduct: " + category);
        if(adapterProduct != null){
            if (category == null || category.isEmpty()){
                if (dao_product.getList().size() <= 0){
                    lotieProduct.setVisibility(View.VISIBLE);
                }else {
                    adapterProduct.addList(dao_product.getList());
                }
            }else {
                if (dao_product.getListByCategory(category).size() == 0){
                    lotieProduct.setVisibility(View.VISIBLE);
                }else {
                    adapterProduct.addList(dao_product.getListByCategory(category));
                }
            }
        }
    }

    @Override
    public void setData(String name) {
//        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (db != null) db.close();
    }

}