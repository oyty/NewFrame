package com.oyty.newframe.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.oyty.newframe.R;
import com.oyty.newframe.widget.custom.PublicTitleView;
import com.oyty.newframe.widget.util.AndroidBugUtil;
import com.oyty.newframe.widget.util.CommonUtil;
import com.oyty.newframe.widget.util.UIUtil;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by oyty on 2018/8/15.
 */
public abstract class BaseActivity extends SupportActivity implements BaseFragment.OnBackToFirstListener {

    protected Context mContext;
    private PublicTitleView mTitleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initBeforeSetView();
        setContentView(initViewID());
        AppManager.getAppManager().addActivity(this);
        initTitleView();
        initTitleBar(mTitleView);
        initView();
        initViewListener();
        process();
    }

    protected void initBeforeSetView() {
    }

    private void initTitleView() {
//        mTitleView = (PublicTitleView) findViewById(R.id.mTitleView);
    }


    public void initTitleBar(PublicTitleView titleView) {

    }

    public abstract int initViewID();

    public void initView() {

    }

    protected void initViewListener() {

    }

    protected abstract void process();

    @Override
    public void onBackToFirstFragment() {

    }

    @Override
    protected void onDestroy() {
        CommonUtil.fixInputMethodManagerLeak(this);
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }


    public PublicTitleView getTitleView() {
        return mTitleView;
    }
}
