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
import com.aibabel.messagemanage.sqlite.SqlUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends com.aibabel.baselibrary.base.BaseActivity {

    private ArrayList<MessageBean> msglist = new ArrayList<>();
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

    private void initRecyclerView(){
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

    private void addItem(){
        MessageBean msgbean = new MessageBean();
        msgbean.setTitle("你有附近3个景点");
        msgbean.setInfo("雍和宫、天坛、故宫，12345667890");
        msgbean.setTime("2019-1-09");
        msgbean.setBadge(false);
        MessageBean msgbean2= new MessageBean();
        msgbean2.setTitle("你有附近3个景点");
        msgbean2.setInfo("雍和宫、天坛、故宫，12345667890");
        msgbean2.setTime("2018-2-2");

        MessageBean msgbean3= new MessageBean();
        msgbean3.setTitle("你有附近3个景点");
        msgbean3.setInfo("雍和宫、天坛、故宫，12345667890");
        msgbean3.setTime("1-10 14:45");
        msgbean3.setBadge(true);

        msglist.add(msgbean2);
        msglist.add(msgbean);
        msglist.add(msgbean3);
        adapter.notifyDataSetChanged();

//        SqlUtils.insertData(msgbean);
//        SqlUtils.insertData(msgbean2);
//        SqlUtils.insertData(msgbean3);
        List<MessageBean>  tt =  SqlUtils.queryMethed();
        Log.e("hjs","tt+"+tt.size());

    }


}
