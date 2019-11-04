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
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<Favorite> favorites;
    private Context context;
    private RecyclerViewNavigator navigator;

    public FavoriteAdapter(List<Favorite> favorites, Context context,RecyclerViewNavigator navigator) {
        this.favorites = favorites;
        this.context = context;
        this.navigator = navigator;
    }

    @Inject
    public FavoriteAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_favorite,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(favorites != null) {
            holder.bind(favorites.get(position));
            holder.imgFavorite.setOnClickListener(view -> navigator.onItemClickListener(position));
        }
    }

    @Override
    public int getItemCount() {
        return favorites == null ? 0 : favorites.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
    private ANImageView imgFavorite;
    private TextView tvName,tvAddress;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFavorite = itemView.findViewById(R.id.imgFavorite);
            tvAddress = itemView.findViewById(R.id.tvAddressFavorite);
            tvName = itemView.findViewById(R.id.tvNameFavorite);
        }
        void bind(Favorite favorite){
            imgFavorite.setDefaultImageResId(R.drawable.img_home1);
            imgFavorite.setErrorImageResId(R.drawable.uploadfailed);
            imgFavorite.setImageUrl(favorite.getImage());
            tvName.setText(favorite.getNamehomestay());
            tvAddress.setText(favorite.getAddress());
        }
    }

    public void set(List<Favorite> list){
        favorites.clear();
        favorites.addAll(list);
        notifyDataSetChanged();
    }
}
