package com.oyty.newframe.widget.bottombar;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.oyty.newframe.widget.util.UIUtil;

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

    /**
     * 获取 Tab
     */
    public BottomBarTab getItem(int index) {
        if (tabs.size() < index) return null;
        return tabs.get(index);
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

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, currentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        if (currentPosition != ss.position) {
            getChildAt(currentPosition).setSelected(false);
            getChildAt(ss.position).setSelected(true);
        }
        currentPosition = ss.position;
    }

    static class SavedState extends BaseSavedState {
        private int position;

        public SavedState(Parcel source) {
            super(source);
            position = source.readInt();
        }

        public SavedState(Parcelable superState, int position) {
            super(superState);
            this.position = position;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(position);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    public void setCurrentItem(final int position) {
        UIUtil.post(new Runnable() {
            @Override
            public void run() {
                getChildAt(position).performClick();
            }
        });
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
