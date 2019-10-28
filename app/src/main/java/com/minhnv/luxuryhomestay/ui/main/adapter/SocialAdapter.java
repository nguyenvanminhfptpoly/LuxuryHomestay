package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Story;

import java.util.List;

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.ViewHolder> {
    private List<Story> stories;
    private Context context;
    private RecyclerViewNavigator navigator;

    public SocialAdapter(List<Story> luxuries, Context context,RecyclerViewNavigator navigator) {
        this.stories = luxuries;
        this.context = context;
        this.navigator = navigator;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_social_home,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(stories.get(position));
        holder.view.setOnClickListener(card -> {navigator.onItemClickListener(position);});
    }

    @Override
    public int getItemCount() {
        return stories == null ? 0 : stories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
    private CardView view;
    private ANImageView imgSocial;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.cardSocial);
            imgSocial = itemView.findViewById(R.id.imgSocial);
        }
        public void bind(Story luxury){
            imgSocial.setImageUrl(luxury.getImage());
            imgSocial.setDefaultImageResId(R.drawable.img_home1);
            imgSocial.setErrorImageResId(R.drawable.uploadfailed);

        }
    }
}
