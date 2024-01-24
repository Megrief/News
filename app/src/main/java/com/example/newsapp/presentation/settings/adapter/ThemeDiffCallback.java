package com.example.newsapp.presentation.settings.adapter;

import androidx.recyclerview.widget.DiffUtil;


import com.example.newsapp.domain.entity.ThemeItem;

import java.util.List;

public class ThemeDiffCallback extends DiffUtil.Callback {

    private final List<ThemeItem> oldList;
    private final List<ThemeItem> newList;
    public ThemeDiffCallback(
            List<ThemeItem> oldList,
            List<ThemeItem> newList
    ) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition) == newList.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).theme.equals(newList.get(newItemPosition).theme);
    }
}
