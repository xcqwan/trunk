package com.gezbox.windmap.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.utils.ViewHolder;

import java.util.List;

/**
 * Created by zombie on 14-11-18.
 */
public class ChooseWindTeamAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mData;
    private int selectedIndex = -1;

    public ChooseWindTeamAdapter(Context context, List<String> data) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_choose, viewGroup, false);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        tv_name.setText(mData.get(position));

        ImageView iv_checked = ViewHolder.get(view, R.id.iv_checked);
        if (selectedIndex == position) {
            iv_checked.setVisibility(View.VISIBLE);
        } else {
            iv_checked.setVisibility(View.GONE);
        }

        View divider = ViewHolder.get(view, R.id.divider);
        if (position == mData.size() - 1) {
            divider.setVisibility(View.GONE);
        } else {
            divider.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        notifyDataSetChanged();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}
