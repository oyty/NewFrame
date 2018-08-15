package com.oyty.newframe.widget.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;

import com.flash.coupon.base.App;

public class UIUtil {

    public static Context getContext() {
        return App.getContext();
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static String getString(int resId) {
        return getContext().getString(resId);
    }

    public static String getString(int resId, Object... formatArgs) {
        return getContext().getString(resId, formatArgs);
    }

    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public static float getDimen(int resId) {
        return getResources().getDimension(resId);
    }

    public static void post(Runnable task) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            // 在主线程中执行的
            task.run();
        } else {
            // 在子线程中执行的
            App.getMainHandler().post(task);
        }
    }

    /**
     * dip 转 px
     */
    public static int dip2px(int dip) {
        //
        // 公式： dp = px / (dpi / 160) px = dp * (dpi / 160)
        // dp = px / denisity
        // px = dp * denisity;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float density = metrics.density;
        return (int) (dip * density + 0.5f);
    }

    public static Handler getMainHandler() {
        return App.getMainHandler();
    }

    /**
     * 执行延时任务
     */
    public static void postDelayed(Runnable task, int delayed) {
        getMainHandler().postDelayed(task, delayed);
    }
}
