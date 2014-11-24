package com.gezbox.windmap.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.adapter.WaitAuditAdapter;
import com.gezbox.windmap.app.widget.listview.SimpleFooter;
import com.gezbox.windmap.app.widget.listview.SimpleHeader;
import com.gezbox.windmap.app.widget.listview.ZrcListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zombie on 14-11-19.
 */
public class WaitAuditListActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener {
    private ZrcListView zlv_wait_audit;
    private WaitAuditAdapter mWaitAuditAdapter;
    private List<String> mWaitAuditData;

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_audit_list);

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
        zlv_wait_audit = (ZrcListView) findViewById(R.id.zlv_wait_audit);

        handler = new Handler();

        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this);
        header.setCircleColor(Color.WHITE);
        zlv_wait_audit.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(Color.WHITE);
        zlv_wait_audit.setFootable(footer);

        // 设置列表项出现动画（可选）
        zlv_wait_audit.setItemAnimForTopIn(R.anim.topitem_in);
        zlv_wait_audit.setItemAnimForBottomIn(R.anim.bottomitem_in);

        zlv_wait_audit.setDivider(null);

        refresh();
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);

        zlv_wait_audit.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        zlv_wait_audit.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
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
                mWaitAuditData = new ArrayList<String>();
                for (int i = 0; i< 3; i++) {
                    mWaitAuditData.add("待审核信息 " + i);
                }
                mWaitAuditAdapter = new WaitAuditAdapter(WaitAuditListActivity.this, mWaitAuditData);
                zlv_wait_audit.setAdapter(mWaitAuditAdapter);

                zlv_wait_audit.setRefreshSuccess();
                zlv_wait_audit.startLoadMore();
            }
        }, 2 * 1000);
    }

    private void loadMore(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWaitAuditData.add("加载更多出现的");
                mWaitAuditAdapter.notifyDataSetChanged();
                zlv_wait_audit.stopLoadMore();
                zlv_wait_audit.setLoadMoreSuccess();
            }
        }, 2 * 1000);
    }
}
