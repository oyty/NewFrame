package com.oyty.newframe;

import com.oyty.newframe.base.BaseActivity;
import com.oyty.newframe.ui.fragment.main.MainFragment;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends BaseActivity {

    @Override
    public int initViewID() {
        return R.layout.activity_main;
    }

    @Override
    protected void process() {

        if(findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.mMainContainer, MainFragment.newInstance());
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
