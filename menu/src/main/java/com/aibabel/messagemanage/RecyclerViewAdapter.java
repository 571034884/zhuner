package com.aibabel.messagemanage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aibabel.menu.R;
import com.aibabel.menu.bean.PushMessageBean;
import com.aibabel.menu.util.LogUtil;
import com.aibabel.messagemanage.sqlite.SqlUtils;
import com.matrixxun.starry.badgetextview.MaterialBadgeTextView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 1/1/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<PushMessageBean> message = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<PushMessageBean> listmsg) {
        message = listmsg;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        try {
            final PushMessageBean tempmsg = message.get(position);
            holder.MsgTitle.setText(tempmsg.getTitle());
            holder.MsgInfo.setText(tempmsg.getContent());


            LogUtil.e("tempmsg.isBadge()=" + tempmsg.isBadge());
            if (tempmsg.isBadge()) {
                holder.tempbadged.setVisibility(View.VISIBLE);
            } else {
                holder.tempbadged.setVisibility(View.INVISIBLE);
            }


            try {
                String time = MessageUtil.getShowRealtime(tempmsg.getTimeCode());
                holder.MsgTime.setText(time);
            } catch (Exception e) {

            }


            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Log.d(TAG, "onClick: clicked on: " + message.get(position));
                    // Log.d(TAG, "onClick: position: " + position);
                    try {
                        LogUtil.e("setOnClickListener pos = " + position);
                        PushMessageBean push_bean = message.get(position);
                        //push_bean.setBadge(false);
                        message.set(position, push_bean);
                        LogUtil.e("push_bean.getId = " + push_bean.getId());
                        push_bean.setBadge(false);
                        push_bean.update(push_bean.getId());
                        push_bean.save();

                        notifyItemChanged(position, push_bean);

                        PushMessageBean new_push_bean = SqlUtils.queryjsonById(push_bean.getId());
                        MessageUtil.openNotification_pushbean(mContext, new_push_bean);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if (message != null) {
            return message.size();
        } else return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView MsgTitle, MsgInfo, MsgTime;
        LinearLayout parentLayout;
        MaterialBadgeTextView tempbadged;

        public ViewHolder(View itemView) {
            super(itemView);
            MsgTitle = itemView.findViewById(R.id.msgtitle);
            MsgInfo = itemView.findViewById(R.id.msg_info);
            MsgTime = itemView.findViewById(R.id.msgtime);
            tempbadged = itemView.findViewById(R.id.badgetextview);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}















