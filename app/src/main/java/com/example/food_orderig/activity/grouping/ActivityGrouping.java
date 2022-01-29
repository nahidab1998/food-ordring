package com.example.food_orderig.activity.grouping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.food_orderig.R;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.GroupingDao;
import com.example.food_orderig.adapter.AdapterGrouping;
import com.example.food_orderig.helper.App;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ActivityGrouping extends AppCompatActivity {

    private ImageView imageView;
    private FloatingActionButton fab_grouping;
    private GroupingDao dao_grouping;
    private DatabaseHelper db;
    private AdapterGrouping adapterGrouping;
    private RecyclerView recyclerView_grouping;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouping);

        initDataBase();
        initID();
        hideFloat();
        onClickFloat();
        set_toolbar();

        adapterGrouping = new AdapterGrouping( new ArrayList<>(), this );
        recyclerView_grouping.setAdapter(adapterGrouping);

    }

    private void set_toolbar() {
        toolbar = findViewById(R.id.toolbar_grouping);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.text));
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_grouping, menu);
        MenuItem item = menu.findItem(R.id.searchGrouping);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setBackground(getResources().getDrawable(R.drawable.ripple_all));

        TextView searchText = (TextView) searchView.findViewById(R.id.search_src_text);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "font/iran_sans.ttf");
        searchText.setTypeface(myCustomFont);
        searchText.setTextSize(14);
//        searchView.setQueryHint("نام دسته بندی را وارد کنید...");

        // for remove icon hint
        EditText searchEdit = ((EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        searchEdit.setTextColor(getResources().getColor(R.color.text));
        searchEdit.setHintTextColor(getResources().getColor(R.color.text));
        searchEdit.setHint("نام دسته بندی را وارد کنید...");

//        View v = searchView.findViewById(androidx.appcompat.R.id.search_plate);
//        v.setBackgroundColor(Color.parseColor("#ef4224"));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterGrouping.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private void onClickFloat() {

        fab_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_new_grouping = new Intent(ActivityGrouping.this, ActivityAddOrEditGrouping.class);
                startActivity(add_new_grouping);
            }
        });
    }

    private void hideFloat() {
        recyclerView_grouping.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (dy >0 ){

                    fab_grouping.hide();

                }else {

                    fab_grouping.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }

        });
    }

    private void initID() {

        imageView=findViewById(R.id.img_grouping);
        fab_grouping =findViewById(R.id.fab_grouping);
        recyclerView_grouping = findViewById(R.id.recycle_grouping);
        toolbar = findViewById(R.id.toolbar_grouping);
    }

    private void initDataBase() {
        db= App.getDatabase();
        dao_grouping = db.groupingDao();
    }

    @Override
    protected void onResume() {

        super.onResume();
        if (adapterGrouping != null){
            adapterGrouping.addList(dao_grouping.getList());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (db != null) db.close();
    }
}