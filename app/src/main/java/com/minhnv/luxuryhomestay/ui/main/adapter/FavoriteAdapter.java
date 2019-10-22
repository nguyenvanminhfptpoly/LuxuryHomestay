package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Favorite;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<Favorite> favorites;
    private Context context;
    private RecyclerViewNavigator navigator;

    public FavoriteAdapter(List<Favorite> favorites, Context context,RecyclerViewNavigator navigator) {
        this.favorites = favorites;
        this.context = context;
        this.navigator = navigator;
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
    private ImageView imgFavorite;
    private TextView tvName,tvAddress;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFavorite = itemView.findViewById(R.id.imgFavorite);
            tvAddress = itemView.findViewById(R.id.tvAddressFavorite);
            tvName = itemView.findViewById(R.id.tvNameFavorite);
        }
        void bind(Favorite favorite){
            Picasso.get().load(favorite.getImage()).error(R.drawable.uploadfailed).into(imgFavorite);
            tvName.setText(favorite.getNamehomestay());
            tvAddress.setText(favorite.getAddress());
        }
    }
}
