package com.oyty.newframe.widget.bottombar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oyty on 2018/8/15.
 */
public class BottomBar extends LinearLayout {

    private static final long TRANSLATE_DURATION_MILLIS = 200;
    private Context mContext;
    private LayoutParams tabParams;
    private OnTabSelectedListener listener;
    private int currentPosition;
    private List<BottomBarTab> tabs = new ArrayList<>();
    private boolean isVisible;
    private Interpolator interpolator = new AccelerateDecelerateInterpolator();

    public BottomBar(Context context) {
        this(context, null);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
        setBackgroundColor(Color.WHITE);

        tabParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        tabParams.weight = 1;
    }

    public BottomBar addItem(final BottomBarTab tab) {
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener == null) {
                    return;
                }
                int pos = tab.getTabPosition();
                if(currentPosition == pos) {
                    listener.onTabReselected(currentPosition);
                } else {
                    listener.onTabSelected(pos, currentPosition);
                    tab.setSelected(true);
                    listener.onTabUnselected(currentPosition);
                    tabs.get(currentPosition).setSelected(false);
                    currentPosition = pos;
                }
            }
        });
        tab.setTabPosition(getChildCount());
        tab.setLayoutParams(tabParams);
        addView(tab);
        tabs.add(tab);
        return this;
    }

    private void toggle(final boolean visible, final boolean animate, boolean force) {
        if(isVisible != visible || force) {
            isVisible = visible;
            int height = getHeight();
            if(height == 0 && !force) {
                ViewTreeObserver observer = getViewTreeObserver();
                if(observer.isAlive()) {
                    observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            ViewTreeObserver currentObserver = getViewTreeObserver();
                            if(currentObserver.isAlive()) {
                                currentObserver.removeOnPreDrawListener(this);
                            }
                            toggle(visible, animate, true);
                            return true;
                        }
                    });
                    return;
                }
            }
            int translationY = visible ? 0 : height;
            if(animate) {
                animate().setInterpolator(interpolator)
                        .setDuration(TRANSLATE_DURATION_MILLIS)
                        .translationY(translationY);
            } else {
                setTranslationY(translationY);
            }
        }
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int position, int prePosition);
        void onTabUnselected(int position);
        void onTabReselected(int position);
    }
}
