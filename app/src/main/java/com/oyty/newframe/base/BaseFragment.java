package com.oyty.newframe.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oyty.newframe.R;
import com.oyty.newframe.ui.fragment.main.HomeFragment;
import com.oyty.newframe.widget.custom.PublicTitleView;
import com.oyty.newframe.widget.util.UIUtil;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by oyty on 2018/8/15.
 * 若要使用activity，可直接使用 {@link #_mActivity}
 */
public abstract class BaseFragment extends SwipeBackFragment {

    private OnBackToFirstListener backToFirstListener;

    protected Context mContext;
    protected View mView;
    private PublicTitleView mTitleView;
    private Unbinder unbinder;
    private QMUITipDialog mLoadingDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if(context instanceof OnBackToFirstListener) {
            backToFirstListener = (OnBackToFirstListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnBackToFirstListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (registerEventBus()) {
            EventBusActivityScope.getDefault(_mActivity).register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(initViewID(), null);
        unbinder = ButterKnife.bind(this, mView);
        initTitleView();
        initTitleBar(mTitleView);
        initView();
        initViewListener();
        process();
        return enableSwipeBack() ? attachToSwipeBack(mView) : mView;
    }

    public void initTitleBar(PublicTitleView titleView) {
    }

    public abstract int initViewID();

    protected void initView() {
    }

    protected void initViewListener() {
    }

    protected abstract void process();


    private void initTitleView() {
        if (mTitleView == null) {
//            mTitleView = (PublicTitleView) mView.findViewById(R.id.mTitleView);
        }
    }


    protected boolean enableSwipeBack() {
        return false;
    }

    public boolean registerEventBus() {
        return false;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        backToFirstListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (registerEventBus()) {
            EventBusActivityScope.getDefault(_mActivity).unregister(this);
        }
    }

    public PublicTitleView getTitleView() {
        return mTitleView;
    }

    public void showProgressDialog() {
        try {
            View currentFocus = _mActivity.getCurrentFocus();
            QMUIKeyboardHelper.hideKeyboard(currentFocus);
        } catch (Exception ignored) {
        }
        if (mLoadingDialog == null) {
            mLoadingDialog = new QMUITipDialog.Builder(_mActivity)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord(UIUtil.getString(R.string.loading))
                    .create();
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing() && !_mActivity.isFinishing()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 处理回退事件
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if(getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            if(this instanceof HomeFragment) {
                _mActivity.finish();
            } else {
                backToFirstListener.onBackToFirstFragment();
            }
        }
        return super.onBackPressedSupport();
    }

    public interface OnBackToFirstListener {
        void onBackToFirstFragment();
    }
}
