package com.oyty.newframe.ui.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.oyty.newframe.R;
import com.oyty.newframe.base.BaseFragment;
import com.oyty.newframe.event.TabReSelectedEvent;
import com.oyty.newframe.widget.bottombar.BottomBar;
import com.oyty.newframe.widget.bottombar.BottomBarTab;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by oyty on 2018/8/16.
 */
public class MainFragment extends BaseFragment {

    @BindView(R.id.mBottomBar)
    BottomBar mBottomBar;

    private List<SupportFragment> fragments = new ArrayList<>();

    public static MainFragment newInstance() {
        Bundle bundle = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int initViewID() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        SupportFragment fragment = findChildFragment(HomeFragment.class);
        if(fragment == null) {
            fragments.clear();
            fragments.add(HomeFragment.newInstance());
            fragments.add(SearchFragment.newInstance());
            fragments.add(PersonalFragment.newInstance());

            loadMultipleRootFragment(R.id.mContentLayout, 0, fragments.get(0), fragments.get(1), fragments.get(2));
        } else {
            fragments.clear();
            fragments.add(fragment);
            fragments.add(findChildFragment(SearchFragment.class));
            fragments.add(findChildFragment(PersonalFragment.class));
        }

        initBottomBar();
    }


    @Override
    protected void process() {

    }

    private void initBottomBar() {
        mBottomBar.addItem(new BottomBarTab(_mActivity, R.drawable.choice_test, "超级券"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.search_test, "超级搜"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.my_test, "我的"));

        mBottomBar.getItem(0).setUnreadCount(2);

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(fragments.get(position), fragments.get(prePosition));
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                EventBusActivityScope.getDefault(_mActivity).post(new TabReSelectedEvent(position));
            }
        });
    }

    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
