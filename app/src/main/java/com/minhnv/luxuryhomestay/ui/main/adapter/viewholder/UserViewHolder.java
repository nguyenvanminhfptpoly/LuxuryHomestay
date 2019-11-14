package com.minhnv.luxuryhomestay.ui.main.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.R;
import com.minhnv.luxuryhomestay.data.local.preference.AppPreferenceHelper;
import com.minhnv.luxuryhomestay.data.model.UserInfo;

public class UserViewHolder extends RecyclerView.ViewHolder {
    private TextView tvUserName, tvName, tvId;


    private UserViewHolder(@NonNull View itemView) {
        super(itemView);
        tvUserName = itemView.findViewById(R.id.tvUserName);
        tvUserName = itemView.findViewById(R.id.tvNameUserInfo);
        tvName = itemView.findViewById(R.id.tvMyNameUserInfo);
        tvId = itemView.findViewById(R.id.tvIdUserInfo);
    }

    public void bind(UserInfo user,AppPreferenceHelper appPreferenceHelper) {
        tvUserName.setText(user.getAddress());
        tvName.setText(user.getPassword());
        String id = String.valueOf(user.getId());
        tvId.setText(id);
        appPreferenceHelper.setCurrentId(id);
        appPreferenceHelper.setCurrentAddress(user.getAddress());
    }

    public static UserViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }


}
