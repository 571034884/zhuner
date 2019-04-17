package com.aibabel.messagemanage.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aibabel.baselibrary.base.BaseFragment;
import com.aibabel.menu.R;
import com.aibabel.menu.bean.PushMessageBean;
import com.aibabel.menu.util.LogUtil;
import com.aibabel.messagemanage.MainActivity;
import com.aibabel.messagemanage.RecyclerViewAdapter;
import com.aibabel.messagemanage.sqlite.SqlUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *==========================================================================================
 * @Author： 张文颖
 *
 * @Date：2019/4/16
 *
 * @Desc：消息列表，完全复制MainActivity代码
 *==========================================================================================
 */
public class Fragment_Message extends BaseFragment {

    private ArrayList<PushMessageBean> msglist = new ArrayList<>();
    RecyclerViewAdapter adapter;

    @Override
    public int getLayout() {
        return R.layout.fragment_message;
    }

    @Override
    public void init(View view, Bundle savedInstanceState) {
        initRecyclerView(view);
        addItem();

        ImageView image_close = view.findViewById(R.id.image_close);
        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                HashMap<String, Serializable> map = new HashMap<>();
//                map.put("menu_notice_close_id","关闭");
//                addStatisticsEvent("menu_notice_close",map);

                ((MainActivity)mContext).finish();
            }
        });
    }



    private void initRecyclerView(View view) {
//        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = view.findViewById(R.id.recyclerv_view);

        LinearLayoutManager layout = new LinearLayoutManager(mContext);
        layout.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        layout.setReverseLayout(true);//列表翻转
        //recyclerView.setLayoutManager(layout);
        adapter = new RecyclerViewAdapter(mContext, msglist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layout);


    }

    private void addItem() {
        List<PushMessageBean> tt = SqlUtils.queryMethed();
        if (tt != null) {
            for (PushMessageBean pushobj : tt) {
                LogUtil.e("timecode = "+pushobj.getTimeCode()+"= baseg="+pushobj.isBadge());
            }

            msglist.addAll(tt);
            if (adapter != null) adapter.notifyDataSetChanged();
        }


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
