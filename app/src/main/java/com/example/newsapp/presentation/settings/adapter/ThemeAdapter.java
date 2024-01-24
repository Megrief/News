package com.example.newsapp.presentation.settings.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.domain.entity.ThemeItem;
import com.example.newsapp.util.OnClick;
import com.example.newspractice.databinding.ThemeCardBinding;

import java.util.ArrayList;
import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeViewHolder> {

    List<ThemeItem> themeItemList = new ArrayList<>();
    private final OnClick<ThemeItem> onDeleteClick;
    private final OnClick<ThemeItem> onItemClick;

    public ThemeAdapter(
            OnClick<ThemeItem> onDeleteClick,
            OnClick<ThemeItem> onItemClick
    ) {
        this.onDeleteClick = onDeleteClick;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ThemeCardBinding binding = ThemeCardBinding.inflate(inflater, parent, false);
        return new ThemeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeViewHolder holder, int position) {
        ThemeItem item = themeItemList.get(position);
        holder.bind(item);
        holder.getDeleteView().setOnClickListener(view -> onDeleteClick.onClick(item));
        holder.itemView.setOnClickListener(view -> onItemClick.onClick(item));
    }

    @Override
    public int getItemCount() {
        return themeItemList.size();
    }

    public void setContent(List<ThemeItem> newList) {
        ThemeDiffCallback diffCallback = new ThemeDiffCallback(themeItemList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        themeItemList.clear();
        themeItemList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }
}
