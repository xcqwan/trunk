package com.gezbox.windmap.app.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * 读取Menifest文件中的元数据
 * Created with IntelliJ IDEA.
 * User: jason
 * Date: 6/8/13
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManifestMetaData {

    private static Object readKey(Context context, String keyName) {
        try {
            ApplicationInfo appi =  context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = appi.metaData;
            Object value = bundle.get(keyName);
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static int getInt(Context context, String keyName) {
        Object object = readKey(context, keyName);
        if(object != null)
            return (Integer)object;
        else
            return -1;
    }
    public static String getString(Context context, String keyName ) {
        return (String ) readKey(context, keyName);
    }
    public static Boolean getBoolean(Context context, String keyName) {
        return (Boolean) readKey(context, keyName);
    }
    public static Object get(Context context, String keyName) {
        return readKey(context, keyName);
    }

}
