package com.aibabel.currencyconversion.custom;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.currencyconversion.R;
import com.aibabel.currencyconversion.app.Constant;
import com.aibabel.currencyconversion.bean.NewCurrencyBean;
import com.aibabel.currencyconversion.utils.CheckFlag;
import com.aibabel.currencyconversion.utils.InternationalizationUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class SortAdapter extends BaseAdapter implements PinnedHeaderListView.PinnedHeaderAdapter, AbsListView.OnScrollListener {
    private List<NewCurrencyBean> list = null;
    private Context mContext;

    private MySectionIndexer mIndexer;
    private int mLocationPosition = -1;

    public SortAdapter(Context mContext, List<NewCurrencyBean> list, MySectionIndexer indexer) {
        this.mContext = mContext;
        this.list = list;
        this.mIndexer = indexer;
    }

    public List<NewCurrencyBean> getData() {
        return this.list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<NewCurrencyBean> list, MySectionIndexer indexer) {
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
        if (null == view) {
            view = View.inflate(mContext, R.layout.item_layout_for_each_currency, null);
        }
        TextView tv_letter = ViewHolder.get(view, R.id.tv_catagory);
        ImageView civ_head = ViewHolder.get(view, R.id.iv_country);
        TextView tv_currency = ViewHolder.get(view, R.id.tv_currency);
        TextView tv_currency_abbreviations = ViewHolder.get(view, R.id.tv_currency_abbreviations);
        ImageView iv_xuanzhong = ViewHolder.get(view, R.id.iv_xuanzhong);

        civ_head.setImageResource(CheckFlag.getFlag(list.get(position).getKey()));

        tv_currency.setText(InternationalizationUtil.getCurrentText(list.get(position)));
        tv_currency_abbreviations.setText(list.get(position).getKey());
        if (list.get(position).getKey().equals(Constant.CURRENCY_ABBREVIATION_VALUE_1) ||
                list.get(position).getKey().equals(Constant.CURRENCY_ABBREVIATION_VALUE_2) ||
                list.get(position).getKey().equals(Constant.CURRENCY_ABBREVIATION_VALUE_3)) {
            iv_xuanzhong.setImageResource(R.mipmap.hl_xuanzhong);
        } else {
            iv_xuanzhong.setImageResource(0);
        }

        int section = mIndexer.getSectionForPosition(position);

        if (position == mIndexer.getPositionForSection(section)) {
            tv_letter.setVisibility(View.VISIBLE);
            tv_letter.setText(list.get(position).getGroup());
        } else {
            tv_letter.setVisibility(View.GONE);
        }

        tv_currency.setText(InternationalizationUtil.getCurrentText(list.get(position)));

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
        CircleImageView civ_head;
        TextView tv_letter;
        TextView tv_currency;
        TextView tv_currency_abbreviations;
        ImageView iv_xuanzhong;

        // I added a generic return type to reduce the casting noise in client code
        @SuppressWarnings("unchecked")
        public static <B extends View> B get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (B) childView;
        }
    }

}