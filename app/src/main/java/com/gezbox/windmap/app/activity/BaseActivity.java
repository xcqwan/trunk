package com.gezbox.windmap.app.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.gezbox.windmap.app.backend.Backend;
import com.gezbox.windmap.app.model.RecommendDeliver;
import com.gezbox.windmap.app.model.RecommendMall;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zombie on 14-10-29.
 */
public class BaseActivity extends Activity {
    private ProgressDialog progressDialog;
    private LocationClient mLocationClient;
    private MyLocationListener mBaiduLocationListener;

    private Callback<Object> smsCallback = new Callback<Object>() {
        @Override
        public void success(Object o, Response response) {
            showProgressDialog("", false);
            Toast.makeText(getApplicationContext(), "上传邀请信息成功", Toast.LENGTH_SHORT ).show();
            finish();
        }

        @Override
        public void failure(RetrofitError error) {
            showProgressDialog("", false);
            if (error.getResponse() != null) {
                Toast.makeText(getApplicationContext(), "上传邀请信息失败, 错误码: " + error.getResponse().getStatus(), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getApplicationContext(), "网络请求失败, 请检查您的网络!", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    /**
     * 通讯提示框
     *
     * @param msg
     * @param showStatus
     */
    public void showProgressDialog(String msg, boolean showStatus) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }

        if (showStatus) {
            progressDialog.setMessage(msg);
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    /**
     * 调用拨号盘
     *
     * @param tel
     */
    public void makeCall(String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 直接发送短信
     * @param message
     * @param tel
     */
    public void sendSMS(String message, final String tel) {
        SmsManager smsManager = SmsManager.getDefault();
        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);
        this.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getApplicationContext(), "短信发送成功", Toast.LENGTH_SHORT).show();

                        RecommendDeliver info = new RecommendDeliver();
                        info.setName("");
                        info.setTel(tel);
                        showProgressDialog("上传邀请信息", true);
                        Backend.with(getApplicationContext()).recommendDeliver(info, smsCallback);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "短信发送失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION));
        smsManager.sendTextMessage(tel, null, message, sentPI, null);
    }

    public void sendSMS(String message, final String name, final String tel) {
        SmsManager smsManager = SmsManager.getDefault();
        String SENT_SMS_ACTION = "SENT_SMS_ACTION";
        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, sentIntent, 0);
        this.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getApplicationContext(), "短信发送成功", Toast.LENGTH_SHORT).show();

                        RecommendMall info = new RecommendMall();
                        info.setPhone(tel);
                        info.setShop_name(name);
                        showProgressDialog("上传邀请信息", true);
                        Backend.with(getApplicationContext()).recommendMall(info, smsCallback);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "短信发送失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT_SMS_ACTION));
        smsManager.sendTextMessage(tel, null, message, sentPI, null);
    }

    /**
     * 得到manifest中的versionCode
     */
    public int getVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 开始定位
     */
    public void startLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        mBaiduLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mBaiduLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        mLocationClient.requestLocation();
    }

    /**
     * 定位结束
     */
    public void finishLocation(double latitude, double longitude, String address) {
        Log.d("Location", latitude + ", " + longitude + ", " + address);
    }

    /**
     * 定位失败
     */
    public void errerLocation() {
        Log.d("Location", "定位失败");
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                errerLocation();
                return;
            }
            double latitude = bdLocation.getLatitude();
            double longitude = bdLocation.getLongitude();
            String address = bdLocation.getProvince() + bdLocation.getCity() + bdLocation.getDistrict() + bdLocation.getStreet();

            finishLocation(latitude, longitude, address);
            mLocationClient.stop();
        }
    }
}
