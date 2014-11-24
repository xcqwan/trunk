package com.gezbox.windmap.app.utils;

import android.os.Environment;

/**
 * Created by zombie on 14-10-29.
 */
public class Constant {
    public final static String SHARE_PREFS_STATID = "share_prefs_statid";

    public static class SharedPrefrence {
        public static final String SHARED_NAME = "wind_map";
        public static final String LOGGED = "logged";
        public static final String TOKEN = "token";
        public static final String USER_ID = "user_id";
        public static final String TEL = "tel";
        public static final String USER_NAME = "user_name";
        public static final String AVATAR = "avatar";
    }

    public static class CameraConstant {
        public final static String FILE_START_NAME = "VMS_";
        public final static String VIDEO_EXTENSION = ".mp4";
        public final static String IMAGE_EXTENSION = ".jpg";
        public final static String DCIM_FOLDER = "/DCIM";
        public final static String CAMERA_FOLDER = "/video";
        public final static String TEMP_FOLDER = "/Temp";
        public final static String CAMERA_FOLDER_PATH = Environment.getExternalStorageDirectory().toString() + CameraConstant.DCIM_FOLDER + CameraConstant.CAMERA_FOLDER;
        public final static String TEMP_FOLDER_PATH = Environment.getExternalStorageDirectory().toString() + CameraConstant.DCIM_FOLDER + CameraConstant.CAMERA_FOLDER + CameraConstant.TEMP_FOLDER;
        public final static String  VIDEO_CONTENT_URI = "content://media/external/video/media";

        public final static int RESOLUTION_HIGH = 1300;
        public final static int RESOLUTION_MEDIUM = 500;
        public final static int RESOLUTION_LOW = 180;

        public final static int RESOLUTION_HIGH_VALUE = 2;
        public final static int RESOLUTION_MEDIUM_VALUE = 1;
        public final static int RESOLUTION_LOW_VALUE = 0;
    }
}
