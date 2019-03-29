package com.aibabel.scenic.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.TextureView;
import android.widget.ImageView;

import com.aibabel.scenic.R;
import com.aibabel.scenic.bean.ScenicBean;
import com.aibabel.scenic.bean.ScenicListBean;
import com.aibabel.scenic.bean.SpotsBean;
import com.aibabel.scenic.utils.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.w3c.dom.Text;

import java.util.List;

public class Adapter_Scenics extends BaseQuickAdapter<ScenicListBean.DataBean, BaseViewHolder> {



    public Adapter_Scenics(int layoutResId, @Nullable List<ScenicListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScenicListBean.DataBean item) {
        helper.setText(R.id.tv_item_name, item.getName());
        helper.setText(R.id.tv_item_sub, String.valueOf(item.getSubcount())+"处景点");
        // 加载网络图片
        Glide.with(mContext).load(item.getCover()).into((ImageView) helper.getView(R.id.iv_item_img));
        if(TextUtils.equals(item.getIsMy(),"1")){
            Glide.with(mContext).load(R.mipmap.ic_store).into((ImageView) helper.getView(R.id.iv_item_save));
        }else{
            Glide.with(mContext).load(R.mipmap.ic_unstore).into((ImageView) helper.getView(R.id.iv_item_save));
        }
        helper.addOnClickListener(R.id.iv_item_save);

    }
}
