package com.aibabel.weather.custom.removeitemrecyclerview;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.weather.R;


public class MyViewHolder extends RecyclerView.ViewHolder {
    public ConstraintLayout cl_item_content;
    public TextView tv_delete;
    public TextView tv_time;
    public TextView tv_city;
    public TextView tv_wendu;
    public LinearLayout ll_item;

    public MyViewHolder(View itemView) {
        super(itemView);
        cl_item_content = itemView.findViewById(R.id.cl_item_content);
        tv_delete = itemView.findViewById(R.id.tv_delete);
        tv_time = itemView.findViewById(R.id.tv_time);
        tv_delete = itemView.findViewById(R.id.tv_delete);
        tv_city = itemView.findViewById(R.id.tv_city);
        tv_wendu = itemView.findViewById(R.id.tv_wendu);
        ll_item = itemView.findViewById(R.id.ll_item);
    }
}
