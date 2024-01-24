package com.example.newsapp.data.network.response;

import com.example.newsapp.data.network.dto.ArticleNetworkDto;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SuccessResponse implements Response {

    @SerializedName("articles")
    public final List<ArticleNetworkDto> articles;

    public SuccessResponse(
            List<ArticleNetworkDto> articles
    ) {
        this.articles = articles;
    }

}
