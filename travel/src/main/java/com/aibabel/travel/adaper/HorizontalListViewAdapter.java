package com.aibabel.travel.adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aibabel.travel.R;
import com.aibabel.travel.bean.DetailBean;
import com.aibabel.travel.widgets.HorizontalListView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


public class HorizontalListViewAdapter extends MyBaseAdapter<DetailBean,HorizontalListView> {

    private Context mContext;
    private LayoutInflater mInflater;
    private  List<DetailBean> list;

    public HorizontalListViewAdapter(Context context, List<DetailBean> list, HorizontalListView view) {
        super(context, list, view);
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.list = list;
    }

    public HorizontalListViewAdapter(Context context, List<DetailBean> list) {
        super(context, list);
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.list = list;
    }

    //    public HorizontalListViewAdapter(Context con, List<DetailBean> list) {
//        mInflater = LayoutInflater.from(con);
//        mContext = con;
//    }
    @Override
    public int getCount() {
        if(null==list){
            return 0;
        }
        return list.size();
    }



    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DetailBean bean = list.get(position);

//        if (null == convertView) {
//            convertView = mInflater.inflate( R.layout.city_item, null);
//        }
//        ImageView iv_item_spot_child =  ViewHolder.get(convertView,R.id.iv_item_child);
//        Glide.with(context)
//                .load(bean.getImageUrl())
//                .into(iv_item_spot_child);
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = mInflater.inflate(R.layout.horizontallistview_item, null);
            vh.iv_item_child =(ImageView)convertView.findViewById(R.id.iv_item_child);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        RequestOptions options = new RequestOptions().error(R.mipmap.error_h).placeholder(R.mipmap.placeholder_h);

        Glide.with(context)
                .load(bean.getImageUrl())
                .apply(options)
                .into(vh.iv_item_child);


        return convertView;
    }

    public void updata(List<DetailBean> list){
        this.list.clear();
        this.list = list;
        this.notifyDataSetChanged();
    }
    private  class ViewHolder {

        private ImageView iv_item_child;

    }

}