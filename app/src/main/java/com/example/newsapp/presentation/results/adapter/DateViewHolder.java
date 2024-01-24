package com.example.newsapp.presentation.results.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.domain.entity.DateContainer;
import com.example.newspractice.databinding.DateCardBinding;

public class DateViewHolder extends RecyclerView.ViewHolder {
    DateCardBinding binding;
    public DateViewHolder(@NonNull DateCardBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(@NonNull DateContainer date) {
        binding.getRoot().setText(date.date);
    }
}
