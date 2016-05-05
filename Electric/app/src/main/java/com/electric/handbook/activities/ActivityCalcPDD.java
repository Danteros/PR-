package com.electric.handbook.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.electric.handbook.R;
import com.electric.handbook.dialogs.EnterNumberDialog;
import com.electric.handbook.listeners.OnNumberEnteredListener;
import com.electric.handbook.mview.MSwitch;


public class ActivityCalcPDD extends AppCompatActivity implements View.OnClickListener {
    MSwitch mSwitchAK;
    TextView tvPowerInfo;
    TextView tvResult;
    TextView tvPowerEnter;
    TextView tvLengthEnter;
    float powerValue = -1f;
    float lengthValue = -1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_pdd);
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
        mSwitchAK = (MSwitch) findViewById(R.id.mSwitchAK);
        tvPowerInfo = (TextView) findViewById(R.id.tvPowerInfo);
        tvResult = (TextView) findViewById(R.id.tvResult);
        tvPowerEnter = (TextView) findViewById(R.id.tvPowerEnter);
        tvLengthEnter = (TextView) findViewById(R.id.tvLengthEnter);
    }

    private void initGlobal() {
        mSwitchAK.setOnClickListener(this);
        tvPowerEnter.setOnClickListener(this);
        tvLengthEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EnterNumberDialog enterNumberDialog;
        switch (v.getId()) {
            case R.id.tvPowerEnter:
                enterNumberDialog = new EnterNumberDialog(new OnNumberEnteredListener() {
                    @Override
                    public void onTextEntered(String text) {
                        tvPowerEnter.setText(text);
                        try {
                            powerValue = Float.parseFloat(text);
                        } catch (Exception ex) {
                            powerValue = -1f;
                        }
                        calculate();
                    }
                });
                String title = getString(R.string.title_power);
                if (mSwitchAK.isSelectedOne()) {
                    title = getString(R.string.title_amperage);
                }
                enterNumberDialog.show(this, title, Math.abs(powerValue) + "");
                break;
            case R.id.tvLengthEnter:
                enterNumberDialog = new EnterNumberDialog(new OnNumberEnteredListener() {
                    @Override
                    public void onTextEntered(String text) {
                        tvLengthEnter.setText(text);
                        try {
                            lengthValue = Float.parseFloat(text);
                        } catch (Exception ex) {
                            lengthValue = -1f;
                        }
                        calculate();
                    }
                });
                enterNumberDialog.show(this, getString(R.string.title_power), Math.abs(lengthValue) + "");
                break;
            default: // Выбор мощности
                if (mSwitchAK.isSelectedOne()) {
                    tvPowerInfo.setText(getString(R.string.title_amperage));
                } else {
                    tvPowerInfo.setText(getString(R.string.title_power));
                }
                tvResult.setText(getString(R.string.title_empty_result));
                tvPowerEnter.setText("");
                break;
        }
    }


    private void calculate() {
        if (powerValue < 0 || lengthValue < 0) {
            tvResult.setText(getString(R.string.title_empty_result));
        } else {
            double result;
            if (mSwitchAK.isSelectedOne()) {
                result = (0.4 * (powerValue) + 0.01 * (lengthValue)) * 3;
            } else {
                result = (1.82 * (powerValue) + 0.01 * (lengthValue)) * 3;
            }
            tvResult.setText(String.format("%.2f", result));
        }
    }


}
