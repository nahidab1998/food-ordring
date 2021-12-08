package com.example.food_orderig.helper;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.food_orderig.database.DatabaseHelper;

public class App extends Application {

    public static DatabaseHelper db;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        db = DatabaseHelper.getInstance(context);
        Log.e("qqqqapp", "onCreate: app" );
    }

    public static DatabaseHelper getDatabase(){
        if (!db.isOpen() || db == null){
            db = DatabaseHelper.getInstance(context);
        }
        return db;
    }

}
