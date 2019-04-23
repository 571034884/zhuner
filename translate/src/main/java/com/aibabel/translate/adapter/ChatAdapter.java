package com.aibabel.translate.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.aibabel.translate.R;
import com.aibabel.translate.bean.MessageBean;
import com.aibabel.translate.utils.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;

import java.util.ArrayList;
import java.util.List;


public class ChatAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {



    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;
    private static final int TYPE_SEND_CH = 1;
    private static final int TYPE_SEND_EN = 2;
    private static final int SEND_CH = R.layout.item_send_ch;
    private static final int SEND_EN = R.layout.item_send_en;
    private List<MessageBean> list;
    ChatOnItemClickListener listener;

    /**
     * 防止Checkbox错乱 做setTag  getTag操作
     */
    private SparseBooleanArray mCheckStates = new SparseBooleanArray();
    private boolean showCheckBox = false;

    public ChatAdapter(Context context, List<MessageBean> data) {
        super(data);
        list = data;
        setMultiTypeDelegate(new MultiTypeDelegate<MessageBean>() {
            @Override
            protected int getItemType(MessageBean entity) {
                String from = entity.getFrom();
                return TextUtils.equals(from, "zh") ? TYPE_SEND_CH : TYPE_SEND_EN;
            }
        });
        getMultiTypeDelegate().registerItemType(TYPE_SEND_CH, SEND_CH)
                .registerItemType(TYPE_SEND_EN, SEND_EN);
    }


    @Override
    protected void convert(final BaseViewHolder helper, MessageBean item) {
        final CheckBox cb = helper.getView(R.id.cb_item_check);
        final LinearLayout llRoot = helper.getView(R.id.ll_item_root);
        final int position = helper.getLayoutPosition();
        Log.e("postiosn:",position+"");
        cb.setTag(position);
        //判断当前checkbox的状态
        if (showCheckBox) {
            cb.setVisibility(View.VISIBLE);
            //防止显示错乱
            cb.setChecked(mCheckStates.get(position, false));
        } else {
            cb.setVisibility(View.GONE);
            //取消掉Checkbox后不再保存当前选择的状态
            cb.setChecked(false);
            mCheckStates.clear();
        }
        //点击监听
        llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showCheckBox) {
                    cb.setChecked(!cb.isChecked());
                    listener.onItemClick(helper.itemView, position);
                }
            }
        });

        cb.setClickable(false);
        //对checkbox的监听 保存选择状态 防止checkbox显示错乱
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int pos = (int) compoundButton.getTag();
                if (b) {
                    mCheckStates.put(pos, true);
                } else {
                    mCheckStates.delete(pos);
                }
            }
        });

        setContent(helper, item);
    }


    private void setContent(BaseViewHolder helper, MessageBean item) {
        helper.setText(R.id.tv_item_asr, item.getTrans_text());
        helper.setText(R.id.tv_item_mt, item.getTrans_result());
        helper.setText(R.id.tv_item_time,StringUtils.formatDate(item.getTime()));
        //控制显示时间
        if (helper.getLayoutPosition() == 0&&list.size()>0) {
            helper.getView(R.id.tv_item_time).setVisibility(View.VISIBLE);
            helper.itemView.setTag(FIRST_STICKY_VIEW);
        } else {
            if (!StringUtils.formatDate(item.getTime()).equals(StringUtils.formatDate(list.get(helper.getLayoutPosition() - 1).getTime()))) {
                helper.getView(R.id.tv_item_time).setVisibility(View.VISIBLE);
                helper.itemView.setTag(HAS_STICKY_VIEW);
            } else {
                helper.getView(R.id.tv_item_time).setVisibility(View.GONE);
                helper.itemView.setTag(NONE_STICKY_VIEW);
            }
        }

    }



    public void setCheckBoxVisibility(boolean visibility) {
        showCheckBox = visibility;
        notifyDataSetChanged();
    }


    /**
     * 全选
     * @param isCheckAll
     */
    public void checkAll(boolean isCheckAll) {
        mCheckStates.clear();
        for (int i = 0; i < list.size(); i++) {
            mCheckStates.put(i, isCheckAll);
        }
        notifyDataSetChanged();
        Log.e("list的size,adapter",list.size()+"");
    }

    //接口回调设置点击事件
    public interface ChatOnItemClickListener {
        //点击事件
        void onItemClick(View view, int position);

    }
    //设置点击事件
    public void setChatOnItemClickListener(ChatOnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }



}
