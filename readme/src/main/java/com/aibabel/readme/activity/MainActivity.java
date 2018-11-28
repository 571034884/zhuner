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

import com.aibabel.readme.R;
import com.aibabel.readme.adapter.CommomRecyclerAdapter;
import com.aibabel.readme.adapter.CommonRecyclerViewHolder;
import com.aibabel.readme.bean.ReadmeItemBean;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        String display = Build.DISPLAY;
        version = display.substring(0, 2);
        Log.e("version","=="+version);
        initAdapter();
        initData(version);
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
                Intent intent = new Intent(MainActivity.this,ViewPagerActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("version",version);
                startActivity(intent);
            }
        }, null) {
            @Override
            public void convert(CommonRecyclerViewHolder holder, Object o, int position) {
                TextView tv_item = holder.getView(R.id.tv_item);
                tv_item.setText(((ReadmeItemBean)o).getItem_name());
            }
        };
        rv.setAdapter(adapter);
    }

    private void initData(String version) {
        switch (version){
            case "PM"://go
                readmeList.add(new ReadmeItemBean(1,"语音翻译"));
                readmeList.add(new ReadmeItemBean(2,"拍照翻译"));
                readmeList.add(new ReadmeItemBean(3,"景区导览"));
                readmeList.add(new ReadmeItemBean(4,"目的地"));
                readmeList.add(new ReadmeItemBean(5,"设置"));
                readmeList.add(new ReadmeItemBean(6,"物体识别"));
                readmeList.add(new ReadmeItemBean(8,"语音秘书"));
                readmeList.add(new ReadmeItemBean(9,"汇率换算"));
                readmeList.add(new ReadmeItemBean(10,"天气"));
                readmeList.add(new ReadmeItemBean(11,"世界钟"));
                readmeList.add(new ReadmeItemBean(12,"sos"));
                break;
            case "PL"://pro
                readmeList.add(new ReadmeItemBean(1,"语音翻译"));
                readmeList.add(new ReadmeItemBean(2,"拍照翻译"));
                readmeList.add(new ReadmeItemBean(3,"景区导览"));
                readmeList.add(new ReadmeItemBean(4,"目的地"));
                readmeList.add(new ReadmeItemBean(5,"设置"));
                readmeList.add(new ReadmeItemBean(6,"物体识别"));
                readmeList.add(new ReadmeItemBean(7,"全球上网"));
                readmeList.add(new ReadmeItemBean(8,"语音秘书"));
                readmeList.add(new ReadmeItemBean(9,"汇率换算"));
                readmeList.add(new ReadmeItemBean(10,"天气"));
                readmeList.add(new ReadmeItemBean(11,"世界钟"));
                readmeList.add(new ReadmeItemBean(12,"sos"));
                break;

            case "PH"://fly
                readmeList.add(new ReadmeItemBean(1,"语音翻译"));
                readmeList.add(new ReadmeItemBean(2,"拍照翻译"));
                readmeList.add(new ReadmeItemBean(3,"景区导览"));
                readmeList.add(new ReadmeItemBean(4,"目的地"));
                readmeList.add(new ReadmeItemBean(5,"设置"));
                readmeList.add(new ReadmeItemBean(6,"物体识别"));
                readmeList.add(new ReadmeItemBean(7,"全球上网"));
                readmeList.add(new ReadmeItemBean(8,"语音秘书"));
                readmeList.add(new ReadmeItemBean(9,"汇率换算"));
                readmeList.add(new ReadmeItemBean(10,"天气"));
                readmeList.add(new ReadmeItemBean(11,"世界钟"));
                readmeList.add(new ReadmeItemBean(12,"sos"));
                break;
        }
    }
}
