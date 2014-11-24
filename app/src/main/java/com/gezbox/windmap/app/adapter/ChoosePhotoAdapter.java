package com.gezbox.windmap.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.utils.Util;
import com.gezbox.windmap.app.utils.ViewHolder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChoosePhotoAdapter extends BaseAdapter {
	private Context mContext;
    private List<String> mData;
    private Set<Integer> mSelectedSet;
    private int max_takes;

	public ChoosePhotoAdapter(Context context, List<String> data, int max_takes) {
        mContext = context;
        mData = data;
        mSelectedSet = new HashSet<Integer>();
        this.max_takes = max_takes;
	}
	
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_choose_photo, null);
        }

        RelativeLayout rl_content = ViewHolder.get(view, R.id.rl_content);
        ImageView iv_photo = ViewHolder.get(view, R.id.iv_photo);
        final LinearLayout ll_checked = ViewHolder.get(view, R.id.ll_checked);

        String path = mData.get(position);
        if (path == null || path.isEmpty()) {
            Picasso.with(mContext).load(R.drawable.ic_default_avatar).into(iv_photo);
        } else {
            File imgFile = new File(path);
            if (imgFile.exists()) {
                Picasso.with(mContext).load(imgFile).into(iv_photo);
            } else {
                Picasso.with(mContext).load(R.drawable.ic_default_avatar).into(iv_photo);
            }
        }

        if (mSelectedSet.contains(position)) {
            ll_checked.setVisibility(View.VISIBLE);
        } else {
            ll_checked.setVisibility(View.GONE);
        }

        rl_content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectedSet.size() >= max_takes && !mSelectedSet.contains(position)) {
                    Toast.makeText(mContext, "最多只能选取" + max_takes + "张", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mSelectedSet.contains(position)) {
                    mSelectedSet.remove(position);
                    ll_checked.setVisibility(View.GONE);
                } else {
                    mSelectedSet.add(position);
                    ll_checked.setVisibility(View.VISIBLE);
                }
            }
        });

		return view;
	}

    public Set<Integer> getmSelectedSet() {
        return mSelectedSet;
    }
}
