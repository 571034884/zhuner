package com.aibabel.translate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.aibabel.translate.R;
import com.aibabel.translate.app.BaseApplication;
import com.aibabel.translate.bean.AsrAndTranResultBean;
import com.aibabel.translate.offline.ChangeOffline;
import com.aibabel.translate.socket.SocketManger;
import com.aibabel.translate.utils.CommonUtils;
import com.aibabel.translate.utils.Constant;
import com.aibabel.translate.utils.FastJsonUtil;
import com.aibabel.translate.utils.StringUtils;
import com.aibabel.translate.utils.ThreadPoolManager;
import com.aibabel.translate.utils.ToastUtil;
import com.aibabel.translate.view.CustomProgress;

import java.nio.charset.Charset;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditActivity extends BaseActivity implements SocketManger.OnReceiveListener {


    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.et_edit)
    EditText etEdit;
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    private String from;
    private String to;
    private String text;
    private int index = 1;
    private CustomProgress dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        ivClose.setOnClickListener(this);
        btConfirm.setOnClickListener(this);
        text = getIntent().getStringExtra("text");
        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");
        etEdit.setText(text + "");

        etEdit.setSelection(etEdit.getText().length());

        etEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0) {
                    Log.e("Clickable", "true");
//                    btCommit.setClickable(true);
                    btConfirm.setEnabled(true);
                } else {
                    btConfirm.setEnabled(false);
                    Log.e("Clickable", "false");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog = new CustomProgress(this, R.style.progress);

        SocketManger.getInstance().setOnReceiveListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                this.finish();
                break;
            case R.id.bt_confirm:
                text = StringUtils.deleteNbsp(etEdit.getText().toString().trim());
                if (TextUtils.isEmpty(text)) {
                    ToastUtil.showShort("不能为空！");
                    return;
                }
                dialog.show();
                btConfirm.setClickable(false);
                sendText(from, to, text);
                break;
        }
    }


    /**
     * 发送数据
     *
     * @param from 源语言
     * @param to   目标语言
     * @param asr  待翻译的内容
     */
    private void sendText(final String from, final String to, final String asr) {
        if (CommonUtils.isAvailable()) {
            sendAsr(from, to, asr);
        } else {
            //先获取离线支持的语言列表，然后判断是否支持当前离线
            ChangeOffline.getInstance().getOfflineList();
            boolean isSupport = ChangeOffline.getInstance().isContansAsr();
            if (isSupport) {
                //如果模型没有启动就启动翻译模型
                ChangeOffline.getInstance().createOrChange();
                //判定模型是否启动完成完成
                if (isJumpLanguage()) {
                    ChangeOffline.getInstance().test();
                    return;
                }
                //判定当前是否为正在翻译或者识别
                if (!BaseApplication.isTran)
                    return;

                //启动线程调用离线翻译
                ThreadPoolManager.getInstance().addTask(new Runnable() {
                    @Override
                    public void run() {
                        //获取翻译结果
                        final String mt = ChangeOffline.getInstance().getTran(from, to).tran(text, index);
                        final String res[] = mt.split("_");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.cancle();
                                if (!TextUtils.isEmpty(mt) && res.length == 2) {
                                    if (res[1].equals("" + index)) {
                                        setFragmentResult(asr, res[0], "", Constant.FLAG_OFFLINE);
                                    }
                                    btConfirm.setClickable(true);
                                } else {
                                    btConfirm.setClickable(true);
                                }
                            }
                        });

                    }
                });

            } else {
                dialog.cancle();
                btConfirm.setClickable(true);
                ToastUtil.showShort("当前语言不支持离线！");
            }

        }
    }


    @Override
    public void onSuccess(int flag, byte[] result) {

        String json = new String(result, Charset.forName("utf-8"));
        AsrAndTranResultBean mtBean = FastJsonUtil.changeJsonToBean(json, AsrAndTranResultBean.class);
        String mt = mtBean.getInfo();
        setFragmentResult(text, mt, "", Constant.FLAG_ONLINE);

    }

    @Override
    public void onError(int flag, String result) {
        dialog.cancle();
        SocketManger.getInstance().disconnect();

        switch (flag) {
            case Constant.CONNECTION_FAILED://
                ToastUtil.showShort(getString(R.string.error_connect));
                break;
            case Constant.TIMEOUT_CONNECTION:
                ToastUtil.showShort(getString(R.string.timeout_connect));
                break;
            case Constant.TIMEOUT_READ:
                ToastUtil.showShort(getString(R.string.timeout_read));
                break;
            case Constant.RESPONSE_ERROR:
                ToastUtil.showShort(getString(R.string.error_response));
                break;

            default:
                break;
        }

    }

    @Override
    public void onFinish() {
        dialog.cancle();
        btConfirm.setClickable(true);
        SocketManger.getInstance().disconnect();
    }

    /**
     * 将翻译结果返回fragment中
     *
     * @param asr
     * @param mt
     * @param en
     * @param flag
     */
    private void setFragmentResult(String asr, String mt, String en, int flag) {
        Intent intent = new Intent();
        intent.putExtra("asr", asr);
        intent.putExtra("mt", mt);
        intent.putExtra("en", en);
        intent.putExtra("flag", flag);
        setResult(201, intent);
        this.finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        SocketManger.getInstance().disconnect();
    }


    /**
     * 是否可以跳转语言列表
     *
     * @return
     */
    public boolean isJumpLanguage() {
        if (ChangeOffline.getInstance().getMapNum() > 0) {
            ToastUtil.showShort(getString(R.string.chuangjian));
            return true;
        }

        return false;
    }
}
