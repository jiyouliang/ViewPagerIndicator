package com.jiyouliang.viewpagerindicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by JiYouLiang on 2018/07/12.
 */

public class FragmentCommon extends Fragment {

    private String mTitle;
    private static String KEY_TITLE = "title";

    public static Fragment getInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        FragmentCommon fragment = new FragmentCommon();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(KEY_TITLE);
        }
        TextView tv = new TextView(getContext());
        tv.setText(mTitle);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}
