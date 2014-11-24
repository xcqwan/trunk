package com.gezbox.windmap.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.adapter.WindAdapter;
import com.gezbox.windmap.app.widget.listview.SimpleFooter;
import com.gezbox.windmap.app.widget.listview.SimpleHeader;
import com.gezbox.windmap.app.widget.listview.ZrcListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zombie on 14-11-17.
 */
public class WindListActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener {
    private ZrcListView zlv_wind;
    private WindAdapter mWindAdapter;
    private List<String> mShopData;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wind_list);

        initCustom();
        initListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            onBackPressed();
        }
    }

    @Override
    public void initCustom() {
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
                    mShopData.add("队员 " + i);
                }
                mWindAdapter = new WindAdapter(WindListActivity.this, mShopData);
                zlv_wind.setAdapter(mWindAdapter);

                zlv_wind.setRefreshSuccess();
                zlv_wind.startLoadMore();
            }
        }, 2 * 1000);
    }

    private void loadMore(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mShopData.add("方大员");
                mWindAdapter.notifyDataSetChanged();
                zlv_wind.stopLoadMore();
                zlv_wind.setLoadMoreSuccess();
            }
        }, 2 * 1000);
    }
}
