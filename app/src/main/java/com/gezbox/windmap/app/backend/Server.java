package com.gezbox.windmap.app.backend;

import com.gezbox.windmap.app.model.*;
import com.gezbox.windmap.app.params.LoginParams;
import retrofit.Callback;
import retrofit.http.*;

import java.util.List;

/**
 * Created by zombie on 14-10-29.
 */
public interface Server {
    /**
     * 获取应用的最新版本号
     *
     * @param callback
     */
    @GET("/apps/883/android/version?app_type=windmap")
    void getLasterVersion(@Header("Authorization") String authorization, Callback<Version> callback);

    /**
     * 邮箱登录, 获取token
     * @param loginParams
     * @param callback
     */
    @POST("/apps/883/user/auth/login")
    void postLogin(@Body LoginParams loginParams, Callback<Token> callback);

    /**
     * 获取用户信息
     * @param authorization
     * @param user_id
     * @param callback
     */
    @GET("/cloud/apps/883/user/auth/accounts/{user_id}")
    void getUserInfo(@Header("Authorization") String authorization, @Path("user_id") String user_id, Callback<WindAccount> callback);

    /**
     * 修改密码
     * @param authorization
     * @param modifyPassword
     * @param user_id
     * @param callback
     */
    @PUT("/cloud/apps/883/user/auth/accounts/{user_id}")
    void putUserInfo(@Header("Authorization") String authorization, @Body ModifyPassword modifyPassword, @Path("user_id") String user_id, Callback<WindAccount> callback);

    /**
     * 风地图数据
     * @param authorization
     * @param callback
     */
    @GET("/cloud/apps/883/stats/data/today/100000000000")
    void getData(@Header("Authorization") String authorization, Callback<DataInfo> callback);

    /**
     * 上传邀请风先生信息
     * @param authorization
     * @param callback
     */
    @POST("/apps/883/user/deliver/recommend")
    void postRecommendDeliver(@Header("Authorization") String authorization, @Body RecommendDeliver recommendDeliver, Callback<Object> callback);

    /**
     * 上传邀请商户信息
     * @param authorization
     * @param callback
     */
    @POST("/apps/883/mall/recommend")
    void postRecommendMall(@Header("Authorization") String authorization, @Body RecommendMall recommendMall, Callback<Object> callback);

    /**
     * 获取地区列表
     *
     * @param area_code
     * @param callback
     */
    @GET("/apps/883/app/areas/{area_code}")
    void getArea(@Path("area_code") String area_code, Callback<List<Area>> callback);

    /**
     * 检查手机号是否允许创建店铺
     *
     * @param tel
     * @param callback
     */
    @GET("/apps/883/mall/shop/create/check")
    void getCreateCheck(@Query("tel") String tel, Callback<CreateCheck> callback);

    /**
     * 上传店铺信息
     * @param authorization
     * @param Shop
     * @param callback
     */
    @POST("/apps/883/mall/map/business/shops")
    void postShop(@Header("Authorization") String authorization, @Body Shop Shop, Callback<Object> callback);

    /**
     * 生成短网址
     * @param url_long
     * @param callback
     */
    @GET("/short_url/shorten.json?source=3931849780")
    void getShortURL(@Query("url_long") String url_long, Callback<List<ShortUrl>> callback);
}
