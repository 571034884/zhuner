package com.aibabel.translate.adapter;

import android.content.Context;
import com.aibabel.translate.R;
import com.aibabel.translate.bean.MessageBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import java.util.List;


public class ChatAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {


    private static final int TYPE_SEND_TEXT = 1;


    private static final int SEND_TEXT = R.layout.item_text_send;



    public ChatAdapter(Context context, List<MessageBean> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<MessageBean>() {
            @Override
            protected int getItemType(MessageBean entity) {
            return 1;
            }
        });
        getMultiTypeDelegate().registerItemType(TYPE_SEND_TEXT, SEND_TEXT);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        setContent(helper, item);
        setStatus(helper, item);
        setOnClick(helper, item);

    }


    private void setStatus(BaseViewHolder helper, MessageBean item) {


    }

    private void setContent(BaseViewHolder helper, MessageBean item) {


//            helper.setText(R.id.chat_item_content_text, msgBody.getMessage());
    }


    private void setOnClick(BaseViewHolder helper, MessageBean item) {
//        MsgBody msgContent = item.getBody();
//        if (msgContent instanceof AudioMsgBody) {
//            helper.addOnClickListener(R.id.rlAudio);
//        }
    }

}
