package com.electric.handbook.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.electric.handbook.R;
import com.electric.handbook.activities.ActivityNews;
import com.electric.handbook.adapters.ItemNews;
import com.electric.handbook.adapters.NewsListAdapter;
import com.electric.handbook.tasks.BaseRequests;
import com.electric.handbook.tasks.RequestTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FragmentNews extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = FragmentNews.class.getSimpleName();
    ListView lvMain;
    ArrayList<ItemNews> items = new ArrayList<>();
    NewsListAdapter adapter;
    BaseRequests baseRequests;
    public static SimpleDateFormat sdfHyphen = new SimpleDateFormat("dd MMMM yyyy");
    public static SimpleDateFormat sdfFull = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        baseRequests = new BaseRequests(getActivity());
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
        adapter = new NewsListAdapter(getActivity(), items);
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(this);
        fillItems();
    }

    private void fillItems() {
        baseRequests.receiveNews(new RequestTask.OnRequestObtainedListener() {
            @Override
            public void onRequestObtained(String response) {
                items.clear();
                adapter.notifyDataSetChanged();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject jsonObject = jsonArray.optJSONObject(i);
                        String title = jsonObject.optString("name");
                        String description = jsonObject.optString("content");
                        String photo = jsonObject.optString("image");
                        String dateString = jsonObject.optString("date");
                        try {
                            Date date = sdfFull.parse(dateString);
                            dateString = sdfHyphen.format(date);
                        } catch (Exception ex) {
                            //Log.e(TAG, Log.getStackTraceString(ex));
                        }
                        ItemNews item = new ItemNews(title, description, photo, dateString);
                        items.add(item);
                        //Log.e(TAG, jsonObject.toString());
                    }
                } catch (Exception ex) {
                    Log.e(TAG, Log.getStackTraceString(ex));
                    Toast.makeText(getActivity(), R.string.error_connection, Toast.LENGTH_LONG).show();
                } finally {
                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new RequestTask.OnRequestErrorListener() {
            @Override
            public void onRequestError() {
                Toast.makeText(getActivity(), R.string.error_connection, Toast.LENGTH_LONG).show();
            }
        }, 0);
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
        ItemNews item = items.get(position);
        Intent intent = new Intent(getActivity(), ActivityNews.class);
        intent.putExtra("title", item.getTitle());
        intent.putExtra("description", item.getDescription());
        intent.putExtra("photo", item.getPhoto());
        intent.putExtra("dateString", item.getDateString());
        startActivity(intent);
    }
}

