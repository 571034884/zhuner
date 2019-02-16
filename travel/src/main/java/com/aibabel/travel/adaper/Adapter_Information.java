package com.aibabel.travel.adaper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aibabel.travel.R;
import com.aibabel.travel.bean.PushBean;
import com.aibabel.travel.utils.CommonUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * ===================================================================================
 *
 * @作者: 张文颖
 * @创建时间:
 * @描述:
 * @修改时间:
 *
 * ====================================================================================
 */

public class Adapter_Information extends MyBaseAdapter<PushBean, ListView> {
    private Context context;


    public Adapter_Information(Context context, List<PushBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PushBean bean = list.get(position);

        if (null == convertView) {
            convertView = View.inflate(context, R.layout.item_information, null);
        }
        ImageView iv_item_spot = ViewHolder.get(convertView, R.id.iv_item_spot);
        TextView tv_item_name = ViewHolder.get(convertView, R.id.tv_item_name);
        TextView tv_item_distance = ViewHolder.get(convertView, R.id.tv_city_distance);
        TextView tv_item_count = ViewHolder.get(convertView, R.id.tv_item_count);


        int count = bean.getCount();

        tv_item_name.setText(bean.getPoi_name_cn() + "");
        tv_item_count.setText("内含" + count + "处语音介绍");
        tv_item_distance.setText("距离"+ CommonUtils.getDistance(bean.getDistance()) + "km");
        RequestOptions options = new RequestOptions().error(R.mipmap.error_h).placeholder(R.mipmap.placeholder_h);
        Glide.with(context)
                .load(bean.getImgUrl())
                .apply(options)
                .into(iv_item_spot);

        return convertView;
    }

}
