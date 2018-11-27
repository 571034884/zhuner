package com.aibabel.ocr.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aibabel.ocr.R;
import com.aibabel.ocr.bean.LanBean;

import java.util.List;

public class Adapter_Language extends MyBaseAdapter<LanBean,ListView> {
    public Adapter_Language(Context context, List<LanBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LanBean bean = list.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_language, null);
        }

        TextView tv_name_zh = ViewHolder.get(convertView, R.id.tv_name_zh);
        TextView tv_name_local = ViewHolder.get(convertView, R.id.tv_name_local);
        ImageView iv_choice = ViewHolder.get(convertView, R.id.iv_choice);

        tv_name_zh.setText(bean.getName());
        tv_name_local.setText(bean.getName_local());
        if(bean.getChoice()==1){
            iv_choice.setImageResource(R.mipmap.select);
        }else{
            iv_choice.setImageBitmap(null);
        }

        return convertView;
    }
}
