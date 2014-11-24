package com.gezbox.windmap.app.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by zombie on 14-10-29.
 */
public class DeviceInfoUtils {
    /**
     * 获取userAgent
     * 格式
     * Android/Android版本号 (设备型号; ROM版本号; 是否root(unrooted, rooted); 使用何种网络) PackageName/版本号
     * 例如
     * Android/4.2 (MI-ONE Plus; MIUI-2.3.6f; unroot; Wi-Fi) com.gezbox.iphonecase/2.1
     *
     * @return
     */
    public static String getUserAgent(Context context) {
        try {
            return "Android/" + Build.VERSION.SDK_INT
                    + " (" + Build.MODEL
                    + "; " + Build.DISPLAY
                    + "; " + (isDeviceRooted() ? "root" : "unroot")
                    + "; " + (getNetWorkStatus(context) == NetworkType.NETWORK_TYPE_WIFI.ordinal() ? "WI-FI" : getNetworkType(context))
                    + "; " + Locale.getDefault().toString()
                    + ") " + context.getPackageName()
                    + "/" + getVersionCode(context)
                    + " stenographer/" + SharedPrefsUtils.getStatID(context);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static enum NetworkType {
        NETWORK_UNAVAILABLE(-1), NETWORK_TYPE_WAP(0), NETWORK_TYPE_NET(1), NETWORK_TYPE_WIFI(2);
        // 定义私有变量
        private int nCode;

        // 构造函数，枚举类型只能为私有
        private NetworkType(int nCode) {
            this.nCode = nCode;
        }

        @Override
        public String toString() {
            return String.valueOf(this.nCode);
        }
    }

    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 检测设备是否root
     *
     * @return
     */
    public static boolean isDeviceRooted() {
        if (checkRootMethod1()) {
            return true;
        }
        if (checkRootMethod2()) {
            return true;
        }
        if (checkRootMethod3()) {
            return true;
        }
        return false;
    }

    public static boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;

        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }
        return false;
    }

    public static boolean checkRootMethod2() {
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Exception e) {
        }

        return false;
    }

    public static boolean checkRootMethod3() {
        if (new ExecShell().executeCommand(ExecShell.SHELL_CMD.check_su_binary) != null) {
            return true;
        } else {
            return false;
        }
    }

    public static class ExecShell {

        private static String LOG_TAG = ExecShell.class.getName();

        public static enum SHELL_CMD {
            check_su_binary(new String[]{"/system/xbin/which", "su"}),;

            String[] command;

            SHELL_CMD(String[] command) {
                this.command = command;
            }
        }

        public ArrayList<String> executeCommand(SHELL_CMD shellCmd) {
            String line = null;
            ArrayList<String> fullResponse = new ArrayList<String>();
            Process localProcess = null;

            try {
                localProcess = Runtime.getRuntime().exec(shellCmd.command);
            } catch (Exception e) {
                return null;
                //e.printStackTrace();
            }

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(localProcess.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));

            try {
                while ((line = in.readLine()) != null) {
                    Log.d(LOG_TAG, "--> Line received: " + line);
                    fullResponse.add(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d(LOG_TAG, "--> Full response was: " + fullResponse);

            return fullResponse;
        }

    }


    /**
     * 获得网络类型
     *
     * @param context
     * @return
     */
    public static int getNetWorkStatus(Context context) {
        if (context == null)
            return -1;
        ConnectivityManager lConnectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != lConnectivity) {
            NetworkInfo info = lConnectivity.getActiveNetworkInfo();
            if (info != null) {
                if (info.getTypeName().toLowerCase().equals("wifi")) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return NetworkType.NETWORK_TYPE_WIFI.ordinal();
                    } else {
                        return NetworkType.NETWORK_UNAVAILABLE.ordinal();
                    }
                } else if (info.getTypeName().toLowerCase().equals("mobile")) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        if (info.getExtraInfo() != null && info.getExtraInfo().indexOf("wap") != -1)
                            return NetworkType.NETWORK_TYPE_WAP.ordinal();
                        else
                            return NetworkType.NETWORK_TYPE_NET.ordinal();
                    }
                }
            }
        }
        return NetworkType.NETWORK_UNAVAILABLE.ordinal();
    }

    static final String NETWORK_TYPE_1xRTT = "1xRTT";
    static final String NETWORK_TYPE_CDMA = "CDMA";
    static final String NETWORK_TYPE_EDGE = "EDGE";
    static final String NETWORK_TYPE_EHRPD = "eHRPD";
    static final String NETWORK_TYPE_EVDO_0 = "EVDO revision 0";
    static final String NETWORK_TYPE_EVDO_A = "EVDO revision A";
    static final String NETWORK_TYPE_EVDO_B = "EVDO revision B";
    static final String NETWORK_TYPE_GPRS = "GPRS";
    static final String NETWORK_TYPE_HSDPA = "HSDPA";
    static final String NETWORK_TYPE_HSPA = "HSPA";
    static final String NETWORK_TYPE_HSPAP = "HSPA+";
    static final String NETWORK_TYPE_HSUPA = "HSUPA";
    static final String NETWORK_TYPE_IDEN = "iDen";
    static final String NETWORK_TYPE_LTE = "LTE";
    static final String NETWORK_TYPE_UMTS = "UMTS";
    static final String NETWORK_TYPE_UNKNOWN = "unknown";

    public static String getNetworkType(Context context) {
        if (context == null)
            return "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (tm.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return NETWORK_TYPE_1xRTT;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return NETWORK_TYPE_CDMA;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return NETWORK_TYPE_EDGE;
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return NETWORK_TYPE_EHRPD;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return NETWORK_TYPE_EVDO_0;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return NETWORK_TYPE_EVDO_A;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return NETWORK_TYPE_EVDO_B;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return NETWORK_TYPE_GPRS;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return NETWORK_TYPE_HSDPA;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return NETWORK_TYPE_HSPA;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORK_TYPE_HSPAP;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return NETWORK_TYPE_HSUPA;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_TYPE_IDEN;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_TYPE_LTE;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return NETWORK_TYPE_UMTS;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return NETWORK_TYPE_UNKNOWN;


        }
        return "";
    }

    public static String getImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String info =  tm.getDeviceId();
        return info;
    }
}
