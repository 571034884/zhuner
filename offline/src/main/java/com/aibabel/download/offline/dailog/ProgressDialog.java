package com.aibabel.download.offline.dailog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aibabel.download.offline.R;
import com.daimajia.numberprogressbar.NumberProgressBar;

public class ProgressDialog {


    public static class Builder {

        private Context mContext;
        private Dialog mDialog;
        private ProgressDialog.Builder.ViewHolder mViewHolder;
        private Handler mViewUpdateHandler;
        private View mView;

        public Builder(Activity context) {
            mContext = context;
            initView();
        }

          public ProgressDialog.Builder setTitle(CharSequence title) {
            mViewHolder.tvTitle.setText(title);
            return this;
        }

        public ProgressDialog.Builder setDesc(CharSequence title) {
            mViewHolder.tvDesc.setText(title);
            return this;
        }


        public ProgressDialog.Builder setTitle(CharSequence title, int color) {
            mViewHolder.tvTitle.setText(title);
            mViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, color));
            return this;
        }

        public ProgressDialog.Builder setTitle(int resid) {
            mViewHolder.tvTitle.setText(resid);
            return this;
        }

        public ProgressDialog.Builder setTitle(int resid, int color) {
            mViewHolder.tvTitle.setText(resid);
            mViewHolder.tvTitle.setTextColor(ContextCompat.getColor(mContext, color));
            return this;
        }

        public ProgressDialog.Builder setCancelable(boolean flag) {
            mDialog.setCancelable(flag);
            return this;
        }

        public ProgressDialog.Builder setCanceledOnTouchOutside(boolean flag) {
            mDialog.setCanceledOnTouchOutside(flag);
            return this;
        }

        public ProgressDialog.Builder showProgress(boolean flag) {
            mViewHolder.progressBar.setVisibility(flag ? View.VISIBLE : View.GONE);
            return this;
        }

        public void setProgress(int precent) {
            mViewUpdateHandler.sendEmptyMessage(precent);
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

        private void initView() {
            mDialog = new Dialog(mContext, com.jiangyy.easydialog.R.style.EasyDialogStyle);
            mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null);
            mViewHolder = new ProgressDialog.Builder.ViewHolder(mView);
            mDialog.setContentView(mView);





            mViewUpdateHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    mViewHolder.progressBar.setProgress(msg.what);
                }
            };
            mViewUpdateHandler.sendEmptyMessage(0);
        }


        class ViewHolder {

            TextView tvTitle;
            TextView tvDesc;
            NumberProgressBar progressBar;


            public ViewHolder(View view) {
                tvTitle = view.findViewById(R.id.dialog_title);
                progressBar = view.findViewById(R.id.dialog_number_progress_bar);
                tvDesc = view.findViewById(R.id.dialog_desc);

            }
        }

    }
}
