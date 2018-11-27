package com.aibabel.alliedclock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.aibabel.alliedclock.R;
import com.aibabel.alliedclock.bean.CityBean;
import com.aibabel.alliedclock.custom.MyExpandableListView;

import java.util.List;


/**
 * 作者：SunSH on 2018/5/18 18:28
 * 功能：
 * 版本：1.0
 */
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter implements MyExpandableListView.TreeHeaderAdapter {

    List<CityBean> list;
    Context context;

    public MyExpandableListViewAdapter(Context context, List<CityBean> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public List<CityBean> getData() {
        return list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateData(List<CityBean> list) {
        this.list = list;
        notifyDataSetInvalidated();
    }

    //  获得某个父项的某个子项
    @Override
    public Object getChild(int parentPos, int childPos) {
        return list.get(parentPos).getChild().get(childPos);
    }

    //  获得父项的数量
    @Override
    public int getGroupCount() {
        return list.size();
    }

    //  获得某个父项的子项数目
    @Override
    public int getChildrenCount(int parentPos) {
        return list.get(parentPos).getChild().size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int parentPos) {
        return list.get(parentPos);
    }

    //  获得某个父项的id
    @Override
    public long getGroupId(int parentPos) {
        return parentPos;
    }

    //  获得某个父项的某个子项的id
    @Override
    public long getChildId(int parentPos, int childPos) {
        return childPos;
    }

    //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
    @Override
    public boolean hasStableIds() {
        return false;
    }

    //  获得父项显示的view
    @Override
    public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_layout_for_group, null);
        }
        TextView text = (TextView) view.findViewById(R.id.tv_group);
        text.setText(list.get(parentPos).getGroup());


        return view;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(int parentPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_layout_for_each_currency, null);
        }
        TextView tv_city = view.findViewById(R.id.tv_city);

        tv_city.setText(list.get(parentPos).getChild().get(childPos).getCityCn() + "(" + list.get(parentPos).getChild().get(childPos).getCountryCn() + ")");
        return view;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public int getSectionForPositionOfGroup(int position) {
        return list.get(position).getGroup().charAt(0);
    }

    public int getPositionOfGroupForSection(int section) {
        for (int i = 0; i < getGroupCount(); i++) {
            String sortStr = list.get(i).getGroup();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getTreeHeaderState(int groupPosition, int childPosition) {
        int childCount = 0;
        if (groupPosition > -1) {
            childCount = getChildrenCount(groupPosition);
        }
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
//        } else if (childPosition == -1 && !treeView.isGroupExpanded(groupPosition)) {
//            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }

    @Override
    public void configureTreeHeader(View header, int groupPosition, int childPosition, int alpha) {
// TODO Auto-generated method stub
        ((TextView) header.findViewById(R.id.tv_group)).setText(list.get(groupPosition).getGroup());
    }

    @Override
    public void onHeadViewClick(int groupPosition, int status) {
//        groupStatusMap.put(groupPosition, status);
    }

    @Override
    public int getHeadViewClickStatus(int groupPosition) {
        return 0;
    }
}
