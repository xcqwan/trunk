package com.gezbox.windmap.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.adapter.HomeCardFragmentPagerAdapter;

/**
 * Created by zombie on 14-10-29.
 */
public class MainCardActivity extends FragmentActivity implements StandardWorkInterface, View.OnClickListener {
    private ViewPager vp_content;

    private TextView tv_home;
    private TextView tv_task;
    private TextView tv_discover;
    private TextView tv_message;
    private TextView tv_write;

    private LinearLayout ll_mask;
    private LinearLayout ll_write;
    private Button btn_write_text;
    private Button btn_write_camera;
    private Button btn_write_photo;
    private Button btn_write_video;

    private HomeCardFragmentPagerAdapter mHomeCardFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_card);

        initCustom();
        initListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == tv_home.getId()) {
            //首页
            int nowIndex = 0;
            if (nowIndex == vp_content.getCurrentItem()) {
                return;
            };
            setSelectedFragmentIndex(nowIndex);
            vp_content.setCurrentItem(nowIndex);
        } else if (id == tv_task.getId()) {
            //任务
            int nowIndex = 1;
            if (nowIndex == vp_content.getCurrentItem()) {
                return;
            };
            setSelectedFragmentIndex(nowIndex);
            vp_content.setCurrentItem(nowIndex);
        } else if (id == tv_discover.getId()) {
            //发现
            int nowIndex = 2;
            if (nowIndex == vp_content.getCurrentItem()) {
                return;
            };
            setSelectedFragmentIndex(nowIndex);
            vp_content.setCurrentItem(nowIndex);
        } else if (id == tv_message.getId()) {
            //协同
            int nowIndex = 3;
            if (nowIndex == vp_content.getCurrentItem()) {
                return;
            };
            setSelectedFragmentIndex(nowIndex);
            vp_content.setCurrentItem(nowIndex);
        } else if (id == tv_write.getId()) {
            //显示选择工具栏
            ll_mask.setEnabled(true);
            ll_mask.setVisibility(View.VISIBLE);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.show_from_topright);
            ll_write.startAnimation(animation);
        } else if (id == ll_mask.getId()) {
            //隐藏选择工具栏
            ll_mask.setEnabled(false);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.hidden_from_topright);
            ll_write.startAnimation(animation);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ll_mask.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else if (id == btn_write_text.getId()) {
            jumpToRecord(RecordActivity.START_WITH_TEXT);
        } else if (id == btn_write_camera.getId()) {
            jumpToRecord(RecordActivity.START_WITH_TAKEPHOTO);
        } else if (id == btn_write_photo.getId()) {
            jumpToRecord(RecordActivity.START_WITH_ALBUM);
        } else if (id == btn_write_video.getId()) {
            jumpToRecord(RecordActivity.START_WITH_VIDEO);
//            Toast.makeText(this, "暂未开放", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initCustom() {
        vp_content = (ViewPager) findViewById(R.id.vp_content);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_task = (TextView) findViewById(R.id.tv_task);
        tv_discover = (TextView) findViewById(R.id.tv_discover);
        tv_message = (TextView) findViewById(R.id.tv_message);
        tv_write = (TextView) findViewById(R.id.tv_write);

        ll_mask = (LinearLayout) findViewById(R.id.ll_mask);
        ll_write = (LinearLayout) findViewById(R.id.ll_write);
        btn_write_text = (Button) findViewById(R.id.btn_write_text);
        btn_write_camera = (Button) findViewById(R.id.btn_write_camera);
        btn_write_photo = (Button) findViewById(R.id.btn_write_photo);
        btn_write_video = (Button) findViewById(R.id.btn_write_video);

        mHomeCardFragmentPagerAdapter = new HomeCardFragmentPagerAdapter(getSupportFragmentManager());
        vp_content.setAdapter(mHomeCardFragmentPagerAdapter);
        vp_content.setOffscreenPageLimit(HomeCardFragmentPagerAdapter.FRAGMENT_COUNT - 1);
        setSelectedFragmentIndex(0);
    }

    @Override
    public void initListener() {
        tv_home.setOnClickListener(this);
        tv_task.setOnClickListener(this);
        tv_discover.setOnClickListener(this);
        tv_message.setOnClickListener(this);
        tv_write.setOnClickListener(this);
        ll_mask.setOnClickListener(this);
        btn_write_text.setOnClickListener(this);
        btn_write_camera.setOnClickListener(this);
        btn_write_photo.setOnClickListener(this);
        btn_write_video.setOnClickListener(this);

        vp_content.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i2) {
//                setSelectedFragmentIndex(position);
            }

            @Override
            public void onPageSelected(int position) {
                setSelectedFragmentIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * 顶部导航栏选择改变
     * @param index
     */
    private void setSelectedFragmentIndex(int index) {
        tv_home.setBackgroundColor(getResources().getColor(R.color.transparent));
        tv_task.setBackgroundColor(getResources().getColor(R.color.transparent));
        tv_discover.setBackgroundColor(getResources().getColor(R.color.transparent));
        tv_message.setBackgroundColor(getResources().getColor(R.color.transparent));

        tv_home.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_home_gray), null, null, null);
        tv_task.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_note_gray), null, null, null);
        tv_discover.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_discover_gray), null, null, null);
        tv_message.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_message_gray), null, null, null);

        if (index == 0) {
            tv_home.setBackgroundColor(getResources().getColor(R.color.list_background));
            tv_home.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_home_white), null, null, null);
        } else if (index == 1) {
            tv_task.setBackgroundColor(getResources().getColor(R.color.list_background));
            tv_task.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_note_white), null, null, null);
        } else if (index == 2) {
            tv_discover.setBackgroundColor(getResources().getColor(R.color.list_background));
            tv_discover.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_discover_white), null, null, null);
        } else if (index == 3) {
            tv_message.setBackgroundColor(getResources().getColor(R.color.list_background));
            tv_message.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_message_white), null, null, null);
        }
    }

    private void jumpToRecord(final String action) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.hidden_from_topright);
        ll_write.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ll_mask.setVisibility(View.GONE);
                Intent intent = new Intent(MainCardActivity.this, RecordActivity.class);
                intent.putExtra("action", action);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
