package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.ImageDetail;

public class ImageDetailViewHolder extends RecyclerView.ViewHolder {
    private ANImageView anImageView;
    public ImageDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        anImageView = itemView.findViewById(R.id.imgDetailHomeStay);
    }
    public void bind(ImageDetail imageDetail){
        anImageView.setDefaultImageResId(R.drawable.img_home1);
        anImageView.setErrorImageResId(R.drawable.uploadfailed);
        anImageView.setImageUrl(imageDetail.getImage());
    }

    public static ImageDetailViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_image_homestay,parent,false);
        return new ImageDetailViewHolder(view);
    }
}
