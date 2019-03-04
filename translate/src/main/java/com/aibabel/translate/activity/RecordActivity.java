package com.aibabel.translate.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.translate.R;
import com.aibabel.translate.adapter.Adapter_Record;
import com.aibabel.translate.bean.RecordBean;
import com.aibabel.translate.sqlite.SqlUtils;
import com.aibabel.translate.view.MyRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordActivity extends BaseActivity {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.rv_record)
    MyRecyclerView rvRecord;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.tvQuxiao)
    TextView tvQuxiao;
    @BindView(R.id.tvQuanxuan)
    TextView tvQuanxuan;
    @BindView(R.id.tvshanchu)
    TextView tvshanchu;
    @BindView(R.id.clBianji)
    ConstraintLayout clBianji;
    private List<RecordBean> newsList = new ArrayList<>();
    //    private CommomRecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Adapter_Record adapter;
    private boolean loading = false;
    private int page = 1;
    private int pagesize = 50;
    private TextView header_view;
    private boolean canLoadMore;
    /**
     * 是否显示ｃｈｅｃｋｂｏｘ
     */
    private boolean isShowCheck;
    private boolean isSelectAll;
    /**
     * 记录选中的ｃｈｅｃｋｂｏｘ
     */
    private List<Integer> checkList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);

        setAdapter(newsList);
        find();
    }

    private void find(int page, int pagesize) {
        newsList.addAll(SqlUtils.retrieve(page, pagesize));
        adapter.updateData(newsList);
        loading = false;
        page++;
    }

    private String initDate(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

    private void setAdapter(List<RecordBean> newsList) {
        adapter = new Adapter_Record(this, newsList);
        adapter.setRecyclerViewOnItemClickListener(new Adapter_Record.RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (isShowCheck) {
                    if (checkList.contains(Integer.valueOf(position))) {
                        checkList.remove(Integer.valueOf(position));
                    } else {
                        checkList.add(position);
                    }
                }
                if (checkList.size() > 0)
                    tvshanchu.setTextColor(getResources().getColor(R.color.white));
                else
                    tvshanchu.setTextColor(getResources().getColor(R.color.gray));
            }

            @Override
            public boolean onItemLongClickListener(View view, int position) {
                if (isShowCheck) {
                    adapter.setShowCheckBox(false);
                    checkList.clear();
                } else {
                    adapter.setShowCheckBox(true);
                    checkList.add(position);
                }
                refreshUI(isShowCheck);
                isShowCheck = !isShowCheck;
                if (checkList.size() > 0)
                    tvshanchu.setTextColor(getResources().getColor(R.color.white));
                else
                    tvshanchu.setTextColor(getResources().getColor(R.color.gray));
                return true;
            }
        });

        header_view = findViewById(R.id.tv_time);
        rvRecord = findViewById(R.id.rv_record);
        rvRecord.setHasFixedSize(true);
        rvRecord.setLayoutManager(new LinearLayoutManager(this));

        rvRecord.setAdapter(adapter);

        rvRecord.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View stickyInfoView = recyclerView.getChildAt(0);
                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    header_view.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }
                View transInfoView = recyclerView.findChildViewUnder(header_view.getMeasuredWidth() / 2, header_view.getMeasuredHeight() + 1);
                if (transInfoView != null && transInfoView.getTag() != null) {
                    int tag = (int) transInfoView.getTag();
                    int deltaY = transInfoView.getTop() - header_view.getMeasuredHeight();
                    if (tag == Adapter_Record.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            header_view.setTranslationY(deltaY);
                        } else {
                            header_view.setTranslationY(0);
                        }
                    } else {
                        header_view.setTranslationY(0);
                    }
                }
            }
        });

        layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rvRecord.setLayoutManager(layoutManager);
        rvRecord.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {
                    //加载更多
                    if (canLoadMore) {
                        find();
                    }
                }
            }
        });
        rvRecord.setEmptyView(tvEmpty);
    }

    private void refreshUI(boolean isShowCheck) {
        if (!isShowCheck) {
            clBianji.setVisibility(View.VISIBLE);
            tvQuxiao.setVisibility(View.VISIBLE);
            ivClose.setVisibility(View.GONE);
        } else {
            clBianji.setVisibility(View.GONE);
            tvQuxiao.setVisibility(View.GONE);
            ivClose.setVisibility(View.VISIBLE);
        }
        adapter.updateData(newsList);
    }

    private void find() {
        canLoadMore = false;
        List list = SqlUtils.retrieve(page, pagesize);
        if (list.size() > 0) {
            newsList.addAll(list);
            canLoadMore = true;
            adapter.updateData(newsList);
            page++;
        }
    }

    @OnClick({R.id.tvQuxiao, R.id.tvQuanxuan, R.id.tvshanchu, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvQuxiao:
                quxiao();
                break;
            case R.id.tvQuanxuan:
                checkList.clear();
                if (!isSelectAll) {
                    tvQuanxuan.setText(getResources().getString(R.string.select_null));
                    tvshanchu.setTextColor(getResources().getColor(R.color.white));
                    adapter.setCheckAll(true);
                    for (int i = 0; i < newsList.size(); i++) {
                        checkList.add(i);
                    }
                } else {
                    tvQuanxuan.setText(getResources().getString(R.string.select_all));
                    tvshanchu.setTextColor(getResources().getColor(R.color.gray));
                    adapter.setCheckAll(false);
                    checkList.clear();
                }
                isSelectAll = !isSelectAll;
                adapter.notifyDataSetChanged();
                break;
            case R.id.tvshanchu:
                try{
                    if (checkList.size() > 0) {
//                    ToastUtil.showShort(checkList.toString());
                        for (int i = 0, j = 0; i < checkList.size(); i++, j++) {
                            int index = checkList.get(i);
                            SqlUtils.deleteById(newsList.remove(index - j).getId());
                        }
                        //添加统计
                        Map<String, String> map = new HashMap<>();
                        map.put("p1",checkList.size()+"");
                        StatisticsManager.getInstance(this).addEventAidl(1321,map);
                        quxiao();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                break;
            case R.id.iv_close:
                StatisticsManager.getInstance(this).addEventAidl(1312);
                finish();
                break;
        }
    }

    public void quxiao() {
        adapter.setShowCheckBox(false);
        checkList.clear();
        refreshUI(isShowCheck);
        isShowCheck = !isShowCheck;
    }

}
