package com.example.newsapp.presentation.results.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.newsapp.domain.entity.Article;
import com.example.newsapp.domain.entity.Entity;
import com.example.newsapp.util.OnClick;
import com.example.newspractice.databinding.NewsCardBinding;
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate;

import java.util.List;

public class ArticleAdapterDelegate extends AbsListItemAdapterDelegate<Article, Entity, ArticleViewHolder> {

    private final OnClick<String> onItemClick;
    public ArticleAdapterDelegate(OnClick<String> onItemClick ) {
        this.onItemClick = onItemClick;
    }

    @Override
    protected boolean isForViewType(@NonNull Entity item, @NonNull List<Entity> items, int position) {
        return item instanceof Article;
    }

    @NonNull
    @Override
    protected ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        NewsCardBinding binding = NewsCardBinding.inflate(inflater);
        return new ArticleViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull Article item, @NonNull ArticleViewHolder holder, @NonNull List<Object> payloads) {
        holder.bind(item);
        holder.itemView.setOnClickListener(view -> onItemClick.onClick(item.getUrl()));
    }
}
