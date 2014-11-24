package com.gezbox.windmap.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.activity.ShopInfoActivity;
import com.gezbox.windmap.app.utils.ViewHolder;

import java.util.List;

/**
 * Created by zombie on 14-11-14.
 */
public class ShopInfoAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mData;
    private boolean isOptMode = false;

    public ShopInfoAdapter(Context context, List<String> data) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_shop_info, viewGroup, false);
        }
        TextView tv_name = ViewHolder.get(view, R.id.tv_name);
        tv_name.setText(mData.get(i));

        Button btn_opt = ViewHolder.get(view, R.id.btn_opt);
        if (isOptMode) {
            btn_opt.setText("解除");
            btn_opt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            btn_opt.setText("");
            btn_opt.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.selector_btn_call_phone), null, null, null);
        }
        btn_opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOptMode) {
                    Toast.makeText(mContext, "点击了解除操作", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "点击了拨打电话", Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinearLayout ll_shop_info = ViewHolder.get(view, R.id.ll_shop_info);
        ll_shop_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, ShopInfoActivity.class));
            }
        });

        return view;
    }

    public void setOptMode(boolean isOptMode) {
        this.isOptMode = isOptMode;
        notifyDataSetChanged();
    }
}
