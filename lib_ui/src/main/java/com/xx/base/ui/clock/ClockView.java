package com.xx.base.ui.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;

/**
 * 时钟
 * @author Lixingxing
 */
public class ClockView extends View {
    private Paint mPaint;

    private float textSize = 30;

    private boolean start = true;//控制开关

    private Handler handler;


    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        handler = new Handler();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);//去锯齿
        mPaint.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        initPaint();

        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();

        final int xPoint = getWidth() / 2;
        final int yPoint = getHeight() / 2;
        final int radius = Math.min(width / 2, height / 2);

        //背景圆
        canvas.drawCircle(xPoint, yPoint, radius, mPaint);

        //中心
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(xPoint, yPoint, 10, mPaint);


        //刻度和数字
        mPaint.setColor(Color.BLACK);
        for (int i = 1; i <= 60; i++) {
            if (i % 5 == 0) {

                mPaint.setStrokeWidth(10);
                double[] p = getPointLocationOnCircle(new float[]{xPoint, yPoint}, radius, i, 60);

                double[] p1 = getPointLocationOnCircle(new float[]{xPoint, yPoint}, radius - 30, i, 60);
                canvas.drawLine((float) p[0], (float) p[1], (float) p1[0], (float) p1[1], mPaint);
                double[] p2 = getPointLocationOnCircle(new float[]{xPoint, yPoint}, radius - 50, i, 60);
                canvas.drawText(i / 5 + "", (float) p2[0] - textSize / 2, (float) p2[1] + textSize / 2, mPaint);
            } else {
                mPaint.setStrokeWidth(5);

                double[] p = getPointLocationOnCircle(new float[]{xPoint, yPoint}, radius, i, 60);

                double[] p1 = getPointLocationOnCircle(new float[]{xPoint, yPoint}, radius - 15, i, 60);
                canvas.drawLine((float) p[0], (float) p[1], (float) p1[0], (float) p1[1], mPaint);
            }
        }

        Calendar c = Calendar.getInstance();
        int second = c.get(Calendar.SECOND);
        int minute = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR);

        //秒钟
        mPaint.setStrokeWidth(3);
        double[] p = getPointLocationOnCircle(new float[]{xPoint, yPoint}, radius - 50, second, 60);
        canvas.drawLine(xPoint, yPoint, (float) p[0], (float) p[1], mPaint);

        //分钟
        mPaint.setStrokeWidth(6);
        double[] p1 = getPointLocationOnCircle(new float[]{xPoint, yPoint}, radius / 2, minute, 60);
        canvas.drawLine(xPoint, yPoint, (float) p1[0], (float) p1[1], mPaint);

        //时钟
        mPaint.setStrokeWidth(9);
        double[] p2 = getPointLocationOnCircle(new float[]{xPoint, yPoint}, radius / 4, hour, 12);
        canvas.drawLine(xPoint, yPoint, (float) p2[0], (float) p2[1], mPaint);

        if (start){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            },1000);
        }

    }

    /**
     * 得到圆等分点的坐标
     * @param centerOfCircle  圆心坐标
     * @param radius  半径
     * @param number  某等分
     * @param total 总等分数
     * @return 等分点的  横纵坐标
     */
    private double[] getPointLocationOnCircle(float[] centerOfCircle, float radius, int number, int total) {
        double[] point = new double[2];
        float x = centerOfCircle[0];
        float y = centerOfCircle[1];

        double locationX;
        double locationY;

        locationX = x + radius * Math.sin(2 * Math.PI / total * number);
        locationY = y - radius * Math.cos(2 * Math.PI / total * number);
        point[0] = locationX;
        point[1] = locationY;
        return point;
    }

}
