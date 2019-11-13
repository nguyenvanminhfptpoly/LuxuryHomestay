package com.minhnv.luxuryhomestay.ui.main.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhnv.luxuryhomestay.data.local.preference.AppPreferenceHelper;
import com.minhnv.luxuryhomestay.data.model.UserInfo;
import com.minhnv.luxuryhomestay.ui.main.adapter.viewholder.UserViewHolder;

import java.util.List;

import javax.inject.Inject;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<UserInfo> users;
    private Context context;
    private AppPreferenceHelper appPreferenceHelper;

    public UserAdapter(List<UserInfo> users, Context context,AppPreferenceHelper appPreferenceHelper) {
        this.users = users;
        this.context = context;
        this.appPreferenceHelper = appPreferenceHelper;
    }

    @Inject
    public UserAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return UserViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof UserViewHolder){
            ((UserViewHolder) holder).bind(users.get(position),appPreferenceHelper);
        }
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    public void set(List<UserInfo> list){
        users.clear();
        users.addAll(list);
        notifyDataSetChanged();
    }
}
