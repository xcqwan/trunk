package com.gezbox.windmap.app.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.activity.StandardWorkInterface;
import com.gezbox.windmap.app.adapter.TaskAdapter;
import com.gezbox.windmap.app.widget.listview.SimpleFooter;
import com.gezbox.windmap.app.widget.listview.SimpleHeader;
import com.gezbox.windmap.app.widget.listview.ZrcListView;
import com.gezbox.windmap.app.widget.listview.ZrcListView.OnStartListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zombie on 14-11-3.
 */
public class TaskFragment extends BaseFragment implements StandardWorkInterface, View.OnClickListener {
    private ZrcListView zlv_task;
    private TaskAdapter mTaskAdapter;
    private List<String> mTaskData;

    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCustom();
        initListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
    }

    @Override
    public void initCustom() {
        zlv_task = (ZrcListView) getView().findViewById(R.id.zlv_task);

        handler = new Handler();

        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(getActivity());
        header.setCircleColor(Color.WHITE);
        zlv_task.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(getActivity());
        footer.setCircleColor(Color.WHITE);
        zlv_task.setFootable(footer);

        // 设置列表项出现动画（可选）
        zlv_task.setItemAnimForTopIn(R.anim.topitem_in);
        zlv_task.setItemAnimForBottomIn(R.anim.bottomitem_in);

        zlv_task.setDivider(null);

        refresh();
    }

    @Override
    public void initListener() {
        zlv_task.setOnRefreshStartListener(new OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        zlv_task.setOnLoadMoreStartListener(new OnStartListener() {
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
                mTaskData = new ArrayList<String>();
                for (int i = 0; i< 10; i++) {
                    mTaskData.add("任务 " + i);
                }
                mTaskAdapter = new TaskAdapter(getActivity(), mTaskData);
                zlv_task.setAdapter(mTaskAdapter);

                zlv_task.setRefreshSuccess();
                zlv_task.startLoadMore();
            }
        }, 2 * 1000);
    }

    private void loadMore(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mTaskData.add("加载更多出现的");
                mTaskAdapter.notifyDataSetChanged();
                zlv_task.setLoadMoreSuccess();
            }
        }, 2 * 1000);
    }
}
