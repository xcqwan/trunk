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
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoAlbumAdapter extends BaseAdapter{
	private Context mContext;
    private List<HashMap<String, String>> mData;
	
	public PhotoAlbumAdapter(Context context, List<HashMap<String, String>> data) {
		mContext = context;
        mData = data;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_photo_album, null);
        }

        ImageView iv_file = ViewHolder.get(view, R.id.iv_file);
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        TextView tv_count = ViewHolder.get(view, R.id.tv_count);

        Map<String, String> data = mData.get(position);
        String name = data.get("filename");
        String count = data.get("filecount");
        String path = data.get("imgpath");

        tv_name.setText(name);
        tv_count.setText(count);

        if (path == null || path.isEmpty()) {
            Picasso.with(mContext).load(R.drawable.ic_default_avatar).into(iv_file);
        } else {
            File imgFile = new File(path);
            if (imgFile.exists()) {
                Picasso.with(mContext).load(imgFile).into(iv_file);
            } else {
                Picasso.with(mContext).load(R.drawable.ic_default_avatar).into(iv_file);
            }
        }

		return view;
	}
}
