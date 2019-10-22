package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.Luxury;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LuxuryAdapter extends RecyclerView.Adapter<LuxuryAdapter.ViewHolder>{
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    private List<Luxury> luxuries;
    private Context context;
    private RecyclerViewNavigator luxury;
    public long mLastClickTime = 0;

    public LuxuryAdapter(List<Luxury> luxuries, Context context, RecyclerViewNavigator luxury) {
        this.luxuries = luxuries;
        this.context = context;
        this.luxury = luxury;
    }

    @NonNull
    @Override
    public LuxuryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview_luxury, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (luxuries != null) {
            holder.bind(luxuries.get(position));
            holder.btnDetailLuxury.setOnClickListener(view -> {
                luxury.onItemClickListener(position);
            });
            holder.imgFavorite.setOnClickListener(view -> {
                luxury.onItemClickDetailListener(position);
            });
        }
    }

    @Override
    public int getItemCount() {
        return luxuries == null ? 0 : luxuries.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvAddress;
        private TextView tvDetail,tvCountLove;
        private ANImageView imgLuxury;
        ImageView imgFavorite;
        private Button btnDetailLuxury;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameUser);
            tvAddress = itemView.findViewById(R.id.tvAddressUser);
            tvDetail = itemView.findViewById(R.id.tvDetailLuxury);
            imgFavorite = itemView.findViewById(R.id.imgFavoriteLuxury);
            imgLuxury = itemView.findViewById(R.id.imgLuxury);
            btnDetailLuxury = itemView.findViewById(R.id.btnDetailLuxury);
        }

        void bind(Luxury luxury) {
            tvName.setText(luxury.getUsername());
            tvAddress.setText(luxury.getAddress());
            tvDetail.setText(luxury.getDetail());
            imgLuxury.setDefaultImageResId(R.drawable.img_home1);
            imgLuxury.setErrorImageResId(R.drawable.uploadfailed);
            imgLuxury.setImageUrl(luxury.getImage());
        }
    }

    public void updateListLuxury(List<Luxury> list){
        LuxuryDiffCallBack callBack = new LuxuryDiffCallBack(this.luxuries,list);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callBack);

        this.luxuries.clear();
        this.luxuries.addAll(list);
        diffResult.dispatchUpdatesTo(this);
    }

}
