package com.example.newsapp.presentation.results.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.newsapp.domain.entity.DateContainer;
import com.example.newsapp.domain.entity.Entity;
import com.example.newspractice.databinding.DateCardBinding;
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate;

import java.util.List;

public class DateAdapterDelegate extends AbsListItemAdapterDelegate<DateContainer, Entity, DateViewHolder> {

    public DateAdapterDelegate() { }

    @Override
    protected boolean isForViewType(@NonNull Entity item, @NonNull List<Entity> items, int position) {
        return item instanceof  DateContainer;
    }

    @NonNull
    @Override
    protected DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        DateCardBinding binding = DateCardBinding.inflate(inflater);
        return new DateViewHolder(binding);
    }

    @Override
    protected void onBindViewHolder(@NonNull DateContainer item, @NonNull DateViewHolder holder, @NonNull List<Object> payloads) {
        holder.bind(item);
    }
}
