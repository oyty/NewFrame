package com.oyty.newframe.ui.fragment.main;

import android.os.Bundle;

import com.oyty.newframe.R;
import com.oyty.newframe.base.BaseFragment;

/**
 * Created by oyty on 2018/8/15.
 */
public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance() {
        Bundle bundle = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initViewID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void process() {

    }


}
