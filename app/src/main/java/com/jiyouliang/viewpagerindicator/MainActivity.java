package com.jiyouliang.viewpagerindicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private ViewPager mViewpager;
    private final List<String> TITLES = Arrays.asList("通讯录", "发现", "我的");
    private List<Fragment> list_fragment = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();

    }

    private void initView() {
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
    }

    private void initDatas() {
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
