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
import com.matrixxun.starry.badgetextview.MaterialBadgeTextView;

import java.util.ArrayList;

/**
 * Created by User on 1/1/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<MessageBean> message = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<MessageBean> listmsg ) {
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
            final MessageBean tempmsg = message.get(position);
            holder.MsgTitle.setText(tempmsg.getTitle());
            holder.MsgInfo.setText(tempmsg.getInfo());
            holder.MsgTime.setText(tempmsg.getTime());
            if(tempmsg.isBadge()){
                holder.tempbadged.setVisibility(View.VISIBLE);
            }


            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: clicked on: " + message.get(position));
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra("image_name", tempmsg.getInfo());
                    mContext.startActivity(intent);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
         if(message!=null){
             return message.size();
         } else return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView MsgTitle,MsgInfo,MsgTime;
        LinearLayout parentLayout;
        MaterialBadgeTextView tempbadged;

        public ViewHolder(View itemView) {
            super(itemView);
            MsgTitle = itemView.findViewById(R.id.msgtitle);
            MsgInfo = itemView.findViewById(R.id.msg_info);
            MsgTime = itemView.findViewById(R.id.msgtime);
            tempbadged= itemView.findViewById(R.id.badgetextview);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}















