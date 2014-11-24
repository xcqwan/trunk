package com.gezbox.windmap.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.utils.ViewHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zombie on 14-11-17.
 */
public class WindOptAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mData;
    private Set<Integer> selectedSet;

    public WindOptAdapter(Context context, List<String> data) {
        mContext = context;
        mData = data;
        selectedSet = new HashSet<Integer>();
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_wind_opt, viewGroup, false);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        tv_name.setText(mData.get(position));

        CheckBox cb_selected = ViewHolder.get(view, R.id.cb_selected);
        cb_selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    selectedSet.add(position);
                } else {
                    selectedSet.remove(position);
                }
            }
        });

        return view;
    }

    public Set<Integer> getSelectedSet() {
        return selectedSet;
    }
}