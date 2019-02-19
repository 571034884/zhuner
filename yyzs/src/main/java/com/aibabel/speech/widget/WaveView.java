package com.aibabel.speech.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.SparseArray;

import com.aibabel.speech.R;
import com.aibabel.speech.util.L;


public class WaveView extends RenderView {

    private static final String TAG = "WaveView";

    private  float  change=1;
    private float line=1;



    public void setChange(float num) {
//        L.e("setChange num>>>>>>>>>>>>>>>>>>"+num);
        if (num>60) {
            num=60;
        }
        if(num==0) {
            change=1;

        }else if (num < 30) {
            change = 1 - num / 80;
            change= (change/2);
        } else {
            change=1-num/110;
            change= (change/2);
        }

        if (num >= 30) {
            line = num / 30 ;
        } else {
            line=1;
        }
        line=line*2;

    }

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*绘图*/

    private final Paint paint = new Paint();

    private final Paint clearScreenPaint = new Paint();

    private int regionStartColor = getResources().getColor(R.color.regionStartColor);
    private int regionCenterColor = getResources().getColor(R.color.regionCenterColor);
    private int regionEndColor = getResources().getColor(R.color.regionEndColor);

    {
        paint.setDither(true);
        paint.setAntiAlias(true);
    }

    private final Path firstPath = new Path();
    private final Path secondPath = new Path();
    /**
     * 两条正弦波之间的波，振幅比较低的那一条
     */
    private final Path centerPath = new Path();

    /**
     * 采样点的数量，越高越精细，
     * 但高于一定限度后人眼察觉不出。
     */
    private static final int SAMPLING_SIZE =64

            ;
    /**
     * 采样点的X
     */
    private float[] samplingX;
    /**
     * 采样点位置均匀映射到[-2,2]的X
     */
    private float[] mapX;

    /**
     * 画布宽高
     */
    private int width, height;
    /**
     * 画布中心的高度
     */
    private int centerHeight;
    /**
     * 振幅
     */
    private int amplitude;

    /**
     * 波峰和两条路径交叉点的记录，包括起点和终点，用于绘制渐变。
     * 通过日志可知其数量范围为7~9个，故这里size取9。
     * <p>
     * 每个元素都是一个float[2]，用于保存xy值
     */
    private final float[][] crestAndCrossPints = new float[9][];

    {//直接分配内存
        for (int i = 0; i < 9; i++) {
            crestAndCrossPints[i] = new float[2];
        }
    }

    /**
     * 用于处理矩形的rectF
     */
    private final RectF rectF = new RectF();
    /**
     * 绘图交叉模式。放在成员变量避免每次重复创建。
     */
    private final Xfermode clipXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    private final Xfermode clearXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

