package com.example.food_orderig.activity.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.product.mvp.ProductModel;
import com.example.food_orderig.activity.product.mvp.ProductView;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.ProductDao;
import com.example.food_orderig.helper.AdapterProduct;
import com.example.food_orderig.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ActivityProduct extends AppCompatActivity implements ProductView {

    ProductModel presenter;
    RecyclerView recyclerView_product;
    FloatingActionButton floatingActionButton_product;
    ProductDao dao_product;
    DatabaseHelper db;
    AdapterProduct adapterProduct;
    Toolbar toolbardel_pro;
    ImageButton btnback;
    TextView texttoolbar;
    CardView toolbarmain_pro;
    CheckBox checkBox;
    ArrayList<Product> list = new ArrayList<>();
    List<Product> selectionlist = new ArrayList<>();
    int counter = 0;
    public boolean isActionMode = false;
    public int position = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        recyclerView_product=findViewById(R.id.recycle_product);

        presenter = new ProductModel(this);
        presenter.getData();

        toolbardel_pro = findViewById(R.id.toolbardel_pro);
        toolbardel_pro.setVisibility(View.GONE);

        texttoolbar = findViewById(R.id.text_toolbar);

        btnback = findViewById(R.id.btn_back);
        checkBox = findViewById(R.id.iv_check_Box);
        btnback.setVisibility(View.GONE);
        toolbarmain_pro = findViewById(R.id.toolbarmain_product);


        db= DatabaseHelper.getInstance(getApplicationContext());
        dao_product= db.productDao();
        adapterProduct = new AdapterProduct( new ArrayList<>(), this);
        recyclerView_product.setAdapter(adapterProduct);

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

        floatingActionButton_product = findViewById(R.id.fab_product);
        floatingActionButton_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearActionMode();
                Intent add_new_product = new Intent(ActivityProduct.this, ActivityAddOrEditProduct.class);
                startActivity(add_new_product);
            }
        });

        btnback.setOnClickListener(v -> {

            clearActionMode();

        });

    }

    private void clearActionMode(){
        isActionMode = false;
        toolbardel_pro.setVisibility(View.GONE);
        texttoolbar.setText("0 item selected");
        btnback.setVisibility(View.GONE);
        counter = 0;
        selectionlist.clear();
        toolbardel_pro.getMenu().clear();
        adapterProduct.notifyDataSetChanged();
    }

    public void startSelection(int index){
        if (!isActionMode){
            isActionMode = true;
            selectionlist.add(dao_product.getList().get(index));
            counter ++;
            updatetoolbartext(counter);
            toolbardel_pro.setVisibility(View.VISIBLE);
            texttoolbar.setVisibility(View.VISIBLE);
//            toolbarmain_pro.setVisibility(View.GONE);
            btnback.setVisibility(View.VISIBLE);

            toolbardel_pro.inflateMenu(R.menu.menu);
            position = index;
            adapterProduct.notifyDataSetChanged();
        }
    }

    public void  check ( View v , int index ){
        if (((CheckBox) v ). isChecked ()){
            selectionlist.add(dao_product.getList().get(index));
            counter ++ ;
            updatetoolbartext(counter);
        }else {
            selectionlist.remove(dao_product.getList().get(index));
            counter -- ;
            updatetoolbartext(counter);
        }
    }

    private void updatetoolbartext(int counter) {
        if (counter==0){
            texttoolbar.setText("0 item selected");

        }
        if (counter==1){
            texttoolbar.setText("1 item selected");

        }
        else{
            texttoolbar.setText(counter + "item selected");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if ( item.getItemId() == R.id.menu_delete && selectionlist.size() > 0 ) {

//            Toast.makeText(ActivityProduct.this, "hello", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("delete" + selectionlist.size() + " items?");
            builder.setTitle("Confirm");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    adapterProduct.delete(adapterProduct.poooo);
                    updatetoolbartext(0);
                    clearActionMode();

                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    finish();
                }
            });
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setData(String name) {
//        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapterProduct != null){
            adapterProduct.addList(dao_product.getList());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null) db.close();
    }

}