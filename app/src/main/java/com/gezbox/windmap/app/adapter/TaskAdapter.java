package com.gezbox.windmap.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.utils.ViewHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zombie on 14-11-11.
 */
public class TaskAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mData;
    private Set<Integer> finishedSet;

    public TaskAdapter(Context context, List<String> data) {
        mContext = context;
        mData = data;
        finishedSet = new HashSet<Integer>();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_task, viewGroup, false);
        }
        TextView tv_task_title = ViewHolder.get(view, R.id.tv_task_title);
        tv_task_title.setText(mData.get(position));

        LinearLayout ll_content = ViewHolder.get(view, R.id.ll_content);
        View v_finished = ViewHolder.get(view, R.id.v_finished);
        CheckBox cb_finish = ViewHolder.get(view, R.id.cb_finish);

        if (finishedSet.contains(position)) {
            ll_content.setBackgroundColor(mContext.getResources().getColor(R.color.background));
            v_finished.setVisibility(View.VISIBLE);
            cb_finish.setChecked(true);
            cb_finish.setEnabled(false);
        } else {
            ll_content.setBackgroundColor(mContext.getResources().getColor(R.color.list_background));
            v_finished.setVisibility(View.GONE);
            cb_finish.setChecked(false);
            cb_finish.setEnabled(true);
        }
        cb_finish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    finishedSet.add(position);
                    notifyDataSetChanged();
                }
            }
        });


        return view;
    }
}
