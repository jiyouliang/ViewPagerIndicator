package com.jiyouliang.viewpagerslidingtab;

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
    private List<String> TITLES = Arrays.asList("热点", "推荐", "科学", "科技", "体育", "军事", "国际", "健康", "电影", "问答", "历史", "美食", "搞笑");
    private ViewPagerSlidingTab mIndicator;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mIndicator = (ViewPagerSlidingTab) findViewById(R.id.indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void initData() {
        for (String title : TITLES) {
            Fragment fragment = TabFragment.getInstance(title);
            mFragmentList.add(fragment);
        }

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        });
    }
}
