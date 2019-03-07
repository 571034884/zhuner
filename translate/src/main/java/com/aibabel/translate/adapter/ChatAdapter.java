package com.aibabel.translate.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;

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
    private boolean showCheckBox = false;

    public ChatAdapter(Context context, List<MessageBean> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<MessageBean>() {
            @Override
            protected int getItemType(MessageBean entity) {
                String from = entity.getFrom();
                return TextUtils.equals(from, "ch") ? TYPE_SEND_CH : TYPE_SEND_EN;
            }
        });
        getMultiTypeDelegate().registerItemType(TYPE_SEND_CH, SEND_CH)
                .registerItemType(TYPE_SEND_EN, SEND_EN);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        setContent(helper, item);
    }


    private void setContent(BaseViewHolder helper, MessageBean item) {
        helper.setText(R.id.tv_item_asr, item.getTrans_text());
        helper.setText(R.id.tv_item_mt, item.getTrans_result());

        if (showCheckBox) {
            helper.getView(R.id.cb_item_select).setVisibility(View.VISIBLE);
            //防止显示错乱
//            helper.setChecked(R.id.cb_item_select,item.isChecked());
        } else {
            helper.getView(R.id.cb_item_select).setVisibility(View.GONE);
        }
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


    public void setCheckBoxVisibility(boolean visibility) {
        showCheckBox = visibility;
        notifyDataSetChanged();
    }
}
