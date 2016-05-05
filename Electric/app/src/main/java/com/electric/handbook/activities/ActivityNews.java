package com.electric.handbook.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.electric.handbook.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class ActivityNews extends AppCompatActivity {
    DisplayImageOptions imageOptions;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    TextView tvName;
    TextView tvDate;
    TextView tvDescription;
    ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        imageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_placeholder)
                .showImageForEmptyUri(R.drawable.ic_placeholder)
                .showImageOnFail(R.drawable.ic_placeholder)
                .cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        initUI();
        initGlobal();

    }

    private void initUI() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
    }

    private void initGlobal() {
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String photo = getIntent().getStringExtra("photo");
        String dateString = getIntent().getStringExtra("dateString");
        tvName.setText(title);
        if (description != null) {
            tvDescription.setText(Html.fromHtml(description));
        }
        imageLoader.displayImage(photo, ivPhoto, imageOptions);
        tvDate.setText(dateString);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
