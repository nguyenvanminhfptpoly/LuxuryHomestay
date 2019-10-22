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

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.ViewHolder> {
    private List<HomestayPrice> homeStay;
    private Context context;
    private RecyclerViewNavigator selected;

    public StaggeredAdapter(List<HomestayPrice> homestays, Context context,RecyclerViewNavigator selected) {
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
    private ImageView imgView;
    private Button btnDetail;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress = itemView.findViewById(R.id.tvAddressHomeStayHot);
            tvName = itemView.findViewById(R.id.tvNameHomStayHot);
            imgView = itemView.findViewById(R.id.imgHomeStayHot);
            btnDetail = itemView.findViewById(R.id.btnDetailHomeStayHot);
        }
        void bind(HomestayPrice homestay){
            Picasso.get().load(homestay.getImage()).error(R.drawable.uploadfailed).into(imgView);
            tvName.setText(homestay.getTitle());
            tvAddress.setText(homestay.getAddress());
        }
    }
}
