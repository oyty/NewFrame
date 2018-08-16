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
    private QMUITipDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initBeforeSetView();
        setContentView(initViewID());
        ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        if (registerEventBus()) {
            EventBus.getDefault().register(this);
        }
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


    public boolean registerEventBus() {
        return false;
    }

    @Override
    public void onBackToFirstFragment() {

    }

    @Override
    protected void onDestroy() {
        CommonUtil.fixInputMethodManagerLeak(this);
        AppManager.getAppManager().finishActivity(this);
        if (registerEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    public void showProgressDialog() {
        try {
            View currentFocus = getCurrentFocus();
            QMUIKeyboardHelper.hideKeyboard(currentFocus);
        } catch (Exception ignored) {
        }
        if (mLoadingDialog == null) {
            mLoadingDialog = new QMUITipDialog.Builder(mContext)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord(UIUtil.getString(R.string.loading))
                    .create();
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing() && !isFinishing()) {
            mLoadingDialog.dismiss();
        }
    }

    public PublicTitleView getTitleView() {
        return mTitleView;
    }
}
