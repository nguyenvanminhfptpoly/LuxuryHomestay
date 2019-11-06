package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.data.model.Favorite;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.FavoriteViewHolder;

import java.util.List;

import javax.inject.Inject;

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Favorite> favorites;
    private Context context;
    private FavoriteViewHolder.UserActionListener callBack;

    public FavoriteAdapter(List<Favorite> favorites, Context context) {
        this.favorites = favorites;
        this.context = context;
    }

    @Inject
    public FavoriteAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return FavoriteViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FavoriteViewHolder){
            ((FavoriteViewHolder) holder).bind(favorites.get(position),callBack);
        }
    }

    public void setUserAction(FavoriteViewHolder.UserActionListener callBack){
        this.callBack = callBack;
    }
    @Override
    public int getItemCount() {
        return favorites == null ? 0 : favorites.size();
    }


    public void set(List<Favorite> list){
        favorites.clear();
        favorites.addAll(list);
        notifyDataSetChanged();
    }

}
