package com.gezbox.windmap.app.activity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.backend.Backend;
import com.gezbox.windmap.app.model.Version;
import com.gezbox.windmap.app.utils.Constant;
import com.gezbox.windmap.app.utils.ResourceUrl;
import com.gezbox.windmap.app.utils.SharedPrefsUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.io.File;

/**
 * 启动页
 * Created by zombie on 14-10-29.
 */
public class LaunchActivity extends BaseActivity {
    private Callback<Version> checkVersionCallback = new Callback<Version>() {
        @Override
        public void success(Version version, Response response) {
            int lastVersion = version.getVersion();
            if (lastVersion > getVersionCode()) {
                showNewVersionDialog(version.getRelease_note());
            } else {
                startAppLogic();
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            startAppLogic();
        }
    };

    private BroadcastReceiver downLoadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //这里可以取得下载的id，这样就可以知道哪个文件下载完成了。适用与多个下载任务的监听
            Intent promptInstall = new Intent(Intent.ACTION_VIEW);
            promptInstall.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/WindMap.apk")), "application/vnd.android.package-archive");
            startActivity(promptInstall);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launch);
        //检查新版本
        Backend.with(this).getLasterVersion(checkVersionCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(downLoadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(downLoadReceiver);
    }

    /**
     * 应用启动逻辑
     */
    private void startAppLogic() {
        SharedPrefsUtils sharedPrefsUtils = new SharedPrefsUtils(this, Constant.SharedPrefrence.SHARED_NAME);
        boolean isLogged = sharedPrefsUtils.getBooleanSP(Constant.SharedPrefrence.LOGGED, false);
        if (isLogged) {
            startActivity(new Intent(LaunchActivity.this, MainCardActivity.class));
        } else {
            startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
        }
        finish();
    }

    /**
     * 下载新版本
     */
    public void downLoad() {
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        String url = ResourceUrl.BASE_URL + "/downloads/apps/883/android/latest-package?app_type=windmap" + "&token=" + Backend.with(this).getTokenWithoutPrefix();
        Uri uri = Uri.parse(url);

        Log.i("download", url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setAllowedOverRoaming(false);

        //设置文件类型
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        request.setMimeType(mimeString);

        //在通知栏中显示
        request.setVisibleInDownloadsUi(true);
        //先删除已有的文件
        File file = new File(Environment.getExternalStorageDirectory() + "/download/WindMap.apk");
        if (file.exists()) {
            file.delete();
        }
        //sdcard的目录下的download文件夹
        request.setDestinationInExternalPublicDir("/download/", "WindMap.apk");
        request.setTitle("风地图更新");
        downloadManager.enqueue(request);
    }

    /**
     * 新版本提示
     */
    public void showNewVersionDialog(String releaseNote) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("有新版本!")
                .setMessage(releaseNote)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            downLoad();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).setCancelable(false)
                .show();
    }
}
