package com.gezbox.windmap.app.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.activity.StandardWorkInterface;
import com.gezbox.windmap.app.adapter.PickerAdapter;
import com.gezbox.windmap.app.widget.spinnerwheel.AbstractWheel;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日期选择控件
 * Created by zombie on 6/9/14.
 */
public class DatePickerPopupWindow extends PopupWindow implements StandardWorkInterface, View.OnClickListener{
    private Context mContext;
    private Button btn_ok;
    private AbstractWheel wvv_year;
    private AbstractWheel wvv_month;
    private AbstractWheel wvv_day;
    private View mView;
    private IDatePickerListener mListener;

    private PickerAdapter mYearAdapter;
    private PickerAdapter mMonthAdapter;
    private PickerAdapter mDayAdapter;

    public DatePickerPopupWindow(Context context, IDatePickerListener listener) {
        super(context);
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.widget_datepicker, null);
        mListener = listener;

        initCustom();
        initListener();

        LinearLayout ll_content = (LinearLayout) mView.findViewById(R.id.ll_content);
        ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        setContentView(mView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setAnimationStyle(R.style.AnimBottom);
        setBackgroundDrawable(null);
    }

    @Override
    public void initCustom() {
        btn_ok = (Button) mView.findViewById(R.id.ok_btn);
        wvv_year = (AbstractWheel) mView.findViewById(R.id.year_wvv);
        wvv_month = (AbstractWheel) mView.findViewById(R.id.month_wvv);
        wvv_day = (AbstractWheel) mView.findViewById(R.id.day_wvv);

        String now = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        int now_year = Integer.valueOf(now.substring(0,4));
        int now_month = Integer.valueOf(now.substring(5, 7));
        int now_day = Integer.valueOf(now.substring(8, 10));
        mYearAdapter = new PickerAdapter(mView.getContext(), getYearList(2014, now_year));
        mMonthAdapter = new PickerAdapter(mView.getContext(), getMonthList());
        mDayAdapter = new PickerAdapter(mView.getContext(), getDayList());

        wvv_year.setViewAdapter(mYearAdapter);
        wvv_month.setViewAdapter(mMonthAdapter);
        wvv_day.setViewAdapter(mDayAdapter);

        wvv_year.setVisibleItems(5);
        wvv_month.setVisibleItems(5);
        wvv_day.setVisibleItems(5);
    }

    @Override
     public void initListener() {
        btn_ok.setOnClickListener(this);
    }

    public void setVisiableStatus(boolean yearStatus, boolean monthStatus, boolean dayStatus) {
        if (!yearStatus) {
            wvv_year.setVisibility(View.GONE);
        }
        if (!monthStatus) {
            wvv_month.setVisibility(View.GONE);
        }
        if (!dayStatus) {
            wvv_day.setVisibility(View.GONE);
        }
    }

    private List<String> getDayList() {
        List<String> result = new ArrayList<String>();
        int day_size = 31;
//        if (month == 4 || month == 6 || month == 9 || month == 11) {
//            day_size = 30;
//        } else if (month == 2) {
//            //闰年
//            if ((year % 400 == 0) || (year % 4 == 0) && (year % 100 != 0)) {
//                day_size = 29;
//            } else {
//                day_size = 28;
//            }
//        }
        for (int i = 1; i <= day_size; i++) {
            result.add(i + "日");
        }

        return result;
    }

    private List<String> getMonthList() {
        List<String> result = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            result.add(i + "月");
        }
        return result;
    }

    private List<String> getYearList(int start_year, int end_year) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i <= (end_year - start_year); i++) {
            result.add(String.valueOf(start_year + i) + "年");
        }

        return result;
    }

    @Override
    public void onClick(View view) {
        int rid = view.getId();
        if (rid == btn_ok.getId()) {
            String year = mYearAdapter.getItemText(wvv_year.getCurrentItem()).toString();
            String month = mMonthAdapter.getItemText(wvv_month.getCurrentItem()).toString();
            String day = mDayAdapter.getItemText(wvv_day.getCurrentItem()).toString();

            if (month.length() < 3) {
                month = "0" + month;
            }
            if (day.length() < 3) {
                day = "0" + day;
            }

            String dateStr = year + month + day;

            try {
                Date selectedDate = DateUtils.parseDate(dateStr, "yyyy年MM月dd日");
                Date nowDate = new Date();
                if (nowDate.before(selectedDate)) {
                    Toast.makeText(mContext, "选择的日期超出当前日期", Toast.LENGTH_SHORT).show();
                    return;
                }

                mListener.onDatePickSubmit(year, month.substring(0, 2), day.substring(0, 2));
                dismiss();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public interface IDatePickerListener {
        public void onDatePickSubmit(String year, String month, String day);
    }
}
