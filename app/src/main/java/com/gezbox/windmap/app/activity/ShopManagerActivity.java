package com.gezbox.windmap.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.adapter.ShopAdapter;
import com.gezbox.windmap.app.adapter.TaskAdapter;
import com.gezbox.windmap.app.widget.listview.SimpleFooter;
import com.gezbox.windmap.app.widget.listview.SimpleHeader;
import com.gezbox.windmap.app.widget.listview.ZrcListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zombie on 14-11-12.
 */
public class ShopManagerActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener {
    private Button btn_shop;
    private Button btn_recruit;

    private ZrcListView zlv_shop;
    private ShopAdapter mShopAdapter;
    private List<String> mShopData;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_manager);

        initCustom();
        initListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            onBackPressed();
        } else if (id == btn_shop.getId()) {
            chooseShop();
        } else if (id == btn_recruit.getId()) {
            chooseRecruit();
        }
    }

    @Override
    public void initCustom() {
        btn_shop = (Button) findViewById(R.id.btn_shop);
        btn_recruit = (Button) findViewById(R.id.btn_recruit);

        zlv_shop = (ZrcListView) findViewById(R.id.zlv_shop);

        handler = new Handler();

        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this);
        header.setCircleColor(Color.WHITE);
        zlv_shop.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(Color.WHITE);
        zlv_shop.setFootable(footer);

        // 设置列表项出现动画（可选）
        zlv_shop.setItemAnimForTopIn(R.anim.topitem_in);
        zlv_shop.setItemAnimForBottomIn(R.anim.bottomitem_in);

        zlv_shop.setDivider(null);

        refresh();
        chooseRecruit();
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        btn_shop.setOnClickListener(this);
        btn_recruit.setOnClickListener(this);

        zlv_shop.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        zlv_shop.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });
    }

    private void chooseShop() {
        btn_shop.setTextColor(getResources().getColor(R.color.red));
        btn_recruit.setTextColor(getResources().getColor(R.color.text_dark));
    }

    private void chooseRecruit() {
        btn_shop.setTextColor(getResources().getColor(R.color.text_dark));
        btn_recruit.setTextColor(getResources().getColor(R.color.red));
    }

    private void refresh(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mShopData = new ArrayList<String>();
                for (int i = 0; i< 3; i++) {
                    mShopData.add("任务 " + i);
                }
                mShopAdapter = new ShopAdapter(getApplicationContext(), mShopData);
                zlv_shop.setAdapter(mShopAdapter);

                zlv_shop.setRefreshSuccess();
                zlv_shop.startLoadMore();
            }
        }, 2 * 1000);
    }

    private void loadMore(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mShopData.add("加载更多出现的");
                mShopAdapter.notifyDataSetChanged();
                zlv_shop.setLoadMoreSuccess();
            }
        }, 2 * 1000);
    }
}
