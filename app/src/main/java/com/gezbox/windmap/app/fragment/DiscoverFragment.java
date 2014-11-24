package com.gezbox.windmap.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.gezbox.windmap.app.R;
import com.gezbox.windmap.app.activity.*;

/**
 * Created by zombie on 14-11-3.
 */
public class DiscoverFragment extends BaseFragment implements StandardWorkInterface, View.OnClickListener {
    private Button btn_map;
    private Button btn_data_monitor;
    private Button btn_adv;
    private Button btn_wind_team_manager;
    private Button btn_district_manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCustom();
        initListener();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == btn_map.getId()) {
            startActivity(new Intent(getActivity(), WindMapActivity.class));
        } else if (id == btn_data_monitor.getId()) {
            startActivity(new Intent(getActivity(), DataMonitorActivity.class));
        } else if (id == btn_adv.getId()) {
            startActivity(new Intent(getActivity(), InviteActivity.class));
        } else if (id == btn_wind_team_manager.getId()) {
            startActivity(new Intent(getActivity(), WindTeamManagerActivity.class));
        } else if (id == btn_district_manager.getId()) {
            startActivity(new Intent(getActivity(), ShopTeamListActivity.class));
        }
    }

    @Override
    public void initCustom() {
        btn_map = (Button) getView().findViewById(R.id.btn_map);
        btn_data_monitor = (Button) getView().findViewById(R.id.btn_data_monitor);
        btn_adv = (Button) getView().findViewById(R.id.btn_adv);
        btn_wind_team_manager = (Button) getView().findViewById(R.id.btn_wind_team_manager);
        btn_district_manager = (Button) getView().findViewById(R.id.btn_district_manager);

    }

    @Override
    public void initListener() {
        btn_map.setOnClickListener(this);
        btn_data_monitor.setOnClickListener(this);
        btn_adv.setOnClickListener(this);
        btn_wind_team_manager.setOnClickListener(this);
        btn_district_manager.setOnClickListener(this);
    }
}
