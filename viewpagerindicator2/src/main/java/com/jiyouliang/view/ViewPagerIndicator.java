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
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiyouliang.viewpagerindicator2.R;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> mTitles;
    private int COLOR_NORMAL = 0x77FFFFFF;
    private int COLOR_HIGHT_LIGHT = 0xFFFFFFFF;
    private OnPageChangeListener mListener;
    private ViewPager mViewPager;

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

        mTitles = new ArrayList<String>();
    }

    /**
     * 控件大小发生改变时调用，该方法中可以获取控件宽高、获取根据或者的宽高设置控件大小
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        System.out.println("onSizeChanged");
        mTriangleWidth = (int) (w / mVisiableTabCount * RADIUS_TRIANGLE_WIDTH);//三角形宽度
        mInitTranslationX = w / mVisiableTabCount / 2 - mTriangleWidth / 2;//初始化三角形指示器位置
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
        //移动右侧隐藏tab的条件
        if (mVisiableTabCount != 1) {
            if (position >= (mVisiableTabCount - 2) && offset > 0 && getChildCount() > mVisiableTabCount) {
                this.scrollTo((position - (mVisiableTabCount - 2)) * tabWidth + (int) (tabWidth * offset), 0);
            }
        } else {
            this.scrollTo(position * tabWidth + (int) (tabWidth * offset), 0);
        }
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
        setOnItemClick();
    }

    /**
     * 屏幕宽度
     *
     * @return
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }

    public void setTabItemTitles(List<String> titles) {
        if (titles != null && titles.size() > 0) {
            this.removeAllViews();
            for (String title : titles) {
                View view = getTextView(title);
                addView(view);
            }
        }
        setOnItemClick();
    }

    private View getTextView(String title) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mVisiableTabCount;
        tv.setLayoutParams(lp);
        tv.setTextSize(16);
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(COLOR_NORMAL);
        return tv;
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mListener = listener;
       /* int childCount = getChildCount();
        if(childCount < 0) return;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

        }*/
    }

    /**
     * 设置关联ViewPager
     */
    public void setViewPager(ViewPager viewPager, final int pos) {
        this.mViewPager = viewPager;
        this.mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mListener != null) {
                    mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
                scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                if (mListener != null) {
                    mListener.onPageSelected(position);
                }
                System.out.println("onPageSelected=" + position);
                setTabHightLight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mListener != null) {
                    mListener.onPageScrollStateChanged(state);
                }
            }
        });
        mViewPager.setCurrentItem(pos);
        setTabHightLight(pos);
    }

    private void resetTagHightLight() {
        int childCount = getChildCount();
        if (childCount < 0) return;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView instanceof TextView) {
                ((TextView) childView).setTextColor(COLOR_NORMAL);
            }
        }
    }

    /**
     * 设置tab选中高亮颜色
     *
     * @param position
     */
    private void setTabHightLight(int position) {
        resetTagHightLight();
        View childView = getChildAt(position);
        ((TextView) childView).setTextColor(COLOR_HIGHT_LIGHT);
    }

    /**
     * item点击高亮
     */
    private void setOnItemClick() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final int index = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(index);
                    int currentItem = mViewPager.getCurrentItem();
//                    setTabHightLight(index);
                }
            });
        }
    }

    /**
     * tab改变回调
     */
    public interface OnPageChangeListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);

        void onPageScrollStateChanged(int state);
    }
}
