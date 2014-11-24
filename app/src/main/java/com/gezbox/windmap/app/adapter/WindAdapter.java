package com.gezbox.windmap.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.utils.ViewHolder;

import java.util.List;

/**
 * Created by zombie on 14-11-17.
 */
public class WindAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mData;

    public WindAdapter(Context context, List<String> data) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_wind, viewGroup, false);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        tv_name.setText(mData.get(i));

        TextView tv_captain = ViewHolder.get(view, R.id.tv_captain);
        if (i == 0) {
            tv_captain.setVisibility(View.VISIBLE);
        } else {
            tv_captain.setVisibility(View.INVISIBLE);
        }

        return view;
    }
}
