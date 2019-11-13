package com.minhnv.luxuryhomestay.ui.main.social.story.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.androidnetworking.widget.ANImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Story;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class DetailStoryActivity extends BaseActivity<DetailStoryViewModel> implements DetailStoryNavigator, View.OnClickListener {
    private ANImageView imageView;
    private TextView textView;
    private SlidrInterface slide;
    private FloatingActionButton fabDownload;
    private String url,dirPath,fileName;

    public static Intent newIntent(Context context) {
        return new Intent(context, DetailStoryActivity.class);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_detail_story);
        super.onCreate(savedInstanceState);
        viewmodel = ViewModelProviders.of(this, factory).get(DetailStoryViewModel.class);
        viewmodel.setNavigator(this);
        initView();
        slide = Slidr.attach(this);
    }

    private void initView() {
        imageView = findViewById(R.id.imgDetailStory);
        textView = findViewById(R.id.tvDetailStory);
        initIntent();
        fabDownload =  findViewById(R.id.fabDownload);
        fabDownload.setOnClickListener(this);
    }

    private void initIntent() {
        Story story = (Story) getIntent().getSerializableExtra("detail_story");
        imageView.setErrorImageResId(R.drawable.uploadfailed);
        imageView.setDefaultImageResId(R.drawable.img_home1);
        assert story != null;
        imageView.setImageUrl(story.getImage());
        textView.setText(story.getTitle());
        url = story.getImage();
        fileName = story.getId() + ".jpg";
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabDownload) {
            viewmodel.DownLoad();
        }
    }

    @SuppressLint("SdCardPath")
    @Override
    public void ActionUserListenerDownLoadFile() {
        dirPath = "/sdcard/Luxury/Wallpaper";
        viewmodel.downLoad(url,dirPath,fileName,DetailStoryActivity.this);
    }

    @Override
    public void showProgress() {
        showLoading();
    }

    @Override
    public void hideProgress() {
        hideLoading();
    }


}
