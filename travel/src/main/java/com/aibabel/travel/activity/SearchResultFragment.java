package com.aibabel.travel.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aibabel.travel.R;
import com.aibabel.travel.adaper.MyBaseAdapter;
import com.aibabel.travel.adaper.ViewHolder;
import com.aibabel.travel.bean.DetailBean;
import com.aibabel.travel.bean.SearchResultBean;
import com.aibabel.travel.utils.CommonUtils;
import com.aibabel.travel.utils.FastJsonUtil;
import com.aibabel.travel.widgets.MyListView;
import com.aibabel.travel.widgets.MyScrollview;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：SunSH on 2018/6/13 20:44
 * 功能：
 * 版本：1.0
 */
public class SearchResultFragment extends Fragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.tv_recent_search)
    TextView tvRecentSearch;
    @BindView(R.id.view1)
    View view1;
    //    @BindView(R.id.rv_recycler)
//    MyRecyclerView rvRecycler;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.hsv_scroll)
    MyScrollview hsvScroll;
    @BindView(R.id.lv_result)
    MyListView lvResult;
    private List<SearchResultBean.DataBean> list = new ArrayList<>();
//    private CommomRecyclerAdapter adapter;
    private Adapter_Results adapter;
    private TextView tv;
    private int length;
    private String searchInput;
    private int start_count = 0;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_destinations, container, false);
        unbinder = ButterKnife.bind(this, view);
        initList();
        return view;
    }

    private void initList(){

        lvResult.setOnItemClickListener(this);
        lvResult.setEmptyView(tvEmpty);
    }



    private void initTextColor(int position, TextView tv_name, String searchInput) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(tv_name.getText().toString());

        int i = tv_name.getText().toString().indexOf(searchInput);//*第一个出现的索引位置
        while (i != -1) {
            spannable.setSpan(new ForegroundColorSpan(Color.RED), i, i + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            i = tv_name.getText().toString().indexOf(searchInput, i + length + 1);//*从这个索引往后开始第一个出现的位置
        }

        tv_name.setText(spannable);

    }

//    private void initRecycler() {
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
//        rvRecycler.setLayoutManager(gridLayoutManager);
////        rvRecycler.setEmptyView();
//        adapter = new CommomRecyclerAdapter<SearchResultBean.DataBean>(getActivity(), list, R.layout.rv_destination_item, new CommomRecyclerAdapter.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
//                String id = list.get(postion).getId() + "";
//                String name = list.get(postion).getName();
//                String path = list.get(postion).getCover();
//                String audioUrl = list.get(postion).getAudios().getUrl();
//                int type = list.get(postion).getType();
//                toDetail(id, name, path, audioUrl, type);
//            }
//
//        }, null) {
//
//            @Override
//            public void convert(CommonRecyclerViewHolder holder, SearchResultBean.DataBean bean, int position) {
//                ImageView iv_destination = holder.getView(R.id.iv_destination);
//                TextView tv_name = holder.getView(R.id.tv_name);
////                TextView tv_title = holder.getView(R.id.tv_title);
////                TextView tv_branch = holder.getView(R.id.tv_branch);
//                tv_name.setText(bean.getName());
////                tv_title.setText(((DestinationBean) o).getDestination_title());
////                tv_branch.setText(((DestinationBean) o).getDestination_branch());
//                RequestOptions options = new RequestOptions().error(R.mipmap.empty_h);
//                Glide.with(getActivity())
//                        .load(bean.getCover())
//                        .apply(options)
//                        .into(iv_destination);
//
////                if (TextUtils.equals(((DestinationBean) o).getDestination_title(), "")) {
////                    tv_title.setVisibility(View.GONE);
////                } else {
////                    tv_title.setVisibility(View.VISIBLE);
////                }
//                if (!TextUtils.equals(tv_name.getText().toString(), "")) {
//                    initTextColor(position, tv_name, searchInput);
//                }
//
//            }
//
//        };
//        rvRecycler.setEmptyView(tvEmpty);
//        rvRecycler.setAdapter(adapter);
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(CommonUtils.isFastClick()){
            String s_id = list.get(position).getId() + "";
            String name = list.get(position).getName();
            String path = list.get(position).getCover();
            String audioUrl = list.get(position).getAudios().getUrl();
            int type = list.get(position).getType();
            toDetail(s_id, name, path, audioUrl, type);
        }
    }

    private void toDetail(String id, String name, String path, String audioUrl, int type) {
        Intent intent = new Intent();
        switch (type) {
            case 0:
                Toast.makeText(getActivity(), R.string.try_again, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                intent.setClass(getActivity(), CountryActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("imgUrl", path);
                startActivity(intent);
                break;
            case 2:
                intent.setClass(getActivity(), CityActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("url", path);
                startActivity(intent);
//                getActivity().finish();
                break;
            case 3:
                intent.setClass(getActivity(), SpotActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("url", path);
                intent.putExtra("audioUrl", audioUrl);
                startActivity(intent);
                break;
            case 4:
                intent.setClass(getActivity(), SpotDetailActivity.class);
                List<DetailBean> details = new ArrayList<>();
                DetailBean detail = new DetailBean();
                detail.setAudioUrl(audioUrl);
                detail.setImageUrl(path);
                detail.setName(name);
                details.add(detail);
                String json = FastJsonUtil.changListToString(details);
                intent.putExtra("position", 0);
                intent.putExtra("list", json);
                startActivity(intent);
                break;
            default:
                break;
        }


    }


    public void refreshSearchList(String searchInput, List<SearchResultBean.DataBean> dataBeans) {
        this.searchInput = searchInput;
        length = searchInput.length();

        list = dataBeans;
        if (null == list) {
            list = new ArrayList<>();
            tvEmpty.setVisibility(View.VISIBLE);
        }
        adapter = new Adapter_Results(getActivity(),list);
        lvResult.setAdapter(adapter);
        adapter.updateData(list);
        lvResult.requestFocus();
    }

    class Adapter_Results extends MyBaseAdapter<SearchResultBean.DataBean, ListView> {
        private Context context;
        List<SearchResultBean.DataBean> resultList = new ArrayList<>();


        public Adapter_Results(Context context, List<SearchResultBean.DataBean> resultList) {
            super(context, resultList);
            this.resultList = resultList;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SearchResultBean.DataBean bean = resultList.get(position);

            if (null == convertView) {
                convertView = View.inflate(context, R.layout.rv_destination_item, null);
            }
            ImageView iv_destination = ViewHolder.get(convertView, R.id.iv_destination);
            TextView tv_name = ViewHolder.get(convertView, R.id.tv_name);

            tv_name.setText(bean.getName());
            RequestOptions options = new RequestOptions().error(R.mipmap.error_h).placeholder(R.mipmap.placeholder_h);
            Glide.with(context)
                    .load(bean.getCover())
                    .apply(options)
                    .into(iv_destination);
            if (!TextUtils.equals(tv_name.getText().toString(), "")) {
                initTextColor(position, tv_name, searchInput);
            }

            return convertView;
        }

        /**
         * 当ListView数据发生变化时,调用此方法来更新ListView
         *
         * @param list
         */
        public void updateData(List<SearchResultBean.DataBean> list) {
            this.resultList = list;
            notifyDataSetChanged();
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }




}
