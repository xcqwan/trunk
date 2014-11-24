package com.gezbox.windmap.app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import java.io.*;

/**
 * Created by zombie on 14-10-31.
 */
public class BitmapUtils {
    public static final int targetWidth = 540;
    public static final int targetHeight = 960;

    /**
     * 根据Bitmap最小边, 居中裁剪正方形
     *
     * @param bitmap
     * @return
     */
    public static Bitmap cropSquare(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int minSize = width > height ? height : width;

        int retX = width > height ? (width - height) / 2 : 0;
        int retY = width < height ? (height - width) / 2 : 0;

        return Bitmap.createBitmap(bitmap, retX, retY, minSize, minSize, null, false);
    }


    /**
     *
     * 设定一个目标大小, 然后进行压缩
     *
     * @param bitmap
     * @param maxSize 单位未K, 如100, 则压缩后的大小为 90-100K
     *                该值如果太大会导致溢出OOM
     * @return
     */
    public static Bitmap compressBitmapWithMaxSize(Bitmap bitmap, int maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;
        do {
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            Log.v("size", String.valueOf(baos.toByteArray().length / 1024));
            if (options >= 10) {
                options -= 10;
            } else {
                options -= 2;
            }
        } while (baos.toByteArray().length / 1024 > maxSize);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

        return BitmapFactory.decodeStream(bais, null, null);
    }


    public static double calScaleRatio(int originalWidth, int originalHeight) {
        if (originalWidth == 0 || originalHeight == 0) {
            return 1;
        } else {
            double ratio = Math.sqrt((targetWidth * targetHeight * 1.0) / (originalWidth * originalHeight));
            if (ratio > 1) {
                return 1;
            } else {
                return ratio;
            }
        }
    }

    public static Bitmap scaleBitmap(Bitmap bitmap) {
        double ratio = calScaleRatio(bitmap.getWidth(), bitmap.getHeight());
        return Bitmap.createScaledBitmap(bitmap, (int) (ratio * bitmap.getWidth()), (int) (ratio * bitmap.getHeight()), false);
    }

    public static Bitmap scaleBitmap(String filename) {
        Bitmap bitmap = BitmapFactory.decodeFile(filename);
        return scaleBitmap(bitmap);

    }

    /**
     * Bitmap 转成文件
     * @param bitmap
     * @return
     */
    public static File bitmapToFile(int index, Bitmap bitmap) {
        File sdRoot = Environment.getExternalStorageDirectory();
        File file = new File(sdRoot, "temp" + index + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] byteArray = baos.toByteArray();

            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(byteArray);

            return file;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
}
