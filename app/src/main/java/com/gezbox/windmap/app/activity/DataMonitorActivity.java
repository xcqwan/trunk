package com.gezbox.windmap.app.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.backend.DataBackend;
import com.gezbox.windmap.app.model.DataInfo;
import com.gezbox.windmap.app.widget.DatePickerPopupWindow;
import com.github.mikephil.charting.charts.LineChart;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zombie on 14-10-29.
 */
public class DataMonitorActivity extends FragmentActivity implements StandardWorkInterface, View.OnClickListener {
    private Button btn_back;
    private Button btn_calendar;
    private Button btn_location;

    private TextView tv_finished_orders;
    private TextView tv_max_history;
    private TextView tv_shop_calls;
    private TextView tv_adopted_delivers;
    private TextView tv_finished_orders_per_persone;

    private Button btn_send;
    private Button btn_shop;
    private Button btn_deliver;

    private View v_divider_left;
    private View v_divider_right;

    private TextView tv_type_1;
    private TextView tv_type_2;
    private TextView tv_type_3;
    private TextView tv_type_4;
    private TextView tv_type_5;
    private TextView tv_type_6;
    private TextView tv_type_7;
    private TextView tv_type_8;
    private TextView tv_type_9;
    private TextView tv_type_10;
    private TextView tv_type_11;
    private TextView tv_type_12;

    private TextView tv_td_1;
    private TextView tv_td_2;
    private TextView tv_td_3;
    private TextView tv_td_4;
    private TextView tv_td_5;
    private TextView tv_td_6;
    private TextView tv_td_7;
    private TextView tv_td_8;
    private TextView tv_td_9;
    private TextView tv_td_10;
    private TextView tv_td_11;
    private TextView tv_td_12;

    private LinearLayout ll_table_right_row_1;
    private LinearLayout ll_table_right_row_2;
    private LinearLayout ll_table_right_row_3;
    private LinearLayout ll_table_right_row_4;

    private DataInfo mDataInfo;

    private TableType mCurrentSelectedIndex = TableType.Send;

    private enum TableType {
        Send,
        Shop,
        Deliver;
    }

