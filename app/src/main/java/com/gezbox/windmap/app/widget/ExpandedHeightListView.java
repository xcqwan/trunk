package com.gezbox.windmap.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * 自动展开列表
 * Created by xcqwan on 2014/5/26.
 */
public class ExpandedHeightListView extends ListView {
    boolean mExpanded = true;

    public ExpandedHeightListView(Context context) {
        super(context);
    }

    public ExpandedHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandedHeightListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // HACK! TAKE THAT ANDROID!
        if (isExpanded()) {
            // Calculate entire height by providing a very large height hint.
            // But do not use the highest 2 bits of this integer; those are
            // reserved for the MeasureSpec mode.
            int expandSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);

            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setExpanded(boolean expanded) {
        this.mExpanded = expanded;
    }
}
