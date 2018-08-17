package com.oyty.newframe.ui.fragment.main;

import android.os.Bundle;
import android.widget.TextView;

import com.oyty.newframe.R;
import com.oyty.newframe.base.BaseFragment;
import com.oyty.newframe.event.TestEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by oyty on 2018/8/15.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.mTestLabel)
    TextView mTestLabel;

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void testEvent(TestEvent event) {
        mTestLabel.setText("this is in test event");
    }

    @Override
    public boolean registerEventBus() {
        return true;
    }
}
