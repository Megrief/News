package com.example.newsapp.presentation.results.screen_state;

import com.example.newsapp.domain.entity.Article;

import java.util.List;

public class ContentState implements ResultsScreenState {
    public final List<Article> articles;

    public ContentState(List<Article> articles) {
        this.articles = articles;
    }

}
