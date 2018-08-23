package com.oyty.newframe.widget.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.oyty.newframe.base.App;
import com.oyty.newframe.base.Constants;
import com.oyty.newframe.entity.UserEntity;

public class PreferenceHelper {

    public static final String PREFERENCE_NAME_CIRCLE = "sharepreference_coupon";
    public static final String SHOW_LOCK__NOTIFICATION = "show_lock_notification";

    private static SharedPreferences getSharedPreference() {
        Context context = App.getContext().getApplicationContext();
        return context.getSharedPreferences(PREFERENCE_NAME_CIRCLE, Context.MODE_PRIVATE);
    }

    public static boolean putString(String tag, String value) {
        SharedPreferences prefs = getSharedPreference();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(tag, value);
        return editor.commit();
    }

    public static String getString(String tag) {
        return getString(tag, "");
    }

    public static String getString(String tag, String defaultValue) {
        SharedPreferences prefs = getSharedPreference();
        return prefs.getString(tag, defaultValue);
    }

    public static boolean putBoolean(String tag, boolean value) {
        SharedPreferences prefs = getSharedPreference();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(tag, value);
        return editor.commit();
    }

    public static boolean getBoolean(String tag) {
        return getBoolean(tag, false);
    }

    public static boolean getBoolean(String tag, boolean defaultValue) {
        SharedPreferences prefs = getSharedPreference();
        return prefs.getBoolean(tag, defaultValue);
    }

    public static boolean isFirstIn() {
        return getBoolean(Constants.IS_FIRST_IN, true);
    }

    public static int getInt(String tag, int defaultValue) {
        SharedPreferences prefs = getSharedPreference();
        return prefs.getInt(tag, defaultValue);
    }

    public static boolean isLogin() {
        /*String cache = getString(Constants.LOGIN_INFO);
        return !TextUtils.isEmpty(cache);*/
        return !TextUtils.isEmpty(getUserData().id);
    }

    public static UserEntity getUserData() {
        String cache = getString(Constants.LOGIN_INFO);
        if (TextUtils.isEmpty(cache)) {
            return new UserEntity();
        } else {
            return GsonUtil.json2Bean(cache, UserEntity.class);
        }
    }

    //
    public static void saveUserData(UserEntity result) {
        putString(Constants.LOGIN_INFO, GsonUtil.bean2Json(result));
    }


    //
//    public static String getLoginMethod() {
//        return getString(Constants.LOGIN_METHOD);
//    }
//
    public static boolean putInt(String tag, int value) {
        SharedPreferences prefs = getSharedPreference();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(tag, value);
        return editor.commit();
    }

    //
//    public static boolean isLockNotificationDisabled(){
//        return getBoolean(SHOW_LOCK__NOTIFICATION,false);
//    }
//    public static void disableLockNotification(boolean flag){
//        putBoolean(SHOW_LOCK__NOTIFICATION,flag);
//    }
//
    public static void logout() {
//        UserEntity userEntity = new UserEntity();
//        userEntity.pick_info = getUserData().pick_info;
//        putString(Constants.LOGIN_INFO, GsonUtil.bean2Json(userEntity));
//        putString(Constants.TOKEN, "");
//        putString(Constants.LOGIN_METHOD, "");
    }

    //
    public static void saveToken(String token) {
        putString(Constants.TOKEN, token);
    }

    public static String getToken() {
        return getString(Constants.TOKEN);
    }

    public static void setHasUpdate(boolean hasUpdate) {
//        putBoolean(Constants.HAS_UPDATE, hasUpdate);
    }

    //    public static boolean getHasUpdate(boolean hasUpdate){
//        return  getBoolean(Constants.HASUPDATE,hasUpdate);
//    }
    public static void setNewestVersion(String name) {
//        putString(Constants.NEWEST_VERSION, name);
    }
//
//
//    public static boolean isInner() {
//        return PreferenceHelper.getBoolean(Constants.Cache.PROMOTIONS_CONFIG);
//    }
}
