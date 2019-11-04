package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
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
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

public class HomeStaysPriceAscAdapter extends RecyclerView.Adapter<HomeStaysPriceAscAdapter.ViewHolder> {
    private List<HomestayPrice> homeStays;
    private Context context;
    private RecyclerViewNavigator listener;
    public HomeStaysPriceAscAdapter(List<HomestayPrice> homestays, Context context, RecyclerViewNavigator listener) {
        this.homeStays = homestays;
        this.context = context;
        this.listener = listener;
    }
    @Inject
    public HomeStaysPriceAscAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_homestays_recyclerview_price_asc, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(homeStays != null) {
            holder.bind(homeStays.get(position));
            holder.btnDetail.setOnClickListener(view -> listener.onItemClickListener(position));
            holder.btnBooking.setOnClickListener(view -> listener.onItemClickDetailListener(position));
        }
    }

    @Override
    public int getItemCount() {
        return homeStays == null ? 0 : homeStays.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ANImageView imgPicture;
        TextView tvPrice, tvName, tvDetail, tvPriceAgo;
        Button btnDetail, btnBooking;
        Context context;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPicture = itemView.findViewById(R.id.imgPriceAsc);
            tvPrice = itemView.findViewById(R.id.tvPriceAsc);
            tvName = itemView.findViewById(R.id.tvNamePriceAsc);
            tvDetail = itemView.findViewById(R.id.tvDetailPriceAsc);
            btnBooking = itemView.findViewById(R.id.btnBookingAsc);
            btnDetail = itemView.findViewById(R.id.btnDetailAsc);
            tvPriceAgo = itemView.findViewById(R.id.tvPriceAgo);
        }

        void bind(HomestayPrice homestay) {
            double percent = (homestay.getPrice() / homestay.getPriceago()) * 100;
            imgPicture.setDefaultImageResId(R.drawable.img_home1);
            imgPicture.setErrorImageResId(R.drawable.uploadfailed);
            imgPicture.setImageUrl(homestay.getImage());
            tvName.setText(homestay.getTitle());
            tvDetail.setText(homestay.getAddress());
            tvPrice.setText(CommonUtils.FormatCredits(homestay.getPrice()));
            String priceAgo = (CommonUtils.FormatCredits(homestay.getPriceago())) + " giảm: " + Math.round(percent) + "%";
            SpannableStringBuilder builder = new SpannableStringBuilder(priceAgo);
            StrikethroughSpan span = new StrikethroughSpan();
            builder.setSpan(span,0,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            tvPriceAgo.setText(builder);


        }


    }

    public void set(List<HomestayPrice> list){
        homeStays.clear();
        homeStays.addAll(list);
        notifyDataSetChanged();
    }
}
