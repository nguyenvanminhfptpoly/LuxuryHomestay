package com.minhnv.luxuryhomestay.ui.main.favorite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Favorite;
import com.minhnv.luxuryhomestay.ui.base.BaseActivity;
import com.minhnv.luxuryhomestay.ui.main.adapter.FavoriteAdapter;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.FavoriteViewHolder;
import com.minhnv.luxuryhomestay.utils.AppLogger;
import com.minhnv.luxuryhomestay.utils.CustomToast;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FavoriteActivity extends BaseActivity<FavoriteViewModel> implements FavoriteNavigator, FavoriteViewHolder.UserActionListener {
    private static final String TAG = "FavoriteActivity";
    private List<Favorite> favorites;
    @Inject
    public FavoriteAdapter adapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, FavoriteActivity.class);

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemes);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_fragment);
        viewmodel = ViewModelProviders.of(this,factory).get(FavoriteViewModel.class);
        viewmodel.setNavigator(this);
        SlidrInterface slide = Slidr.attach(this);
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewFavorite);
        Toolbar toolbar = findViewById(R.id.toolbarFavorite);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Homestay yêu thích");
        toolbar.setNavigationOnClickListener(view ->
            onBackPressed()
        );

        viewmodel.ServerLoadFavorite();
        favorites = new ArrayList<>();
        adapter = new FavoriteAdapter(favorites, getApplicationContext());
        adapter.setUserAction(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        onNextList();


    }

    private void onNextList(){
        compositeDisposable.add(viewmodel.listBehaviorSubject.share()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(response -> adapter.set(response)));
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
        AppLogger.d(TAG, "onSuccess: ");
    }

    @Override
    public void onDeleteComplete() {
        hideLoading();
        adapter.notifyDataSetChanged();
        favorites.clear();
        viewmodel.ServerLoadFavorite();
        AppLogger.d(TAG,"success");
    }

    @Override
    public void onFailed() {
        hideLoading();
        CustomToast.makeText(this,getString(R.string.delete_error),Toast.LENGTH_LONG,CustomToast.ERROR).show();
    }

    @Override
    public void ServerLoadListFavorite() {
        try {
            int idUser = Integer.parseInt(appPreferenceHelper.getCurrentId());
            viewmodel.loadFavorite(idUser);
        }catch (NumberFormatException e){
            e.getMessage();
        }

    }

    @Override
    public void onActionAddFavoriteByUser(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(FavoriteActivity.this);
        builder.setTitle("Xóa homstay yêu thích này? ");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(false);
        Favorite favorite = favorites.get(position);
        Integer id = Integer.valueOf(favorite.getId());
        builder.setPositiveButton("Đồng ý", (dialogInterface, i) -> {
            favorites.clear();
            viewmodel.ServerLoadFavorite();
            viewmodel.deleteFavorite(id);
            showLoading();
            dialogInterface.dismiss();
        });
        builder.setNegativeButton("Không", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
