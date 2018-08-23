package com.oyty.newframe.ui.activity;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.oyty.newframe.R;
import com.oyty.newframe.base.BaseActivity;
import com.oyty.newframe.base.BaseHomeFragment;
import com.oyty.newframe.base.Constants;
import com.oyty.newframe.entity.MainNavigationEntity;
import com.oyty.newframe.ui.adapter.MainViewPagerAdapter;
import com.oyty.newframe.widget.bottomnavigation.AHBottomNavigation;
import com.oyty.newframe.widget.bottomnavigation.AHBottomNavigationItem;
import com.oyty.newframe.widget.bottomnavigation.AHBottomNavigationViewPager;
import com.oyty.newframe.widget.util.GsonUtil;
import com.oyty.newframe.widget.util.PreferenceHelper;
import com.oyty.newframe.widget.util.QiniuUtil;
import com.oyty.newframe.widget.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.mViewPager)
    AHBottomNavigationViewPager mViewPager;
    @BindView(R.id.mBottomNavigation)
    AHBottomNavigation mBottomNavigation;
    private ArrayList<BaseHomeFragment> baseFragments = new ArrayList<>();
    private List<AHBottomNavigationItem> bottomItems = new ArrayList<>();
    private MainViewPagerAdapter adapter;
    private BaseHomeFragment currentFragment;

    @Override
    public int initViewID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initBottomNavigation();
    }

    @Override
    protected void process() {

    }

    private void initBottomNavigation() {
        String cache = PreferenceHelper.getString(Constants.Cache.MAIN_NAVIGATION);
        if (TextUtils.isEmpty(cache)) {
            cache = Constants.StaticCache.MAIN_NAVIGATION;
        }
        List<MainNavigationEntity> entities = GsonUtil.json2Array(cache, new TypeToken<List<MainNavigationEntity>>() {
        });
        baseFragments.clear();
        bottomItems.clear();
        for (int i = 0; i < entities.size(); i++) {
            MainNavigationEntity entity = entities.get(i);
            bottomItems.add(new AHBottomNavigationItem(entity.title, QiniuUtil.getImageURL(entity.img_click), QiniuUtil.getImageURL(entity.img_unclick)));

            try {
                baseFragments.add((BaseHomeFragment) Class.forName(entity.function).newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        mBottomNavigation.addItems(bottomItems);

        mBottomNavigation.setDefaultBackgroundColor(UIUtil.getColor(R.color.white));
        mBottomNavigation.setBehaviorTranslationEnabled(false);
        mBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        mBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(currentFragment == null) {
                    currentFragment = adapter.getCurrentFragment();
                }
                // true: was already selected
                if(wasSelected) {
                    currentFragment.refresh();
                    return true;
                }

                if(currentFragment != null) {
                    currentFragment.willBeHidden();
                }

                mViewPager.setCurrentItem(position, false);

                if(currentFragment == null) {
                    return true;
                }

                currentFragment = adapter.getCurrentFragment();
                currentFragment.willBeDisplayed();

                return true;
            }
        });

        mViewPager.setOffscreenPageLimit(4);
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), baseFragments);
        mViewPager.setAdapter(adapter);

        currentFragment = adapter.getCurrentFragment();
    }

}
