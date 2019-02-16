package com.aibabel.map.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.map.R;
import com.aibabel.map.bean.transitzh.TransitRoutesZhBean;
import com.aibabel.map.bean.transitzh.TransitStepsZhBean;
import com.aibabel.map.bean.transitzh.TransitVehicleZhBean;

import java.util.List;

/**
 * Created by fytworks on 2018/12/3.
 */

public class TransitDetailsZhAdapter extends BaseAdapter {

    public static final int WALK_LINE = 0;
    public static final int TRANSIT_LINE = 1;
    public static final int ERROR = 2;


    private Context mContext;
    private TransitRoutesZhBean zhBean;
    private LayoutInflater mInflater;
    private String startName;
    private String endName;
    public TransitDetailsZhAdapter(Context context, TransitRoutesZhBean zhBean,String startName,String endName) {
        this.mContext = context;
        this.zhBean = zhBean;
        this.startName = startName;
        this.endName = endName;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return zhBean.getSteps().size();
    }

    @Override
    public Object getItem(int position) {
        return zhBean.getSteps().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public int getItemViewType(int position) {
        List<TransitStepsZhBean> transitStepsZhBeans = zhBean.getSteps().get(position);
        TransitStepsZhBean transitStepsZhBean = transitStepsZhBeans.get(0);
        int type = transitStepsZhBean.getType();
        if (type == 3){
            //公交
            return TRANSIT_LINE;
        }else if (type == 5){
            //步行
            return WALK_LINE;
        }
        return ERROR;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderWalk holderWalk;
        ViewHolderTransit holderTransit;
        int viewType = getItemViewType(position);
        TransitStepsZhBean steps = zhBean.getSteps().get(position).get(0);
        if (viewType == WALK_LINE){
            if (convertView == null){
                holderWalk = new ViewHolderWalk();
                convertView = mInflater.inflate(R.layout.transit_details_walking, parent, false);
                holderWalk.llStart = convertView.findViewById(R.id.ll_start);
                holderWalk.llEnd = convertView.findViewById(R.id.ll_end);
                holderWalk.vTop = convertView.findViewById(R.id.view_top);
                holderWalk.vBottom = convertView.findViewById(R.id.view_bottom);
                holderWalk.tvWalkInst = convertView.findViewById(R.id.walk_inst);
                holderWalk.tvStartName = convertView.findViewById(R.id.tv_start);
                holderWalk.tvEndName = convertView.findViewById(R.id.tv_end);
                convertView.setTag(holderWalk);
            }else{
                holderWalk = (ViewHolderWalk) convertView.getTag();
            }
            if (position == 0){
                holderWalk.llStart.setVisibility(View.VISIBLE);
                holderWalk.llEnd.setVisibility(View.GONE);
                holderWalk.vTop.setVisibility(View.VISIBLE);
                holderWalk.vBottom.setVisibility(View.GONE);
                holderWalk.tvStartName.setText("起点 ("+startName+")");
            }else if (position == zhBean.getSteps().size()-1){
                holderWalk.llStart.setVisibility(View.GONE);
                holderWalk.llEnd.setVisibility(View.VISIBLE);
                holderWalk.vTop.setVisibility(View.GONE);
                holderWalk.vBottom.setVisibility(View.VISIBLE);
                holderWalk.tvEndName.setText("终点 ("+endName+")");
            }else{
                holderWalk.llStart.setVisibility(View.GONE);
                holderWalk.llEnd.setVisibility(View.GONE);
                holderWalk.vTop.setVisibility(View.GONE);
                holderWalk.vBottom.setVisibility(View.GONE);
            }
            holderWalk.tvWalkInst.setText(steps.getInstruction()+"("+steps.getDuration()/60+"分钟)");

        }else if (viewType == TRANSIT_LINE){
            if (convertView == null){
                holderTransit = new ViewHolderTransit();
                convertView = mInflater.inflate(R.layout.transit_details_transit, parent, false);
                holderTransit.tvStart = convertView.findViewById(R.id.tv_start);
                holderTransit.tvEnds = convertView.findViewById(R.id.tv_ends);
                holderTransit.tvBuy = convertView.findViewById(R.id.tv_buy);
                holderTransit.tvDetails = convertView.findViewById(R.id.tv_details);
                holderTransit.tvTimer = convertView.findViewById(R.id.tv_timer);
                convertView.setTag(holderTransit);
            }else{
                holderTransit = (ViewHolderTransit) convertView.getTag();
            }
            TransitVehicleZhBean vehicle = steps.getVehicle();
            holderTransit.tvStart.setText(vehicle.getStart_name()+"");
            holderTransit.tvEnds.setText(vehicle.getEnd_name()+"");

            String name = "<font color = '#02a9ff'>" + vehicle.getName() + "</font>  "+vehicle.getDirect_text();
            holderTransit.tvDetails.setText(Html.fromHtml(name));
            holderTransit.tvTimer.setText("首"+vehicle.getStart_time()+"  末"+vehicle.getEnd_time());
            holderTransit.tvBuy.setText(vehicle.getStop_num()+"站 ("+steps.getDuration()/60+"分钟)");


        }


        return convertView;
    }
    public class ViewHolderWalk {
        public TextView tvWalkInst;
        public LinearLayout llStart;
        public LinearLayout llEnd;
        public  View vTop;
        public  View vBottom;
        public TextView tvStartName;
        public TextView tvEndName;
    }
    public class ViewHolderTransit {
        public TextView tvStart;
        public TextView tvDetails;
        public TextView tvTimer;
        public TextView tvBuy;
        public TextView tvEnds;
    }
}
