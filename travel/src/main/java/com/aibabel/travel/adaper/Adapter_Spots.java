package com.aibabel.travel.adaper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aibabel.travel.R;
import com.aibabel.travel.bean.SpotBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * ===================================================================================
 * 
 * @作者: 张文颖
 * 
 * @创建时间:
 * 
 * @描述:
 * 
 * @修改时间:
 * 
 * ====================================================================================
 */

public class Adapter_Spots extends MyBaseAdapter<SpotBean.DataBean.ResultsBean, ListView> {
private  Context context;
	

	public Adapter_Spots(Context context, List<SpotBean.DataBean.ResultsBean> list) {
		super(context, list);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SpotBean.DataBean.ResultsBean bean = list.get(position);

		if (null == convertView) {
			convertView = View.inflate(context, R.layout.city_item, null);
		}
		ImageView iv_city_item =  ViewHolder.get(convertView,R.id.iv_city_item);
		TextView tv_city_name =  ViewHolder.get(convertView,R.id.tv_city_name);
		TextView tv_city_number =  ViewHolder.get(convertView,R.id.tv_city_number);

		int count = 1 + bean.getSubCount();
//		Drawable drawable = context.getResources().getDrawable(R.mipmap.empty_h);
		RequestOptions options = new RequestOptions().placeholder(R.mipmap.placeholder_h).error(R.mipmap.error_h);
		tv_city_number.setText("内含"+count+"处语音介绍");
		tv_city_name.setText(bean.getName()+"");
		Glide.with(context)
				.load(bean.getCover())
				.apply(options)
				.into(iv_city_item);

		return convertView;
	}

}
