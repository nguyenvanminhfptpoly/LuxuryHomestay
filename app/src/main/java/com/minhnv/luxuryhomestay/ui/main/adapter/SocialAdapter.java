package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.ViewHolder> {
    private List<Luxury> luxuries;
    private Context context;

    public SocialAdapter(List<Luxury> luxuries, Context context) {
        this.luxuries = luxuries;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_social_home,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(luxuries.get(position));
    }

    @Override
    public int getItemCount() {
        return luxuries == null ? 0 : luxuries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

    private CircleImageView imgSocial;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSocial = itemView.findViewById(R.id.imgSocial);
        }
        public void bind(Luxury luxury){
            Picasso.get().load(luxury.getImage()).error(R.drawable.uploadfailed).into(imgSocial);
        }
    }
}
