package com.minhnv.luxuryhomestay.ui.main.social.post.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.social.list.SocialActivity;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class PostDetailActivity extends BaseActivity<PostDetailViewModel> implements PostDetailNavigator {

    private ANImageView imgPostDetail;
    private TextView tvAddressPostDetail;
    private TextView tvDetailPost;
    private SlidrInterface slide;

    public static Intent newIntent(Context context) {
        return new Intent(context, SocialActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        slide = Slidr.attach(this);
        initView();
    }

    private void initView() {
        imgPostDetail =  findViewById(R.id.imgPostDetail);
        tvAddressPostDetail =  findViewById(R.id.tvAddressPostDetail);
        tvDetailPost =  findViewById(R.id.tvDetailPost);
        initIntent();
    }

    private void initIntent(){
        Luxury luxury = (Luxury) getIntent().getSerializableExtra("luxury_detail");
        imgPostDetail.setDefaultImageResId(R.drawable.img_home1);
        imgPostDetail.setErrorImageResId(R.drawable.uploadfailed);
        assert luxury != null;
        imgPostDetail.setImageUrl(luxury.getImage());
        tvAddressPostDetail.setText(luxury.getAddress());
        tvDetailPost.setText(luxury.getDetail());

    }
}
