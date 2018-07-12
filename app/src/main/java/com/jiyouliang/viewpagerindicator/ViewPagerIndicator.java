package com.jiyouliang.viewpagerindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by JiYouLiang on 2018/07/12.
 */

public class ViewPagerIndicator extends LinearLayout {

    private int mTriangleWidth; //三角形宽度
    private int mTriangleHeight;
    private Paint mPaint;//画笔
    private Path mPath;//路径
    private float mTranslationX;//x轴偏移

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3)); //避免三角形角度过于尖锐
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTriangleWidth = 50;
        mTriangleHeight = (int) (mTriangleWidth / 1.75);

        initTriangle();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //绘制三角形
        canvas.save();//保存状态
        mTranslationX = getWidth()/3/2 - mTriangleWidth/2;
        canvas.translate(mTranslationX, getHeight());//平移
        canvas.drawPath(mPath, mPaint);//绘制
        canvas.restore();//还原
        super.dispatchDraw(canvas);
    }

    /**
     * 初始化三角形
     */
    private void initTriangle() {
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight / 2);
        mPath.close();//闭合路径

    }
}
