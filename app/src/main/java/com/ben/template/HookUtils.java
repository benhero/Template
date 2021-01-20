package com.ben.template;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Mac地址拦截器：参考https://blog.csdn.net/EthanCo/article/details/111544333
 *
 * @author Benhero
 * @date 2021-01-06
 */
public class HookUtils {
    //    public static final String TAG = "Hook_Mac";
    public static final String TAG = "JKL";

    private static WifiInfo cacheWifiInfo = null;

    public static void hookMacAddress(String from, Context context) {
        try {
            Class<?> iWifiManager = Class.forName("android.net.wifi.IWifiManager");
            Field serviceField = WifiManager.class.getDeclaredField("mService");
            serviceField.setAccessible(true);

            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            // real mService
            Object realIwm = serviceField.get(wifi);
            // replace mService  with Proxy.newProxyInstance
            serviceField.set(wifi, Proxy.newProxyInstance(iWifiManager.getClassLoader(),
                    new Class[]{iWifiManager},
                    new TelHandler(TAG, "getConnectionInfo", realIwm)));
            Log.i(TAG, from + ": wifiManager hook success");
        } catch (Exception e) {
            Log.e(TAG, from + ": printStackTrace:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static class InvocationHandler implements java.lang.reflect.InvocationHandler {

        private final String tag;
        private final String methodName;
        private Object real;

        /**
         * 反射处理器
         *
         * @param tag        日志TAG
         * @param methodName 拦截的方法名
         * @param real       x
         */
        public InvocationHandler(String tag, String methodName, Object real) {
            this.real = real;
            this.methodName = methodName;
            this.tag = tag;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.d(tag, "method invoke " + method.getName());
            if (methodName.equals(method.getName())) {
                // 拦截Mac地址的获取
//                    Toast.makeText(GlobalApplication.get().getApplicationContext(), "正在获取Mac地址")
                try {
                    throw new NullPointerException();
                } catch (NullPointerException e1) {
                    // 打印调用任务栈
                    Log.e(tag, Log.getStackTraceString(e1));
                }

                if (cacheWifiInfo != null) {
                    Log.d(tag, "cacheWifiInfo:" + cacheWifiInfo);
                    return cacheWifiInfo;
                }
                WifiInfo wifiInfo = null;
                try {
                    Class cls = WifiInfo.class;
                    wifiInfo = (WifiInfo) cls.newInstance();
                    Field mMacAddressField = cls.getDeclaredField("mMacAddress");
                    mMacAddressField.setAccessible(true);
                    mMacAddressField.set(wifiInfo, "");
                    cacheWifiInfo = wifiInfo;
                    Log.d(tag, "wifiInfo:" + wifiInfo);
                } catch (Exception e) {
                    Log.e(tag, "WifiInfo error:" + e.getMessage());
                }
                return wifiInfo;
            } else {
                return method.invoke(real, args);
            }
        }
    }

    public static String getMacDefault(Context context) {
        String mac = "0";
        if (context == null) {
            return mac;
        }
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();

        return mac;
    }


    public static String getIMEI(Context context){
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
//
//            }else {
//                Method method = tm.getClass().getMethod("getImei");
//                imei = (String) method.invoke(tm);
//            }
            imei = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

    public static void hookImeiAddress(String from, Context context) {
        try {
            Class<?> telephonyManager = Class.forName("android.telephony.TelephonyManager");
            Field serviceField = TelephonyManager.class.getDeclaredField("mService");
            serviceField.setAccessible(true);


            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            // real mService
            Object realIwm = serviceField.get(tel);
            // replace mService  with Proxy.newProxyInstance
            serviceField.set(tel, Proxy.newProxyInstance(telephonyManager.getClassLoader(),
                    new Class[]{telephonyManager},
                    new TelHandler(TAG, "getDeviceId", realIwm)));

            Method getDeviceId = TelephonyManager.class.getDeclaredMethod("getDeviceId");
            getDeviceId.setAccessible(true);

            Log.i(TAG, from + ": wifiManager hook success");
        } catch (Exception e) {
            Log.e(TAG, from + ": printStackTrace:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static class TelHandler implements java.lang.reflect.InvocationHandler {

        private final String tag;
        private final String methodName;
        private Object real;

        /**
         * 反射处理器
         *
         * @param tag        日志TAG
         * @param methodName 拦截的方法名
         * @param real       x
         */
        public TelHandler(String tag, String methodName, Object real) {
            this.real = real;
            this.methodName = methodName;
            this.tag = tag;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.d(tag, "method invoke " + method.getName());
            if (methodName.equals(method.getName())) {
                // 拦截Mac地址的获取
//                    Toast.makeText(GlobalApplication.get().getApplicationContext(), "正在获取Mac地址")
                try {
                    throw new NullPointerException();
                } catch (NullPointerException e1) {
                    // 打印调用任务栈
                    Log.e(tag, Log.getStackTraceString(e1));
                }

                if (cacheWifiInfo != null) {
                    Log.d(tag, "cacheWifiInfo:" + cacheWifiInfo);
                    return cacheWifiInfo;
                }
//                WifiInfo wifiInfo = null;
//                try {
//                    Class cls = WifiInfo.class;
//                    wifiInfo = (WifiInfo) cls.newInstance();
//                    Field mMacAddressField = cls.getDeclaredField("mMacAddress");
//                    mMacAddressField.setAccessible(true);
//                    mMacAddressField.set(wifiInfo, "");
//                    cacheWifiInfo = wifiInfo;
//                    Log.d(tag, "wifiInfo:" + wifiInfo);
//                } catch (Exception e) {
//                    Log.e(tag, "WifiInfo error:" + e.getMessage());
//                }
//                return wifiInfo;
                return "T";
            } else {
                return method.invoke(real, args);
            }
        }
    }
}