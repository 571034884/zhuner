package com.aibabel.map.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.aibabel.baselibrary.base.BaseActivity;
import com.aibabel.baselibrary.http.BaseCallback;
import com.aibabel.baselibrary.http.OkGoUtil;
import com.aibabel.baselibrary.utils.ToastUtil;
import com.aibabel.map.R;
import com.aibabel.map.adapter.SearchAddressAdapter;
import com.aibabel.map.bean.search.AddressBean;
import com.aibabel.map.bean.search.AddressResult;
import com.aibabel.map.utils.ActivityConstant;
import com.aibabel.map.utils.ApiConstant;
import com.aibabel.map.utils.CommonUtils;
import com.aibabel.map.utils.DBUtil;
import com.aibabel.map.utils.KeyBords;
import com.lzy.okgo.OkGo;

import org.litepal.LitePal;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

/**
 * 起点-终点搜索页
 *
 * Created by fytworks on 2018/11/27.
 */

public class SearchActivity extends BaseActivity{

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.et_loca)
    EditText etLoca;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.lv_item_des)
    ListView lvItemDes;

    //0 起点   1 终点
    String type = "0";
    String city = "北京";
    String coorType = "bd09ll";
    //0 国内  1国外
    int locationWhere = 0;
    @Override
    public int getLayout(Bundle bundle) {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        String value = getIntent().getStringExtra("value");
        type = getIntent().getStringExtra("type");
        city = getIntent().getStringExtra("city");
        coorType = getIntent().getStringExtra("coorType");
        locationWhere = getIntent().getIntExtra("locationWhere",0);
        if (type.equals("0")){
            etLoca.setHint("我的位置");
            if (!value.equals("我的位置")){
                etLoca.setText(value);
                //光标移动到最后
                etLoca.setSelection(value.length());
            }
        }else if (type.equals("1")){

            if (!value.equals("我的位置")){
                etLoca.setText(value);
                //光标移动到最后
                etLoca.setSelection(value.length());
            }
            etLoca.setHint("请输入终点");
        }

        newAdapterEt();

        viewItemOnClick();

        //数据库操作
        resultList = LitePal.findAll(AddressResult.class,true);
        if (resultList.size() != 0){
            Collections.reverse(resultList);
            showViewData();
        }


    }

    private void viewItemOnClick() {

        lvItemDes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!CommonUtils.isNetworkAvailable(mContext)){
                    ToastUtil.showShort(mContext,"请检查网络连接");
                    return;
                }

                if (resultList == null || resultList.get(position) == null){
                    return;
                }

                AddressResult addressResult = resultList.get(position);

                KeyBords.closeKeybord(etLoca,mContext);
                if (DBUtil.isSaveALL(addressResult.getUid(),addressResult.getName())){
                    addressResult.save();
                    addressResult.getLocation().save();
                }
                Intent intent = new Intent();
                intent.putExtra("type",type);
                intent.putExtra("address",addressResult);
                SearchActivity.this.setResult(ActivityConstant.RESULT_OK, intent);
                SearchActivity.this.finish();
                overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
            }
        });

//        etLoca.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
//                    //其次再做相应操作
//                    String content = etLoca.getText().toString();
//                    if (!TextUtils.isEmpty(content)) {
//                        searchOkGo(content);
//                    }
//                }
//
//                return false;
//            }
//        });
    }

    private void newAdapterEt() {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        etLoca.setFilters(new InputFilter[]{filter});

        /* 当输入关键字变化时，动态更新建议列表 */
        etLoca.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {}
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (cs.length() <= 0) {
                    //数据库操作
                    resultList = LitePal.findAll(AddressResult.class,true);
                    if (resultList.size() != 0){
                        Collections.reverse(resultList);
                        showViewData();
                    }
                    return;
                }
                searchOkGo(cs.toString());
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                this.finish();
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                KeyBords.closeKeybord(etLoca,mContext);
                break;
            case R.id.iv_search:
                String loca = etLoca.getText().toString();
                if (TextUtils.isEmpty(loca)){
                    ToastUtil.showShort(mContext,"请输入位置信息");
                    return;
                }
                searchOkGo(loca);
                break;
        }
    }

    List<AddressResult> resultList = null;
    private void searchOkGo(String loca) {
        if (!CommonUtils.isNetworkAvailable(mContext)){
            ToastUtil.showShort(mContext,"请检查网络连接");
            return;
        }
        OkGo.cancelAll(OkGo.getInstance().getOkHttpClient());
        Map param = new HashMap();
        param.put("query",loca.trim());
        param.put("region",city);
        param.put("city_limit","true");
        param.put("coord_type",coorType+"");
        param.put("language","zh-CN");
        param.put("locationWhere",locationWhere+"");
        OkGoUtil.get(false, ApiConstant.API_SEARCH_CITY, param, AddressBean.class, new BaseCallback<AddressBean>() {
            @Override
            public void onSuccess(String s, AddressBean addressBean, String s1) {
                if (addressBean.getData().getStatus() == 0){
                    resultList = addressBean.getData().getResult();
                    if (resultList == null || resultList.size() == 0){
                        ToastUtil.showShort(mContext,"没有搜索到结果");
                        return;
                    }
                    showViewData();
                }else{
                    ToastUtil.showShort(mContext,"准儿没有找到信息");
                }
            }

            @Override
            public void onError(String s, String s1, String s2) {
                ToastUtil.showShort(mContext,s1);
            }

            @Override
            public void onFinsh(String s) {

            }
        });
    }

    SearchAddressAdapter addressAdapter = null;
    private void showViewData() {
        addressAdapter = new SearchAddressAdapter(mContext,resultList);
        lvItemDes.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onDestroy() {

        KeyBords.closeKeybord(etLoca,mContext);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理

        KeyBords.closeKeybord(etLoca,mContext);
    }

    @Override
    protected void onStop() {
        KeyBords.closeKeybord(etLoca,mContext);

        super.onStop();
    }
}
