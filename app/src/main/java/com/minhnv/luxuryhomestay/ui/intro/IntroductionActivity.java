package com.minhnv.luxuryhomestay.ui.intro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.UserInfo;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.LinearLayoutManagerWithSmoothScroller;
import com.minhnv.luxuryhomestay.ui.main.adapter.UserAdapter;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

public class IntroductionActivity extends BaseActivity<IntroductionViewModel> implements IntroductionNavigator {
    private static final String TAG = "IntroductionActivity";
    private SlidrInterface slide;
    private Toolbar toolbar4;
    private RecyclerView recyclerView;
    private List<UserInfo> userInfos;
    private UserAdapter userAdapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, IntroductionActivity.class);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemes);
        }
        super.onCreate(savedInstanceState);
        viewmodel = ViewModelProviders.of(this,factory).get(IntroductionViewModel.class);
        viewmodel.setNavigator(this);
        setContentView(R.layout.introduction_fragment);
        initView();
        slide = Slidr.attach(this);
    }

    private void initView() {
        toolbar4 =  findViewById(R.id.toolbar4);
        recyclerView =  findViewById(R.id.recyclerView);
        setUpRecyclerView();
    }
    private void setUpRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(this));
        userInfos = new ArrayList<>();
        viewmodel.ServerLoadUser();
        userAdapter = new UserAdapter(userInfos,getApplicationContext(),appPreferenceHelper);
        recyclerView.setAdapter(userAdapter);
    }

    @Override
    public void ANError(Throwable throwable) {
        AppLogger.d(TAG, "ANError: "+throwable);
    }

    @Override
    public void onSuccess() {
        AppLogger.d(TAG, "onSuccess: user Information");
    }

    @Override
    public void loadUser() {
        try {
            viewmodel.loadUser(Integer.parseInt(appPreferenceHelper.getCurrentPassword()));
        }catch (NumberFormatException e){
            e.getMessage();
        }
        compositeDisposable.add(viewmodel.listPublishSubject.share()
            .subscribe(data -> userAdapter.set(data)));
    }
}
