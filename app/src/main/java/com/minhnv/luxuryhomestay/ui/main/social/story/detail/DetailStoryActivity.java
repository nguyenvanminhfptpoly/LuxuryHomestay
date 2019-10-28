package com.minhnv.luxuryhomestay.ui.main.social.story.detail;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Story;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.social.list.SocialActivity;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class DetailStoryActivity extends BaseActivity<DetailStoryViewModel> implements DetailStoryNavigator {
    private ANImageView imageView;
    private TextView textView;
    private SlidrInterface  slide;
    public static Intent newIntent(Context context) {
        return new Intent(context, DetailStoryActivity.class);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail_story;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        viewmodel = ViewModelProviders.of(this,factory).get(DetailStoryViewModel.class);
        viewmodel.setNavigator(this);
        initView();
        slide = Slidr.attach(this);
    }

    private void initView() {
        imageView = findViewById(R.id.imgDetailStory);
        textView = findViewById(R.id.tvDetailStory);
        initIntent();
    }
    private void initIntent(){
        Story story = (Story) getIntent().getSerializableExtra("detail_story");
        imageView.setErrorImageResId(R.drawable.uploadfailed);
        imageView.setDefaultImageResId(R.drawable.img_home1);
        assert story != null;
        imageView.setImageUrl(story.getImage());
        textView.setText(story.getTitle());
    }
}
