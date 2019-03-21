package com.aibabel.messagemanage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.aibabel.menu.R;
import com.aibabel.menu.bean.PushMessageBean;
import com.aibabel.menu.util.LogUtil;
import com.aibabel.messagemanage.sqlite.SqlUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends com.aibabel.baselibrary.base.BaseActivity {

    private ArrayList<PushMessageBean> msglist = new ArrayList<>();
    RecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        setContentView(R.layout.activity_main_mm);
        initRecyclerView();
        addItem();

        ImageView image_close = findViewById(R.id.image_close);
        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });

    }

    @Override
    public int getLayout(Bundle savedInstanceState) {
        return -1;
    }

    @Override
    public void init() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        layout.setReverseLayout(true);//列表翻转
        //recyclerView.setLayoutManager(layout);


        adapter = new RecyclerViewAdapter(this, msglist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layout);


    }

    private void addItem() {
//        PushMessageBean msgbean = new PushMessageBean();
//        msgbean.setTitle("你有附近3个景点");
//        msgbean.setContent("雍和宫、天坛、故宫，12345667890");
//        msgbean.setTimeCode("");
//        msgbean.setBadge(false);
//        PushMessageBean msgbean2= new PushMessageBean();
//        msgbean2.setTitle("你有附近3个景点");
//        msgbean2.setContent("雍和宫、天坛、故宫，12345667890");
//        msgbean2.setTimeCode("");
//
//        PushMessageBean msgbean3= new PushMessageBean();
//        msgbean3.setTitle("你有附近3个景点");
//        msgbean3.setContent("雍和宫、天坛、故宫，12345667890");
//        msgbean3.setTimeCode("");
//        msgbean3.setBadge(true);
//
//        msglist.add(msgbean2);
//        msglist.add(msgbean);
//        msglist.add(msgbean3);

        List<PushMessageBean> tt = SqlUtils.queryMethed();
        if (tt != null) {
            for (PushMessageBean pushobj : tt) {
            }

            msglist.addAll(tt);
            if (adapter != null) adapter.notifyDataSetChanged();
        }


//        updateMessBean(tt.get(0),tt.get(0).getId());
//        PushMessageBean tttttt = tt.get(0);
//        tttttt.setContent("update ");
//        tttttt.setBadge(false);
//        tttttt.update(tttttt.getId());

//        tt = SqlUtils.queryMethed();
//        if (tt != null) {
//            for (PushMessageBean pushobj : tt) {
//                LogUtil.e("new pushobj = " + pushobj.isBadge());
//                LogUtil.e("new pushobj id= " + pushobj.getId());
//            }
//        }

    }

    public  static void updateMessBean(PushMessageBean sqlbean,Long ID) {
        sqlbean.setBadge(false);
        sqlbean.update(ID);
        sqlbean.save();

        List<PushMessageBean> tt = SqlUtils.queryMethed();
        if (tt != null) {
            for (PushMessageBean pushobj : tt) {
                LogUtil.e("query = id" + pushobj.getId() + " new  = " + pushobj.isBadge());
            }
        }

    }


}
