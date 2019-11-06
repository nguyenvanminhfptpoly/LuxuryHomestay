package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Story;

public  class SocialViewHolder extends RecyclerView.ViewHolder{
    private CardView view;
    private ANImageView imgSocial;
    private SocialViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.cardSocial);
        imgSocial = itemView.findViewById(R.id.imgSocial);
    }
    public void bind(Story luxury,CallBack callBack){
        imgSocial.setImageUrl(luxury.getImage());
        imgSocial.setDefaultImageResId(R.drawable.img_home1);
        imgSocial.setErrorImageResId(R.drawable.uploadfailed);

        imgSocial.setOnClickListener(v -> callBack.viewStoriesDetail(getAdapterPosition()));
    }
    public interface CallBack{
        void viewStoriesDetail(int position);
    }

    public static SocialViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_social_home,parent,false);
        return new SocialViewHolder(view);
    }
}
