package com.minhnv.luxuryhomestay.ui.intro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

public class IntroductionActivity extends BaseActivity<IntroductionViewModel> implements IntroductionNavigator {
    private SlidrInterface slide;
    public static Intent newIntent(Context context) {
        return new Intent(context, IntroductionActivity.class);

    }

    @Override
    public int getLayoutId() {
        return R.layout.introduction_fragment;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        slide = Slidr.attach(this);
    }


}
