package com.aibabel.map.bean;

import java.io.Serializable;

/**
 * Created by fytworks on 2018/12/5.
 */

public class TrafficBean implements Serializable{

    /**
     *
     0：无效
     1：直行
     2：右前方转弯
     3：右转
     4：右后方转弯
     5：掉头
     6：左后方转弯
     7：左转
     8：左前方转弯
     9：左侧
     10：右侧
     11：分歧-左
     12：分歧中央
     13：分歧右
     14：环岛
     15：进渡口
     16：出渡口
     //----------默认新加类型-------------
     17：起点
     18：终点
     */
    private int turn;//标识
    private String name;//详情

    public TrafficBean() {
    }

    public TrafficBean(int turn, String name) {
        this.turn = turn;
        this.name = name;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
