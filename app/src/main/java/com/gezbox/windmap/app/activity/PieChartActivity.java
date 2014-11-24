package com.gezbox.windmap.app.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.gezbox.windmap.app.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;

import java.util.ArrayList;

/**
 * Created by zombie on 14-11-21.
 */
public class PieChartActivity extends BaseActivity implements StandardWorkInterface, View.OnClickListener, OnChartValueSelectedListener {
    private PieChart pc_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

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
        pc_chart = (PieChart) findViewById(R.id.pc_chart);

        // change the color of the center-hole
        pc_chart.setHoleColor(getResources().getColor(R.color.white));

//        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
//
//        pc_chart.setValueTypeface(tf);
//        pc_chart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));

        pc_chart.setHoleRadius(60f);

        pc_chart.setDescription("");

        pc_chart.setDrawYValues(true);
        pc_chart.setDrawCenterText(true);

        pc_chart.setDrawHoleEnabled(true);

        pc_chart.setRotationAngle(0);

        // draws the corresponding description value into the slice
        pc_chart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pc_chart.setRotationEnabled(true);

        // display percentage values
        pc_chart.setUsePercentValues(true);
//        pc_chart.setUnit(" €");
//        pc_chart.setDrawUnitsInChart(true);

        // add a selection listener
        pc_chart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

        pc_chart.setCenterText("饼图示例");

        setData(21, 100);

        pc_chart.animateXY(1500, 1500);
        //设置初始化动画
        pc_chart.spin(2000, 0, 360);

        Legend l = pc_chart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setTextColor(getResources().getColor(R.color.text_light));
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
    }

    @Override
    public void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(this);
    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++)
            xVals.add("part " + i);

        PieDataSet set1 = new PieDataSet(yVals1, "");
        set1.setSliceSpace(3f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        set1.setColors(colors);

        PieData data = new PieData(xVals, set1);
        pc_chart.setData(data);

        // undo all highlights
        pc_chart.highlightValues(null);

        pc_chart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex) {
        if (e == null)
            return;
        Log.d("PieChart", "Value: " + e.getVal() + ", xIndex: " + e.getXIndex() + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.d("PieChart", "nothing selected");
    }
}
