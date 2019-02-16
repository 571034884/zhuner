package com.aibabel.travel.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.aibabel.travel.R;
import com.aibabel.travel.adaper.Adapter_Information;
import com.aibabel.travel.bean.PushBean;
import com.aibabel.travel.utils.FastJsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class InformationActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_info)
    ListView lvInfo;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private Adapter_Information adapter;
    List<PushBean> list = new ArrayList<>();

    @Override
    public int initLayout() {
        return R.layout.activity_information;
    }

    @Override
    public void init() {
        ivClose.setOnClickListener(this);
        lvInfo.setOnItemClickListener(this);
        String json = getIntent().getStringExtra("json");
        list = FastJsonUtil.changeJsonToList(json, PushBean.class);
        adapter = new Adapter_Information(this, list);
        lvInfo.setAdapter(adapter);
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PushBean bean = list.get(position);
        String s_id = bean.getPoi_id() + "";
        String url = bean.getImgUrl();
        String count = bean.getCount() + "";
        String audios = bean.getAudios();
        String name = bean.getPoi_name_cn();
        toScenic(s_id, url, count, name, audios);
    }


    /**
     * @param id
     * @param url
     * @param count
     */
    private void toScenic(String id, String url, String count, String name, String audioUrl) {
        Intent intent = new Intent(this, SpotActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("url", url);
        intent.putExtra("count", count);
        intent.putExtra("name", name);
        intent.putExtra("audioUrl", audioUrl);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close:
                this.finish();
                break;
        }
    }
}
