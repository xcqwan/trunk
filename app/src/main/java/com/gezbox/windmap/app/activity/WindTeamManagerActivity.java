package com.gezbox.windmap.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.adapter.WindTeamAdapter;
import com.gezbox.windmap.app.widget.listview.SimpleFooter;
import com.gezbox.windmap.app.widget.listview.SimpleHeader;
import com.gezbox.windmap.app.widget.listview.ZrcListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zombie on 14-11-17.
 */
public class WindTeamManagerActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener {
    private Button btn_unallot_wind;

    private ZrcListView zlv_wind_team;
    private WindTeamAdapter mWindTeamAdapter;
    private List<String> mShopData;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wind_team_manager);

        initCustom();
        initListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            onBackPressed();
        } else if (id == btn_unallot_wind.getId()) {
            startActivity(new Intent(this, UnallotWindListActivity.class));
        }
    }

    @Override
    public void initCustom() {
        btn_unallot_wind = (Button) findViewById(R.id.btn_unallot_wind);
        zlv_wind_team = (ZrcListView) findViewById(R.id.zlv_wind_team);

        handler = new Handler();

        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this);
        header.setCircleColor(Color.WHITE);
        zlv_wind_team.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(Color.WHITE);
        zlv_wind_team.setFootable(footer);

        // 设置列表项出现动画（可选）
        zlv_wind_team.setItemAnimForTopIn(R.anim.topitem_in);
        zlv_wind_team.setItemAnimForBottomIn(R.anim.bottomitem_in);

        zlv_wind_team.setDivider(null);

        refresh();
    }

    @Override
    public void initListener() {
        btn_unallot_wind.setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        zlv_wind_team.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        zlv_wind_team.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
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
                    mShopData.add("风小队 " + i);
                }
                mWindTeamAdapter = new WindTeamAdapter(WindTeamManagerActivity.this, mShopData);
                zlv_wind_team.setAdapter(mWindTeamAdapter);

                zlv_wind_team.setRefreshSuccess();
                zlv_wind_team.startLoadMore();
            }
        }, 2 * 1000);
    }

    private void loadMore(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mShopData.add("加载更多出现的");
                mWindTeamAdapter.notifyDataSetChanged();
                zlv_wind_team.stopLoadMore();
                zlv_wind_team.setLoadMoreSuccess();
            }
        }, 2 * 1000);
    }
}
