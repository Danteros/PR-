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


public class FragmentElectric extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    ListView lvMain;
    ArrayList<String> items = new ArrayList<>();
    CalcsListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_electric, container, false);
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
        String[] calcItems = getResources().getStringArray(R.array.electric_items);
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
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "electro_1/index"));
                break;
            case 1:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "electro_2/index"));
                break;
            case 2:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "electro_3/index"));
                break;
            case 3:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "electro_4/index"));
                break;
            case 4:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "electro_5/index"));
                break;
            case 5:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "electric/table_of_consumers"));
                break;
            case 6:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "electric/connection_of_wires/index"));
                break;
           case 7:
               startActivity(new Intent(getActivity(), ActivityWebView.class)
                       .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                       .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "electric/assemble_cabinet/index"));
               break;
           case 8:
               startActivity(new Intent(getActivity(), ActivityWebView.class)
                       .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                       .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "electric/automatic_switches/index"));
               break;
            case 9:
                startActivity(new Intent(getActivity(), ActivityWebView.class)
                        .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                        .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "electro_6/index"));
                break;
           case 10:
               startActivity(new Intent(getActivity(), ActivityWebView.class)
                       .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                       .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "electric/ip_defence"));
               break;
           case 11:
               startActivity(new Intent(getActivity(), ActivityWebView.class)
                       .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                       .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "electric/cable_for_shipbuilders"));
               break;
           case 12:
               startActivity(new Intent(getActivity(), ActivityWebView.class)
                       .putExtra(ActivityWebView.EXTRA_TITLE, items.get(position))
                       .putExtra(ActivityWebView.EXTRA_PATH_PAGE, "electric/wiring_the_house/index"));
               break;

        }
    }
}

