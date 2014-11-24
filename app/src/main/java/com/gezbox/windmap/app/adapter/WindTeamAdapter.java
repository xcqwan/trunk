package com.gezbox.windmap.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.activity.UnallotWindListActivity;
import com.gezbox.windmap.app.activity.WindListActivity;
import com.gezbox.windmap.app.activity.WindTeamInfoActivity;
import com.gezbox.windmap.app.utils.ViewHolder;

import java.util.List;

/**
 * Created by zombie on 14-11-17.
 */
public class WindTeamAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mData;

    public WindTeamAdapter(Context context, List<String> data) {
        mContext = context;
        mData = data;
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_wind_team, viewGroup, false);
        }

        TextView tv_name = ViewHolder.get(view, R.id.tv_team_name);
        tv_name.setText(mData.get(position));

        Button btn_wind = ViewHolder.get(view, R.id.btn_wind);
        btn_wind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, WindListActivity.class));
            }
        });

        Button btn_data = ViewHolder.get(view, R.id.btn_data);
        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, WindTeamInfoActivity.class));
            }
        });


        if (position == mData.size() - 1) {
            ViewHolder.get(view, R.id.divider).setVisibility(View.GONE);
        } else {
            ViewHolder.get(view, R.id.divider).setVisibility(View.VISIBLE);
        }

        return view;
    }
}