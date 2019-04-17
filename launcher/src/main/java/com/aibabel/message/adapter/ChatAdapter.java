package com.aibabel.message.adapter;

import android.content.Context;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMMessage;

import java.util.List;


public class ChatAdapter extends BaseQuickAdapter<EMMessage, BaseViewHolder> {


    private static final int TYPE_SEND_TEXT = 1;
    private static final int TYPE_RECEIVE_TEXT = 2;
    private static final int TYPE_SEND_IMAGE = 3;
    private static final int TYPE_RECEIVE_IMAGE = 4;

    private static final int SEND_TEXT = R.layout.item_text_send;
    private static final int RECEIVE_TEXT = R.layout.item_text_receive;
    private static final int SEND_IMAGE = R.layout.item_image_send;
    private static final int RECEIVE_IMAGE = R.layout.item_image_receive;
    /*
    private static final int SEND_LOCATION = R.layout.item_location_send;
    private static final int RECEIVE_LOCATION = R.layout.item_location_receive;*/


    public ChatAdapter(Context context, List<EMMessage> data) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<EMMessage>() {
            @Override
            protected int getItemType(EMMessage entity) {
                boolean isSend = entity.direct()==EMMessage.Direct.SEND;
                if (EMMessage.Type.TXT == entity.getType()) {
                    return isSend ? TYPE_SEND_TEXT : TYPE_RECEIVE_TEXT;
                } else if (EMMessage.Type.IMAGE == entity.getType()) {
                    return isSend ? TYPE_SEND_IMAGE : TYPE_RECEIVE_IMAGE;
                }
                return 0;
            }
        });
        getMultiTypeDelegate().registerItemType(TYPE_SEND_TEXT, SEND_TEXT)
                .registerItemType(TYPE_RECEIVE_TEXT, RECEIVE_TEXT)
                .registerItemType(TYPE_SEND_IMAGE, SEND_IMAGE)
                .registerItemType(TYPE_RECEIVE_IMAGE, RECEIVE_IMAGE);
    }

    @Override
    protected void convert(BaseViewHolder helper, EMMessage item) {
        setContent(helper, item);
        setStatus(helper, item);

    }


    private void setStatus(BaseViewHolder helper, EMMessage item) {
        if (EMMessage.Type.TXT == item.getType()) {
            //只需要设置自己发送的状态
            boolean isSend = item.direct()==EMMessage.Direct.SEND;
            if (isSend) {
                if (item.status() == EMMessage.Status.INPROGRESS) {
                    helper.setVisible(R.id.chat_item_progress, true).setVisible(R.id.chat_item_fail, false);
                } else if (item.status() == EMMessage.Status.FAIL) {
                    helper.setVisible(R.id.chat_item_progress, false).setVisible(R.id.chat_item_fail, true);
                } else if (item.status() == EMMessage.Status.SUCCESS) {
                    helper.setVisible(R.id.chat_item_progress, false).setVisible(R.id.chat_item_fail, false);
                }
            }
        }

    }

    private void setContent(BaseViewHolder helper, EMMessage item) {
        if (item.getType()==EMMessage.Type.TXT) {
//            TextMsgBody msgBody = (TextMsgBody) item.getBody();
            helper.setText(R.id.chat_item_content_text, item.getBody().toString());
        } else if (item.getType()==EMMessage.Type.IMAGE) {
//            ImageMsgBody msgBody = (ImageMsgBody) item.getBody();
            Glide.with(mContext).load("").into((ImageView) helper.getView(R.id.bivPic));
//            GlideUtils.loadChatImage(mContext, msgBody.getThumbUrl(), (ImageView) helper.getView(R.id.bivPic));
//            if (TextUtils.isEmpty(msgBody.getThumbPath())) {
//                GlideUtils.loadChatImage(mContext, msgBody.getThumbUrl(), (ImageView) helper.getView(R.id.bivPic));
//            } else {
//                File file = new File(msgBody.getThumbPath());
//                if (file.exists()) {
//                    GlideUtils.loadChatImage(mContext, msgBody.getThumbPath(), (ImageView) helper.getView(R.id.bivPic));
//                } else {
//                    GlideUtils.loadChatImage(mContext, msgBody.getThumbUrl(), (ImageView) helper.getView(R.id.bivPic));
//                }
//            }
        }
    }


}
