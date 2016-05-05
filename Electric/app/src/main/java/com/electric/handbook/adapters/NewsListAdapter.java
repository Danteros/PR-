package com.electric.handbook.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.electric.handbook.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by Anatoliy on 29.10.2015.
 */
public class NewsListAdapter extends BaseAdapter {
    static LayoutInflater inflater = null;
    Context context;
    DisplayImageOptions imageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    ArrayList<ItemNews> items;

    public NewsListAdapter(Context context, ArrayList<ItemNews> items) {
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        imageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_placeholder)
                .showImageForEmptyUri(R.drawable.ic_placeholder)
                .showImageOnFail(R.drawable.ic_placeholder)
                .cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
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
            v = inflater.inflate(R.layout.item_news, null);
        }
        ItemNews item = items.get(position);
        TextView tvName = (TextView) v.findViewById(R.id.tvName);
        tvName.setText(item.getTitle());
        TextView tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvDate.setText(item.getDateString());
        ImageView ivPhoto = (ImageView) v.findViewById(R.id.ivPhoto);
        imageLoader.displayImage(item.getPhoto(), ivPhoto, imageOptions);
        return v;
    }

    public void setItems(ArrayList<ItemNews> items) {
        this.items = items;
    }
}
