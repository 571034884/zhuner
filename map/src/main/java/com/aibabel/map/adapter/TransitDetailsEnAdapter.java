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
import com.aibabel.map.bean.transiten.TransitDetailEnBean;
import com.aibabel.map.bean.transiten.TransitRoutesEnBean;
import com.aibabel.map.bean.transiten.TransitSchemesEnBean;
import com.aibabel.map.bean.transiten.TransitStepsEnBean;
import com.aibabel.map.bean.transiten.TransitVehicleEnBean;
import com.aibabel.map.bean.transitzh.TransitRoutesZhBean;
import com.aibabel.map.bean.transitzh.TransitStepsZhBean;
import com.aibabel.map.bean.transitzh.TransitVehicleZhBean;

import java.util.List;

/**
 * Created by fytworks on 2018/12/3.
 */

public class TransitDetailsEnAdapter extends BaseAdapter {

    public static final int WALK_LINE = 0;
    public static final int TRANSIT_LINE = 1;
    public static final int ERROR = 2;


    private Context mContext;
    private TransitRoutesEnBean enBean;
    private LayoutInflater mInflater;
    private String startName;
    private String endName;
    public TransitDetailsEnAdapter(Context context, TransitRoutesEnBean enBean,String startName,String endName) {
        this.mContext = context;
        this.enBean = enBean;
        this.startName = startName;
        this.endName = endName;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return enBean.getSteps().size();
    }

    @Override
    public Object getItem(int position) {
        return enBean.getSteps().get(position);
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
        TransitStepsEnBean stepsEnBean = enBean.getSteps().get(position);
        TransitVehicleEnBean vehicle_info = stepsEnBean.getSchemes().get(0).getVehicle_info();
        int type = vehicle_info.getType();
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
        TransitSchemesEnBean schemesEnBean = enBean.getSteps().get(position).getSchemes().get(0);
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
            }else if (position == enBean.getSteps().size()-1){
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
            holderWalk.tvWalkInst.setText(schemesEnBean.getInstructions()+"("+schemesEnBean.getDuration()/60+"分钟)");

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
            TransitDetailEnBean vehicle = schemesEnBean.getVehicle_info().getDetail();
            holderTransit.tvStart.setText(vehicle.getOn_station()+"");
            holderTransit.tvEnds.setText(vehicle.getOff_station()+"");
            String name = "<font color = '#02a9ff'>" + vehicle.getName();
            holderTransit.tvDetails.setText(Html.fromHtml(name)+"");
            holderTransit.tvTimer.setText("首"+vehicle.getFirst_time()+"  末"+vehicle.getLast_time());
            holderTransit.tvBuy.setText(vehicle.getStop_num()+"站 ("+schemesEnBean.getDuration()/60+"分钟)");
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
