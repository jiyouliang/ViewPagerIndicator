package com.jiyouliang.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.jiyouliang.viewpagerindicator2.R;

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
    private int mVisiableTabCount;//可见tab个数
    private final static int DEFAULT_VISIABLE_TAB_COUNT = 4;//默认可见tab个数

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        //可见tab个数
        mVisiableTabCount = a.getInt(R.styleable.ViewPagerIndicator_visiable_tab_count, DEFAULT_VISIABLE_TAB_COUNT);
        if (mVisiableTabCount < 0) {
            mVisiableTabCount = DEFAULT_VISIABLE_TAB_COUNT;
        }

        a.recycle();
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
        System.out.println("onSizeChanged");
        mTriangleWidth = (int) (w / mVisiableTabCount * RADIUS_TRIANGLE_WIDTH);//三角形宽度
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

    /**
     * 绘制子view
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        System.out.println("dispatchDraw");
        //绘制三角形
        canvas.save();//保存之前状态
        //平移画布
        canvas.translate(mInitTranslationX + mTranslationX, getHeight());
        canvas.drawPath(mPath, mPaint);//绘制路径
        canvas.restore();//还原状态，避免覆盖之前状态
        super.dispatchDraw(canvas);
    }

    /**
     * 跟随手指滑动
     *
     * @param position
     * @param offset   移动偏移量
     */
    public void scroll(int position, float offset) {
        int tabWidth = getWidth() / mVisiableTabCount;
        mTranslationX = (int) (tabWidth * (offset + position));//x轴移动距离
        invalidate();//刷新view，当修改某个view时需要调用该方法才能看到刷新后的view
    }

    /**
     * xml解析完毕回调
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount == 0) return;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) childView.getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth() / mVisiableTabCount;//计算每个tab宽度
        }
    }

    /**
     * 屏幕宽度
     * @return
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }
}
