package com.oyty.newframe.ui.fragment.main;

import com.oyty.newframe.R;
import com.oyty.newframe.base.BaseFragment;
import com.oyty.newframe.ui.fragment.account.LoginFragment;

import butterknife.OnClick;

/**
 * Created by oyty on 2018/8/16.
 */
public class PersonalFragment extends BaseFragment {

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    @Override
    public int initViewID() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void process() {

    }

    @OnClick(R.id.mLoginAction)
    public void loginAction() {
        ((MainFragment)getParentFragment()).startBrotherFragment(LoginFragment.newInstance());
    }


}
