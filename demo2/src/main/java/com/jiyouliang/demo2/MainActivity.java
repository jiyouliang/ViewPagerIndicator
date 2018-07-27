package com.jiyouliang.demo2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPagerIndicator mIndicator;
    private ViewPager mViewpager;
    private final List<String> mTitles = Arrays.asList("关注", "推荐", "科技", "体育", "军事", "汽车", "国际");
    private final List<Fragment> mFragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("position="+position+",positionOffset="+positionOffset+",positionOffsetPixels="+positionOffsetPixels);
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        mIndicator.setTabTitles(mTitles);
        for(String title : mTitles){
            MyFragment fragment = MyFragment.getInstance(title);
            mFragments.add(fragment);
        }

        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
    }
}
