package com.minhnv.luxuryhomestay.ui.intro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemes);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_fragment);
        slide = Slidr.attach(this);
    }
}
