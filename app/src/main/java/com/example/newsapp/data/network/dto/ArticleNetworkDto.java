package com.example.newsapp.data.network.dto;

import com.google.gson.annotations.SerializedName;

public class ArticleNetworkDto {
    @SerializedName("title")
    String title;

    @SerializedName("source")
    Source source;

    @SerializedName("publishedAt")
    String publishedAt;

    @SerializedName("description")
    String description;

    @SerializedName("urlToImage")
    String urlToImage;

    @SerializedName("url")
    String url;

    public ArticleNetworkDto(
            String title,
            Source source,
            String publishedAt,
            String description,
            String urlToImage,
            String url
    ) {
        this.title = title;
        this.source = source;
        this.publishedAt = publishedAt;
        this.description = description;
        this.urlToImage = urlToImage;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source.getName();
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getUrl() {
        return url;
    }
}
