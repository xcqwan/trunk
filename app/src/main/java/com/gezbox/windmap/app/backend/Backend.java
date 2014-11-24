package com.gezbox.windmap.app.backend;

import android.content.Context;
import android.util.Log;
import com.gezbox.windmap.app.model.*;
import com.gezbox.windmap.app.params.LoginParams;
import com.gezbox.windmap.app.utils.Constant;
import com.gezbox.windmap.app.utils.DeviceInfoUtils;
import com.gezbox.windmap.app.utils.ResourceUrl;
import com.gezbox.windmap.app.utils.SharedPrefsUtils;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

import java.util.List;

/**
 * Created by zombie on 14-10-29.
 */
public class Backend {
    private static Backend singleton = null;
    private SharedPrefsUtils mSharedPrefsUtils;
    private RestAdapter mRestAdapter;
    private Server mServerInstance;

    public static Backend with(Context context) {
        if (singleton == null) {
            singleton = new Backend(context);
        }
        return singleton;
    }

    private Backend(final Context context) {
        mSharedPrefsUtils = new SharedPrefsUtils(context, Constant.SharedPrefrence.SHARED_NAME);
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
                requestFacade.addHeader("User-Agent", DeviceInfoUtils.getUserAgent(context));
            }
        };

        mRestAdapter = new RestAdapter.Builder()
                .setEndpoint(ResourceUrl.BASE_URL)
                .setRequestInterceptor(requestInterceptor)
                .build();
        mRestAdapter.setLogLevel(RestAdapter.LogLevel.FULL);

        mServerInstance = mRestAdapter.create(Server.class);
    }

    public void getLasterVersion(Callback<Version> callback) {
        mServerInstance.getLasterVersion(getToken(), callback);
    }

    public void postLogin(LoginParams params, Callback<Token> callback) {
        mServerInstance.postLogin(params, callback);
    }

    public void getUserInfo(String user_id, Callback<WindAccount> callback) {
        mServerInstance.getUserInfo(getToken(), user_id, callback);
    }

    public void modifyPassword(ModifyPassword modifyPassword, Callback<WindAccount> callback) {
        mServerInstance.putUserInfo(getToken(), modifyPassword, getUserID(), callback);
    }

    public void getData(Callback<DataInfo> callback) {
        mServerInstance.getData(getToken(), callback);
    }

    public void recommendDeliver(RecommendDeliver info, Callback<Object> smsCallback) {
        mServerInstance.postRecommendDeliver(getToken(), info, smsCallback);
    }

    public void recommendMall(RecommendMall info, Callback<Object> smsCallback) {
        mServerInstance.postRecommendMall(getToken(), info, smsCallback);
    }

    public void getArea(String area_code, Callback<List<Area>> callback) {
        mServerInstance.getArea(area_code, callback);
    }

    public void createCheck(String tel, Callback<CreateCheck> callback) {
        mServerInstance.getCreateCheck(tel, callback);
    }

    public void postShop(Shop shop, Callback<Object> callback) {
        mServerInstance.postShop(getToken(), shop, callback);
    }

    public String getToken() {
        return "token " + mSharedPrefsUtils.getStringSP(Constant.SharedPrefrence.TOKEN, "");
    }

    public String getTokenWithoutPrefix() {
        return mSharedPrefsUtils.getStringSP(Constant.SharedPrefrence.TOKEN, "");
    }

    public String getUserID() {
        return mSharedPrefsUtils.getStringSP(Constant.SharedPrefrence.USER_ID, "");
    }
}
