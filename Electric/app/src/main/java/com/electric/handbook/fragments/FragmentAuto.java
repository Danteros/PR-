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
import com.electric.handbook.activities.ActivityWebView;
import com.electric.handbook.adapters.CalcsListAdapter;

import java.util.ArrayList;


public class FragmentAuto extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    ListView lvMain;
    ArrayList<String> items = new ArrayList<>();
    CalcsListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_auto, container, false);
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
        String[] calcItems = getResources().getStringArray(R.array.auto_items);
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
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "auto_1/index"));
                break;
            case 1:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "auto_2/index"));
                break;
            case 2:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "auto_3/index"));
                break;
            case 3:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "automation/thermal_points/index"));
                break;
            case 4:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "automation/extracts_from_the_snp"));
                break;
            case 5:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "automation/power_and_motor_currents"));
                break;
            case 6:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "automation/designation_of_switching_devices/index"));
                break;
            case 7:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "automation/resistance_of_thermometers"));
                break;
            case 8:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "automation/table_of_fuse_links"));
                break;

        }
    }
}
