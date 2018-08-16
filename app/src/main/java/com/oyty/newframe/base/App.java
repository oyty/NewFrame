package com.oyty.newframe.base;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.bumptech.glide.Glide;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

public class App extends Application implements ComponentCallbacks2 {

    private static Context context;
    private static Handler sMainHandler;
//    private ApplicationLike tinkerApplicationLike;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        sMainHandler = new Handler();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
//        initTinkerPatch();//tinker
        initJPush();//极光
        initAlibcTrade();//阿里百川
        initUMeng();//友盟
        initCrash();//奔溃记录
        initBugly();//bugly
//        initGrowingIO();

        initFragmentation();
    }

    private void initFragmentation() {
        Fragmentation.builder()
                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
                /**
                 * 可以获取到{@link me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning}
                 * 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
                 */
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
    }


    {
//        PlatformConfig.setWeixin("wxca1ecf021bebe08d", "c1ac743ccdda71fe31c3dd1225d8b974");
//        PlatformConfig.setSinaWeibo("102786163", "bd276c502928ecd063040194c8b938e6", "http://sns.whalecloud.com/sina2/callback");
//        PlatformConfig.setQQZone("1106913235", "tD9rSdDJDMm0FpOE");
    }

    private void initCrash() {
//        CrashHandler handler = CrashHandler.getInstance();
//        handler.init(this);
    }

    private void initJPush() {
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
    }

    private void initBugly() {
//        if (!BuildConfig.DEBUGABLE) {
//            CrashReport.initCrashReport(getApplicationContext(), "d2d3953347", BuildConfig.DEBUGABLE);
//        }
    }

    private void initUMeng() {
//        UMConfigure.setLogEnabled(BuildConfig.DEBUGABLE);
//        UMConfigure.init(this, "5b31e4e6f29d9819bb000013"
//                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    public static Handler getMainHandler() {
        return sMainHandler;
    }

    private void initAlibcTrade() {
//        AlibcTradeManager.getInstance().initAlibcTrade(this);
    }

    /**
     * 我们需要确保至少对主进程跟patch进程初始化 TinkerPatch
     */
    /*private void initTinkerPatch() {
        // 我们可以从这里获得Tinker加载过程的信息
        if (BuildConfig.TINKER_ENABLE) {
            tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
            // 初始化TinkerPatch SDK
            TinkerPatch.init(
                    tinkerApplicationLike
//                new TinkerPatch.Builder(tinkerApplicationLike)
//                    .requestLoader(new OkHttp3Loader())
//                    .build()
            )
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true);
            // 获取当前的补丁版本
            Log.d(TAG, "Current patch version is " + TinkerPatch.with().getPatchVersion());

            // fetchPatchUpdateAndPollWithInterval 与 fetchPatchUpdate(false)
            // 不同的是，会通过handler的方式去轮询
            TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
        }
        // 使用条件下发之前，需要先在APP里面设置当前APP的条件
    }*/

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if(level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }
}
