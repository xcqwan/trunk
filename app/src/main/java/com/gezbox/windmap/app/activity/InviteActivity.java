package com.gezbox.windmap.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.backend.ShortURLBackend;
import com.gezbox.windmap.app.model.ShortUrl;
import com.gezbox.windmap.app.utils.Constant;
import com.gezbox.windmap.app.utils.SMSUtils;
import com.gezbox.windmap.app.utils.SharedPrefsUtils;
import com.gezbox.windmap.app.utils.ValidataUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.List;

/**
 * Created by zombie on 14-11-6.
 */
public class InviteActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener {
    private TextView tv_wind;
    private TextView tv_shop;
    private EditText et_wind_tel;

    private LinearLayout ll_wind;
    private LinearLayout ll_shop;
    private EditText et_shop_name;
    private EditText et_shop_tel;

    private TextView tv_hint_wind;
    private TextView tv_hint_shop;

    private Button btn_submit;

    private boolean mHasWindSelected;

    private SharedPrefsUtils mSharedPrefsUtils;
    private String user_name;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        initCustom();
        initListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            onBackPressed();
        } else if (id == tv_wind.getId()) {
            if (mHasWindSelected) {
                return;
            }
            mHasWindSelected = true;
            selectWind(mHasWindSelected);
        } else if (id == tv_shop.getId()) {
            if (!mHasWindSelected) {
                return;
            }
            mHasWindSelected = false;
            selectWind(mHasWindSelected);
        } else if (id == btn_submit.getId()) {
            if (mHasWindSelected) {
                final String tel = et_wind_tel.getText().toString();
                if (tel.isEmpty() || !ValidataUtils.isMobileNO(tel)) {
                    Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgressDialog("生成短网址", true);
                ShortURLBackend.with(this).getShort(SMSUtils.inviteWindAddress(user_name, user_id), new Callback<List<ShortUrl>>() {
                    @Override
                    public void success(List<ShortUrl> shortUrls, Response response) {
                        showProgressDialog("", false);
                        if (!shortUrls.isEmpty()) {
                            sendSMS(SMSUtils.inviteWindMessage(shortUrls.get(0).getUrl_short()), tel);
                        } else {
                            Toast.makeText(getApplicationContext(), "生成短网址失败, 失败码: " + response.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showProgressDialog("", false);
                        if (error.getResponse() != null) {
                            Toast.makeText(InviteActivity.this, "生成短网址失败, 错误码: " + error.getResponse().getStatus(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(InviteActivity.this, "网络请求失败, 请检查您的网络!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                final String name = et_shop_name.getText().toString();
                final String tel = et_shop_tel.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(this, "请输入商户名称", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tel.isEmpty() || !ValidataUtils.isMobileNO(tel)) {
                    Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                showProgressDialog("生成短网址", true);
                ShortURLBackend.with(this).getShort(SMSUtils.inviteShopAddress(user_name, user_id), new Callback<List<ShortUrl>>() {
                    @Override
                    public void success(List<ShortUrl> shortUrls, Response response) {
                        showProgressDialog("", false);
                        if (!shortUrls.isEmpty()) {
                            sendSMS(SMSUtils.inviteShopMessage(shortUrls.get(0).getUrl_short()), name, tel);
                        } else {
                            Toast.makeText(getApplicationContext(), "生成短网址失败, 失败码: " + response.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        showProgressDialog("", false);
                        if (error.getResponse() != null) {
                            Toast.makeText(InviteActivity.this, "生成短网址失败, 错误码: " + error.getResponse().getStatus(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(InviteActivity.this, "网络请求失败, 请检查您的网络!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public void initCustom() {
        tv_wind = (TextView) findViewById(R.id.tv_wind);
        tv_shop = (TextView) findViewById(R.id.tv_shop);
        et_wind_tel = (EditText) findViewById(R.id.et_wind_tel);

        ll_wind = (LinearLayout) findViewById(R.id.ll_wind);
        ll_shop = (LinearLayout) findViewById(R.id.ll_shop);
        et_shop_name = (EditText) findViewById(R.id.et_shop_name);
        et_shop_tel = (EditText) findViewById(R.id.et_shop_tel);

        tv_hint_wind = (TextView) findViewById(R.id.tv_hint_wind);
        tv_hint_shop = (TextView) findViewById(R.id.tv_hint_shop);

        btn_submit = (Button) findViewById(R.id.btn_submit);

        mSharedPrefsUtils = new SharedPrefsUtils(this, Constant.SharedPrefrence.SHARED_NAME);
        user_name = mSharedPrefsUtils.getStringSP(Constant.SharedPrefrence.USER_NAME, "");
        user_id = mSharedPrefsUtils.getStringSP(Constant.SharedPrefrence.USER_ID, "");

        mHasWindSelected = true;
        selectWind(mHasWindSelected);
    }

    @Override
    public void initListener() {
        tv_wind.setOnClickListener(this);
        tv_shop.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    private void selectWind(boolean hasSelected) {
        tv_wind.setTextColor(getResources().getColor(R.color.text_dark));
        tv_shop.setTextColor(getResources().getColor(R.color.text_dark));
        if (hasSelected) {
            tv_wind.setTextColor(getResources().getColor(R.color.red));

            ll_wind.setVisibility(View.VISIBLE);
            ll_shop.setVisibility(View.GONE);
            tv_hint_wind.setVisibility(View.VISIBLE);
            tv_hint_shop.setVisibility(View.GONE);
        } else {
            tv_shop.setTextColor(getResources().getColor(R.color.red));

            ll_wind.setVisibility(View.GONE);
            ll_shop.setVisibility(View.VISIBLE);
            tv_hint_wind.setVisibility(View.GONE);
            tv_hint_shop.setVisibility(View.VISIBLE);
        }
    }
}
