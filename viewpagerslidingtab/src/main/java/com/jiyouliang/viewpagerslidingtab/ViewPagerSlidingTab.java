package com.jiyouliang.viewpagerslidingtab;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JiYouLiang on 2018/07/26.
 */

public class ViewPagerSlidingTab extends HorizontalScrollView {

    private LinearLayout mLayoutContainer;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener = new PageChangeListener();
    private int currentPosition = 0;
    private float currentPositionOffset = 0f;
    private int tabCount;
    private int scrollOffset = 0;
    private int lastScrollX = 0;

    public ViewPagerSlidingTab(Context context) {
        this(context, null);
    }

    public ViewPagerSlidingTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);
        FrameLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLayoutContainer = new LinearLayout(context);
        mLayoutContainer.setOrientation(LinearLayout.HORIZONTAL);
        mLayoutContainer.setHorizontalScrollBarEnabled(false);
        mLayoutContainer.setVerticalScrollBarEnabled(false);
        mLayoutContainer.setLayoutParams(lp);
//        removeAllViews();
        addView(mLayoutContainer);//使用LinearLayout管理所有tab，因为HorizontalScrollView只能有一个子控件

    }


    public void setTabTitles(List<String> titles) {
        mLayoutContainer.removeAllViews();
        for (String title : titles) {
            mLayoutContainer.addView(getTabView(title));
        }
    }

    private View getTabView(String title) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        lp.setMargins(60, 0, 60, 0);
        tv.setLayoutParams(lp);
        tv.setTextSize(16);

        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setText(title);
        return tv;
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(mListener);
        tabCount = mViewPager.getAdapter().getCount();
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentPosition = position;
            currentPositionOffset = positionOffset;

            scrollToChild(position, positionOffset);
//            invalidate();
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void scrollToChild(int position, float offset) {
        View childView = mLayoutContainer.getChildAt(position);
        int width = childView.getWidth();
        int left = childView.getLeft();
        int right = childView.getRight();

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) childView.getLayoutParams();
        int lMgi = lp.leftMargin;
        int rMgi = lp.rightMargin;

        System.out.println("lMgi=" + lMgi + ",position=" + position + ",offset=" + offset + ",width=" + width + ",left=" + left + ",right=" + right);
        int tabWidth = width + lMgi + rMgi;
        float scrollX = tabWidth * (position + offset);
        scrollTo((int) scrollX, 0);
    }


}
