package com.example.newsapp.presentation.results.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.newsapp.domain.entity.Article;
import com.example.newsapp.domain.entity.DateContainer;
import com.example.newsapp.domain.entity.Entity;

public class
EntityDiffCallback extends DiffUtil.ItemCallback<Entity> {

    @Override
    public boolean areItemsTheSame(@NonNull Entity oldItem, @NonNull Entity newItem) {
        return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Entity oldItem, @NonNull Entity newItem) {
        if (oldItem instanceof Article && newItem instanceof Article) {
            return oldItem.equals(newItem);
        } else if (oldItem instanceof DateContainer && newItem instanceof DateContainer) {
            return ((DateContainer) oldItem).date.equals(((DateContainer) newItem).date);
        }
        return false;
    }
}
