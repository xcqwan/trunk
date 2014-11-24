package com.gezbox.windmap.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.activity.ModifyInformationActivity;
import com.gezbox.windmap.app.activity.StandardWorkInterface;
import com.gezbox.windmap.app.backend.DataBackend;
import com.gezbox.windmap.app.model.DataInfo;
import com.gezbox.windmap.app.utils.Constant;
import com.gezbox.windmap.app.utils.SharedPrefsUtils;
import com.squareup.picasso.Picasso;
import org.apache.commons.lang3.time.DateFormatUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.Date;

/**
 * Created by zombie on 14-10-29.
 */
public class HomeFragment extends BaseFragment implements StandardWorkInterface, View.OnClickListener
{
    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_tel;

    private Button btn_refresh;
    private TextView tv_finished_orders;
    private TextView tv_max_history;
    private TextView tv_shop_calls;
    private TextView tv_adopted_delivers;
    private TextView tv_finished_orders_per_persone;

    private SharedPrefsUtils mSharedPrefsUtils;

    private Callback<DataInfo> callback = new Callback<DataInfo>() {
        @Override
        public void success(DataInfo dataInfo, Response response) {
            tv_finished_orders.setText(dataInfo.getFinished_orders());
            tv_max_history.setText(dataInfo.getHighest_orders());
            tv_shop_calls.setText(dataInfo.getShop_calls());
            tv_adopted_delivers.setText(dataInfo.getAdopted_delivers());

            if (dataInfo.getAdopted_delivers() == null || Integer.parseInt(dataInfo.getAdopted_delivers()) == 0) {
                tv_finished_orders_per_persone.setText("0");
            } else {
                tv_finished_orders_per_persone.setText(Integer.parseInt(dataInfo.getFinished_orders())/Integer.parseInt(dataInfo.getAdopted_delivers())+"");
            }
            Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            if (retrofitError.getResponse() != null) {
                Toast.makeText(getActivity(), "网络请求失败, 错误码: " + retrofitError.getResponse().getStatus(), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getActivity(), "网络请求失败, 请检查您的网络!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCustom();
        initListener();

        refreshData();
    }

    @Override
    public void initCustom() {
        iv_avatar = (ImageView) getView().findViewById(R.id.iv_avatar);
        tv_name = (TextView) getView().findViewById(R.id.tv_name);
        tv_tel = (TextView) getView().findViewById(R.id.tv_tel);

        btn_refresh = (Button) getView().findViewById(R.id.btn_refresh);
        tv_finished_orders = (TextView) getView().findViewById(R.id.tv_finished_orders);
        tv_max_history = (TextView) getView().findViewById(R.id.tv_max_history);
        tv_shop_calls = (TextView) getView().findViewById(R.id.tv_shop_calls);
        tv_adopted_delivers = (TextView) getView().findViewById(R.id.tv_adopted_delivers);
        tv_finished_orders_per_persone = (TextView) getView().findViewById(R.id.tv_finished_orders_per_persone);

        mSharedPrefsUtils = new SharedPrefsUtils(getActivity(), Constant.SharedPrefrence.SHARED_NAME);
        String avatar = mSharedPrefsUtils.getStringSP(Constant.SharedPrefrence.AVATAR, "");
        if (avatar.isEmpty()) {
            iv_avatar.setImageResource(R.drawable.ic_default_avatar);
        } else {
            Picasso.with(getActivity()).load(avatar).placeholder(R.drawable.ic_default_avatar).into(iv_avatar);
        }

        tv_name.setText(mSharedPrefsUtils.getStringSP(Constant.SharedPrefrence.USER_NAME, ""));
        tv_tel.setText(mSharedPrefsUtils.getStringSP(Constant.SharedPrefrence.TEL, ""));
    }

    @Override
    public void initListener() {
        iv_avatar.setOnClickListener(this);
        btn_refresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == iv_avatar.getId()) {
            startActivity(new Intent(getActivity(), ModifyInformationActivity.class));
        } else if (id == btn_refresh.getId()) {
            refreshData();
        }
    }

    private void refreshData() {
        DataBackend.with(getActivity()).getData(callback);
    }
}
