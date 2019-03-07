package com.aibabel.translate.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;

import com.aibabel.translate.R;
import com.aibabel.translate.bean.MessageBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import java.util.List;


public class ChatAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {


    private static final int TYPE_SEND_CH = 1;
    private static final int TYPE_SEND_EN = 2;


    private static final int SEND_CH = R.layout.item_send_ch;
    private static final int SEND_EN = R.layout.item_send_en;
    private static final int SEND_DATE = R.layout.item_send_en;
    /**
     * 防止Checkbox错乱 做setTag  getTag操作
     */
    private SparseBooleanArray mBooleanArray = new SparseBooleanArray();


    public ChatAdapter(Context context, List<MessageBean> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<MessageBean>() {
            @Override
            protected int getItemType(MessageBean entity) {
                String from = entity.getFrom();
                return from=="ch" ? TYPE_SEND_CH : TYPE_SEND_EN;
            }
        });
        getMultiTypeDelegate().registerItemType(TYPE_SEND_CH, SEND_CH)
                .registerItemType(TYPE_SEND_EN, SEND_EN);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        setContent(helper, item);
//        setStatus(helper, item);
//        setOnClick(helper, item);
//        setOnLongClick(helper, item);

    }


//    private void setStatus(BaseViewHolder helper, MessageBean item) {
//
//
//    }

    private void setContent(BaseViewHolder helper, MessageBean item) {
            helper.setText(R.id.tv_item_asr, item.getTrans_text());
            helper.setText(R.id.tv_item_mt, item.getTrans_result());
    }


    private void setOnClick(BaseViewHolder helper, MessageBean item) {
//        MsgBody msgContent = item.getBody();
//        if (msgContent instanceof AudioMsgBody) {
//            helper.addOnClickListener(R.id.rlAudio);
//        }
    }
    private void setOnLongClick(BaseViewHolder helper, MessageBean item) {

//        helper.addOnLongClickListener(R.id.rlAudio);
    }


    public void setItemChecked(int position) {

//        if (mLastCheckedPosition == position)
//
//            return;
//
//        mBooleanArray.put(position, true);
//
//        if (mLastCheckedPosition-1) {
//            mBooleanArray.put(mLastCheckedPosition,false);
//            mAdapter.notifyItemChanged(mLastCheckedPosition);
//        }
//
//        mAdapter.notifyDataSetChanged();
//
//        mLastCheckedPosition = position;
    }




}
