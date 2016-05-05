package com.electric.handbook.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.electric.handbook.R;
import com.electric.handbook.dialogs.SingleSelectDialog;
import com.electric.handbook.mview.MSwitch;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;


public class ActivityCalcSection extends AppCompatActivity implements View.OnClickListener {

    private HashMap<Float, String> al220A, al220k, al380A, al380k, cu220A, cu220k, cu380A, cu380k;
    private MSwitch alCu, v220380, aKw;
    private TextView selectPowerMess, numPower, selection, automat;
    private float selectedPower;
    private int selectedNumber = -1;
    private Float[] curPowerData;
    private SingleSelectDialog singleSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_section);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        alCu = (MSwitch) findViewById(R.id.calcSectionAlCu);
        v220380 = (MSwitch) findViewById(R.id.calcSection220380);
        aKw = (MSwitch) findViewById(R.id.calcSectionAK);
        findViewById(R.id.calcSectionPower).setOnClickListener(this);
        selectPowerMess = (TextView) findViewById(R.id.calcSectionPowerMess);
        numPower = (TextView) findViewById(R.id.calcSectionPowerNum);
        selection = (TextView) findViewById(R.id.calcSectionSelection);
        automat = (TextView) findViewById(R.id.calcSectionAutomat);

        singleSelectDialog = new SingleSelectDialog(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPower = curPowerData[i];
                selectedNumber = i;
                calculate();
            }
        });

        alCu.setOnClickListener(onClickListener);
        v220380.setOnClickListener(onClickListener);
        aKw.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            clearData();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        al220A = new HashMap<Float, String>() {{
            put(20f, "2.5,20");
            put(25f, "4,25");
            put(32f, "6,32");
            put(40f, "10,40");
            put(50f, "16,50");
            put(63f, "25,63");
            put(80f, "25,80");
            put(100f, "35,100");
            put(125f, "50,125");
            put(160f, "70,160");
            put(200f, "120,200");
        }};

        al220k = new HashMap<Float, String>() {{
            put(4.4f, "2.5,20");
            put(5.5f, "4,25");
            put(7f, "6,32");
            put(8.8f, "10,40");
            put(11f, "16,50");
            put(13.8f, "25,63");
            put(17.5f, "25,80");
            put(22f, "35,100");
            put(27.5f, "50,125");
            put(35f, "70,160");
            put(44f, "120,200");
        }};

        al380A = new HashMap<Float, String>() {{
            put(16f, "2.5,16");
            put(20f, "4,20");
            put(25f, "6,25");
            put(32f, "10,32");
            put(40f, "16,40");
            put(50f, "16,50");
            put(63f, "25,63");
            put(80f, "35,80");
            put(100f, "50,100");
            put(125f, "70,125");
            put(160f, "120,160");
        }};

        al380k = new HashMap<Float, String>() {{
            put(6f, "2.5,16");
            put(7.6f, "2.5,20");
            put(9.5f, "2.5,25");
            put(12f, "2.5,32");
            put(15f, "4,40");
            put(19f, "6,50");
            put(23.9f, "10,63");
            put(30f, "16,80");
            put(38f, "25,100");
            put(47.5f, "35,125");
            put(60.5f, "50,160");
        }};

        cu220A = new HashMap<Float, String>() {{
            put(10f, "1.5,10");
            put(16f, "1.5,16");
            put(20f, "2.5,20");
            put(25f, "2.5,25");
            put(32f, "4,32");
            put(40f, "6,40");
            put(50f, "10,50");
            put(63f, "10,63");
            put(80f, "16,80");
            put(100f, "25,100");
            put(125f, "35,125");
            put(160f, "50,160");
        }};

        cu220k = new HashMap<Float, String>() {{
            put(3.5f, "1.5,16");
            put(4.4f, "2.5,20");
            put(5.5f, "2.5,25");
            put(7f, "4,32");
            put(8.8f, "6,40");
            put(11f, "10,50");
            put(13.8f, "10,63");
            put(17.5f, "16,80");
            put(22f, "25,100");
            put(27.5f, "35,125");
            put(35f, "50,160");
            put(60.5f, "70,200");
        }};

        cu380A = new HashMap<Float, String>() {{
            put(16f, "1.5,16");
            put(20f, "2.5,20");
            put(25f, "4,25");
            put(32f, "6,32");
            put(40f, "10,40");
            put(50f, "16,50");
            put(63f, "16,63");
            put(80f, "25,80");
            put(100f, "35,100");
            put(125f, "50,125");
            put(160f, "70,160");
            put(200f, "95,200");
        }};

        cu380k = new HashMap<Float, String>() {{
            put(6f, "1.5,16");
            put(7.6f, "1.5,20");
            put(9.5f, "1.5,25");
            put(12f, "2.5,32");
            put(15f, "2.5,40");
            put(19f, "4,50");
            put(23.9f, "6,63");
            put(30f, "10,80");
            put(38f, "16,100");
            put(47.5f, "25,125");
            put(60.5f, "35,160");
            put(76f, "50,200");
        }};
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.calcSectionPower: // Выбор мощности
                Set<Float> strings = getCurHash().keySet();
                curPowerData = strings.toArray(new Float[strings.size()]);
                Arrays.sort(curPowerData);
                String[] data = new String[curPowerData.length];
                for (int i = 0; i < curPowerData.length; i++)
                    data[i] = "" + curPowerData[i];
                String result = "до " + SingleSelectDialog.MASK_IN;
                if (aKw.isSelectedOne()) {
                    result += " А";
                } else {
                    result += " кВт";
                }
                singleSelectDialog.setOutMask(result);
                if (aKw.isSelectedOne()) {
                    singleSelectDialog.show(this, "Сила тока", data);
                } else {
                    singleSelectDialog.show(this, "Мощность", data);
                }

                break;
        }

    }


    private HashMap<Float, String> getCurHash() {
        if (alCu.isSelectedOne())
            if (v220380.isSelectedOne())
                if (aKw.isSelectedOne()) return al220A;
                else return al220k;
            else if (aKw.isSelectedOne()) return al380A;
            else return al380k;
        else if (v220380.isSelectedOne())
            if (aKw.isSelectedOne()) return cu220A;
            else return cu220k;
        else if (aKw.isSelectedOne()) return cu380A;
        else return cu380k;
    }

    private void calculate() {
        if (aKw.isSelectedOne())
            selectPowerMess.setText("Сила тока");
        else
            selectPowerMess.setText("Мощность");
        String suffixString;
        if (aKw.isSelectedOne()) {
            suffixString = " А";
        } else {
            suffixString = " кВт";
        }
        numPower.setText("до " + selectedPower + suffixString);
        String[] tempData = getCurHash().get(selectedPower).split(",");
        selection.setText(tempData[0]);
        automat.setText(tempData[1]);
    }

    private void clearData() { // Выбраны новые данные очиста предыдущих результатов
        if (selectedNumber != -1) {
            Set<Float> strings = getCurHash().keySet();
            curPowerData = strings.toArray(new Float[strings.size()]);
            Arrays.sort(curPowerData);
            selectedPower = curPowerData[selectedNumber];

            calculate();
        } else {
            if (aKw.isSelectedOne())
                selectPowerMess.setText("Выберите силу тока...");
            else
                selectPowerMess.setText("Выберите мощность...");
        }
    }
}
