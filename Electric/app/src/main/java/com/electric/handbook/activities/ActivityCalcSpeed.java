package com.electric.handbook.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.electric.handbook.R;

import info.hoang8f.android.segmented.SegmentedGroup;


public class ActivityCalcSpeed extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener{

    private int numWinding = 2;
    private TextView messFrequency, messSpeed;
    private SeekBar frequency, speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_speed);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SegmentedGroup segmentedGroup = (SegmentedGroup) findViewById(R.id.calcSpeedSegmentGroup);
        segmentedGroup.setTintColor(Color.parseColor("#5294c3"), Color.WHITE);
        segmentedGroup.setOnCheckedChangeListener(this);
        frequency = (SeekBar) findViewById(R.id.calcSpeedFrequency);
        speed = (SeekBar) findViewById(R.id.calcSpeedSpeed);
        speed.setMax(3600);
        speed.setOnSeekBarChangeListener(this);

        frequency.setOnSeekBarChangeListener(this);
        messSpeed = (TextView) findViewById(R.id.calcSpeedMess);
        messFrequency = (TextView) findViewById(R.id.calcSpeedFrequencyMess);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.calcSpeedSegment2:
                numWinding = 2;
                speed.setMax(3600);
                break;
            case R.id.calcSpeedSegment4:
                speed.setMax(1800);
                numWinding = 4;
                break;
            case R.id.calcSpeedSegment6:
                speed.setMax(1200);
                numWinding = 6;
                break;
            case R.id.calcSpeedSegment8:
                speed.setMax(900);
                numWinding = 8;
                break;
        }
        speed.setProgress(frequency.getProgress() * 60 / numWinding);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.calcSpeedFrequency:
                messFrequency.setText(i + " Гц");
                if (b)
                    speed.setProgress(i * 60 / numWinding);
                break;
            case R.id.calcSpeedSpeed:
                messSpeed.setText(i + " об/мин");
                if (b)
                    frequency.setProgress(i * numWinding / 60);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
