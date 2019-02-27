package com.aibabel.readme.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aibabel.aidlaar.StatisticsManager;
import com.aibabel.readme.BuildConfig;
import com.aibabel.readme.R;
import com.aibabel.readme.adapter.CommomRecyclerAdapter;
import com.aibabel.readme.adapter.CommonRecyclerViewHolder;
import com.aibabel.readme.bean.ReadmeItemBean;
import com.aibabel.readme.utils.CommonUtils;
import com.aibabel.readme.utils.WeizhiUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.taobao.sophix.SophixManager;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    private CommomRecyclerAdapter adapter;
    private List<ReadmeItemBean> readmeList = new ArrayList<>();
    private String version;
    private String proVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String display = Build.DISPLAY;
        proVersion = display.substring(9, 10);
        Log.e("version", "==" + proVersion);
        version = display.substring(0, 2);
        Log.e("version", "==" + version);

//        version = "PH";
//        proVersion = "S";
        initAdapter();
        initData(version);
        Map map = new HashMap();
        StatisticsManager.getInstance(MainActivity.this).addEventAidl( "进入页面", map);
        rexiufu();
    }

    public void rexiufu() {
        String latitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "latitude");
        String longitude = WeizhiUtil.getInfo(this, WeizhiUtil.CONTENT_URI_WY, "longitude");
        String url = "http://abroad.api.joner.aibabel.cn:7001" + "/v1/jonersystem/GetAppNew?sn=" + CommonUtils.getSN() + "&no=" + CommonUtils.getRandom() + "&sl=" + CommonUtils.getLocalLanguage() + "&av=" + BuildConfig.VERSION_NAME + "&app=" + getPackageName() + "&sv=" + Build.DISPLAY + "&lat=" + latitude + "&lng=" + longitude;

        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("热修复", response.body().toString());
                        if (!TextUtils.isEmpty(response.body().toString())) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                boolean isNew = (Boolean) ((JSONObject) jsonObject.get("data")).get("isNew");
                                if (isNew) {
                                    SophixManager.getInstance().queryAndLoadNewPatch();
                                    Log.e("success:", "=================" + isNew + "=================");
                                } else {
                                    Log.e("failed:", "=================" + isNew + "=================");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Exception:", "==========" + e.getMessage() + "===========");
                            }
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                    }
                });


    }



    private void initAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        rv.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new CommomRecyclerAdapter(this, readmeList, R.layout.recy_item, new CommomRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommonRecyclerViewHolder holder, int postion) {
                int type = readmeList.get(postion).getType();

                Map map = new HashMap();
                map.put("页面名称", readmeList.get(postion).getItem_name());
                StatisticsManager.getInstance(MainActivity.this).addEventAidl( "进入页面", map);
                Map map1 = new HashMap();
                map1.put("内容名称", readmeList.get(postion).getItem_name());
                StatisticsManager.getInstance(MainActivity.this).addEventAidl( "点击内容", map1);
                Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("version", version);
                intent.putExtra("proVersion", proVersion);
                startActivity(intent);
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                TextView tv_item = holder.getView(R.id.tv_item);
                tv_item.setText(((ReadmeItemBean) o).getItem_name());
            }
        };
        rv.setAdapter(adapter);
    }

    private void initData(String version) {
        switch (version) {
            case "PM"://go
                readmeList.add(new ReadmeItemBean(1, "语音翻译"));
                readmeList.add(new ReadmeItemBean(2, "拍照翻译"));
                readmeList.add(new ReadmeItemBean(3, "景区导览"));
                readmeList.add(new ReadmeItemBean(4, "目的地"));
                readmeList.add(new ReadmeItemBean(5, "设置"));
                readmeList.add(new ReadmeItemBean(6, "物体识别"));
                readmeList.add(new ReadmeItemBean(8, "语音秘书"));
                readmeList.add(new ReadmeItemBean(9, "汇率换算"));
                readmeList.add(new ReadmeItemBean(10, "天气"));
                readmeList.add(new ReadmeItemBean(11, "世界钟"));
                readmeList.add(new ReadmeItemBean(12, "sos"));
                break;
            case "PL"://pro
                if (TextUtils.equals(proVersion, "S")) {
                    readmeList.add(new ReadmeItemBean(1, "语音翻译"));
                    readmeList.add(new ReadmeItemBean(2, "拍照翻译"));
                    readmeList.add(new ReadmeItemBean(3, "景区导览"));
                    readmeList.add(new ReadmeItemBean(4, "目的地"));
                    readmeList.add(new ReadmeItemBean(5, "设置"));
                    readmeList.add(new ReadmeItemBean(6, "物体识别"));
                    readmeList.add(new ReadmeItemBean(7, "全球上网"));
                    readmeList.add(new ReadmeItemBean(8, "语音秘书"));
                    readmeList.add(new ReadmeItemBean(9, "汇率换算"));
                    readmeList.add(new ReadmeItemBean(10, "天气"));
                    readmeList.add(new ReadmeItemBean(11, "世界钟"));
                    readmeList.add(new ReadmeItemBean(12, "sos"));
                } else if (TextUtils.equals(proVersion, "L")) {
                    readmeList.add(new ReadmeItemBean(1, "菜单"));
                    readmeList.add(new ReadmeItemBean(2, "语音翻译"));
                    readmeList.add(new ReadmeItemBean(3, "拍照翻译"));
                    readmeList.add(new ReadmeItemBean(4, "当地玩乐"));
                    readmeList.add(new ReadmeItemBean(5, "地图"));
                    readmeList.add(new ReadmeItemBean(6, "时钟"));
                    readmeList.add(new ReadmeItemBean(7, "天气"));
                    readmeList.add(new ReadmeItemBean(8, "汇率"));
                    readmeList.add(new ReadmeItemBean(9, "美食"));
                    readmeList.add(new ReadmeItemBean(10, "景区导览"));
                    readmeList.add(new ReadmeItemBean(11, "出入境"));
                    readmeList.add(new ReadmeItemBean(12, "优惠券"));
                    readmeList.add(new ReadmeItemBean(13, "语音秘书"));
                    readmeList.add(new ReadmeItemBean(14, "全球上网"));
                    readmeList.add(new ReadmeItemBean(15, "离线管理"));
                    readmeList.add(new ReadmeItemBean(16, "设置"));
                    readmeList.add(new ReadmeItemBean(17, "sos"));
                }
                break;
            case "PH"://fly
                readmeList.add(new ReadmeItemBean(1, "语音翻译"));
                readmeList.add(new ReadmeItemBean(2, "拍照翻译"));
                readmeList.add(new ReadmeItemBean(3, "景区导览"));
                readmeList.add(new ReadmeItemBean(4, "目的地"));
                readmeList.add(new ReadmeItemBean(5, "设置"));
//                readmeList.add(new ReadmeItemBean(6, "物体识别"));
                readmeList.add(new ReadmeItemBean(7, "全球上网"));
                readmeList.add(new ReadmeItemBean(8, "语音秘书"));
                readmeList.add(new ReadmeItemBean(9, "汇率换算"));
                readmeList.add(new ReadmeItemBean(10, "天气"));
                readmeList.add(new ReadmeItemBean(11, "世界钟"));
                readmeList.add(new ReadmeItemBean(12, "sos"));
                readmeList.add(new ReadmeItemBean(13, "离线管理"));
                break;
        }
    }
}