    private Callback<DataInfo> callback = new Callback<DataInfo>() {
        @Override
        public void success(DataInfo dataInfo, Response response) {
            mDataInfo = dataInfo;
            tv_finished_orders.setText(dataInfo.getFinished_orders());
            tv_max_history.setText(dataInfo.getHighest_orders());
            tv_shop_calls.setText(dataInfo.getShop_calls());
            tv_adopted_delivers.setText(dataInfo.getAdopted_delivers());

            if (Integer.parseInt(dataInfo.getAdopted_delivers()) == 0) {
                tv_finished_orders_per_persone.setText("0");
            } else {
                tv_finished_orders_per_persone.setText(Integer.parseInt(dataInfo.getFinished_orders())/Integer.parseInt(dataInfo.getAdopted_delivers())+"");
            }

            if (mCurrentSelectedIndex == TableType.Send) {
                chooseSend();
            } else if (mCurrentSelectedIndex == TableType.Shop) {
                chooseShop();
            } else {
                chooseDeliver();
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            if (retrofitError.getResponse() != null) {
                Toast.makeText(DataMonitorActivity.this, "网络请求失败, 错误码: " + retrofitError.getResponse().getStatus(), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(DataMonitorActivity.this, "网络请求失败, 请检查您的网络!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_monitor);

        initCustom();
        initListener();
        DataBackend.with(this).getData(callback);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void initCustom() {
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_calendar = (Button) findViewById(R.id.btn_calendar);
        btn_location = (Button) findViewById(R.id.btn_location);

        tv_finished_orders = (TextView) findViewById(R.id.tv_finished_orders);
        tv_max_history = (TextView) findViewById(R.id.tv_max_history);
        tv_shop_calls = (TextView) findViewById(R.id.tv_shop_calls);
        tv_adopted_delivers = (TextView) findViewById(R.id.tv_adopted_delivers);
        tv_finished_orders_per_persone = (TextView) findViewById(R.id.tv_finished_orders_per_persone);

        btn_send = (Button) findViewById(R.id.btn_send);
        btn_shop = (Button) findViewById(R.id.btn_shop);
        btn_deliver = (Button) findViewById(R.id.btn_deliver);

        v_divider_left = findViewById(R.id.v_divider_left);
        v_divider_right = findViewById(R.id.v_divider_right);

        tv_type_1 = (TextView) findViewById(R.id.tv_type_1);
        tv_type_2 = (TextView) findViewById(R.id.tv_type_2);
        tv_type_3 = (TextView) findViewById(R.id.tv_type_3);
        tv_type_4 = (TextView) findViewById(R.id.tv_type_4);
        tv_type_5 = (TextView) findViewById(R.id.tv_type_5);
        tv_type_6 = (TextView) findViewById(R.id.tv_type_6);
        tv_type_7 = (TextView) findViewById(R.id.tv_type_7);
        tv_type_8 = (TextView) findViewById(R.id.tv_type_8);
        tv_type_9 = (TextView) findViewById(R.id.tv_type_9);
        tv_type_10 = (TextView) findViewById(R.id.tv_type_10);
        tv_type_11 = (TextView) findViewById(R.id.tv_type_11);
        tv_type_12 = (TextView) findViewById(R.id.tv_type_12);

        tv_td_1 = (TextView) findViewById(R.id.tv_td_1);
        tv_td_2 = (TextView) findViewById(R.id.tv_td_2);
        tv_td_3 = (TextView) findViewById(R.id.tv_td_3);
        tv_td_4 = (TextView) findViewById(R.id.tv_td_4);
        tv_td_5 = (TextView) findViewById(R.id.tv_td_5);
        tv_td_6 = (TextView) findViewById(R.id.tv_td_6);
        tv_td_7 = (TextView) findViewById(R.id.tv_td_7);
        tv_td_8 = (TextView) findViewById(R.id.tv_td_8);
        tv_td_9 = (TextView) findViewById(R.id.tv_td_9);
        tv_td_10 = (TextView) findViewById(R.id.tv_td_10);
        tv_td_11 = (TextView) findViewById(R.id.tv_td_11);
        tv_td_12 = (TextView) findViewById(R.id.tv_td_12);

        ll_table_right_row_1 = (LinearLayout) findViewById(R.id.ll_table_right_row_1);
        ll_table_right_row_2 = (LinearLayout) findViewById(R.id.ll_table_right_row_2);
        ll_table_right_row_3 = (LinearLayout) findViewById(R.id.ll_table_right_row_3);
        ll_table_right_row_4 = (LinearLayout) findViewById(R.id.ll_table_right_row_4);

        chooseSend();
    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(this);
        btn_calendar.setOnClickListener(this);
        btn_location.setOnClickListener(this);

        btn_send.setOnClickListener(this);
        btn_shop.setOnClickListener(this);
        btn_deliver.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == btn_back.getId()) {
            onBackPressed();
        } else if (id == btn_calendar.getId()) {
            //选择时间
            showCalendar();
        } else if (id == btn_location.getId()) {
            //选择地区
            startActivity(new Intent(this, LineChartActivity.class));
        } else if (id == btn_send.getId()) {
            //送单
            chooseSend();
        } else if (id == btn_shop.getId()) {
            //商户
            chooseShop();
        } else if (id == btn_deliver.getId()) {
            //配送
            chooseDeliver();
        }
    }

    private void showCalendar() {
        DatePickerPopupWindow datePickerPopupWindow = new DatePickerPopupWindow(this, new DatePickerPopupWindow.IDatePickerListener() {
            @Override
            public void onDatePickSubmit(String year, String month, String day) {
                btn_calendar.setText(month + "-" + day);
            }
        });
        datePickerPopupWindow.showAtLocation(btn_calendar, Gravity.CENTER, 0, 0);
    }

    private void chooseSend() {
        mCurrentSelectedIndex = TableType.Send;
        btn_send.setTextColor(getResources().getColor(R.color.red));
        btn_shop.setTextColor(getResources().getColor(R.color.text_dark));
        btn_deliver.setTextColor(getResources().getColor(R.color.text_dark));
        enableRightRow(false);
        tv_type_1.setText("呼叫人次");
        tv_type_2.setText("投诉人次");
        tv_type_3.setText("最远距离 (KM)");
        tv_type_4.setText("最长时间 (Min)");
        tv_type_5.setText("应答人次");
        tv_type_6.setText("异常单数");
        tv_type_7.setText("异常平均距离");
        tv_type_8.setText("平均时间 (Min)");

        if (mDataInfo != null) {
            tv_td_1.setText(mDataInfo.getGroup_calls());
            tv_td_2.setText("- -");
            tv_td_3.setText(mDataInfo.getMost_distance());
            tv_td_4.setText("- -");
            tv_td_5.setText(mDataInfo.getAdopted_delivers());
            tv_td_6.setText(mDataInfo.getError_orders());
            tv_td_7.setText("- -");
            tv_td_8.setText("- -");
        }
    }

    private void chooseShop() {
        mCurrentSelectedIndex = TableType.Shop;
        btn_send.setTextColor(getResources().getColor(R.color.text_dark));
        btn_shop.setTextColor(getResources().getColor(R.color.red));
        btn_deliver.setTextColor(getResources().getColor(R.color.text_dark));
        enableRightRow(true);
        tv_type_1.setText("呼叫门店");
        tv_type_2.setText("呼叫门店送达单数");
        tv_type_3.setText("呼叫商圈");
        tv_type_4.setText("呼叫商圈送达单数");
        tv_type_5.setText("新呼叫门店");
        tv_type_6.setText("新呼叫门店送达单数");
        tv_type_7.setText("新呼叫商圈");
        tv_type_8.setText("新呼叫商圈送达单数");
        tv_type_9.setText("未呼叫门店");
        tv_type_10.setText("未呼叫门店流失单数");
        tv_type_11.setText("未呼叫商圈");
        tv_type_12.setText("未呼叫商圈流失单数");

        if (mDataInfo != null) {
            tv_td_1.setText(mDataInfo.getShop_calls());
            tv_td_2.setText(mDataInfo.getFinished_orders());
            tv_td_3.setText(mDataInfo.getBusiness_district_calls());
            tv_td_4.setText(mDataInfo.getFinished_orders());
            tv_td_5.setText(mDataInfo.getFirst_call_shops());
            tv_td_6.setText(mDataInfo.getShop_first_orders());
            tv_td_7.setText(mDataInfo.getFirst_call_business_district());
            tv_td_8.setText(mDataInfo.getFirst_call_business_district_orders());
            tv_td_9.setText(mDataInfo.getNo_call_shops());
            tv_td_10.setText("- -");
            tv_td_11.setText(mDataInfo.getNo_call_business_district());
            tv_td_12.setText("- -");
        }
    }

    private void chooseDeliver() {
        mCurrentSelectedIndex = TableType.Deliver;
        btn_send.setTextColor(getResources().getColor(R.color.text_dark));
        btn_shop.setTextColor(getResources().getColor(R.color.text_dark));
        btn_deliver.setTextColor(getResources().getColor(R.color.red));
        enableRightRow(true);
        tv_type_1.setText("应答配送员");
        tv_type_2.setText("应答配送员送达单数");
        tv_type_3.setText("应答配送队");
        tv_type_4.setText("应答配送队送达单数");
        tv_type_5.setText("新配送人员");
        tv_type_6.setText("新配送人员送达单数");
        tv_type_7.setText("新配送队");
        tv_type_8.setText("新配送队送达单数");
        tv_type_9.setText("未配送人员");
        tv_type_10.setText("未配送人员削减运送力");
        tv_type_11.setText("未配送队");
        tv_type_12.setText("未配送队削减运送单力");

        if (mDataInfo != null) {
            tv_td_1.setText(mDataInfo.getAdopted_delivers());
            tv_td_2.setText(mDataInfo.getFinished_orders());
            tv_td_3.setText(mDataInfo.getAdopted_teams());
            tv_td_4.setText("- -");
            tv_td_5.setText(mDataInfo.getFirst_send_deliver());
            tv_td_6.setText(mDataInfo.getDeliver_first_orders());
            tv_td_7.setText(mDataInfo.getFirst_send_team());
            tv_td_8.setText(mDataInfo.getFirst_send_team_orders());
            tv_td_9.setText(mDataInfo.getNo_send_deliver());
            tv_td_10.setText("- -");
            tv_td_11.setText(mDataInfo.getNo_send_team());
            tv_td_12.setText("- -");
        }
    }

    private void enableRightRow(boolean enable) {
        if (enable) {
            ll_table_right_row_1.setVisibility(View.VISIBLE);
            ll_table_right_row_2.setVisibility(View.VISIBLE);
            ll_table_right_row_3.setVisibility(View.VISIBLE);
            ll_table_right_row_4.setVisibility(View.VISIBLE);
        } else {
            ll_table_right_row_1.setVisibility(View.GONE);
            ll_table_right_row_2.setVisibility(View.GONE);
            ll_table_right_row_3.setVisibility(View.GONE);
            ll_table_right_row_4.setVisibility(View.GONE);
        }
    }
}
