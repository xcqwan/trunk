package com.gezbox.windmap.app.activity;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.adapter.ChooseWindTeamAdapter;
import com.gezbox.windmap.app.adapter.WindOptAdapter;
import com.gezbox.windmap.app.widget.ChooseWindow;
import com.gezbox.windmap.app.widget.listview.SimpleFooter;
import com.gezbox.windmap.app.widget.listview.SimpleHeader;
import com.gezbox.windmap.app.widget.listview.ZrcListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zombie on 14-11-17.
 */
public class UnallotWindListActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener {
    private Button btn_option;

    private ZrcListView zlv_wind;
    private WindOptAdapter mWindOptAdapter;
    private List<String> mShopData;

    private ChooseWindow mChooseWindow;
    private ChooseWindTeamAdapter mChooseWindTeamAdapter;
    private List<String> mWindTeamData;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unallot_wind_list);

        initCustom();
        initListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            onBackPressed();
        } else if (id == btn_option.getId()) {
            //判断是否已选中未分配风先生, 是则弹出风队选框
            if (mWindOptAdapter == null || mWindOptAdapter.getSelectedSet().isEmpty()) {
                Toast.makeText(this, "请先选择需要分配的风先生", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mChooseWindow == null) {
                mWindTeamData = new ArrayList<String>();
                for (int i = 0; i< 3; i++) {
                    mWindTeamData.add("风小队 " + i);
                }
                mChooseWindTeamAdapter = new ChooseWindTeamAdapter(this, mWindTeamData);

                mChooseWindow = new ChooseWindow(this, mChooseWindTeamAdapter, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        mChooseWindTeamAdapter.setSelectedIndex(i);
                        Toast.makeText(UnallotWindListActivity.this, "提交完成后将关闭弹出层", Toast.LENGTH_SHORT).show();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mChooseWindow.dismiss();
                            }
                        }, 2 * 1000);
                    }
                });
            }
            mChooseWindTeamAdapter.setSelectedIndex(-1);
            mChooseWindow.showAtLocation(btn_option, Gravity.BOTTOM, 0, 0);
        }
    }

    @Override
    public void initCustom() {
        btn_option = (Button) findViewById(R.id.btn_option);
        zlv_wind = (ZrcListView) findViewById(R.id.zlv_wind);

        handler = new Handler();

        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this);
        header.setCircleColor(Color.WHITE);
        zlv_wind.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(Color.WHITE);
        zlv_wind.setFootable(footer);

        // 设置列表项出现动画（可选）
        zlv_wind.setItemAnimForTopIn(R.anim.topitem_in);
        zlv_wind.setItemAnimForBottomIn(R.anim.bottomitem_in);

        zlv_wind.setDivider(null);

        refresh();
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        btn_option.setOnClickListener(this);

        zlv_wind.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        zlv_wind.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });
    }

    private void refresh(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mShopData = new ArrayList<String>();
                for (int i = 0; i< 3; i++) {
                    mShopData.add("风先生 " + i);
                }
                mWindOptAdapter = new WindOptAdapter(UnallotWindListActivity.this, mShopData);
                zlv_wind.setAdapter(mWindOptAdapter);

                zlv_wind.setRefreshSuccess();
                zlv_wind.startLoadMore();
            }
        }, 2 * 1000);
    }

    private void loadMore(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mShopData.add("放大图");
                mWindOptAdapter.notifyDataSetChanged();
                zlv_wind.stopLoadMore();
                zlv_wind.setLoadMoreSuccess();
            }
        }, 2 * 1000);
    }


}
