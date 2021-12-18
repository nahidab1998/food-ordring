package com.example.food_orderig.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.food_orderig.R;
import com.example.food_orderig.activity.grouping.ActivityAddOrEditGrouping;
import com.example.food_orderig.database.DatabaseHelper;
import com.example.food_orderig.database.dao.GroupingDao;
import com.example.food_orderig.helper.App;
import com.example.food_orderig.model.Grouping;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

public class AdapterGrouping extends RecyclerView.Adapter<AdapterGrouping.ViewholderGrouping> {

    List<Grouping> list;
    Context context;

    LinearLayout edite_grouping , delete_grouping;

    DatabaseHelper database;
    GroupingDao dao;
    Grouping grouping;

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
    public void onBindViewHolder(ViewholderGrouping holder, @SuppressLint("RecyclerView") int position) {

        grouping = list . get(position);

        holder.textView_showname_grouping.setText(grouping.name);
        holder.imageView_grouping.setImageURI(Uri.parse(grouping.picture));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBottomSheetDialogclick(position);
            }
        });

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

    private void showBottomSheetDialogclick (int pos) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.btnsheet_deleteedite);

        grouping = list . get(pos);

        delete_grouping = bottomSheetDialog.findViewById(R.id.delerebtn);
        delete_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("حذف")
                        .setMessage("آیا از حذف کامل این دسته بندی اطمینان دارید؟")
                        .setPositiveButton("تأیید", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                database = App.getDatabase();
                                dao = database.groupingDao();
                                dao.deleteGrouping(grouping);
                                list.remove(pos);
                                notifyItemRemoved(pos);
                                notifyItemRangeChanged(pos,list.size());
                                notifyDataSetChanged();
                                Toast.makeText(context, grouping.name +" با موفقیت حذف شد ", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                bottomSheetDialog.dismiss();
                            }
                        })
                        .create()
                        .show();
                bottomSheetDialog.dismiss();

            }
        });

        edite_grouping = bottomSheetDialog.findViewById(R.id.editbtn);
        edite_grouping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityAddOrEditGrouping.class);
                intent.putExtra("Grouping",new Gson().toJson(list.get(pos)));
                context.startActivity(intent);
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }


    public void addList(List<Grouping> arrayList){
        list.clear();
        list.addAll(arrayList);
        notifyDataSetChanged();
    }
}
