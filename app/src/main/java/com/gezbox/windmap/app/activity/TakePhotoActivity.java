package com.gezbox.windmap.app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.utils.BitmapUtils;
import com.gezbox.windmap.app.utils.EncryptUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zombie on 14-10-31.
 */
public class TakePhotoActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener {
    private SurfaceView sv_cemera;

    private RelativeLayout rl_top_1;
    private RelativeLayout rl_top_2;
    private RelativeLayout rl_top_3;
    private RelativeLayout rl_top_4;
    private RelativeLayout rl_top_5;
    private RelativeLayout rl_top_6;
    private RelativeLayout rl_top_7;
    private RelativeLayout rl_top_8;
    private RelativeLayout rl_top_9;

    private ImageView iv_take_1;
    private ImageView iv_take_2;
    private ImageView iv_take_3;
    private ImageView iv_take_4;
    private ImageView iv_take_5;
    private ImageView iv_take_6;
    private ImageView iv_take_7;
    private ImageView iv_take_8;
    private ImageView iv_take_9;

    private TextView tv_address;

    private LinearLayout ll_take_photo;

    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;

    private List<Bitmap> mTakePhotoList = new ArrayList<Bitmap>();
    private ArrayList<CharSequence> mUploadID = new ArrayList<CharSequence>();

    private int max_takes = 0;

    /**
     * 拍照回调
     */
    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // 根据拍照所得的数据创建位图
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

            Matrix matrix = new Matrix();
            matrix.reset();
            matrix.postRotate(90);
            Bitmap bMapRotate = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap = BitmapUtils.scaleBitmap(bMapRotate);

            int nowIndex = mTakePhotoList.size();
            uploadPhoto(nowIndex, bitmap);

            mTakePhotoList.add(bitmap);
            mUploadID.add("");
            topPhotoChange();

            if (mTakePhotoList.size() == 9) {
                mCamera.stopPreview();
            } else {
                mCamera.startPreview();
            }

