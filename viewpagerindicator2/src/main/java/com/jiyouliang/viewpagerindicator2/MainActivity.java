package com.jiyouliang.viewpagerindicator2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.jiyouliang.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private List<String> TITLES = Arrays.asList("关注", "热点", "体育", "军事");
    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mIndicator = (ViewPagerIndicator) findViewById(R.id.vp_indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void initData(){
        mFragmentList = new ArrayList<Fragment>();
        for(String title : TITLES){
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(title);
            mFragmentList.add(fragment);
        }
        FragmentPagerAdapter adapter = new MyAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);
    }

    private static class MyAdapter extends FragmentPagerAdapter{

        private final List<Fragment> fragmentList;

        public MyAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
