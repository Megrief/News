package com.example.newsapp.util;

import androidx.annotation.NonNull;

import com.example.newsapp.data.db.dto.ArticleDb;
import com.example.newsapp.data.db.dto.ThemeItemDb;
import com.example.newsapp.data.network.dto.ArticleNetworkDto;
import com.example.newsapp.domain.entity.Article;
import com.example.newsapp.domain.entity.ThemeItem;

public class Mapper {

    @NonNull
    public static Article toArticle(@NonNull ArticleDb articleDb) {
        return new Article(
            articleDb.title,
            articleDb.source,
            articleDb.publishedAt,
            articleDb.description,
            articleDb.urlToImage,
            articleDb.url,
            articleDb.id
        );
    }

    @NonNull
    public static ArticleDb toArticleDb(@NonNull Article article) {
        return new ArticleDb(
                article.getId(),
                article.getTitle(),
                article.getDescription(),
                article.getSource(),
                article.getPublishedAt(),
                article.getUrl(),
                article.getUrlToImage()
        );
    }

    @NonNull
    public static ThemeItemDb toThemeItemDb(@NonNull ThemeItem themeItem) {
        return new ThemeItemDb(themeItem.theme, themeItem.id);
    }

    @NonNull
    public static ThemeItem toThemeItem(@NonNull ThemeItemDb themeItemDb) {
        return new ThemeItem(themeItemDb.theme, themeItemDb.id);
    }

    @NonNull
    public static Article toArticle(@NonNull ArticleNetworkDto dto) {
        return new Article(
                dto.getTitle(),
                dto.getSource(),
                formatDate(dto.getPublishedAt()),
                dto.getDescription(),
                dto.getUrlToImage(),
                dto.getUrl(),
                0
        );
    }

    @NonNull
    private static String formatDate(@NonNull String publishedAt) {
        int index = publishedAt.indexOf("T");
        return publishedAt.substring(0, index);
    }
}
