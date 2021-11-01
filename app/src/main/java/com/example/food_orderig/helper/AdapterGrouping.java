package com.example.food_orderig.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;
import com.example.food_orderig.model.Grouping;

import java.util.List;

public class AdapterGrouping extends RecyclerView.Adapter<AdapterGrouping.ViewholderGrouping> {

    List<Grouping> list;
    Context context;

    public AdapterGrouping(List<Grouping> list , Context context ){
        this.list = list;
        this.context = context;

    }

    @Override
    public ViewholderGrouping onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater =LayoutInflater.from(parent. getContext());
        View view = layoutInflater.inflate(R.layout.list_grouping,parent,false);
        ViewholderGrouping viewholderGrouping = new ViewholderGrouping(view);
        return viewholderGrouping;
    }

    @Override
    public void onBindViewHolder(ViewholderGrouping holder, int position) {

        Grouping grouping = list . get(position);
        holder.textView_showname_grouping.setText(grouping.name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewholderGrouping extends RecyclerView.ViewHolder {

        TextView textView_showname_grouping;
        ImageView imageView_grouping;

        public ViewholderGrouping(View itemView) {
            super(itemView);

            textView_showname_grouping=itemView.findViewById(R.id.name_grouping);
            imageView_grouping=itemView.findViewById(R.id.img_grouping);

        }
    }
}
