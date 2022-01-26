package com.example.food_orderig.activity.orderingdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.food_orderig.R;
import com.example.food_orderig.adapter.AdaptersavedOrdering;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.SavedOrderDao;
import com.example.food_orderig.helper.App;

public class ActivityOrderingDetails extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdaptersavedOrdering adaptersavedOrdering;
    private SavedOrderDao dao;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_details);

        initDatabase();
        initId();
        initRecycler();
        set_toolbar();
    }

    private void set_toolbar() {
        toolbar = findViewById(R.id.toolbar_ordering);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.text));
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_ordering, menu);
        MenuItem item = menu.findItem(R.id.searchOrdering);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setBackground(getResources().getDrawable(R.drawable.ripple_all));

        TextView searchText = (TextView) searchView.findViewById(R.id.search_src_text);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "font/iran_sans.ttf");
        searchText.setTypeface(myCustomFont);
        searchText.setTextSize(14);

        // for remove icon hint
        EditText searchEdit = ((EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        searchEdit.setTextColor(getResources().getColor(R.color.text));
        searchEdit.setHintTextColor(getResources().getColor(R.color.text));
        searchEdit.setHint("");

//        View v = searchView.findViewById(androidx.appcompat.R.id.search_plate);
//        v.setBackgroundColor(Color.parseColor("#ef4224"));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptersavedOrdering.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void initDatabase(){
        dao = App.getDatabase().savedOrderDao();
    }

    private void initId(){
        recyclerView = findViewById(R.id.recycle_saved);
        toolbar = findViewById(R.id.toolbar_ordering);
    }

    private void initRecycler(){
        recyclerView.setHasFixedSize(true);
        adaptersavedOrdering = new AdaptersavedOrdering(this, dao.getOrderListDate("1400/10/21"));
        recyclerView.setAdapter(adaptersavedOrdering);
    }

    @Override
    protected void onResume() {

        super.onResume();
        if (adaptersavedOrdering != null){
            adaptersavedOrdering.addList(dao.getOrderList());
        }
    }
}