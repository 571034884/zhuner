package com.aibabel.map.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.map.R;
import com.aibabel.map.bean.TrafficBean;

import java.util.List;

/**
 * Created by fytworks on 2018/12/5.
 */

public class TrafficAdapter extends BaseAdapter{

    private Context mContext;
    private List<TrafficBean> all;
    private LayoutInflater mInflater;
    private int index;

    public TrafficAdapter(Context context, List<TrafficBean> all,int index){
        this.mContext = context;
        this.all = all;
        this.index = index;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return all.size();
    }

    @Override
    public Object getItem(int position) {
        return all.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.traffic_item, parent, false);
            holder.mName = convertView.findViewById(R.id.tv_name);
            holder.mIcon = convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TrafficBean trafficBean = all.get(position);
        holder.mIcon.setVisibility(View.VISIBLE);
        selectIconShow(holder.mIcon,trafficBean.getTurn());
        holder.mName.setText(Html.fromHtml(trafficBean.getName()));

        return convertView;
    }

    private void selectIconShow(ImageView mIcon, int turn) {
        switch (turn){
            case 0://无效
                mIcon.setImageResource(R.mipmap.ic_go_straight);
                break;
            case 1://直行
                mIcon.setImageResource(R.mipmap.ic_go_straight);
                break;
            case 2://右前方转弯
                mIcon.setImageResource(R.mipmap.ic_right_front);
                break;
            case 3://右转
                mIcon.setImageResource(R.mipmap.ic_right_direction);
                break;
            case 4://右后方转弯
                mIcon.setImageResource(R.mipmap.ic_right_round);
                break;
            case 5://掉头
                mIcon.setImageResource(R.mipmap.ic_diao_tou);
                break;
            case 6://左后方转弯
                mIcon.setImageResource(R.mipmap.ic_left_round);
                break;
            case 7://左转
                mIcon.setImageResource(R.mipmap.ic_left_direction);
                break;
            case 8://左前方转弯
                mIcon.setImageResource(R.mipmap.ic_left_front);
                break;
            case 9://左侧
                mIcon.setImageResource(R.mipmap.ic_left_turn);
                break;
            case 10://右侧
                mIcon.setImageResource(R.mipmap.ic_right_turn);
                break;
            case 11://分歧-左
                mIcon.setImageResource(R.mipmap.ic_left_branch);
                break;
            case 12://分歧中央
                mIcon.setImageResource(R.mipmap.ic_center_branch);
                break;
            case 13://分歧-右
                mIcon.setImageResource(R.mipmap.ic_right_branch);
                break;
            case 14://环岛
                mIcon.setImageResource(R.mipmap.ic_turn_ring);
                break;
            case 15://进渡口
                mIcon.setImageResource(R.mipmap.ic_in_bat);
                break;
            case 16://出渡口
                mIcon.setImageResource(R.mipmap.ic_in_bat);
                break;
            case 17://起点
                mIcon.setImageResource(R.mipmap.ic_start_traffic);
                break;
            case 18://终点
                mIcon.setImageResource(R.mipmap.ic_end_traffic);
                break;
        }
    }

    public class ViewHolder {
        public ImageView mIcon;
        public TextView mName;
    }
}
