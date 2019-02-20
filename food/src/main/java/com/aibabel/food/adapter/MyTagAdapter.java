package com.aibabel.food.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.aibabel.food.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

/**
 * 作者：SunSH on 2018/12/12 19:25
 * 功能：
 * 版本：1.0
 */
public class MyTagAdapter extends TagAdapter<String> {

    private Context context;
    private List<String> datas;
    private int layoutId;

    public MyTagAdapter(Context context, List<String> datas, int layoutId) {
        super(datas);
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        TextView tv = (TextView) ((Activity) context).getLayoutInflater().inflate(layoutId, null, false);
        tv.setText(s);
        return tv;
    }

    public void refereshData(List<String> datas){
        this.datas =datas;
        notifyDataChanged();
    }
}
