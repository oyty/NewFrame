package com.oyty.newframe.ui.fragment.main;

import com.oyty.newframe.R;
import com.oyty.newframe.base.BaseFragment;
import com.oyty.newframe.event.TestEvent;
import com.oyty.newframe.ui.fragment.account.LoginFragment;
import com.oyty.newframe.widget.util.UIUtil;

import butterknife.OnClick;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

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

//        showProgressDialog();
//        UIUtil.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dismissProgressDialog();
//            }
//        }, 5000);
//        EventBusActivityScope.getDefault(_mActivity).post(new TestEvent());

        if (getParentFragment() != null) {
            ((MainFragment)getParentFragment()).startBrotherFragment(LoginFragment.newInstance());
        }
    }


}
