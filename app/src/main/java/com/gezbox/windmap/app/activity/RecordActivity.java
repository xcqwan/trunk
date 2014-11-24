package com.gezbox.windmap.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.adapter.PhotoAdapter;
import com.gezbox.windmap.app.backend.Backend;
import com.gezbox.windmap.app.model.Shop;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;

/**
 * Created by zombie on 14-10-31.
 */
public class RecordActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener, PhotoAdapter.IPhotoListener {
    public static final int REQUEST_CODE_TAKE_PHOTO = 11;
    public static final int REQUEST_CODE_TAKE_VIDEO = 12;
    public static final int REQUEST_CODE_ALBUM = 13;
    public static final int REQUEST_CODE_PREFECT_SHOP_INFO = 14;

    public static final String START_WITH_TEXT = "start_with_text";
    public static final String START_WITH_TAKEPHOTO = "start_with_takephoto";
    public static final String START_WITH_VIDEO = "start_with_video";
    public static final String START_WITH_ALBUM = "start_with_album";

    private Button btn_submit;
    private EditText et_message;
    private Button btn_relation_member;
    private Button btn_relation_project;
    private VideoView vv_video;
    private GridView gv_photo;
    private TextView tv_location;
    private Button btn_prefect_shop_info;

    private PhotoAdapter mPhotoAdapter;
    private ArrayList<CharSequence> mPhotoList;

    private Shop mShop;
    private double latitude;
    private double longitude;

    private boolean isLocationed;

    private String action;

    private Callback<Object> callback = new Callback<Object>() {
        @Override
        public void success(Object o, Response response) {
            showProgressDialog("", false);
            Toast.makeText(RecordActivity.this, "上传店铺信息成功", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void failure(RetrofitError error) {
            showProgressDialog("", false);
            if (error.getResponse() != null) {
                Toast.makeText(RecordActivity.this, "上传店铺信息失败, 错误码: " + error.getResponse().getStatus(), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(RecordActivity.this, "网络请求失败, 请检查您的网络!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        initCustom();
        initListener();

        startLocation();
        initAction();
    }

    @Override
    public void finishLocation(double latitude, double longitude, String address) {
        super.finishLocation(latitude, longitude, address);
        if (latitude == 0 || longitude == 0) {
            errerLocation();
            return;
        }
        this.latitude = latitude;
        this.longitude = longitude;
        isLocationed = true;
        tv_location.setText(address);
        tv_location.setClickable(false);
        tv_location.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_location_blue), null, null, null);
    }

    @Override
    public void errerLocation() {
        super.errerLocation();
        isLocationed = false;
        latitude = 0;
        longitude = 0;
        tv_location.setText("定位失败, 点击重新定位");
        tv_location.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_location_gray), null, null, null);
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_location.setText("正在定位...");
                startLocation();
            }
        });
    }

    private void initAction() {
        action = getIntent().getStringExtra("action");
        if (action == null || action.isEmpty()) {
            return;
        }
        if (action.equals(START_WITH_TEXT)) {
            gv_photo.setVisibility(View.GONE);
            vv_video.setVisibility(View.GONE);
        } else if (action.equals(START_WITH_TAKEPHOTO) || action.equals(START_WITH_ALBUM)) {
            gv_photo.setVisibility(View.VISIBLE);
            vv_video.setVisibility(View.GONE);

            addPhoto();
        } else if (action.equals(START_WITH_VIDEO)) {
            gv_photo.setVisibility(View.GONE);
            vv_video.setVisibility(View.VISIBLE);

            Intent intent = new Intent(this, VideoRecorderActivity.class);
            startActivityForResult(intent, REQUEST_CODE_TAKE_VIDEO);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            onBackPressed();
        } else if (id == btn_prefect_shop_info.getId()) {
            Intent intent = new Intent(this, PrefectShopInfoActivity.class);
            intent.putExtra("shop", mShop);

            startActivityForResult(intent, REQUEST_CODE_PREFECT_SHOP_INFO);
        } else if (id ==  R.id.btn_submit) {
            if (action.equals(START_WITH_VIDEO)) {
                Toast.makeText(this, "视频上传功能尚未开放", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mShop == null) {
                Toast.makeText(this, "请先补全店铺信息", Toast.LENGTH_SHORT).show();
                return;
            }
//            if (mPhotoList.size() == 1 && mPhotoList.get(0).toString().isEmpty()) {
//                Toast.makeText(this, "请添加照片", Toast.LENGTH_SHORT).show();
//                return;
//            }
            if (!isLocationed) {
                Toast.makeText(this, "请等待定位完成", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder sb_ids = new StringBuilder();
            for (CharSequence key : mPhotoList) {
                if (key.toString().isEmpty()) {
                    continue;
                } else {
                    if (sb_ids.length() > 0) {
                        sb_ids.append(",");
                    }
                    sb_ids.append(key.toString());
                }
            }
            mShop.setImages(sb_ids.toString());
            mShop.setUpload_latitude(latitude);
            mShop.setUpload_longitude(longitude);

            showProgressDialog("上传店铺信息", true);
            Backend.with(this).postShop(mShop, callback);
        }
    }

    @Override
    public void initCustom() {
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_message = (EditText) findViewById(R.id.et_message);
        btn_relation_member = (Button) findViewById(R.id.btn_relation_member);
        btn_relation_project = (Button) findViewById(R.id.btn_relation_project);
        vv_video = (VideoView) findViewById(R.id.vv_video);
        gv_photo = (GridView) findViewById(R.id.gv_photo);
        tv_location = (TextView) findViewById(R.id.tv_location);
        btn_prefect_shop_info = (Button) findViewById(R.id.btn_prefect_shop_info);

        mPhotoList = new ArrayList<CharSequence>();
        mPhotoList.add("");
        mPhotoAdapter = new PhotoAdapter(this, mPhotoList, this);
        gv_photo.setAdapter(mPhotoAdapter);
    }

    @Override
    public void initListener() {
        btn_prefect_shop_info.setOnClickListener(this);
        findViewById(R.id.btn_submit).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    @Override
    public void addPhoto() {
        if (action.equals(START_WITH_TAKEPHOTO)) {
            Intent intent = new Intent(this, TakePhotoActivity.class);
            int max_takes = 10 - mPhotoList.size();
            intent.putExtra("max_takes", max_takes);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
        } else {
            Intent intent = new Intent(this, PhotoAlbumActivity.class);
            int max_takes = 10 - mPhotoList.size();
            intent.putExtra("max_takes", max_takes);
            startActivityForResult(intent, REQUEST_CODE_ALBUM);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PREFECT_SHOP_INFO && resultCode == RESULT_OK) {
            //处理补全后的信息
            mShop = data.getParcelableExtra("shop_info");
        }
        if ((requestCode == REQUEST_CODE_TAKE_PHOTO || requestCode == REQUEST_CODE_ALBUM )&& resultCode == RESULT_OK) {
            //拍照/从相册选取后的信息
            ArrayList<CharSequence> upload_ids = data.getCharSequenceArrayListExtra("upload_ids");
            if (mPhotoList.size() + upload_ids.size() >= 10) {
                mPhotoList.remove(0);
            }
            mPhotoList.addAll(upload_ids);
            mPhotoAdapter.notifyDataSetChanged();
        }
        if (requestCode == REQUEST_CODE_TAKE_VIDEO) {
            if (resultCode == RESULT_OK) {
                //录制视频后的信息
                vv_video.setVideoURI(data.getData());
                vv_video.start();
            } else {
                onBackPressed();
            }
        }
    }
}
