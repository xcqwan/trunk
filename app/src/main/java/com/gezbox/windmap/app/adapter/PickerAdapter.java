package com.gezbox.windmap.app.adapter;

import android.content.Context;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.widget.spinnerwheel.adapters.AbstractWheelTextAdapter;

import java.util.List;


/**
 * Created by zombie on 6/9/14.
 */
public class PickerAdapter extends AbstractWheelTextAdapter {
    private Context mContext;
    private List<String> mData;

    public PickerAdapter(Context context, List<String> data) {
        super(context, R.layout.item_picker, R.id.item_tv);
        mContext = context;
        mData = data;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= getItemsCount())
            return "";
        return mData.get(index);
    }

    @Override
    public int getItemsCount() {
        return mData.size();
    }
}
