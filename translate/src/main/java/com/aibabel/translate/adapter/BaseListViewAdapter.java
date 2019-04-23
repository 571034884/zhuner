package com.aibabel.translate.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * ListView的数据适配器基类
 * @author cbb
 *
 * @param <T>
 */
public abstract class BaseListViewAdapter<T> extends BaseAdapter{
	public List<T> list;

	/**
	 * 因为自己是父类，不知道子类传入的是什么 所以就用泛型规定
	 * @param list
	 */
	public BaseListViewAdapter(List<T> list){
		this.list = list;
	}
	
	@Override
	public int getCount() {
		if (null != list) {
			return list.size();			
		}else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		if (null != list) {
			return list.get(position);		
		}else {
			return 0;
		}
		
	}
	@Override
	public long getItemId(int position) {
		if (null != list) {
			return position;
		}else {
			return 0;
		}
		
	}
	
	/**
	 * 不知道子类的具体实现 所以抽象出去
	 */
	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
}
