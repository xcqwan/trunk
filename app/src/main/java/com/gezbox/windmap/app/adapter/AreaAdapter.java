package com.gezbox.windmap.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.model.Area;

import java.util.List;

/**
 * Created by zombie on 7/23/14.
 */
public class AreaAdapter extends BaseAdapter {
    private Context mContext;
    private List<Area> mDatas;

    public AreaAdapter(Context context, List<Area> data) {
        mContext = context;
        mDatas = data;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_area, null);
            holder = new ViewHolder();

            holder.item_tv = (TextView) view.findViewById(R.id.item_tv);
            holder.divider = view.findViewById(R.id.divider);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Area item = mDatas.get(position);
        holder.item_tv.setText(item.getName());
        if (position == mDatas.size() - 1) {
            holder.divider.setVisibility(View.GONE);
        } else {
            holder.divider.setVisibility(View.VISIBLE);
        }

        return view;
    }

    class ViewHolder {
        TextView item_tv;
        View divider;
    }
}
