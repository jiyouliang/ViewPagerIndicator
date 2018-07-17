package com.jiyouliang.viewpagerindicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private MyViewPager mViewpager;
    private final List<String> TITLES = Arrays.asList("热点","关注", "推荐", "世界杯", "科技", "体育", "财经", "军事", "娱乐", "汽车");
    private List<Fragment> list_fragment = new ArrayList<Fragment>();
    private ViewPagerIndicator mViewPagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();

    }

    private void initView() {
        mViewpager = (MyViewPager) findViewById(R.id.viewpager);
        mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.vp_indicator);
        mViewPagerIndicator.setViewPager(mViewpager, 0);
    }

    private void initDatas() {
        mViewPagerIndicator.setTabViews(TITLES);
        for (String title : TITLES) {
            Fragment fragment = FragmentCommon.getInstance(title);
            list_fragment.add(fragment);
        }

        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list_fragment.get(position);
            }

            @Override
            public int getCount() {
                return list_fragment.size();
            }
        });

    }


}
