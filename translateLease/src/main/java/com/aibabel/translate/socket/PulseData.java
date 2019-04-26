package com.aibabel.translate.socket;

import com.aibabel.translate.utils.CommonUtils;
import com.xuhao.android.libsocket.sdk.bean.IPulseSendable;
//import com.xuhao.android.libsocket.sdk.client.bean.IPulseSendable;

import org.json.JSONObject;

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
    private String str = CommonUtils.getSN();



    @Override
    public byte[] parse() {
        byte[] body = getPulseStr().getBytes(Charset.defaultCharset());
        ByteBuffer bb = ByteBuffer.allocate(4 + 1 + body.length);
        bb.order(ByteOrder.BIG_ENDIAN);
        bb.putInt(body.length + 1);
        bb.put(new byte[]{(byte) 98});
        bb.put(body);
        return bb.array();

    }

    private String getPulseStr(){
        String pulse_str = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dev",str);
            pulse_str = jsonObject.toString();
        }catch ( Exception e){
            e.printStackTrace();
        }

        return  pulse_str;


    }



}
