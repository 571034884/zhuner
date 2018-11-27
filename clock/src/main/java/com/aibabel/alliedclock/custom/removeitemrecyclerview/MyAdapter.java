package com.aibabel.alliedclock.custom.removeitemrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.aibabel.alliedclock.R;
import com.aibabel.alliedclock.app.Constant;
import com.aibabel.alliedclock.bean.ClockBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.TimeZone;

public class MyAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<ClockBean> mList;

    public MyAdapter(Context context, List<ClockBean> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.item_layout_for_allied_clock, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder viewHolder = (MyViewHolder) holder;
        if (mList.get(position).getCity().equalsIgnoreCase(Constant.CURRENT_LOCATION_VALUE)) {
            viewHolder.iv_dingwei.setImageResource(R.mipmap.sj_dingwei);
        } else {
            viewHolder.iv_dingwei.setImageResource(0);
        }

        float currentTimeZone = Float.valueOf("GMT+8".substring(3, "GMT+8".length()));//固定时差和东八区比较
        String timeZome = mList.get(position).getTiemZone().substring(3, mList.get(position).getTiemZone().length());
        float positionTimeZone = 0.0f;
        if (timeZome.contains(":")) {
            String[] posTimeZone = mList.get(position).getTiemZone().substring(3, mList.get(position).getTiemZone().length()).split(":");
            positionTimeZone = Float.valueOf(posTimeZone[0]) + Float.valueOf(posTimeZone[1]) / 60;
        } else {
            positionTimeZone = Float.valueOf(timeZome);
        }
        float chazhi = positionTimeZone - currentTimeZone;
        String[] currentTime = showTime(TimeZone.getTimeZone("GMT+8")).split(" ");
        float when = Float.valueOf(currentTime[0].split(":")[0]) + chazhi;
        String chazhiString = (chazhi > 0) ? "+" + chazhi : chazhi + "";
//        if (chazhiString.contains(".")) {
//            String[] temp = chazhiString.split("\\.");
//            String
//            chazhiString = temp[0] + ":" + (int)(Float.valueOf(temp[1]) / 100 * 60);
//        }
        if (((currentTime[1].equals("下午") || currentTime[1].equals("PM") || currentTime[1].equals("午後") || currentTime[1].equals("오후")) ? when + 12 : when) < 0) {
            viewHolder.tv_relative_time.setText(mContext.getResources().getString(R.string.zuotian) + "," + chazhiString + mContext.getResources().getString(R.string.xiaoshi));
        } else if (((currentTime[1].equals("下午") || currentTime[1].equals("PM") || currentTime[1].equals("午後") || currentTime[1].equals("오후")) ? when + 12 : when) > 23) {
            viewHolder.tv_relative_time.setText(mContext.getResources().getString(R.string.mingtian) + "," + chazhiString + mContext.getResources().getString(R.string.xiaoshi));
        } else {
            viewHolder.tv_relative_time.setText(mContext.getResources().getString(R.string.jintian) + "," + chazhiString + mContext.getResources().getString(R.string.xiaoshi));
        }
        viewHolder.tv_city.setText(mList.get(position).getCityCN());
//        String[] time = showTime(TimeZone.getTimeZone(mList.get(position).getTiemZone())).split(" ");


////        float currentTimeZone = Float.valueOf(Constant.CURRENT_TIMEZONE_VALUE.substring(3, Constant.CURRENT_TIMEZONE_VALUE.length()));
//        float currentTimeZone = Float.valueOf("GMT+8".substring(3, "GMT+8".length()));//固定时差和东八区比较
//        String timeZome = mList.get(position).getTiemZone().substring(3, mList.get(position).getTiemZone().length());
//        float positionTimeZone = 0.0f;
//        if (timeZome.contains(":")) {
//            String[] posTimeZone = mList.get(position).getTiemZone().substring(3, mList.get(position).getTiemZone().length()).split(":");
//            positionTimeZone = Float.valueOf(posTimeZone[0]) + Float.valueOf(posTimeZone[1]) / 60;
//        } else {
//            positionTimeZone = Float.valueOf(timeZome);
//        }
//        float chazhi = positionTimeZone - currentTimeZone;
//        String[] currentTime = showTime(TimeZone.getTimeZone("GMT+8")).split(" ");
//        float when = Float.valueOf(currentTime[0].split(":")[0]) + chazhi;
//        String chazhiString = (chazhi > 0) ? "+" + chazhi : chazhi + "";
//        if (((currentTime[1].equals("下午") || currentTime[1].equals("PM") || currentTime[1].equals("午後") || currentTime[1].equals("오후")) ? when + 12 : when) < 0) {
//            viewHolder.tv_relative_time.setText(mContext.getResources().getString(R.string.zuotian) + "," + chazhiString + mContext.getResources().getString(R.string.xiaoshi));
//        } else if (((currentTime[1].equals("下午") || currentTime[1].equals("PM") || currentTime[1].equals("午後") || currentTime[1].equals("오후")) ? when + 12 : when) > 23) {
//            viewHolder.tv_relative_time.setText(mContext.getResources().getString(R.string.mingtian) + "," + chazhiString + mContext.getResources().getString(R.string.xiaoshi));
//        } else {
//            viewHolder.tv_relative_time.setText(mContext.getResources().getString(R.string.jintian) + "," + chazhiString + mContext.getResources().getString(R.string.xiaoshi));
//        }
//        viewHolder.tv_city.setText(mList.get(position).getCityCN());
////        String[] time = showTime(TimeZone.getTimeZone(mList.get(position).getTiemZone())).split(" ");

        viewHolder.tc_time.setTimeZone(mList.get(position).getTiemZone());
//        if (TextUtils.equals(time[1], "上午")) viewHolder.tv_time_standard.setText("AM");
//        if (TextUtils.equals(time[1], "AM")) viewHolder.tv_time_standard.setText("AM");
//        if (TextUtils.equals(time[1], "午前")) viewHolder.tv_time_standard.setText("AM");
//        if (TextUtils.equals(time[1], "오전")) viewHolder.tv_time_standard.setText("AM");
//        if (TextUtils.equals(time[1], "下午")) viewHolder.tv_time_standard.setText("PM");
//        if (TextUtils.equals(time[1], "PM")) viewHolder.tv_time_standard.setText("PM");
//        if (TextUtils.equals(time[1], "午後")) viewHolder.tv_time_standard.setText("PM");
//        if (TextUtils.equals(time[1], "오후")) viewHolder.tv_time_standard.setText("PM");
//        viewHolder.tv_time_standard.setText(time[1]);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public long getItemId(int position) {
//        return super.getItemId(position);
        return position;
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    //通过时区的id获得当时的时间
    public String getTime() {
        Time time = new Time();
        time.setToNow();
        int year = time.year;
        int month = time.month;
        int day = time.monthDay;
        int minute = time.minute;
        int hour = time.hour;
        int sec = time.second;
        return year + "年" + (month + 1) + "月" + day + "日 " + hour + ":" + minute + ":" + sec;
    }

    public String showTime(TimeZone timeZone) {
        String dateTemp = "";
        try {
            DateFormat df_from = new SimpleDateFormat("yyyy年M月d日 H:m:s");
            Date date = df_from.parse(getTime());
            DateFormat df_to = new SimpleDateFormat("hh:mm aaa");
            df_to.setTimeZone(timeZone);
            dateTemp = df_to.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTemp;
    }
}
