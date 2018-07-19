package com.jiyouliang.viewpagerindicator2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by JiYouLiang on 2018/07/19.
 */

public class VpSimpleFragment extends Fragment {

    private String title;
    private static final String BUNDLE_TITLE = "title";


    public static VpSimpleFragment newInstance(String tilte){
        System.out.println("VpSimpleFragment newInstance");
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, tilte);
        VpSimpleFragment fragment = new VpSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("VpSimpleFragment onCreateView");
        Bundle bundle = getArguments();
        if(bundle != null){
            title = bundle.getString(BUNDLE_TITLE);
        }
        TextView tv = new TextView(getContext());
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);

        return tv;
    }
}
