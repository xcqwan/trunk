package com.gezbox.windmap.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.adapter.AreaAdapter;
import com.gezbox.windmap.app.backend.Backend;
import com.gezbox.windmap.app.model.Area;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zombie on 7/23/14.
 */
public class ChooseAreaActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener, AdapterView.OnItemClickListener {
    private Button btn_pre_step;
    private Button btn_cancel;
    private ListView lv_area;

    private AreaAdapter mAreaAdapter;
    private List<Area> mAreaList;

    private Area mStateArea;
    private Area mCityArea;
    private Area mDistrictArea;

    private Callback<List<Area>> callback = new Callback<List<Area>>() {
        @Override
        public void success(List<Area> areas, Response response) {
            showProgressDialog("", false);
            mAreaList.clear();
            mAreaList.addAll(areas);
            mAreaAdapter.notifyDataSetChanged();
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            showProgressDialog("", false);
            Toast.makeText(ChooseAreaActivity.this, "获取地区信息失败", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_area);

        initCustom();
        initListener();
        initDatas();
    }

    private void initDatas() {
        btn_pre_step.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.VISIBLE);
        getAreaList("0");
    }

    private void getAreaList(String area_code) {
        showProgressDialog("获取地区信息", true);
        Backend.with(this).getArea(area_code, callback);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onClick(View view) {
        int rid = view.getId();
        if (rid == btn_pre_step.getId()) {
            //上一步
            if (mCityArea == null) {
                mStateArea = null;
                getAreaList("0");

                btn_pre_step.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.VISIBLE);
            } else {
                mCityArea = null;
                getAreaList(mStateArea.getCode());
            }
        } else if (rid == btn_cancel.getId()) {
            //取消
            onBackPressed();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Area area = mAreaList.get(position);
        if (mStateArea == null) {
            mStateArea = area;
        } else if (mCityArea == null) {
            mCityArea = area;
        } else {
            mDistrictArea = area;
            Intent intent = new Intent();
            intent.putExtra("state", mStateArea.getName());
            intent.putExtra("state_code", mStateArea.getCode());
            intent.putExtra("city", mCityArea.getName());
            intent.putExtra("city_code", mCityArea.getCode());
            intent.putExtra("district", mDistrictArea.getName());
            intent.putExtra("district_code", mDistrictArea.getCode());
            setResult(RESULT_OK, intent);
            finish();
            return;
        }
        btn_pre_step.setVisibility(View.VISIBLE);
        btn_cancel.setVisibility(View.GONE);
        getAreaList(area.getCode());
    }

    @Override
    public void initCustom() {
        btn_pre_step = (Button) findViewById(R.id.btn_pre_step);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        lv_area = (ListView) findViewById(R.id.lv_area);

        mAreaList = new ArrayList<Area>();
        mAreaAdapter = new AreaAdapter(this, mAreaList);
        lv_area.setAdapter(mAreaAdapter);
    }

    @Override
    public void initListener() {
        btn_pre_step.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        lv_area.setOnItemClickListener(this);
    }
}
