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
import com.oyty.newframe.ui.fragment.HomeFragment;
import com.oyty.newframe.widget.custom.PublicTitleView;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by oyty on 2018/8/15.
 */
public abstract class BaseFragment extends SupportFragment {

    private OnBackToFirstListener backToFirstListener;

    protected Context mContext;
    protected FragmentActivity activity;
    protected View mView;
    private PublicTitleView mTitleView;
    private Unbinder unbinder;
    private View mProgressView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
            EventBus.getDefault().register(this);
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
        return mView;
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
            EventBus.getDefault().unregister(this);
        }
    }

    public PublicTitleView getTitleView() {
        return mTitleView;
    }

    public void showProgressDialog() {
        ((BaseActivity) mContext).showProgressDialog();
    }

    public void dismissProgressDialog() {
        ((BaseActivity) mContext).dismissProgressDialog();
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