            ll_take_photo.setEnabled(true);
        }
    };

    /**
     * 自动聚焦回调
     */
    private Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean b, Camera camera) {
            if (b) {
                // takePicture()方法需要传入三个监听参数
                // 第一个监听器；当用户按下快门时激发该监听器
                // 第二个监听器；当相机获取原始照片时激发该监听器
                // 第三个监听器；当相机获取JPG照片时激发该监听器
                camera.takePicture(null, null, pictureCallback);
            }
        }
    };

    /**
     * surface Holder 回调
     */
    private SurfaceHolder.Callback surfaceHolderCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            // 此处默认打开后置摄像头
            // 通过传入参数可以打开前置摄像头
            mCamera = Camera.open();
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.setDisplayOrientation(90);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 开始预览
            mCamera.startPreview();
            ll_take_photo.setClickable(true);
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
            Camera.Parameters parameters = mCamera.getParameters();
            // 设置预览照片时每秒显示多少帧的最小值和最大值
            parameters.setPreviewFpsRange(4, 10);
            // 设置照片的格式
            parameters.setPictureFormat(ImageFormat.JPEG);
            // 设置JPG照片的质量
            parameters.set("jpeg-quality", 100);

            int surfaceWidth = sv_cemera.getWidth();
            int surfaceHeight = sv_cemera.getHeight();

            int targetWidth = surfaceWidth;
            int targetHeight = surfaceHeight;
            double distance = Math.pow(surfaceWidth, 2) + Math.pow(surfaceHeight, 2);
            for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
                int w = size.width;
                int h = size.height;
                double d = Math.pow(surfaceWidth - w, 2) + Math.pow(surfaceHeight - h, 2);
                if (d < distance) {
                    targetWidth = w;
                    targetHeight = h;
                    distance = d;
                }
            }
            //设置预览大小
            parameters.setPreviewSize(targetWidth, targetHeight);

            if ((surfaceWidth - targetWidth) * (surfaceHeight - targetHeight) < 0) {
                int temp = targetHeight;
                targetHeight = targetWidth;
                targetWidth = temp;
            }
            sv_cemera.setLayoutParams(new RelativeLayout.LayoutParams(targetWidth, targetHeight));
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            //释放摄像头
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_take_photo);

        initCustom();
        initListener();

        max_takes = getIntent().getIntExtra("max_takes", 0);

        startLocation();
    }

    @Override
    public void finishLocation(double latitude, double longitude, String address) {
        super.finishLocation(latitude, longitude, address);
        tv_address.setText(address);
        tv_address.setClickable(false);
        tv_address.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_location_blue), null, null, null);
    }

    @Override
    public void errerLocation() {
        super.errerLocation();
        tv_address.setText("定位失败, 点击重新定位");
        tv_address.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_location_gray), null, null, null);
        tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_address.setText("正在定位...");
                startLocation();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_cancel) {
            //取消
            onBackPressed();
        } else if (id == ll_take_photo.getId()) {
            //拍摄
            if (mTakePhotoList.size() == max_takes) {
                Toast.makeText(this, "最多只能拍摄" + max_takes + "张", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mCamera != null) {
                //摄像头自动对焦后才能拍摄
                ll_take_photo.setEnabled(false);
                mCamera.autoFocus(autoFocusCallback);
            }
        } else if (id == R.id.ll_submit) {
            //完成
            //检查是否所有都上传完成
            StringBuilder sb_unfinished = new StringBuilder();
            for (int i = 0 ; i < mUploadID.size() ; i++) {
                String key = mUploadID.get(i).toString();
                if (key.isEmpty()) {
                    if (sb_unfinished.length() > 0) {
                        sb_unfinished.append(", ");
                    }
                    sb_unfinished.append(i + 1);
                }
            }
            if (sb_unfinished.length() > 0) {
                Toast.makeText(this, "请等待第" + sb_unfinished.toString() + "张图片上传完成", Toast.LENGTH_SHORT).show();
                return;
            }

            mTakePhotoList.clear();
            Intent intent = new Intent();
            intent.putCharSequenceArrayListExtra("upload_ids", mUploadID);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void initCustom() {
        sv_cemera = (SurfaceView) findViewById(R.id.sv_cemera);

        rl_top_1 = (RelativeLayout) findViewById(R.id.rl_top_1);
        rl_top_2 = (RelativeLayout) findViewById(R.id.rl_top_2);
        rl_top_3 = (RelativeLayout) findViewById(R.id.rl_top_3);
        rl_top_4 = (RelativeLayout) findViewById(R.id.rl_top_4);
        rl_top_5 = (RelativeLayout) findViewById(R.id.rl_top_5);
        rl_top_6 = (RelativeLayout) findViewById(R.id.rl_top_6);
        rl_top_7 = (RelativeLayout) findViewById(R.id.rl_top_7);
        rl_top_8 = (RelativeLayout) findViewById(R.id.rl_top_8);
        rl_top_9 = (RelativeLayout) findViewById(R.id.rl_top_9);

        iv_take_1 = (ImageView) findViewById(R.id.iv_take_1);
        iv_take_2 = (ImageView) findViewById(R.id.iv_take_2);
        iv_take_3 = (ImageView) findViewById(R.id.iv_take_3);
        iv_take_4 = (ImageView) findViewById(R.id.iv_take_4);
        iv_take_5 = (ImageView) findViewById(R.id.iv_take_5);
        iv_take_6 = (ImageView) findViewById(R.id.iv_take_6);
        iv_take_7 = (ImageView) findViewById(R.id.iv_take_7);
        iv_take_8 = (ImageView) findViewById(R.id.iv_take_8);
        iv_take_9 = (ImageView) findViewById(R.id.iv_take_9);

        tv_address = (TextView) findViewById(R.id.tv_address);

        ll_take_photo = (LinearLayout) findViewById(R.id.ll_take_photo);

        mSurfaceHolder = sv_cemera.getHolder();
        //设置屏幕常亮
        mSurfaceHolder.setKeepScreenOn(true);
        //设置回调
        mSurfaceHolder.addCallback(surfaceHolderCallback);
    }

    @Override
    public void initListener() {
        findViewById(R.id.ll_cancel).setOnClickListener(this);
        ll_take_photo.setOnClickListener(this);
        findViewById(R.id.ll_submit).setOnClickListener(this);
    }

    private void topPhotoChange() {
        if (mTakePhotoList.size() >= 9) {
            iv_take_9.setImageBitmap(mTakePhotoList.get(8));
        }
        if (mTakePhotoList.size() >= 8) {
            iv_take_8.setImageBitmap(mTakePhotoList.get(7));
        }
        if (mTakePhotoList.size() >= 7) {
            iv_take_7.setImageBitmap(mTakePhotoList.get(6));
        }
        if (mTakePhotoList.size() >= 6) {
            iv_take_6.setImageBitmap(mTakePhotoList.get(5));
        }
        if (mTakePhotoList.size() >= 5) {
            iv_take_5.setImageBitmap(mTakePhotoList.get(4));
        }
        if (mTakePhotoList.size() >= 4) {
            iv_take_4.setImageBitmap(mTakePhotoList.get(3));
        }
        if (mTakePhotoList.size() >= 3) {
            iv_take_3.setImageBitmap(mTakePhotoList.get(2));
        }
        if (mTakePhotoList.size() >= 2) {
            iv_take_2.setImageBitmap(mTakePhotoList.get(1));
        }
        if (mTakePhotoList.size() >= 1) {
            iv_take_1.setImageBitmap(mTakePhotoList.get(0));
        }
    }

    private void uploadPhoto(final int index, final Bitmap bitmap) {
        EncryptUtil.uploadImageByQiNiu(BitmapUtils.bitmapToFile(index, bitmap), new UpCompletionHandler() {
            @Override
            public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                if (responseInfo.statusCode == 200) {
                    try {
                        mUploadID.set(index, jsonObject.getString("key"));
                        Toast.makeText(getApplicationContext(), "第" + (index + 1) + "张图片上传成功", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    uploadPhoto(index, bitmap);
                    Toast.makeText(getApplicationContext(), "第" + (index + 1) + "张图片上传失败, 正在重新上传", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
