package com.minhnv.luxuryhomestay.ui.main.social.list;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.minhnv.luxuryhomestay.data.model.Story;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.LinearLayoutManagerWithSmoothScroller;
import com.minhnv.luxuryhomestay.ui.main.adapter.LuxuryAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.SocialAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.LuxuryViewHolder;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.SocialViewHolder;
import com.minhnv.luxuryhomestay.ui.main.social.post.PostLuxuryActivity;
import com.minhnv.luxuryhomestay.ui.main.social.post.detail.PostDetailActivity;
import com.minhnv.luxuryhomestay.ui.main.social.story.PostStoryActivity;
import com.minhnv.luxuryhomestay.ui.main.social.story.detail.DetailStoryActivity;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SocialActivity extends BaseActivity<SocialViewModel> implements SocialNavigator, LuxuryViewHolder.UserActionListener, SocialViewHolder.UserActionListener {
    private static final String TAG = "SocialActivity";
    private List<Luxury> luxuries;
    private List<Story> stories;
    @Inject
    public SocialAdapter socialAdapter;
    @Inject
    public LuxuryAdapter adapter;
    private Integer love = 0;
    private SlidrInterface slide;
    private Handler  handler = new Handler();
    private Runnable runnable;

    public static Intent newIntent(Context context) {
        return new Intent(context, SocialActivity.class);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemes);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_fragment);
        viewmodel = ViewModelProviders.of(this,factory).get(SocialViewModel.class);
        viewmodel.setNavigator(this);
        initView();
        setUpRecyclerView();
        setUpRecyclerViewLuxury();
    }

    private void initView(){
        Toolbar toolbar = findViewById(R.id.toolbarLuxury);

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Luxury");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        TextView tvNameUser = findViewById(R.id.tvNameUser);
        String nameUser = getString(R.string.title_header) + " " + (appPreferenceHelper.getCurrentPhoneNumber() == null ? appPreferenceHelper.getCurrentPassword() : appPreferenceHelper.getCurrentPhoneNumber());
        tvNameUser.setText(nameUser);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            luxuries.clear();
            viewmodel.ServerLoadListLuxury();
            stories.clear();
            viewmodel.ServerLoadListStory();
            new Handler().postDelayed(() -> {
                // Stop animation (This will be after 3 seconds)
                swipeRefreshLayout.setRefreshing(false);
            }, 1500);
        });

       fetchData();
    }
    private void setUpRecyclerViewLuxury(){
        viewmodel.ServerLoadListLuxury();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewLuxury);
        luxuries = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new LuxuryAdapter(luxuries, getApplicationContext());
        adapter.setUserAction(this);
        recyclerView.setAdapter(adapter);
    }
    private void setUpRecyclerView(){
        viewmodel.ServerLoadListStory();
        RecyclerView recyclerView= findViewById(R.id.recyclerViewHome);
        recyclerView.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(this,LinearLayoutManagerWithSmoothScroller.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);
        stories = new ArrayList<>();
        socialAdapter = new SocialAdapter(stories, getApplicationContext());
        socialAdapter.setUserAction(this);
        recyclerView.setAdapter(socialAdapter);
    }
    private void fetchData(){
        compositeDisposable.add(viewmodel.listPublishSubject.share()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(response ->
                    adapter.set(response)
                ));

        compositeDisposable.add(viewmodel.listBehaviorSubject.share()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(data ->
                    socialAdapter.set(data)
                ));
    }


    @Override
    public void HandlerError(Throwable throwable) {
        if(!isNetworkConnected()){
            backToLogin();
        }
        AppLogger.d(TAG, "HandlerError: "+throwable);
    }

    @Override
    public void onSuccess() {
        socialAdapter.notifyDataSetChanged();
        AppLogger.d(TAG, "onSuccess: ");
    }

    @Override
    public void loadList() {
        viewmodel.luxuryList();
    }

    @Override
    public void loadStory() {
        viewmodel.loadListStory();
    }


    @Override
    public void onFailed() {
        AppLogger.d(TAG, "onFailed: ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_social,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.postLuxury){
//            startActivity(PostLuxuryActivity.newIntent(getApplicationContext()));
            AlertDialog.Builder builder = new AlertDialog.Builder(SocialActivity.this);
            @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.dialog_select_post_luxury,null);
            LinearLayout ll_post_luxury = view.findViewById(R.id.ll_post_luxury);
            LinearLayout ll_post_story = view.findViewById(R.id.ll_post_story);
            builder.setView(view);
            Dialog dialog = builder.create();
            dialog.show();

            ll_post_luxury.setOnClickListener(ll -> startActivity(PostLuxuryActivity.newIntent(getApplicationContext())));
            ll_post_story.setOnClickListener(ll -> startActivity(PostStoryActivity.newIntent(getApplicationContext())));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActionShareByUser(int position) {
        Luxury luxury = luxuries.get(position);
        Intent senIntent = new Intent();
        senIntent.setAction(Intent.ACTION_SEND);
        senIntent.putExtra(Intent.EXTRA_SUBJECT,luxury.getAddress());
        senIntent.putExtra(Intent.EXTRA_TEXT,luxury.getImage());
        senIntent.setType("text/plain");
        startActivity(Intent.createChooser(senIntent,"Chia sẻ đến"));
    }

    @Override
    public void onActionViewDetailLuxuryByUser(int position) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        Intent intent = new Intent(SocialActivity.this , PostDetailActivity.class);
        intent.putExtra("luxury_detail", luxuries.get(position));
        startActivity(intent);
    }

    @Override
    public void onActionViewStoryDetailByUser(int position) {
        Intent intent = DetailStoryActivity.newIntent(getApplicationContext());
        intent.putExtra("detail_story",stories.get(position));
        startActivity(intent);
    }

//
}
