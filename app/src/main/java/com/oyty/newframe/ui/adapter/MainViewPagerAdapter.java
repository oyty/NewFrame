package com.oyty.newframe.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.oyty.newframe.base.BaseFragment;
import com.oyty.newframe.base.BaseHomeFragment;

import java.util.ArrayList;

/**
 * Created by oyty on 2018/8/23.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseHomeFragment> fragments;
    private BaseHomeFragment currentFragment;

    public MainViewPagerAdapter(FragmentManager manager, ArrayList<BaseHomeFragment> fragments) {
        super(manager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((BaseHomeFragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    public BaseHomeFragment getCurrentFragment() {
        return currentFragment;
    }
}
