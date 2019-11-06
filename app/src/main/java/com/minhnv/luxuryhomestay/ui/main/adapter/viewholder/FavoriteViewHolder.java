package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Favorite;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {
    private ANImageView imgFavorite;
    private TextView tvName,tvAddress;
    public FavoriteViewHolder(@NonNull View itemView) {
        super(itemView);
        imgFavorite = itemView.findViewById(R.id.imgFavorite);
        tvAddress = itemView.findViewById(R.id.tvAddressFavorite);
        tvName = itemView.findViewById(R.id.tvNameFavorite);
    }
    public void bind(Favorite favorite, CallBack callBack){
        imgFavorite.setDefaultImageResId(R.drawable.img_home1);
        imgFavorite.setErrorImageResId(R.drawable.uploadfailed);
        imgFavorite.setImageUrl(favorite.getImage());
        tvName.setText(favorite.getNamehomestay());
        tvAddress.setText(favorite.getAddress());
        imgFavorite.setOnClickListener(view -> {
            callBack.openSelected(getAdapterPosition());
        });
    }

    public interface CallBack{
        void openSelected(int position);
    }

    public static FavoriteViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_favorite,parent,false);
        return new FavoriteViewHolder(view);
    }
}
