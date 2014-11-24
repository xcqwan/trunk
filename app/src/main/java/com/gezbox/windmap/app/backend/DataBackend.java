package com.gezbox.windmap.app.backend;

import android.content.Context;
import com.gezbox.windmap.app.model.DataInfo;
import com.gezbox.windmap.app.utils.Constant;
import com.gezbox.windmap.app.utils.DeviceInfoUtils;
import com.gezbox.windmap.app.utils.ResourceUrl;
import com.gezbox.windmap.app.utils.SharedPrefsUtils;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by zombie on 14-11-10.
 */
public class DataBackend {
    private static DataBackend singleton = null;
    private SharedPrefsUtils mSharedPrefsUtils;
    private RestAdapter mRestAdapter;
    private Server mServerInstance;

    public static DataBackend with(Context context) {
        if (singleton == null) {
            singleton = new DataBackend(context);
        }
        return singleton;
    }

    private DataBackend(final Context context) {
        mSharedPrefsUtils = new SharedPrefsUtils(context, Constant.SharedPrefrence.SHARED_NAME);
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
                requestFacade.addHeader("User-Agent", DeviceInfoUtils.getUserAgent(context));
            }
        };

        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(ResourceUrl.DATA_URL)
                .setRequestInterceptor(requestInterceptor)
                .build();
        mRestAdapter.setLogLevel(RestAdapter.LogLevel.FULL);

        mServerInstance = mRestAdapter.create(Server.class);
    }

    public void getData(Callback<DataInfo> callback) {
        mServerInstance.getData(getToken(), callback);
    }

    public String getToken() {
        return "token " + mSharedPrefsUtils.getStringSP(Constant.SharedPrefrence.TOKEN, "");
    }
}
