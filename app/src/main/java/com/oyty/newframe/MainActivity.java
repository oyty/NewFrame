package com.oyty.newframe;

import android.support.v4.app.ActivityCompat;

import com.oyty.newframe.base.BaseActivity;
import com.oyty.newframe.event.TabReSelectedEvent;
import com.oyty.newframe.ui.fragment.main.HomeFragment;
import com.oyty.newframe.ui.fragment.main.MainFragment;
import com.oyty.newframe.ui.fragment.main.PersonalFragment;
import com.oyty.newframe.ui.fragment.main.SearchFragment;
import com.oyty.newframe.widget.bottombar.BottomBar;
import com.oyty.newframe.widget.bottombar.BottomBarTab;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends BaseActivity implements MainFragment.OnBackToFirstListener {

    BottomBar mBottomBar;

    private List<SupportFragment> fragments = new ArrayList<>();

    @Override
    public int initViewID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mBottomBar = findViewById(R.id.mBottomBar);

        SupportFragment fragment = findFragment(HomeFragment.class);
        if(fragment == null) {
            fragments.clear();
            fragments.add(HomeFragment.newInstance());
            fragments.add(SearchFragment.newInstance());
            fragments.add(PersonalFragment.newInstance());

            loadMultipleRootFragment(R.id.mContentLayout, 0, fragments.get(0), fragments.get(1), fragments.get(2));
        } else {
            fragments.clear();
            fragments.add(fragment);
            fragments.add(findFragment(SearchFragment.class));
            fragments.add(findFragment(PersonalFragment.class));
        }

        initBottomBar();
    }

    @Override
    protected void process() {

    }

    private void initBottomBar() {
        mBottomBar.addItem(new BottomBarTab(mContext, R.drawable.choice_test, "超级券"))
                .addItem(new BottomBarTab(mContext, R.drawable.search_test, "超级搜"))
                .addItem(new BottomBarTab(mContext, R.drawable.my_test, "我的"));

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
                SupportFragment currentFragment = fragments.get(position);
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

                if(count == 1) {
                    // 交互场景：重选tab 如果列表不在顶部则移动到顶部，如果已在顶部，则刷新
                    EventBusActivityScope.getDefault(MainActivity.this).post(new TabReSelectedEvent(position));
                }
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }
}
