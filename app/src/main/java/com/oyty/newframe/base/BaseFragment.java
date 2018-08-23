package com.oyty.newframe.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oyty.newframe.widget.custom.PublicTitleView;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by oyty on 2018/8/15.
 */
public abstract class BaseFragment extends Fragment {


    protected Context mContext;
    protected FragmentActivity mActivity;
    protected View mView;
    private PublicTitleView mTitleView;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(registerEventBus()) {
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

}
