package com.aibabel.alliedclock.custom.removeitemrecyclerview;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.aibabel.alliedclock.R;


public class MyViewHolder extends RecyclerView.ViewHolder {
    public ConstraintLayout cl_item_content;
    public TextView tv_delete;
    public TextView tv_relative_time;
    public TextView tv_city;
//    public TextView tv_time;
//    public TextView tv_time_standard;
    public TextClock tc_time;
    public ImageView iv_dingwei;
    public LinearLayout ll_item;

    public MyViewHolder(View itemView) {
        super(itemView);
        cl_item_content = itemView.findViewById(R.id.cl_item_content);
        ll_item = itemView.findViewById(R.id.ll_item);
        tv_delete = itemView.findViewById(R.id.tv_delete);
        tv_relative_time = itemView.findViewById(R.id.tv_relative_time);
        tv_city = itemView.findViewById(R.id.tv_city);
//        tv_time = itemView.findViewById(R.id.tv_time);
//        tv_time_standard = itemView.findViewById(R.id.tv_time_standard);
        tc_time = itemView.findViewById(R.id.tc_time);
        iv_dingwei = itemView.findViewById(R.id.iv_dingwei);
    }
}
