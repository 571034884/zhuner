package com.aibabel.menu.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


public class CustomDialog {

    public static class Builder {

        private Context mContext;
        private Dialog mDialog;
//        private ChangeCityDialog.Builder.ViewHolder mViewHolder;
        private View mView;

        public Builder(Activity context,int LayoutId) {
            mContext = context;
            initView(LayoutId);
        }



        public CustomDialog.Builder setTv(int lauoutId, CharSequence title) {
            ((TextView) mView.findViewById(lauoutId)).setText(title);
            return this;
        }

        public CustomDialog.Builder setTvListener(int lauoutId, String name, View.OnClickListener click) {
            if (!TextUtils.isEmpty(name)) {
                ((TextView) mView.findViewById(lauoutId)).setText(name);
            }
            ((TextView) mView.findViewById(lauoutId)).setOnClickListener(click);
            return this;
        }
//        public ChangeCityDialog.Builder setSureListener( View.OnClickListener click) {
//            mViewHolder.tvSure.setOnClickListener(click);
//            return this;
//        }

        public CustomDialog.Builder setCancelable(boolean flag) {
            mDialog.setCancelable(flag);
            return this;
        }

        public CustomDialog.Builder setCanceledOnTouchOutside(boolean flag) {
            mDialog.setCanceledOnTouchOutside(flag);
            return this;
        }




        public Dialog create() {
            return mDialog;
        }

        public void show() {
            if (mDialog != null) {
                mDialog.show();
            }
        }

        public void dismiss() {
            if (mDialog != null) {
                mDialog.dismiss();
            }
        }

        public boolean isShowing() {
            if (mDialog != null) {
                return mDialog.isShowing();
            }
            return false;
        }

        private void initView(int layoutId) {
            mDialog = new Dialog(mContext);
            mView = LayoutInflater.from(mContext).inflate(layoutId, null);
//            mViewHolder = new ChangeCityDialog.Builder.ViewHolder(mView);
            mDialog.setContentView(mView);


        }


//        class ViewHolder {
//
//
//            TextView tvDesc;
//
//            TextView tvCancle;
//            TextView tvSure;
//
//
//
//
//            public ViewHolder(View view) {
//                tvDesc = view.findViewById(R.id.dialog_change_content);
//                tvCancle = view.findViewById(R.id.dialog_change_btm_cancle);
//
//                tvSure = view.findViewById(R.id.dialog_change_btm_sure);
//
//            }
//        }

    }
}