    private final Xfermode srcXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC);

    private final int backGroundColor = Color.rgb(24, 33, 41);



    @Override
    protected synchronized void onRender(Canvas canvas, long millisPassed) {
        if (samplingX == null) {//首次初始化
            //赋值基本参数
            width = canvas.getWidth();
            height = canvas.getHeight();
//            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色
            centerHeight = height >> 1;
            amplitude = width >> 3;//振幅为宽度的1/8

            //初始化采样点和映射
            samplingX = new float[SAMPLING_SIZE + 1];//因为包括起点和终点所以需要+1个位置
            mapX = new float[SAMPLING_SIZE + 1];//同上
            float gap = width / (float) SAMPLING_SIZE;//确定采样点之间的间距
            float x;
            for (int i = 0; i <= SAMPLING_SIZE; i++) {
                x = i * gap;
                samplingX[i] = x;
                mapX[i] = (x / (float) width) * 4 - 2;//将x映射到[-2,2]的区间上
            }

        }

        //清屏

        clearScreenPaint.setXfermode(clearXfermode);
        canvas.drawPaint(clearScreenPaint);
        clearScreenPaint.setXfermode(srcXfermode);


        //重置所有path并移动到起点
        firstPath.rewind();
        secondPath.rewind();
        centerPath.rewind();
        firstPath.moveTo(0, centerHeight);

        secondPath.moveTo(0, centerHeight);
        centerPath.moveTo(0, centerHeight);

        //当前时间的偏移量，通过该偏移量使得每次绘图都向右偏移，让画面动起来
        //如果希望速度快一点，可以调小分母
        float offset = millisPassed / 600F;

        //提前申明各种临时参数
        float x;
        float[] xy;

        //波形函数的值，包括上一点，当前点和下一点
        float lastV, curV = 0, nextV = (float) (amplitude * calcValue(mapX[0], offset));
        //波形函数的绝对值，用于筛选波峰和交错点
        float absLastV, absCurV, absNextV;
        //上一个筛选出的点是波峰还是交错点
        boolean lastIsCrest = false;
        //筛选出的波峰和交叉点的数量，包括起点和终点
        int crestAndCrossCount = 0;

        //遍历所有采样点
        for (int i = 0; i <= SAMPLING_SIZE; i++) {
            //计算采样点的位置
            x = samplingX[i];
            lastV = curV;
            curV = nextV;
            //提前算出下一采样点的值
            nextV = i < SAMPLING_SIZE ? (float) (amplitude * calcValue(mapX[i + 1], offset)) : 0;

            //连接路径
            firstPath.lineTo(x, centerHeight + curV/(8f*change));
            secondPath.lineTo(x, centerHeight - curV/(8f*change));
            //中间那条路径的振幅是上下的1/5
            centerPath.lineTo(x, centerHeight + curV / (24F*change));

            //记录极值点
            absLastV = Math.abs(lastV);
            absCurV = Math.abs(curV);
            absNextV = Math.abs(nextV);
            if (i == 0 || i == SAMPLING_SIZE/*起点终点*/ || (lastIsCrest && absCurV < absLastV && absCurV < absNextV)/*上一个点为波峰，且该点是极小值点*/) {
                xy = crestAndCrossPints[crestAndCrossCount++];
                xy[0] = x;
                xy[1] = 0;
                lastIsCrest = false;
            } else if (!lastIsCrest && absCurV > absLastV && absCurV > absNextV) {/*上一点是交叉点，且该点极大值*/
                xy = crestAndCrossPints[crestAndCrossCount++];
                xy[0] = x;
                xy[1] = curV;
                lastIsCrest = true;
            }
        }
        //连接所有路径到终点
        firstPath.lineTo(width, centerHeight);
        secondPath.lineTo(width, centerHeight);
        centerPath.lineTo(width, centerHeight);

        //记录layer
        int saveCount = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);  //相关知识点： http://blog.csdn.net/cquwentao/article/details/51423371

        //填充上下两条正弦函数
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        canvas.drawPath(firstPath, paint);
        canvas.drawPath(secondPath, paint);

        //绘制渐变
        paint.setStyle(Paint.Style.FILL);
        paint.setColor( getResources().getColor(R.color.regionCenterColor));
        paint.setXfermode(clipXfermode);
        float startX, crestY, endX;
        for (int i = 2; i < crestAndCrossCount; i += 2) {
            //每隔两个点可绘制一个矩形。这里先计算矩形的参数
            startX = crestAndCrossPints[i - 2][0];
            crestY = crestAndCrossPints[i - 1][1];
            endX = crestAndCrossPints[i][0];

            //crestY有正有负，无需去计算渐变是从上到下还是从下到上
            paint.setShader(new LinearGradient(0, centerHeight + crestY, 0, centerHeight - crestY, getResources().getColor(R.color.regionStartColor), getResources().getColor(R.color.regionEndColor), Shader.TileMode.CLAMP));
            rectF.set(startX, centerHeight + crestY, endX, centerHeight - crestY);
            canvas.drawRect(rectF, paint);
        }
        //清理一下
        paint.setShader(null);
        paint.setXfermode(null);

        //叠加layer，因为使用了SRC_IN的模式所以只会保留波形渐变重合的地方
        canvas.restoreToCount(saveCount);

        //绘制上弦线
        paint.setStrokeWidth(0.8f*line);
//        L.e("---------"+1.5f*change);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(regionStartColor);
        canvas.drawPath(firstPath, paint);

        //绘制下弦线
        paint.setColor(regionEndColor);
        canvas.drawPath(secondPath, paint);

        //绘制中间线
        paint.setStrokeWidth(0.2f*line);
        paint.setColor(regionCenterColor);
        canvas.drawPath(centerPath, paint);

    }

    /**
     * 计算波形函数中x对应的y值
     *
     * @param mapX   换算到[-2,2]之间的x值
     * @param offset 偏移量
     * @return
     */
    private double calcValue(float mapX, float offset) {
        int keyX = (int) (mapX*1000);
        offset %= 2;
        double sinFunc = Math.sin(0.75 * Math.PI * mapX - offset * Math.PI);
        double recessionFunc;
        if(recessionFuncs.indexOfKey(keyX) >=0 ){
            recessionFunc = recessionFuncs.get(keyX);
        }else {
            recessionFunc = Math.pow(4/ (4+ Math.pow(mapX, 4)), 2.5);
            recessionFuncs.put(keyX,recessionFunc);
        }
        return sinFunc * recessionFunc;
    }

    SparseArray<Double> recessionFuncs = new SparseArray<>();
}
