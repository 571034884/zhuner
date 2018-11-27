package com.aibabel.weather.custom;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aibabel.weather.R;
import com.aibabel.weather.bean.CityBean;
import com.aibabel.weather.bean.CityListBean;

import java.util.List;

public class SortAdapter extends BaseAdapter implements PinnedHeaderListView.PinnedHeaderAdapter, AbsListView.OnScrollListener {
    private List<CityListBean.DataBean> list = null;
    private Context mContext;

    private MySectionIndexer mIndexer;
    private int mLocationPosition = -1;

    public SortAdapter(Context mContext, List<CityListBean.DataBean> list, MySectionIndexer indexer) {
        this.mContext = mContext;
        this.list = list;
        this.mIndexer = indexer;
    }

    public List<CityListBean.DataBean> getData() {
        return this.list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<CityListBean.DataBean> list, MySectionIndexer indexer) {
        this.list = list;
        this.mIndexer = indexer;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final CityListBean.DataBean mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_contact, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.tv_city_name);
            view.setTag(viewHolder);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.tv_catagory);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        int section = mIndexer.getSectionForPosition(position);

        if (position == mIndexer.getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getGroup());
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(this.list.get(position).getCityCn());

        return view;

    }

    @Override
    public int getPinnedHeaderState(int position, int visibleItemCount) {
        int realPosition = position;
        if (realPosition < 0 || (mLocationPosition != -1 && mLocationPosition == realPosition)) {
            return PINNED_HEADER_GONE;
        }
        mLocationPosition = -1;
        int section = mIndexer.getSectionForPosition(realPosition);
        int nextSectionPosition = mIndexer.getPositionForSection(section + 1);
        if (nextSectionPosition != -1 && realPosition == nextSectionPosition - 1) {
            if (nextSectionPosition != 100000 && nextSectionPosition > visibleItemCount)
                return PINNED_HEADER_VISIBLE;
            return PINNED_HEADER_PUSHED_UP;
        }
        return PINNED_HEADER_VISIBLE;

    }

    @Override
    public void configurePinnedHeader(View header, int position, int alpha) {
        int section = mIndexer.getSectionForPosition(position);
        if (section != -1) {
            String title = mIndexer.getSections()[section];
            ((TextView) header.findViewById(R.id.group_title)).setText(title);
        } else {
            ((TextView) header.findViewById(R.id.group_title)).setText("");
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (view instanceof PinnedHeaderListView) {
            ((PinnedHeaderListView) view).configureHeaderView(firstVisibleItem, visibleItemCount);
        }

    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
    }

}