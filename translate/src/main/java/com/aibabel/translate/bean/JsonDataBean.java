package com.aibabel.translate.bean;



//import com.xuhao.android.common.interfacies.client.msg.ISendable;

import com.xuhao.android.libsocket.sdk.bean.ISendable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * Created by Tony on 2017/10/24.
 */

public class JsonDataBean implements ISendable {
    private String content;
    private int cmd;

    public JsonDataBean(int cmd, String content) {
        this.cmd = cmd;
        this.content = content;
    }

    @Override
    public byte[] parse() {
        byte[] body = content.getBytes(Charset.defaultCharset());
        ByteBuffer bb = ByteBuffer.allocate(4 + 1 + body.length);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putInt(body.length + 1);
        bb.put(new byte[]{(byte) cmd});
        bb.put(body);
        return bb.array();
    }
}
