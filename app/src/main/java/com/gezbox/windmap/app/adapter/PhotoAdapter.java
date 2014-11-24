package com.gezbox.windmap.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.utils.ResourceUrl;
import com.gezbox.windmap.app.utils.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zombie on 14-11-3.
 */
public class PhotoAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<CharSequence> mData;
    private IPhotoListener mListener;

    public PhotoAdapter(Context context, ArrayList<CharSequence> data, IPhotoListener listener) {
        mContext = context;
        mData = data;
        mListener = listener;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_photo, viewGroup, false);
        }
        ImageView iv_photo = ViewHolder.get(view, R.id.iv_photo);
        String path = mData.get(i).toString();
        if (path.isEmpty()) {
            iv_photo.setImageResource(R.drawable.selector_btn_add);
            iv_photo.setEnabled(true);
        } else {
            Picasso.with(mContext).load(ResourceUrl.IMAGE_URL + path).into(iv_photo);
            iv_photo.setEnabled(false);
        }

        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.addPhoto();
            }
        });

        return view;
    }

    public interface IPhotoListener {
        public void addPhoto();
    }
}
