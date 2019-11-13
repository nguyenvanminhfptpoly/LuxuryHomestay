package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.data.model.ImageDetail;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.ImageDetailViewHolder;

import java.util.List;

public class ImageDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ImageDetail> imageDetails;
    private Context context;

    public ImageDetailAdapter(List<ImageDetail> imageDetails, Context context) {
        this.imageDetails = imageDetails;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ImageDetailViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ImageDetailViewHolder){
            ((ImageDetailViewHolder) holder).bind(imageDetails.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return imageDetails == null ? 0 : imageDetails.size();
    }

    public void set(List<ImageDetail> list){
        imageDetails.clear();
        imageDetails.addAll(list);
        notifyDataSetChanged();
    }
}
