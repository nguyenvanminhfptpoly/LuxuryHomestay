package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Favorite;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.FavoriteViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Favorite> favorites;
    private Context context;
    private FavoriteViewHolder.CallBack callBack;

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

    public void setCallBack(FavoriteViewHolder.CallBack  callBack){
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

    public void remove(List<Favorite> list){
        int position = favorites.indexOf(list);
        favorites.remove(position);
        notifyItemRemoved(position);
    }


}
