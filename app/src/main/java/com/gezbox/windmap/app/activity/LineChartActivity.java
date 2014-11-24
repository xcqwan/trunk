package com.gezbox.windmap.app.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.gezbox.windmap.app.R;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;

import java.util.ArrayList;

/**
 * Created by zombie on 14-11-21.
 */
public class LineChartActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener, OnChartValueSelectedListener {
    private LineChart lc_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        initCustom();
        initListener();
    }

    @Override
    public void initCustom() {
        lc_chart = (LineChart) findViewById(R.id.lc_chart);

        lc_chart.setOnChartValueSelectedListener(this);

        //设置Y轴度量
        lc_chart.setUnit(" $");
        lc_chart.setDrawUnitsInChart(true);

        //设置Y轴是否从0开始
        lc_chart.setStartAtZero(false);

        //设置各点是否显示Y轴数值
        lc_chart.setDrawYValues(false);
        //设置各点显示的数值颜色
        lc_chart.setValueTextColor(getResources().getColor(R.color.blue));

        //设置图表边框
        lc_chart.setDrawBorder(true);
        lc_chart.setBorderColor(getResources().getColor(R.color.fiord));
        lc_chart.setBorderPositions(new BarLineChartBase.BorderPosition[] {
                BarLineChartBase.BorderPosition.TOP,
                BarLineChartBase.BorderPosition.BOTTOM,
                BarLineChartBase.BorderPosition.LEFT,
                BarLineChartBase.BorderPosition.RIGHT
        });

        //设置图表备注
        lc_chart.setDescription("");

        //设置是否可以高亮显示点击的点
        lc_chart.setHighlightEnabled(true);

        //设置是否可以点击
        lc_chart.setTouchEnabled(true);

        //设置拖动、缩放
        lc_chart.setDragEnabled(true);
        lc_chart.setScaleEnabled(true);

        lc_chart.setDrawGridBackground(false);
        lc_chart.setDrawVerticalGrid(false);
        lc_chart.setDrawHorizontalGrid(true);
        lc_chart.setGridColor(getResources().getColor(R.color.fiord));

        //设置是否可以通过双指同时缩放X、Y轴
        //true  X、Y同时缩放
        //false 双指左右分离, 缩放X轴    双指上下分离, 缩放Y轴
        lc_chart.setPinchZoom(true);

        // add data
        setData(45, 100);
        //设置初始化动画
        lc_chart.animateXY(2500, 2500);

        //设置字体
        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        // get the legend (only possible after setting data)
        Legend l = lc_chart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(tf);
        l.setTextColor(getResources().getColor(R.color.text_light));

        XLabels xl = lc_chart.getXLabels();
        xl.setPosition(XLabels.XLabelPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setTextColor(getResources().getColor(R.color.text_light));

        YLabels yl = lc_chart.getYLabels();
        yl.setTypeface(tf);
        yl.setTextColor(getResources().getColor(R.color.text_light));
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            onBackPressed();
        }
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex) {
        Log.d("LineChart", e.toString());
    }

    @Override
    public void onNothingSelected() {
        Log.d("LineChart", "Nothing selected.");
    }

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(val, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "图表示例");
        set1.setColor(getResources().getColor(R.color.green));
        set1.setCircleColor(getResources().getColor(R.color.green));
        set1.setLineWidth(2f);
        set1.setCircleSize(4f);

        //设置是否补充曲线
        set1.setDrawCubic(false);

        //设置是否填充下方区域
        set1.setDrawFilled(true);

        //设置高亮选中颜色
        set1.setHighLightColor(getResources().getColor(R.color.blue));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        lc_chart.setData(data);
    }
}
