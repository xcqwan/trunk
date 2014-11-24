package com.gezbox.windmap.app.activity;

import android.os.Bundle;
import android.view.View;
import com.gezbox.windmap.app.R;

/**
 * Created by zombie on 14-11-11.
 */
public class CreateDailyActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_daily);

        initCustom();
        initListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            onBackPressed();
        }
    }

    @Override
    public void initCustom() {

    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);
    }
}
