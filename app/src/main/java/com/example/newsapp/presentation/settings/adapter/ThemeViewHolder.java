package com.example.newsapp.presentation.settings.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.domain.entity.ThemeItem;
import com.example.newspractice.databinding.ThemeCardBinding;

public class ThemeViewHolder extends RecyclerView.ViewHolder {

    private final ThemeCardBinding binding;

    public ThemeViewHolder(@NonNull ThemeCardBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ThemeItem theme) {
        binding.themeName.setText(theme.theme);
    }

    public View getDeleteView() {
        return binding.delete;
    }
}
