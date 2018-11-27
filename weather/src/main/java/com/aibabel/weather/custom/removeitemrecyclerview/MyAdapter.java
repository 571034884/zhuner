package com.aibabel.weather.custom.removeitemrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aibabel.weather.R;
import com.aibabel.weather.bean.WeatherBean;
import com.aibabel.weather.bean.WeatherUrlBean;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<WeatherBean> mList;
    private List<WeatherUrlBean> mUrlList;

    public MyAdapter(Context context, List<WeatherBean> list, List<WeatherUrlBean> urlList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
        mUrlList = urlList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.item_layout_for_city_weather, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        //禁止复用
//        holder.setIsRecyclable(false);

        if (mList.get(position).getWeatherobj() != null && mList.get(position).getWeatherobj().size() > 1) {
            setMaxMinText(viewHolder.tv_time, mList.get(position).getWeatherobj().get(0).getTemplow(), mList.get(position).getWeatherobj().get(0).getTemphigh());
        } else {
            setMaxMinText(viewHolder.tv_time, "", "");
        }

//        if ((int)viewHolder.tv_city.getTag() != position)
        viewHolder.tv_city.setText(mUrlList.get(position).getCityCN() + "," + mUrlList.get(position).getCountryCN());
        viewHolder.tv_wendu.setText(mList.get(position).getWeatherNowData().getTemperature_string() + "°");

        viewHolder.tv_city.setTag(position);
//        viewHolder.tv_wendu.setText(mList.get(position).getWeatherNowData().getTemperature_string().split("\\(")[1].split(" ")[0] + "°");
    }

    public void setMaxMinText(TextView textView, String textMax, String textMin) {
        if (TextUtils.equals(textMax, "")) {
            textView.setText("--~");
        } else {
            textView.setText(textMax + "°~");
        }
        if (TextUtils.equals(textMin, "")) {
            textView.append("--");
        } else {
            textView.append(textMin + "°");
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void removeItem(int position) {
        mList.remove(position);
        mUrlList.remove(position);
        notifyItemRemoved(position);
//        notifyDataSetChanged();
        notifyItemRangeChanged(position,mList.size()-position);
    }

    @Override
    public long getItemId(int position) {
//        return super.getItemId(position);
    return position;
    }
}
