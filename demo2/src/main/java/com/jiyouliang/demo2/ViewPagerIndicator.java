package com.jiyouliang.demo2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JiYouLiang on 2018/07/24.
 */


public class ViewPagerIndicator extends LinearLayout {

    private static final int VISIABLE_TAB_COUNT = 4;//默认显示的tab个数
    private static final float RATIO_TRIANGLE_WIDTH = 1 / 6F;//三角形指示器和tab宽度比例
    private final Path mPath;
    private int mTriangleWidth; //三角形宽度
    private int mTriangleHeight;//三角形高度
    private Paint mPaint;
    private int mInitTransitionX;//三角形初始化坐标原点x轴
    private int mTransitionX;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();//画笔
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setPathEffect(new CornerPathEffect(4));//毕竟角度过于尖锐
        mPaint.setAntiAlias(true);//抗锯齿

        mPath = new Path();//路径
    }


    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }

    //动态设置tab标题
    public void setTabTitles(List<String> titles) {
        if (titles == null || titles.size() == 0) return;
        removeAllViews();
        for (String title : titles) {
            View view = getTextView(title);
            addView(view);
        }
    }

    private View getTextView(String title) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.weight = 0;
        tv.setLayoutParams(lp);
        tv.setTextSize(16);
        //getWidth()此时为0
        lp.width = getScreenWidth() / VISIABLE_TAB_COUNT;
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setText(title);
        return tv;
    }

    //绘制子view
    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        System.out.println("dispatchDraw");
        //绘制三角形指示器
        canvas.translate(mInitTransitionX + mTransitionX, getHeight());

        mPath.moveTo(0, 0);//移动到原点
        mPath.lineTo(mTriangleWidth, 0);//绘制线条
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();//关闭路径

        canvas.drawPath(mPath, mPaint);//绘制路径
        canvas.restore();
        super.dispatchDraw(canvas);

    }

    //控件大小发生改变时调用，该方法中可以获取控件宽高
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        System.out.println("onSizeChanged");
        int tabWidth = w / VISIABLE_TAB_COUNT;
        //计算三角形指示器宽高
        mTriangleWidth = (int) (tabWidth * RATIO_TRIANGLE_WIDTH);
        mTriangleHeight = mTriangleWidth / 2;
        mInitTransitionX = (tabWidth - mTriangleWidth) / 2;

    }

    /**
     * 滑动
     */
    public void scroll(int position, float offset) {
        int tabWidth = getWidth() / VISIABLE_TAB_COUNT;
        //x轴移动距离,invalidate()调用后引起dispatchDraw()方法调用，重绘三角形指示器
        mTransitionX = (int) ((position + offset) * tabWidth);
        //下面是为了移动ViewPagerIndicator，将隐藏的tab移动显示出来
        if (position < getChildCount() - 2) {//最后一个item不移动
            if ((position >= VISIABLE_TAB_COUNT - 2) && offset > 0) {
//            scrollTo((int) ((position - 2) * tabWidth * offset), 0);
                this.scrollTo((position - (VISIABLE_TAB_COUNT - 2)) * tabWidth + (int) (tabWidth * offset), 0);
            }
        }
        invalidate();
    }
}
