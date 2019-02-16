package com.aibabel.map.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aibabel.map.R;
import com.aibabel.map.utils.ColorTextUtil;

import java.util.List;

/**
 * Created by fytworks on 2018/11/19.
 */

public class SearchSuggesAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> allPoi;
    private LayoutInflater mInflater;
    private String change;

    public SearchSuggesAdapter(Context context, List<String> allPoi,String change) {
        this.mContext = context;
        this.allPoi = allPoi;
        this.change = change;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return allPoi.size();
    }

    @Override
    public Object getItem(int position) {
        return allPoi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_sugges_item, viewGroup, false);
            holder.mName = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mName.setText(Html.fromHtml(ColorTextUtil.changeTextColor(allPoi.get(position),change)));
        return convertView;
    }

    public class ViewHolder {
        public TextView mName;
    }

}
