package com.example.newsapp.presentation.results.adapter;

import androidx.recyclerview.widget.DiffUtil;


import com.example.newsapp.domain.entity.Article;

import java.util.List;

public class ArticlesDiffCallback extends DiffUtil.Callback {

    private final List<Article> oldList;
    private final List<Article> newList;

    public ArticlesDiffCallback(
            List<Article> oldList,
            List<Article> newList
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
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
