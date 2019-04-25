//package com.aibabel.message.fragment;
//
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.aibabel.baselibrary.base.BaseFragment;
//import com.aibabel.menu.R;
//import com.aibabel.menu.bean.PushMessageBean;
//import com.aibabel.menu.utils.LogUtil;
//import com.aibabel.message.adapter.RecyclerViewAdapter;
//import com.aibabel.message.sqlite.SqlUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//
//public class MainActivity extends BaseFragment {
//
//    private ArrayList<PushMessageBean> msglist = new ArrayList<>();
//    RecyclerViewAdapter adapter;
//
//
//    @BindView(R.id.image_close)
//    ImageView image_close;
//    @BindView(R.id.recyclerv_view)
//    RecyclerView recyclerView;
//
//    public void initContent() {
//        initRecyclerView();
//        addItem();
//
//        //ImageView image_close = findViewById(R.id.image_close);
//        image_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                HashMap<String, Serializable> map = new HashMap<>();
////                map.put("menu_notice_close_id","关闭");
////                addStatisticsEvent("menu_notice_close",map);
//
//                getActivity().finish();
//            }
//        });
//
//    }
//
//
//    String TAG  = "TAG";
//    @Override
//    public int getLayout() {
//        return R.layout.activity_main_mm;
//    }
//
//    @Override
//    public void init(View view, Bundle savedInstanceState) {
//        initContent();
//    }
//
//    private void initRecyclerView() {
//        Log.d(TAG, "initRecyclerView: init recyclerview.");
////        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
//
//        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
//        layout.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
//        layout.setReverseLayout(true);//列表翻转
//        //recyclerView.setLayoutManager(layout);
//
//
//        adapter = new RecyclerViewAdapter(getActivity(), msglist);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(layout);
//
//
//    }
//
//    private void addItem() {
////        PushMessageBean msgbean = new PushMessageBean();
////        msgbean.setTitle("你有附近3个景点");
////        msgbean.setContent("雍和宫、天坛、故宫，12345667890");
////        msgbean.setTimeCode("");
////        msgbean.setBadge(false);
////        PushMessageBean msgbean2= new PushMessageBean();
////        msgbean2.setTitle("你有附近3个景点");
////        msgbean2.setContent("雍和宫、天坛、故宫，12345667890");
////        msgbean2.setTimeCode("");
////
////        PushMessageBean msgbean3= new PushMessageBean();
////        msgbean3.setTitle("你有附近3个景点");
////        msgbean3.setContent("雍和宫、天坛、故宫，12345667890");
////        msgbean3.setTimeCode("");
////        msgbean3.setBadge(true);
////
////        msglist.add(msgbean2);
////        msglist.add(msgbean);
////        msglist.add(msgbean3);
//
//        List<PushMessageBean> tt = SqlUtils.queryMethed();
//        if (tt != null) {
//            for (PushMessageBean pushobj : tt) {
//
//                LogUtil.e("timecode = "+pushobj.getTimeCode()+"= baseg="+pushobj.isBadge());
//
//            }
//
//            msglist.addAll(tt);
//            if (adapter != null) adapter.notifyDataSetChanged();
//        }
//
//
////        updateMessBean(tt.get(0),tt.get(0).getId());
////        PushMessageBean tttttt = tt.get(0);
////        tttttt.setContent("update ");
////        tttttt.setBadge(false);
////        tttttt.update(tttttt.getId());
//
////        tt = SqlUtils.queryMethed();
////        if (tt != null) {
////            for (PushMessageBean pushobj : tt) {
////                LogUtil.e("new pushobj = " + pushobj.isBadge());
////                LogUtil.e("new pushobj id= " + pushobj.getId());
////            }
////        }
//
//    }
//
//    public  static void updateMessBean(PushMessageBean sqlbean,Long ID) {
//        sqlbean.setBadge(false);
//        sqlbean.update(ID);
//        sqlbean.save();
//
//        List<PushMessageBean> tt = SqlUtils.queryMethed();
//        if (tt != null) {
//            for (PushMessageBean pushobj : tt) {
//                LogUtil.e("query = id" + pushobj.getId() + " new  = " + pushobj.isBadge());
//            }
//        }
//
//    }
//
//
//}
