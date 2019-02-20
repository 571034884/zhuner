package com.aibabel.food.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.food.R;
import com.aibabel.food.bean.KindBeanold;
import com.aibabel.food.bean.PopupWindowBean;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewAdapter;
import com.zhouyou.recyclerview.adapter.HelperRecyclerViewHolder;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/4 17:36
 * 功能：
 * 版本：1.0
 */
public class PopupWindowAdapter1 extends HelperRecyclerViewAdapter<PopupWindowBean.DataBean.DistrictsBean> {

    public Context context;
    public String check;

    public PopupWindowAdapter1(Context context, String check) {
        super(context, R.layout.item_kind_change);
        this.context = context;
        this.check = check;
    }

    @Override
    protected void HelperBindData(HelperRecyclerViewHolder viewHolder, int position, PopupWindowBean.DataBean.DistrictsBean item) {
        ((TextView) viewHolder.getView(R.id.tvKindItem)).setText(item.getName() + "(" + item.getCount() + ")");
        Log.e("", "tvKindItem: null"+ item.getName());
        if (check != null && check.equals(item.getName())) {
            ((TextView) viewHolder.getView(R.id.tvKindItem)).setTextColor(context.getResources().getColor(R.color.red));
            ((ImageView) viewHolder.getView(R.id.ivCheck)).setImageResource(R.mipmap.check);
        } else {
            ((ImageView) viewHolder.getView(R.id.ivCheck)).setImageResource(0);
            ((TextView) viewHolder.getView(R.id.tvKindItem)).setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    public void refresh(String check) {
        this.check = check;
        notifyDataSetChanged();
    }
}
