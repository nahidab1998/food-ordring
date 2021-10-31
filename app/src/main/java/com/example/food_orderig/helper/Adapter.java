package com.example.food_orderig.helper;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;

public class Adapter extends RecyclerView.Adapter<Adapter.Viewholder> {

    int picture;
    EditText editTextnameproduct;


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView textView_showname_grouping;
        ImageView imageView_grouping;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            textView_showname_grouping=itemView.findViewById(R.id.name_grouping);
            imageView_grouping=itemView.findViewById(R.id.img_grouping);

        }
    }
}
