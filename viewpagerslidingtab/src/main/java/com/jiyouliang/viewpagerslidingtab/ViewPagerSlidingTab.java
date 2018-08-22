package com.jiyouliang.viewpagerslidingtab;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
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
    private static final int COLOR_WHITE = 0xFFFFFFFF;
    private static final int COLOR_NORMAL = 0x77FFFFFF;

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
        for (int i = 0; i < titles.size(); i++) {
            addTab(titles, i);
        }
    }


    private View getTabView(String title) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        lp.setMargins(60, 0, 60, 0);
        tv.setLayoutParams(lp);
        tv.setTextSize(16);

        tv.setTextColor(COLOR_NORMAL);
        tv.setGravity(Gravity.CENTER);
        tv.setText(title);
        return tv;
    }

    private void addTab(List<String> titles, final int position) {
        View tabView = getTabView(titles.get(position));
        //点击切换tab
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position);
            }
        });
        mLayoutContainer.addView(tabView);
    }

    private void setTabColor(int position){
        resetTabsColor();
        ((TextView)mLayoutContainer.getChildAt(position)).setTextColor(COLOR_WHITE);
    }

    private void resetTabsColor() {
        for (int i = 0; i < mLayoutContainer.getChildCount(); i++) {
            View childView = mLayoutContainer.getChildAt(i);
            if(childView instanceof TextView){
                ((TextView) childView).setTextColor(COLOR_NORMAL);
            }
        }
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(mListener);
        setTabColor(0);
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            scrollToChild(position, positionOffset);
//            invalidate();
        }

        @Override
        public void onPageSelected(int position) {
            setTabColor(position);
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
