package com.gezbox.windmap.app.backend;

import android.content.Context;
import com.gezbox.windmap.app.model.ShortUrl;
import com.gezbox.windmap.app.utils.Constant;
import com.gezbox.windmap.app.utils.DeviceInfoUtils;
import com.gezbox.windmap.app.utils.ResourceUrl;
import com.gezbox.windmap.app.utils.SharedPrefsUtils;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

import java.util.List;

/**
 * Created by zombie on 14-11-14.
 */
public class ShortURLBackend {
    private static ShortURLBackend singleton = null;
    private SharedPrefsUtils mSharedPrefsUtils;
    private RestAdapter mRestAdapter;
    private Server mServerInstance;

    public static ShortURLBackend with(Context context) {
        if (singleton == null) {
            singleton = new ShortURLBackend(context);
        }
        return singleton;
    }

    private ShortURLBackend(final Context context) {
        mSharedPrefsUtils = new SharedPrefsUtils(context, Constant.SharedPrefrence.SHARED_NAME);
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
                requestFacade.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36");
            }
        };

        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(ResourceUrl.SHORT_URL)
                .setRequestInterceptor(requestInterceptor)
                .build();
        mRestAdapter.setLogLevel(RestAdapter.LogLevel.FULL);

        mServerInstance = mRestAdapter.create(Server.class);
    }

    public void getShort(String url, Callback<List<ShortUrl>> callback) {
        mServerInstance.getShortURL(url, callback);
    }
}
