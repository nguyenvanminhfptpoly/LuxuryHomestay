package com.minhnv.luxuryhomestay.ui.main.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.minhnv.luxuryhomestay.data.model.Luxury;

import java.util.List;

public class LuxuryDiffCallBack extends DiffUtil.Callback {
    private List<Luxury> oldLuxuries;
    private List<Luxury> newLuxuries;

    public LuxuryDiffCallBack(List<Luxury> oldLuxury, List<Luxury> newLuxury) {
        this.oldLuxuries = oldLuxury;
        this.newLuxuries = newLuxury;
    }

    @Override
    public int getOldListSize() {
        return oldLuxuries == null ? 0 : oldLuxuries.size();
    }

    @Override
    public int getNewListSize() {
        return newLuxuries == null ? 0 : newLuxuries.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldLuxuries.get(oldItemPosition).getId().equals(newLuxuries.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Luxury oldLuxury = oldLuxuries.get(oldItemPosition);
        final Luxury newLuxury = newLuxuries.get(newItemPosition);
        return oldLuxury.getUsername().equals(newLuxury.getUsername());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
