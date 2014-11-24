package com.gezbox.windmap.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.adapter.ShopTeamAdapter;
import com.gezbox.windmap.app.widget.listview.SimpleFooter;
import com.gezbox.windmap.app.widget.listview.SimpleHeader;
import com.gezbox.windmap.app.widget.listview.ZrcListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zombie on 14-11-14.
 */
public class ShopTeamListActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener {
    private ZrcListView zlv_shop_team;
    private ShopTeamAdapter mShopTeamAdapter;
    private List<String> mShopData;

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_team_list);

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
        zlv_shop_team = (ZrcListView) findViewById(R.id.zlv_shop_team);

        handler = new Handler();

        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this);
        header.setCircleColor(Color.WHITE);
        zlv_shop_team.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(Color.WHITE);
        zlv_shop_team.setFootable(footer);

        // 设置列表项出现动画（可选）
        zlv_shop_team.setItemAnimForTopIn(R.anim.topitem_in);
        zlv_shop_team.setItemAnimForBottomIn(R.anim.bottomitem_in);

        zlv_shop_team.setDivider(null);

        refresh();
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);

        zlv_shop_team.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        zlv_shop_team.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
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
                    mShopData.add("商圈 " + i);
                }
                mShopTeamAdapter = new ShopTeamAdapter(ShopTeamListActivity.this, mShopData);
                zlv_shop_team.setAdapter(mShopTeamAdapter);

                zlv_shop_team.setRefreshSuccess();
                zlv_shop_team.startLoadMore();
            }
        }, 2 * 1000);
    }

    private void loadMore(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mShopData.add("加载更多出现的");
                mShopTeamAdapter.notifyDataSetChanged();
                zlv_shop_team.setLoadMoreSuccess();
            }
        }, 2 * 1000);
    }
}
