package com.example.food_orderig.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;
import com.example.food_orderig.model.Grouping;

import java.util.List;

public class AdapterGrouping extends RecyclerView.Adapter<AdapterGrouping.Viewholder> {

    List<Grouping> list;
    Context context;

    public AdapterGrouping(List<Grouping> list , Context context ){
        this.list = list;
        this.context = context;

    }

    @Override
    public Viewholder onCreateViewHolder( ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =LayoutInflater.from(parent. getContext());
        View view = layoutInflater.inflate(R.layout.list_grouping,parent,false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {

        Grouping grouping = list . get(position);
        holder.textView_showname_grouping.setText(grouping.name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView textView_showname_grouping;
        ImageView imageView_grouping;

        public Viewholder(View itemView) {
            super(itemView);

            textView_showname_grouping=itemView.findViewById(R.id.name_grouping);
            imageView_grouping=itemView.findViewById(R.id.img_grouping);

        }
    }
}
