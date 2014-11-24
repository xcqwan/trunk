package com.gezbox.windmap.app.utils;

import android.util.Log;
import com.loopj.android.http.Base64;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.utils.UrlSafeBase64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by chenfei on 14-11-5.
 */
public class EncryptUtil {


    private static final int BUFFER_SIZE = 1024;

    /**
     * BASE64 加密
     *
     * @param str
     * @return
     */
    public static String encryptBASE64(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            byte[] encode = str.getBytes("UTF-8");
            // base64 加密
            return new String(Base64.encode(encode, 0, encode.length, Base64.DEFAULT), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "US-ASCII";

    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     * @throws java.security.InvalidKeyException
     * @see <a href =
     * "http://tools.ietf.org/html/draft-hammer-oauth-10#section-3.4.2">HMAC-SHA1</a>
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        Mac mac = Mac.getInstance(MAC_NAME);
        SecretKeySpec spec = new SecretKeySpec(encryptKey.getBytes("US-ASCII"), MAC_NAME);
        mac.init(spec);
        byte[] text = encryptText.getBytes(ENCODING);
        return mac.doFinal(text);
    }


    //下面这个函数用于将字节数组换成成16进制的字符串

    public static String byteArrayToHex(byte[] byteArray) {

        // 首先初始化一个字符数组，用来存放每个16进制字符

        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））

        char[] resultCharArray = new char[byteArray.length * 2];


        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

        int index = 0;

        for (byte b : byteArray) {

            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];

            resultCharArray[index++] = hexDigits[b & 0xf];

        }


        // 字符数组组合成字符串返回

        return new String(resultCharArray);
    }




    public static String getFileMD5String(File file) throws IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            FileInputStream in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            messageDigest.update(byteBuffer);
            return byteArrayToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {

        }
        return "失败";
    }


    public static void uploadImageByQiNiu(File file, UpCompletionHandler upCompletionHandler) {

        final String MY_ACCESS_KEY = "zVxhvVY8ggEUftanwKVdmqNLvoi2IXrOTZG9NwMT";
        final String MY_SECRET_KEY = "IIiL9fdiVOHqmiixrF6NYY-pRMVU5Gjo5UfnYPUE";

        try {
            String md5 = EncryptUtil.getFileMD5String(file);
            String putPolicy = "{" + "\"scope\":\"mrwind:" + md5 + ".jpg" + "\"," + "\"deadline\":1577836800" + "}";    //截至日期到2020.1.1
            Log.i("qiniu", "上传策略" + putPolicy);
            String encodedPutPolicy = UrlSafeBase64.encodeToString(putPolicy);
            Log.i("qiniu", "对JSON编码的上传策略进行URL安全的Base64编码，得到待签名字符串：" + encodedPutPolicy);

            byte[] sign;
            String encodedSign;
            String uploadToken;
            sign = EncryptUtil.HmacSHA1Encrypt(encodedPutPolicy, MY_SECRET_KEY);
            encodedSign = UrlSafeBase64.encodeToString(sign);
            Log.i("qiniu", "对签名进行URL安全的Base64编码：" + encodedSign);
            uploadToken = MY_ACCESS_KEY + ":" + encodedSign + ":" + encodedPutPolicy;
            Log.i("qiniu", "将AccessKey、encodedSign和encodedPutPolicy用:连接起来：" + uploadToken);

            UploadManager uploadManager = new UploadManager();
            uploadManager.put(file, md5 + ".jpg", uploadToken
                    , upCompletionHandler, null);
        } catch (Exception e) {

        }
    }
}
