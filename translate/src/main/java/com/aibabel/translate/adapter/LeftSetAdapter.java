package com.aibabel.translate.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.translate.R;
import com.aibabel.translate.utils.SharePrefUtil;

import java.util.List;


/**
 * Created by BoBo on 2015/9/18.
 */

public class LeftSetAdapter extends BaseListViewAdapter<Integer> {
    /**
     * 因为自己是父类，不知道子类传入的是什么 所以就用泛型规定
     *
     * @param list
     */
    public Context mContext;

    public LeftSetAdapter(List<Integer> list, Context context) {
        super(list);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeftViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.menuleft_slide_item, null);
            holder = new LeftViewHolder();

            holder.tv_slide_name = convertView.findViewById(R.id.tv_slide_name);
            holder.tv_slide_red = convertView.findViewById(R.id.iv_slide_red);
            convertView.setTag(holder);
        } else {
            holder = (LeftViewHolder) convertView.getTag();
        }

        if (position == 1){
            holder.tv_slide_red.setVisibility(View.VISIBLE);
            boolean type = SharePrefUtil.getBoolean(mContext,"type",false);
            if (type){
                holder.tv_slide_red.setVisibility(View.INVISIBLE);
            }
        }else{
            holder.tv_slide_red.setVisibility(View.INVISIBLE);
        }



        holder.tv_slide_name.setText(list.get(position));
        return convertView;
    }

    public class LeftViewHolder {
        private TextView tv_slide_name;
        private ImageView tv_slide_red;
    }
}
