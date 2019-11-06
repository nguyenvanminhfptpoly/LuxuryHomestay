package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.widget.ANImageView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.utils.CommonUtils;


public class HomeStayPriceViewHolder extends RecyclerView.ViewHolder {
    private ANImageView imgPicture;
    private TextView tvPrice, tvName, tvDetail, tvPriceAgo;
    private Button btnDetail, btnBooking;
    private Context context;

    private HomeStayPriceViewHolder(@NonNull View itemView) {
        super(itemView);
        imgPicture = itemView.findViewById(R.id.imgPriceAsc);
        tvPrice = itemView.findViewById(R.id.tvPriceAsc);
        tvName = itemView.findViewById(R.id.tvNamePriceAsc);
        tvDetail = itemView.findViewById(R.id.tvDetailPriceAsc);
        btnBooking = itemView.findViewById(R.id.btnBookingAsc);
        btnDetail = itemView.findViewById(R.id.btnDetailAsc);
        tvPriceAgo = itemView.findViewById(R.id.tvPriceAgo);
    }

    public void bind(HomestayPrice homestay, HomeStayPriceViewHolder.CallBack callBack) {
        double percent = ((homestay.getPrice() - homestay.getPriceago()) / homestay.getPriceago()) * 100;
        imgPicture.setDefaultImageResId(R.drawable.img_home1);
        imgPicture.setErrorImageResId(R.drawable.uploadfailed);
        imgPicture.setImageUrl(homestay.getImage());
        tvName.setText(homestay.getTitle());
        tvDetail.setText(homestay.getAddress());
        tvPrice.setText(CommonUtils.FormatCredits(homestay.getPrice()));
        String priceAgo = (CommonUtils.FormatCredits(homestay.getPriceago())) + " " + Math.round(percent) + "%";
        SpannableStringBuilder builder = new SpannableStringBuilder(priceAgo);
        StrikethroughSpan span = new StrikethroughSpan();
        builder.setSpan(span,0,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvPriceAgo.setText(builder);
        btnBooking.setOnClickListener(v -> callBack.selectBooking(getAdapterPosition()));
        btnDetail.setOnClickListener(v -> callBack.viewDetail(getAdapterPosition()));
    }

    public static HomeStayPriceViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homestays_recyclerview_price_asc,parent,false);
        return new HomeStayPriceViewHolder(view);
    }

    public interface CallBack{
        void selectBooking(int position);
        void viewDetail(int position);
    }



}
