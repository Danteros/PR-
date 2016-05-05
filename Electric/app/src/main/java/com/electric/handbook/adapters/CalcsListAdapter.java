package com.electric.handbook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.electric.handbook.R;

import java.util.ArrayList;

/**
 * Created by Anatoliy on 29.10.2015.
 */
public class CalcsListAdapter extends BaseAdapter {
    static LayoutInflater inflater = null;
    Context context;
    ArrayList<String> items;

    public CalcsListAdapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;
        if (convertView == null) {
            v = inflater.inflate(R.layout.item_calc, null);
        }
        TextView tvName = (TextView) v.findViewById(R.id.tvName);
        tvName.setText(items.get(position));
        return v;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }
}
