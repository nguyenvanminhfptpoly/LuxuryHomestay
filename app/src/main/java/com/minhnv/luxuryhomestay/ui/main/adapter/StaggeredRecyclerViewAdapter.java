package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Homestay;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.ViewHolder> {
    private List<Homestay> homeStay;
    private Context context;
    private RecyclerViewNavigator selected;

    public StaggeredRecyclerViewAdapter(List<Homestay> homestays, Context context,RecyclerViewNavigator selected) {
        this.homeStay = homestays;
        this.context = context;
        this.selected = selected;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_staggeredgridlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(homeStay != null){
            holder.bind(homeStay.get(position));
            holder.btnDetail.setOnClickListener(view -> {selected.onItemClickListener(position);});
        }
    }

    @Override
    public int getItemCount() {
        return homeStay == null ? 0 : homeStay.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
    private TextView tvName,tvAddress;
    private ANImageView imgView;
    private Button btnDetail;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress = itemView.findViewById(R.id.tvAddressHomeStayHot);
            tvName = itemView.findViewById(R.id.tvNameHomStayHot);
            imgView = itemView.findViewById(R.id.imgHomeStayHot);
            btnDetail = itemView.findViewById(R.id.btnDetailHomeStayHot);
        }
        void bind(Homestay homestay){
            imgView.setImageUrl(homestay.getImage());
            imgView.setErrorImageResId(R.drawable.uploadfailed);
            imgView.setDefaultImageResId(R.drawable.img_home1);
            tvName.setText(homestay.getName());
            tvAddress.setText(homestay.getAddress());
        }
    }
}
