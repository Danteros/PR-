package com.electric.handbook.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.electric.handbook.dialogs.EnterNumberDialog;
import com.electric.handbook.listeners.OnNumberEnteredListener;
import com.electric.handbook.R;
import com.electric.handbook.dialogs.SingleSelectDialog;

import java.util.ArrayList;
import java.util.List;


public class ActivityCalcLight extends AppCompatActivity implements View.OnClickListener{

    private TextView plMess, typeRoomMess, typeMess, coofMess, numLightMess;
    private List<Model> typeRoom, typeLight;
    private float selectedPl = -1f, lightCoof = 1.5f;
    private int lightType = -1, typeRoomFl = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_light);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

        plMess = (TextView) findViewById(R.id.calcLightPlMess);
        typeRoomMess = (TextView) findViewById(R.id.calcLightTypeRoomMess);
        typeMess = (TextView) findViewById(R.id.calcLightTypeMess);
        coofMess = (TextView) findViewById(R.id.calcLightCoofMess);
        numLightMess = (TextView) findViewById(R.id.calcLightNum);

        findViewById(R.id.calcLightCoof).setOnClickListener(this);
        findViewById(R.id.calcLightType).setOnClickListener(this);
        findViewById(R.id.calcLightTypeRoom).setOnClickListener(this);
        findViewById(R.id.calcLightPl).setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private String[] data;
    @Override
    public void onClick(View view) {
        EnterNumberDialog enterNumberDialog;
        SingleSelectDialog singleSelectDialog;
        switch (view.getId()) {
            case R.id.calcLightCoof:
                enterNumberDialog = new EnterNumberDialog(new OnNumberEnteredListener() {
                    @Override
                    public void onTextEntered(String text) {
                        coofMess.setText(text);
                        lightCoof = Float.parseFloat(text);
                        calculate();
                    }
                });
                enterNumberDialog.show(this, "Коэффициент запаса", "" + lightCoof);
                break;
            case R.id.calcLightType:
                data = getArNames(typeLight);
                singleSelectDialog = new SingleSelectDialog(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        lightType = typeLight.get(i).value;
                        typeMess.setText("" + lightType);
                        calculate();
                    }
                });
                singleSelectDialog.show(this, "Тип лампы", data);
                break;
            case R.id.calcLightTypeRoom:
                data = getArNames(typeRoom);
                singleSelectDialog = new SingleSelectDialog(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        typeRoomFl = typeRoom.get(i).value;
                        typeRoomMess.setText("" + typeRoomFl);
                        calculate();
                    }
                });
                singleSelectDialog.show(this, "Тип помещения", data);
                break;
            case R.id.calcLightPl:
                enterNumberDialog = new EnterNumberDialog(new OnNumberEnteredListener() {
                    @Override
                    public void onTextEntered(String text) {
                        plMess.setText(text);
                        selectedPl = Float.parseFloat(text);
                        calculate();
                    }
                });
                enterNumberDialog.show(this, "Площадь помещения", selectedPl != -1f ? "" + selectedPl:null);
                break;
        }
    }

    private void calculate() {
        if (selectedPl == -1f || typeRoomFl == -1f || lightType == -1f) return;
        float out = (selectedPl * lightCoof * typeRoomFl) / lightType;
        numLightMess.setText("" + Math.round(out));
    }

    private void init() {
        typeLight = new ArrayList<Model>(){{
            add(new Model("Лампа накаливания 40Вт", 415));
            add(new Model("Лампа накаливания 60Вт", 750));
            add(new Model("Лампа накаливания 100Вт", 1400));
            add(new Model("Лампа накаливания 150Вт", 2160));
            add(new Model("Люминисцентная лампа 15Вт", 700));
            add(new Model("Люминисцентная лампа 25-30Вт", 1200));
            add(new Model("Люминисцентная лампа 40-50Вт", 1800));
            add(new Model("Люминисцентная лампа 60-80Вт", 2500));
            add(new Model("Галогеновая лампа 42Вт", 625));
            add(new Model("Галогеновая лампа 55Вт", 900));
            add(new Model("Галогеновая лампа 70Вт", 1170));
            add(new Model("Светодиодная лампа 4-5Вт", 400));
            add(new Model("Светодиодная лампа 8-10Вт", 700));
            add(new Model("Светодиодная лампа 12-15Вт", 1200));
            add(new Model("Светодиодная лампа 18-20Вт", 1800));
            add(new Model("Светодиодная лампа 25-30Вт", 2500));
        }};


        typeRoom = new ArrayList<Model>() {{
            add(new Model("Комната, спальня, гостиная, кухня", 150));
            add(new Model("Детская комната", 200));
            add(new Model("Кабинет, библиотека", 300));
            add(new Model("Ванные, с/у, уборные", 50));
            add(new Model("Лестница", 20));
            add(new Model("Сауна, бассейн", 100));
            add(new Model("Техническое помещение", 30));
            add(new Model("Торговый зал магазина", 400));
            add(new Model("Гостиный номер", 150));
            add(new Model("Кабинет врача", 400));
            add(new Model("Спортивный зал", 200));
            add(new Model("Зал многоцелевого назначения", 400));
            add(new Model("Зал кафе, бара, ресторанов", 200));
        }};
    }

    private String[] getArNames(List<Model> list) {
        String[] ar = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ar[i] = list.get(i).name;
        }
        return ar;
    }

    class Model {
        public String name;
        public int value;

        public Model(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }
}
