package com.aibabel.speech.tcp;

import com.xuhao.android.libsocket.sdk.bean.IPulseSendable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 *==========================================================================================
 * @Author：CreateBy 张文颖
 *
 * @Date：2018/4/24
 *
 * @Desc：心跳bean
 *==========================================================================================
 */
public class PulseData implements IPulseSendable {
    private String str = "pulse";
    @Override
    public byte[] parse() {
        byte[] body = str.getBytes(Charset.defaultCharset());
        ByteBuffer bb = ByteBuffer.allocate(4 + 1 + body.length);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putInt(body.length + 1);
        bb.put(new byte[]{(byte) 98});
        bb.put(body);
        return bb.array();
    }
}
