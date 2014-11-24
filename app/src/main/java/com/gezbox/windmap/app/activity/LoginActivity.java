package com.gezbox.windmap.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.backend.Backend;
import com.gezbox.windmap.app.model.Token;
import com.gezbox.windmap.app.model.WindAccount;
import com.gezbox.windmap.app.params.LoginParams;
import com.gezbox.windmap.app.utils.Constant;
import com.gezbox.windmap.app.utils.MD5Utils;
import com.gezbox.windmap.app.utils.SharedPrefsUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 登录页
 * Created by zombie on 14-10-29.
 */
public class LoginActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener {
    private EditText et_username;
    private EditText et_password;

    private Callback<Token> loginCallback = new Callback<Token>() {
        @Override
        public void success(Token token, Response response) {
            showProgressDialog("", false);

            SharedPrefsUtils sharedPrefsUtils = new SharedPrefsUtils(LoginActivity.this, Constant.SharedPrefrence.SHARED_NAME);
            sharedPrefsUtils.setStringSP(Constant.SharedPrefrence.TOKEN, token.getToken());
            sharedPrefsUtils.setStringSP(Constant.SharedPrefrence.USER_ID, token.getId() + "");

            showProgressDialog("获取账号信息", true);
            Backend.with(getApplicationContext()).getUserInfo(token.getId() + "", accountCallback);
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            showProgressDialog("", false);
            if (retrofitError.getResponse() != null && retrofitError.getResponse().getStatus() == 422) {
                Toast.makeText(LoginActivity.this, "账号或密码有误, 请重试!", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(LoginActivity.this, "网络请求失败, 请检查您的网络!", Toast.LENGTH_SHORT).show();
        }
    };

    private Callback<WindAccount> accountCallback = new Callback<WindAccount>() {
        @Override
        public void success(WindAccount windAccount, Response response) {
            showProgressDialog("", false);

            SharedPrefsUtils sharedPrefsUtils = new SharedPrefsUtils(LoginActivity.this, Constant.SharedPrefrence.SHARED_NAME);
            sharedPrefsUtils.setBooleanSP(Constant.SharedPrefrence.LOGGED, true);
            sharedPrefsUtils.setStringSP(Constant.SharedPrefrence.TEL, windAccount.getTel());
            sharedPrefsUtils.setStringSP(Constant.SharedPrefrence.USER_NAME, windAccount.getUser_name());
            sharedPrefsUtils.setStringSP(Constant.SharedPrefrence.AVATAR, windAccount.getAvatar().getUrl());

            startActivity(new Intent(LoginActivity.this, MainCardActivity.class));
            finish();
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            showProgressDialog("", false);
            if (retrofitError.getResponse() != null) {
                Toast.makeText(LoginActivity.this, "获取账号信息失败, 失败码: " + retrofitError.getResponse().getStatus(), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(LoginActivity.this, "网络请求失败, 请检查您的网络!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initCustom();
        initListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_submit) {
            //登录
            String tel = et_username.getText().toString();
            String passWord = et_password.getText().toString();
            if (tel.isEmpty()) {
                Toast.makeText(this, "请填写账号", Toast.LENGTH_SHORT).show();
                et_username.requestFocus();
                return;
            }
            if (passWord.isEmpty()) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                et_password.requestFocus();
                return;
            }

            LoginParams loginParams = new LoginParams();
            loginParams.setTel(tel);
            loginParams.setPassword(MD5Utils.encryptPSW("", passWord));
            showProgressDialog("正在登录...", true);
            Backend.with(this).postLogin(loginParams, loginCallback);
        }
    }

    @Override
    public void initCustom() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_submit).setOnClickListener(this);
    }
}
