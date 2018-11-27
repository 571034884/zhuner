package com.aibabel.ocr.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.ocr.R;
import com.aibabel.ocr.bean.LanBean;
import com.aibabel.ocr.utils.StringUtils;

import java.util.ArrayList;

/**
 * ==========================================================================================
 *
 * @Author：CreateBy 张文颖
 * @Date：2018/5/2
 * @Desc： 选择语言adapter
 * ==========================================================================================
 */

public class LanAdapter extends BaseExpandableListAdapter {

    private ArrayList<LanBean> lists;
    private Context context;

    public LanAdapter(ArrayList<LanBean> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        if (null == lists) {
            return 0;
        }
        return lists.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (null == lists || null == lists.get(i).getChild()) {
            return 0;
        }

        return lists.get(i).getChild().size();
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int position, boolean isExpanded, View convertView, ViewGroup viewGroup) {

        LanBean bean = lists.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_group, null);
        }
        TextView tv_name_zh = ViewHolder.get(convertView, R.id.tv_name_zh);
        TextView tv_name_local = ViewHolder.get(convertView, R.id.tv_name_local);
        ImageView iv_choice = ViewHolder.get(convertView, R.id.iv_choice);
        ImageView iv_elv = ViewHolder.get(convertView, R.id.iv_elv);

        tv_name_zh.setText(bean.getName());
        tv_name_local.setText(bean.getName_local());

        if (StringUtils.hasChild(bean)) {
            if(!isExpanded){
                iv_elv.setBackground(context.getResources().getDrawable(R.mipmap.right));
            }else{
                iv_elv.setBackground(context.getResources().getDrawable(R.mipmap.buttom));
            }
        } else {
            iv_elv.setBackground(null);
        }

        if (bean.getChoice() == 1) {
            iv_choice.setImageResource(R.mipmap.select);
        } else {
            iv_choice.setImageBitmap(null);
        }
        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(context, R.layout.item_child, null);
        }
        TextView tv_name_zh = ViewHolder.get(view, R.id.tv_name_zh);
        TextView tv_name_en = ViewHolder.get(view, R.id.tv_name_en);
        ImageView iv_choice = ViewHolder.get(view, R.id.iv_choice);

        tv_name_zh.setText(lists.get(i).getChild().get(i1).getVar());
        tv_name_en.setText(lists.get(i).getChild().get(i1).getVar_local());
        if (lists.get(i).getChild().get(i1).getChoice() == 1) {
            iv_choice.setImageResource(R.mipmap.select);
        } else {
            iv_choice.setImageBitmap(null);
        }
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


}
