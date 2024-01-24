package com.example.newsapp.util.result;

import com.example.newsapp.domain.entity.Article;

import java.util.List;

public class Success implements Result {
    public final List<Article> results;

    public Success(
            List<Article> results
    ) {
        this.results = results;
    }

}
