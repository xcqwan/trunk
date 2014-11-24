package com.gezbox.windmap.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.gezbox.windmap.app.R;

/**
 * Created by zombie on 14-11-19.
 */
public class WindMapActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener, BaiduMap.OnMarkerClickListener {
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    private Marker mMarker;
    private Marker support_1;
    private Marker support_2;
    private InfoWindow mInfoWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wind_map);

        initCustom();
        initListener();
        startLocation();
    }

    @Override
    public void finishLocation(double latitude, double longitude, String address) {
        super.finishLocation(latitude, longitude, address);
        LatLng ll = new LatLng(latitude, longitude);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);

        /**
         * zindex值表示层叠等级   越大越上
         */
        LatLng now = new LatLng(latitude, longitude);
        OverlayOptions nowOpt = new MarkerOptions().position(now).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_blue)).zIndex(1);
        mMarker = (Marker) mBaiduMap.addOverlay(nowOpt);

        LatLng s1 = new LatLng(latitude + 0.01, longitude + 0.01);
        OverlayOptions s1Opt = new MarkerOptions().position(s1).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_black)).zIndex(2);
        support_1 = (Marker) mBaiduMap.addOverlay(s1Opt);

        LatLng s2 = new LatLng(latitude - 0.01, longitude - 0.01);
        OverlayOptions s2Opt = new MarkerOptions().position(s2).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_gray)).zIndex(3);
        support_2 = (Marker) mBaiduMap.addOverlay(s2Opt);
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
        mMapView = (MapView) findViewById(R.id.mv_wind);
        mMapView.setSystemUiVisibility(View.GONE);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setMaxAndMinZoomLevel(19.0f, 5.0f);
        mBaiduMap.setMyLocationEnabled(true);

        removeLogoAndZoomControl();
    }

    /**
     * 隐藏地图logo 缩放工具栏
     */
    private void removeLogoAndZoomControl() {
        int count = mMapView.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = mMapView.getChildAt(i);
            if (child instanceof ImageView || child instanceof ZoomControls) {
                child.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);

        mBaiduMap.setOnMarkerClickListener(this);
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                Toast.makeText(WindMapActivity.this, "缩放等级变化: " + mapStatus.zoom + ", 中心点经纬度变化: " + mapStatus.target.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Button button = new Button(this);
        button.setBackgroundResource(R.drawable.selector_btn_border_blue);
        button.setPadding(10, 10, 10, 10);
        if (marker == mMarker) {
            button.setText("当前位置");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBaiduMap.hideInfoWindow();
                }
            });
        } else if (marker == support_1) {
            button.setText("辅助位置1");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(WindMapActivity.this, "点击了辅助位置1", Toast.LENGTH_SHORT).show();
                    mBaiduMap.hideInfoWindow();
                }
            });
        } else if (marker == support_2) {
            button.setText("辅助位置2");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(WindMapActivity.this, "点击了辅助位置2", Toast.LENGTH_SHORT).show();
                    mBaiduMap.hideInfoWindow();
                }
            });
        }

        LatLng latLng = marker.getPosition();
        mInfoWindow = new InfoWindow(button, latLng, -30);
        mBaiduMap.showInfoWindow(mInfoWindow);

        return true;
    }
}
