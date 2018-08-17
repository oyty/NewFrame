package com.oyty.newframe.ui.fragment.account;

import com.oyty.newframe.R;
import com.oyty.newframe.base.BaseFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by oyty on 2018/8/16.
 */
public class LoginFragment extends BaseFragment {

    public static SupportFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public int initViewID() {
        return R.layout.fragment_login;
    }

    @Override
    protected void process() {

    }

    @Override
    protected boolean enableSwipeBack() {
        return true;
    }
}
