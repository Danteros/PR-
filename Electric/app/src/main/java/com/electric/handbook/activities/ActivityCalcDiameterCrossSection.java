package com.electric.handbook.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.electric.handbook.R;


public class ActivityCalcDiameterCrossSection extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    SeekBar sbDiameter;
    SeekBar sbCrossSection;
    TextView tvDiameterValue;
    TextView tvCrossSectionValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_diameter_cross_section);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
        initGlobal();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        sbDiameter = (SeekBar) findViewById(R.id.sbDiameter);
        sbCrossSection = (SeekBar) findViewById(R.id.sbCrossSection);
        tvDiameterValue = (TextView) findViewById(R.id.tvDiameterValue);
        tvCrossSectionValue = (TextView) findViewById(R.id.tvCrossSectionValue);
    }

    private void initGlobal() {
        sbDiameter.setOnSeekBarChangeListener(this);
        sbCrossSection.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }
    }


    private void calculate() {

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sbDiameter:

                if (fromUser) {
                    double value = Math.PI * progress * progress / 400f;
                    sbCrossSection.setProgress((int) Math.round(value));
                    tvDiameterValue.setText(String.format(getString(R.string.cross_section_mm), String.format("%.1f", progress / 10f)).replace(",","."));
                    tvCrossSectionValue.setText(String.format(getString(R.string.cross_section_square_mm), (int) Math.round(value)));
                }

                break;
            case R.id.sbCrossSection:

                if (fromUser) {
                    double value = Math.sqrt(progress * 4 / Math.PI);
                    sbDiameter.setProgress((int) Math.round(value * 10));
                    tvDiameterValue.setText(String.format(getString(R.string.cross_section_mm), String.format("%.1f", value)).replace(",","."));
                    tvCrossSectionValue.setText(String.format(getString(R.string.cross_section_square_mm), progress));

                }
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
