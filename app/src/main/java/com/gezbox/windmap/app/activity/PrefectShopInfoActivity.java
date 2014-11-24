package com.gezbox.windmap.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.backend.Backend;
import com.gezbox.windmap.app.model.CreateCheck;
import com.gezbox.windmap.app.model.Location;
import com.gezbox.windmap.app.model.Shop;
import com.gezbox.windmap.app.utils.ValidataUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by zombie on 14-11-12.
 */
public class PrefectShopInfoActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener {
    public static final int REQUEST_CODE_CHOOSE_AREA = 11;
    private Button btn_address;

    private EditText et_shop_name;
    private EditText et_tel;
    private EditText et_street;
    private EditText et_shop_phone;
    private EditText et_shop_subscriber_tel;
    private ToggleButton tbtn_has_android;

    private Location location = new Location();
    private Shop mShop;

    //检查手机号是否可用
    private Callback<CreateCheck> createCheckCallback = new Callback<CreateCheck>() {
        @Override
        public void success(CreateCheck createCheck, Response response) {
            showProgressDialog("", false);
            if (createCheck.isStatus()) {
                //手机号可以创建店铺
                saveAndFinished();
            } else {
                //手机号已存在店铺
                Toast.makeText(PrefectShopInfoActivity.this, "该手机号已注册, 请更换手机号码", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            showProgressDialog("", false);
            if (retrofitError.getResponse() != null) {
                Toast.makeText(PrefectShopInfoActivity.this, "检查手机号是否可用失败, 错误码: " + retrofitError.getResponse().getStatus(), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(PrefectShopInfoActivity.this, "网络请求失败, 请检查您的网络!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefect_shop_info);

        initCustom();
        initListener();
        initAction();
    }

    private void initAction() {
        mShop = getIntent().getParcelableExtra("shop");
        if (mShop != null) {
            location = mShop.getLocation();

            et_shop_name.setText(mShop.getName());
            et_tel.setText(mShop.getTel());
            et_tel.setEnabled(false);
            et_street.setText(location.getStreet());
            et_shop_phone.setText(mShop.getPhone());
            et_shop_subscriber_tel.setText(mShop.getSubscriber_tel());
            tbtn_has_android.setChecked(mShop.isHas_android());

            btn_address.setText(location.getState() + location.getCity() + location.getDistrict());
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            onBackPressed();
        } else if (id == btn_address.getId()) {
            startActivityForResult(new Intent(this, ChooseAreaActivity.class), REQUEST_CODE_CHOOSE_AREA);
        } else if (id == R.id.tv_call_me) {
            makeCall("400-0123-575");
        } else if (id == R.id.btn_submit) {
            //保存
            String name = et_shop_name.getText().toString();
            String tel = et_tel.getText().toString();
            String street = et_street.getText().toString();
            String phone = et_shop_phone.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(this, "请填写店铺名称", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!ValidataUtils.isMobileNO(tel)) {
                Toast.makeText(this, "请填写正确的手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (location.getState_code() == null) {
                Toast.makeText(this, "请选择省、市、区", Toast.LENGTH_SHORT).show();
                return;
            }
            if (street.isEmpty()) {
                Toast.makeText(this, "请填写店铺详细地址", Toast.LENGTH_SHORT).show();
                return;
            }
            if (phone.isEmpty()) {
                Toast.makeText(this, "请填写店铺电话", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mShop == null) {
                showProgressDialog("检查手机号是否可用", true);
                Backend.with(this).createCheck(tel, createCheckCallback);
            } else {
                saveAndFinished();
            }
        }
    }

    public void saveAndFinished() {
        String name = et_shop_name.getText().toString();
        String tel = et_tel.getText().toString();
        String street = et_street.getText().toString();
        String phone = et_shop_phone.getText().toString();
        String subscriber_tel = et_shop_subscriber_tel.getText().toString();
        boolean has_android = tbtn_has_android.isChecked();

        Shop shop = mShop;
        if (shop == null) {
            shop = new Shop();
        }
        shop.setName(name);
        shop.setPhone(phone);
        shop.setSubscriber_tel(subscriber_tel);
        shop.setTel(tel);
        shop.setHas_android(has_android);
        location.setStreet(street);
        shop.setLocation(location);

        Intent intent = new Intent();
        intent.putExtra("shop_info", shop);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void initCustom() {
        btn_address = (Button) findViewById(R.id.btn_address);

        et_shop_name = (EditText) findViewById(R.id.et_shop_name);
        et_tel = (EditText) findViewById(R.id.et_tel);
        et_street = (EditText) findViewById(R.id.et_street);
        et_shop_phone = (EditText) findViewById(R.id.et_shop_phone);
        et_shop_subscriber_tel = (EditText) findViewById(R.id.et_shop_subscriber_tel);
        tbtn_has_android = (ToggleButton) findViewById(R.id.tbtn_has_android);
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_submit).setOnClickListener(this);
        findViewById(R.id.tv_call_me).setOnClickListener(this);
        btn_address.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHOOSE_AREA && resultCode == RESULT_OK) {
            location.setState(data.getStringExtra("state"));
            location.setState_code(data.getStringExtra("state_code"));
            location.setCity(data.getStringExtra("city"));
            location.setCity_code(data.getStringExtra("city_code"));
            location.setDistrict(data.getStringExtra("district"));
            location.setDistrict_code(data.getStringExtra("district_code"));

            btn_address.setText(location.getState() + location.getCity() + location.getDistrict());
        }
    }
}
