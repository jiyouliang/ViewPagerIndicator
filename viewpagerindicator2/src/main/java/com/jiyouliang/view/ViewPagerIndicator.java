package com.jiyouliang.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by JiYouLiang on 2018/07/19.
 */

public class ViewPagerIndicator extends LinearLayout {
    private Paint mPaint;
    private Path mPath;
    private int mTriangleWidth;
    private int mTriangleHeight;
    private static final float RADIUS_TRIANGLE_WIDTH = 1 / 6F;
    private int mInitTranslationX;//初始化x轴偏移
    private int mTranslationX;//移动时的偏移

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(Color.WHITE);//画笔颜色
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));//避免过于尖锐
    }

    /**
     * 控件大小发生改变时调用，该方法中可以获取控件宽高、获取根据或者的宽高设置控件大小
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w / 3 * RADIUS_TRIANGLE_WIDTH);//三角形宽度
        mInitTranslationX = w / 3 / 2 - mTriangleWidth / 2;//初始化三角形指示器位置
        initTriangle();
    }

    /**
     * 初始化三角形
     */
    private void initTriangle() {
        mTriangleHeight = mTriangleWidth / 2;//三角形高度
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();//闭合路径
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //绘制三角形
        canvas.save();//保存之前状态
        //平移画布
        canvas.translate(mInitTranslationX+ mTranslationX, getHeight());
        canvas.drawPath(mPath, mPaint);//绘制路径
        canvas.restore();//还原状态，避免覆盖之前状态
        super.dispatchDraw(canvas);
    }
}
