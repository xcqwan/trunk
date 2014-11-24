package com.gezbox.windmap.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.adapter.PhotoAlbumAdapter;
import com.gezbox.windmap.app.model.FileTraversal;
import com.gezbox.windmap.app.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhotoAlbumActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener, OnItemClickListener {
    private static final int REQUEST_SELECTED_IMAGE = 1;

    private GridView gv_album;
    private PhotoAlbumAdapter mPhotoAlbumAdapter;
    private List<FileTraversal> mLocalList;

    private Util mUtil;
    private int max_takes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);

        initCustom();
        initListener();

        max_takes = getIntent().getIntExtra("max_takes", 0);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(this,ChoosePhotoActivity.class);
        intent.putExtra("max_takes", max_takes);
        intent.putExtra("data", mLocalList.get(position));

		startActivityForResult(intent, REQUEST_SELECTED_IMAGE);
    }

    @Override
    public void initCustom() {
        gv_album = (GridView) findViewById(R.id.gv_album);

        mUtil = new Util(this);
        mLocalList = mUtil.LocalImgFileList();
        List<HashMap<String, String>> listdata = new ArrayList<HashMap<String,String>>();
		if (mLocalList != null) {
			for (int i = 0; i < mLocalList.size(); i++) {
                FileTraversal fileTraversal = mLocalList.get(i);

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("filecount", fileTraversal.getFilecontent().size() + "");
				map.put("imgpath", fileTraversal.getFilecontent().get(0) == null ? null : (fileTraversal.getFilecontent().get(0)));
				map.put("filename", fileTraversal.getFilename());
				listdata.add(map);
			}
		}
        mPhotoAlbumAdapter = new PhotoAlbumAdapter(this, listdata);
        gv_album.setAdapter(mPhotoAlbumAdapter);
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        gv_album.setOnItemClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECTED_IMAGE && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            onBackPressed();
        }
    }
}
