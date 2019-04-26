package com.aibabel.translate.bean;

//import com.xuhao.android.common.interfacies.client.msg.ISendable;

import com.xuhao.android.libsocket.sdk.bean.ISendable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Tony on 2017/10/24.
 */

public class ByteDataBean implements ISendable {
    private byte[] content;
    private int cmd;

    public ByteDataBean(int cmd, byte[] content) {
        this.cmd = cmd;
        this.content = content;
    }

    @Override
    public byte[] parse() {
        ByteBuffer bb = ByteBuffer.allocate(4 + 1 + content.length);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putInt(content.length + 1);
        bb.put(new byte[]{(byte) cmd});
        bb.put(content);
        return bb.array();
    }
}
