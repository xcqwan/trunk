package com.gezbox.windmap.app.utils;

/**
 * Created by zombie on 14-11-6.
 */
public class SMSUtils {
    public static String inviteWindAddress(String name, String id) {
        return "http://0.123feng.com/invite?name=" + name + "&user_id=" + id;
    }

    public static String inviteWindMessage(String url) {
        return "推荐一份好工作，行业超高底薪加奖金，外卖配送员月收入也能超6000啦，前往 " + url + " 即刻申请加入风先生。";
    }

    public static String inviteShopAddress(String name, String id) {
        return "http://b.123feng.com/invite?name=" + name + "&user_id=" + id;
    }

    public static String inviteShopMessage(String url) {
        return "我正在用风先生超快配送服务，一键即可呼叫配送员，点击 " + url + " 进入注册页面，让风先生帮我们送货吧！";
    }
}