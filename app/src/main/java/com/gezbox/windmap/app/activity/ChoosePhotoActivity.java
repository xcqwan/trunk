package com.gezbox.windmap.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.Toast;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.adapter.ChoosePhotoAdapter;
import com.gezbox.windmap.app.model.FileTraversal;
import com.gezbox.windmap.app.utils.EncryptUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class ChoosePhotoActivity extends BaseActivity implements StandardWorkInterface, OnClickListener {
    private GridView gv_photo;
    private ChoosePhotoAdapter mChoosePhotoAdapter;

    private FileTraversal mFileTraversal;
    private int max_takes;

    private ArrayList<CharSequence> mUploadID = new ArrayList<CharSequence>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_photo);

        mFileTraversal = getIntent().getParcelableExtra("data");
        max_takes = getIntent().getIntExtra("max_takes", 0);

        initCustom();
        initListener();
	}

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            onBackPressed();
        } else if (id == R.id.btn_submit) {
            //确认
            Set<Integer> selectedSet = mChoosePhotoAdapter.getmSelectedSet();
            if (selectedSet.isEmpty()) {
                Toast.makeText(this, "你还未选择图片", Toast.LENGTH_SHORT).show();
                return;
            }
            showProgressDialog("正在上传图片", true);
            Iterator<Integer> iterator = selectedSet.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                mUploadID.add("");
                uploadPhoto(index, mFileTraversal.getFilecontent().get(iterator.next()));
                index++;
            }
        }
    }

    @Override
    public void initCustom() {
        gv_photo = (GridView) findViewById(R.id.gv_photo);

        mChoosePhotoAdapter = new ChoosePhotoAdapter(this, mFileTraversal.getFilecontent(), max_takes);
        gv_photo.setAdapter(mChoosePhotoAdapter);
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    public void uploadPhoto(final int index, final String path) {
        EncryptUtil.uploadImageByQiNiu(new File(path), new UpCompletionHandler() {
            @Override
            public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                if (responseInfo.statusCode == 200) {
                    try {
                        mUploadID.set(index, jsonObject.getString("key"));
                        if (checkAllUploadFinished()) {
                            showProgressDialog("", false);
                            Intent intent = new Intent();
                            intent.putCharSequenceArrayListExtra("upload_ids", mUploadID);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    uploadPhoto(index, path);
                    Toast.makeText(getApplicationContext(), "第" + (index + 1) + "张图片上传失败, 正在重新上传", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean checkAllUploadFinished() {
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
            return false;
        }
        return true;
    }
}
