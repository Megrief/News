package com.example.newsapp.presentation.results.adapter;

import com.example.newsapp.domain.entity.Entity;
import com.example.newsapp.util.OnClick;
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter;

public class EntityAdapter extends AsyncListDifferDelegationAdapter<Entity> {

    public EntityAdapter(
            EntityDiffCallback diffCallback,
            OnClick<String> onItemClick
    ) {
        super(diffCallback);
        delegatesManager.addDelegate(new ArticleAdapterDelegate(onItemClick))
                .addDelegate(new DateAdapterDelegate());
    }

}
