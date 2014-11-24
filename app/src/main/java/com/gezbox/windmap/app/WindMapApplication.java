package com.gezbox.windmap.app;

import android.app.Application;
import com.baidu.mapapi.SDKInitializer;
import com.joshdholtz.sentry.Sentry;

/**
 * Created by zombie on 14-11-13.
 */
public class WindMapApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SDKInitializer.initialize(this);

        if (!BuildConfig.DEBUG) {
            Sentry.init(getApplicationContext(), "http://182.92.109.147:9000", "http://a62ad277fd0448f8ba27af6234cad7a3:843890e8026a442994452fcbda970031@182.92.109.147:9000/19");
        }
    }
}
