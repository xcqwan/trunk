package com.gezbox.windmap.app.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.gezbox.windmap.app.R;

/**
 * Created by zombie on 14-11-18.
 */
public class ChooseWindow extends PopupWindow{

    public ChooseWindow(Context context, BaseAdapter adapter, AdapterView.OnItemClickListener listener) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.windget_choose, null);
        ExpandedHeightListView item_lv = (ExpandedHeightListView) view.findViewById(R.id.item_lv);
        item_lv.setAdapter(adapter);
        item_lv.setOnItemClickListener(listener);

        LinearLayout ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
        ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setAnimationStyle(R.style.AnimBottom);
        setBackgroundDrawable(null);
    }
}
