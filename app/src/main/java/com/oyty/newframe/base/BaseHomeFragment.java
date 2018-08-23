package com.oyty.newframe.base;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.oyty.newframe.R;

import butterknife.BindView;

/**
 * Created by oyty on 2018/8/23.
 */
public abstract class BaseHomeFragment extends BaseFragment {

    @BindView(R.id.mFragmentContainer)
    FrameLayout mFragmentContainer;
    /**
     * Refresh
     */
    public abstract void refresh();

    /**
     * Called when a fragment will be displayed
     */
    public void willBeDisplayed() {
        if (mFragmentContainer != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            mFragmentContainer.startAnimation(fadeIn);
        }
    }

    /**
     * Called when a fragment will be hidden
     */
    public void willBeHidden() {
        if (mFragmentContainer != null) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            mFragmentContainer.startAnimation(fadeOut);
        }
    }
}
