package com.example.newsapp.presentation.results.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.domain.entity.Article;
import com.example.newsapp.util.OnClick;
import com.example.newspractice.databinding.NewsCardBinding;

import java.util.ArrayList;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    OnClick<String> onItemClick;

    List<Article> contentList = new ArrayList<>();

    public ArticlesAdapter(
        OnClick<String> onItemClick
    ) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NewsCardBinding binding = NewsCardBinding.inflate(inflater);
        return new ArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article item = contentList.get(position);
        holder.bind(item);
        holder.itemView.setOnClickListener(view -> onItemClick.onClick(item.getUrl()));
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public void setContent(List<Article> articles) {
        ArticlesDiffCallback diffCallback = new ArticlesDiffCallback(contentList, articles);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        contentList.clear();
        contentList.addAll(articles);
        diffResult.dispatchUpdatesTo(this);
    }
}
