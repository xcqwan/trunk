package com.gezbox.windmap.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.backend.Backend;
import com.gezbox.windmap.app.model.ModifyPassword;
import com.gezbox.windmap.app.model.WindAccount;
import com.gezbox.windmap.app.utils.Constant;
import com.gezbox.windmap.app.utils.MD5Utils;
import com.gezbox.windmap.app.utils.SharedPrefsUtils;
import com.squareup.picasso.Picasso;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zombie on 14-11-11.
 */
public class ModifyInformationActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener {
    private ImageView iv_avatar;
    private TextView tv_name;
    private TextView tv_tel;
    private EditText et_password;
    private EditText et_confirm_password;

    private SharedPrefsUtils mSharedPrefsUtils;

    private Callback<WindAccount> accountCallback = new Callback<WindAccount>() {
        @Override
        public void success(WindAccount windAccount, Response response) {
            showProgressDialog("", false);
            Toast.makeText(ModifyInformationActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void failure(RetrofitError error) {
            showProgressDialog("", false);
            if (error.getResponse() != null) {
                Toast.makeText(ModifyInformationActivity.this, "修改密码失败, 失败码: " + error.getResponse().getStatus(), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(ModifyInformationActivity.this, "网络请求失败, 请检查您的网络!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_infomation);

        initCustom();
        initListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            onBackPressed();
        } else if (id == R.id.btn_submit) {
            //修改密码
            String psd = et_password.getText().toString();
            String comfirm_psd = et_confirm_password.getText().toString();

            if (!psd.equals(comfirm_psd)) {
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }

            ModifyPassword modifyPassword = new ModifyPassword();
            modifyPassword.setPassword(MD5Utils.create32Md5(psd).toLowerCase());

            showProgressDialog("提交修改", true);
            Backend.with(this).modifyPassword(modifyPassword, accountCallback);
        }
    }

    @Override
    public void initCustom() {
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_tel = (TextView) findViewById(R.id.tv_tel);
        et_password = (EditText) findViewById(R.id.et_password);
        et_confirm_password = (EditText) findViewById(R.id.et_confirm_password);

        mSharedPrefsUtils = new SharedPrefsUtils(this, Constant.SharedPrefrence.SHARED_NAME);
        String avatar = mSharedPrefsUtils.getStringSP(Constant.SharedPrefrence.AVATAR, "");
        if (avatar.isEmpty()) {
            iv_avatar.setImageResource(R.drawable.ic_default_avatar);
        } else {
            Picasso.with(this).load(avatar).placeholder(R.drawable.ic_default_avatar).into(iv_avatar);
        }

        tv_name.setText(mSharedPrefsUtils.getStringSP(Constant.SharedPrefrence.USER_NAME, ""));
        tv_tel.setText(mSharedPrefsUtils.getStringSP(Constant.SharedPrefrence.TEL, ""));
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_submit).setOnClickListener(this);
    }
}
