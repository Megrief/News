package com.example.newsapp.presentation.results.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.domain.entity.Article;
import com.example.newspractice.R;
import com.example.newspractice.databinding.NewsCardBinding;

public class ArticleViewHolder extends RecyclerView.ViewHolder {

    NewsCardBinding binding;
    public ArticleViewHolder(@NonNull NewsCardBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Article item) {
        if (item.getUrlToImage().isEmpty()) {
            binding.articleImage.setVisibility(View.GONE);
        } else {
            Glide.with(binding.getRoot())
                    .load(item.getUrlToImage())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .centerCrop()
                    .into(binding.articleImage);
        }

        binding.description.setText(item.getDescription());
        binding.publishedAt.setText(item.getPublishedAt());
        binding.source.setText(item.getSource());
        binding.title.setText(item.getTitle());
    }
}
