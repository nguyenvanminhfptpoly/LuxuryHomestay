package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.content.Context;
import android.os.SystemClock;
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
import com.google.android.material.card.MaterialCardView;
import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.model.HomestayPrice;
import com.minhnv.luxuryhomestay.utils.CommonUtils;


public class HomeStayPriceViewHolder extends RecyclerView.ViewHolder {
    private ANImageView imgPicture;
    private TextView tvPrice, tvName, tvDetail, tvPriceAgo;
    private MaterialCardView cardView;
    private Context context;
    public long mLastClickTime = 0;
    private HomeStayPriceViewHolder(@NonNull View itemView) {
        super(itemView);
        imgPicture = itemView.findViewById(R.id.imgPriceAsc);
        tvPrice = itemView.findViewById(R.id.tvPriceAsc);
        tvName = itemView.findViewById(R.id.tvNamePriceAsc);
        tvDetail = itemView.findViewById(R.id.tvDetailPriceAsc);
        cardView = itemView.findViewById(R.id.itemTouchHomeStayPrice);
        tvPriceAgo = itemView.findViewById(R.id.tvPriceAgo);

    }

    public void bind(HomestayPrice homestay, UserActionListener callBack) {
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
        cardView.setOnClickListener(v ->{
            if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            callBack.onActionViewDetailHomeStayByUser(getAdapterPosition());});


    }

    public static HomeStayPriceViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homestays_recyclerview_price_asc,parent,false);
        return new HomeStayPriceViewHolder(view);
    }

    public interface UserActionListener {
        void onActionViewDetailHomeStayByUser(int position);
    }



}
