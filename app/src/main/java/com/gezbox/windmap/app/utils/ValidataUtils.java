package com.gezbox.windmap.app.utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zombie on 14-11-6.
 */
public class ValidataUtils {
    /**
     * 判断邮箱地址是否正确
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    /**
     * 判断手机号码是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    /**
     * 判断固定电话是否正确
     *
     * @param tel
     * @return
     */
    public static boolean isTel(String tel) {
        Pattern p = Pattern.compile("^((010|021|022|023|0\\d{3})?(\\d{7}|\\d{8}))$");
        Matcher m = p.matcher(tel);

        return m.matches();
    }
}
