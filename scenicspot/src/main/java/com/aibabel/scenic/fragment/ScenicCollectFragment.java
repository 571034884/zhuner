package com.aibabel.scenic.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.baselibrary.http.BaseBean;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.sphelper.SPHelper;
import com.aibabel.baselibrary.utils.FastJsonUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.scenic.R;
import com.aibabel.scenic.activity.ScenicActivity;
import com.aibabel.scenic.activity.SpotsActivity;
import com.aibabel.scenic.adapter.Adapter_Scenics;
import com.aibabel.scenic.bean.CollectBean;
import com.aibabel.scenic.bean.ScenicListBean;
import com.aibabel.scenic.okgo.ApiConstant;
import com.aibabel.scenic.utils.CommonUtils;
import com.aibabel.scenic.utils.Logs;
import com.aibabel.scenic.view.EmptyLayout;
import com.aibabel.scenic.view.RecyclerItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


@SuppressLint("ValidFragment")
public class ScenicCollectFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv_scenic)
    RecyclerView rvScenic;
    @BindView(R.id.el_error)
    EmptyLayout elError;
    private List<ScenicListBean.DataBean> list;
    private Adapter_Scenics mAdapter;
    private int page;
    private final int PAGE_SIZE = 50;
    private String cityName;


    public ScenicCollectFragment(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_scenic;
    }

    @Override
    public void init(View view, Bundle savedInstanceState) {
        page = 1;
        list = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        //设置布局管理器
        rvScenic.setLayoutManager(layoutManager);
        mAdapter = new Adapter_Scenics(R.layout.item_scenics, list);
        rvScenic.addItemDecoration(new RecyclerItemDecoration(10, 2));
        rvScenic.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getDataValue();
            }


        }, rvScenic);
        //错误处理
        elError.setOnBtnClickListener(new EmptyLayout.onClickListener() {
            @Override
            public void onBtnClick() {
                getDataValue();
            }
        });
        getDataValue();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (CommonUtils.isFastClick()) {


            try {
                HashMap<String, Serializable> map = new HashMap<>();
                map.put("scenic_list_btn_collect_des_id", position);
                map.put("scenic_list_btn_collect_des_name", list.get(position).getCityname());
                ((ScenicActivity) getActivity()).addStatisticsEvent("scenic_list_btn_collect_des", map);
            }catch (Exception e){}
            Intent intent = new Intent();
            intent.setClass(getActivity(), SpotsActivity.class);
            intent.putExtra("poiId", list.get(position).getIdstring());
            startActivity(intent);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.iv_item_save:
                String id = list.get(position).getIdstring();
                if (CommonUtils.isFastClick()) {
                    cancelCollection(id);
                }
                break;
        }
    }


    /**
     * 网络请求
     */
    private void getDataValue() {
        if (!CommonUtils.isNetworkAvailable(getContext())) {
            elError.setErrorType(EmptyLayout.NETWORK_EMPTY);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("cityName", cityName);
        map.put("leaseId", SPHelper.getString("order_oid", ""));
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(PAGE_SIZE));
        OkGoUtil.get(mContext,ApiConstant.GET_COLLECTION, map, BaseBean.class, new BaseCallback() {
            @Override
            public void onSuccess(String method, BaseBean model, String json) {
                Logs.e(json);
                ScenicListBean scenicListBean = FastJsonUtil.changeJsonToBean(json, ScenicListBean.class);

                boolean isRefresh = false;
                if (page == 1) {
                    isRefresh = true;
                } else {
                    isRefresh = false;
                }
                setData(isRefresh, scenicListBean);

            }

            @Override
            public void onError(String method, String message, String json) {
                Logs.e("景区--" + ApiConstant.GET_HOME_SCENIC + "：" + message);
                mAdapter.loadMoreFail();
                if (page == 1) {
                    try{
                        elError.setErrorType(EmptyLayout.ERROR_EMPTY);
                    }catch (Exception e){
                        ToastUtil.showShort(mContext,"准儿出错了");
                    }

                }
            }

            @Override
            public void onFinsh(String method) {

            }
        });
    }


    /**
     * 向页面赋值
     *
     * @param isRefresh
     */
    private void setData(boolean isRefresh, ScenicListBean scenicBean) {
        page++;
        List<ScenicListBean.DataBean> datas = scenicBean.getData();
        final int size = datas == null ? 0 : list.size();
        if (isRefresh) {
            mAdapter.setNewData(datas);
            list = datas;
            if (null != list && list.size() == 0) {
                elError.setErrorType(EmptyLayout.NORMAL_EMPTY);
            } else {
                elError.setErrorType(EmptyLayout.SUCCESS_EMPTY);
            }

            Logs.e("第一次执行");
        } else {
            if (size > 0) {
                Logs.e("第二次执行");
                list.addAll(datas);
                mAdapter.addData(datas);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
//            if (page > 2) {
//                Toast.makeText(getContext(), "no more data", Toast.LENGTH_SHORT).show();
//            }
        } else {
            mAdapter.loadMoreComplete();
        }
    }


    /**
     * 增加收藏
     *
     * @param id
     */
    private void cancelCollection(String id) {
        if (!CommonUtils.isNetworkAvailable(getContext())) {
            ToastUtil.showShort(getContext(), getString(R.string.error_net));
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("poiIdStr", id);
        map.put("leaseId", SPHelper.getString("order_oid",""));
        map.put("page", String.valueOf(page));
        map.put("pageSize", String.valueOf(PAGE_SIZE));
        OkGoUtil.get(ApiConstant.GET_CANCEL_COLLECTION, map, BaseBean.class, new BaseCallback() {
            @Override
            public void onSuccess(String method, BaseBean model, String json) {
//                ToastUtil.showShort(mContext, "成功");
                Logs.e(json);
                ((ScenicActivity)getActivity()).isChanged = true;
                CollectBean bean = FastJsonUtil.changeJsonToBean(json, CollectBean.class);
                update(bean, id);
            }

            @Override
            public void onError(String method, String message, String json) {
                ToastUtil.showShort(mContext, message + " ");
                Logs.e("景区--" + ApiConstant.GET_HOME_SCENIC + "：" + message);
                mAdapter.loadMoreFail();
            }

            @Override
            public void onFinsh(String method) {

            }
        });
    }

    /**
     * 取消成功，更新UI
     *
     * @param bean
     * @param id
     */
    private void update(CollectBean bean, String id) {

        try {
            if (bean.getData().isStatus()) {
                for (int i = 0; i < list.size(); i++) {
                    if (TextUtils.equals(id, list.get(i).getIdstring())) {
                        list.remove(i);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
