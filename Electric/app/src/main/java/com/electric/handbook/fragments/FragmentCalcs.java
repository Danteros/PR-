package com.electric.handbook.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.electric.handbook.R;
import com.electric.handbook.activities.ActivityCalcDiameterCrossSection;
import com.electric.handbook.activities.ActivityCalcLight;
import com.electric.handbook.activities.ActivityCalcPDD;
import com.electric.handbook.activities.ActivityCalcSection;
import com.electric.handbook.activities.ActivityCalcSpeed;
import com.electric.handbook.activities.ActivityDocuments;
import com.electric.handbook.activities.ActivityOnlineEngineer;
import com.electric.handbook.adapters.CalcsListAdapter;

import java.util.ArrayList;


public class FragmentCalcs extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    ListView lvMain;
    ArrayList<String> items = new ArrayList<>();
    CalcsListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calcs, container, false);
        initUI(view);
        initGlobal();


        return view;
    }

    private void initUI(View v) {
        lvMain = (ListView) v.findViewById(R.id.lvMain);
    }

    private void initGlobal() {
        lvMain.setOnItemClickListener(this);
        lvMain.setDivider(null);
        adapter = new CalcsListAdapter(getActivity(), items);
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(this);
        fillItems();
    }

    private void fillItems() {
        String[] calcItems = getResources().getStringArray(R.array.calc_items);
        for (String item : calcItems) {
            items.add(item);
        }
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), ActivityCalcSection.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(), ActivityCalcLight.class));
                break;
            case 2:
                startActivity(new Intent(getActivity(), ActivityCalcSpeed.class));
                break;
            case 3:
                startActivity(new Intent(getActivity(), ActivityCalcPDD.class));
                break;
            case 4:
                startActivity(new Intent(getActivity(), ActivityCalcDiameterCrossSection.class));
                break;
            case 5:
                startActivity(new Intent(getActivity(), ActivityOnlineEngineer.class));
                break;
            case 6:
                startActivity(new Intent(getActivity(), ActivityDocuments.class));
                break;
        }
    }
}
