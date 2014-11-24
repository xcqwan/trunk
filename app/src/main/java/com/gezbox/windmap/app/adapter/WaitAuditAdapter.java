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
 * Created by zombie on 14-11-19.
 */
public class WaitAuditAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mData;

    public WaitAuditAdapter(Context context, List<String> data) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_wait_audit, viewGroup, false);
        }

        TextView tv_content = ViewHolder.get(view, R.id.tv_content);
        tv_content.setText(mData.get(position));

        return view;
    }
}