package com.example.newsapp.domain.entity;

public class Article implements Entity {

    final String title;

    final String source;

    final String publishedAt;

    final String description;

    final String urlToImage;

    final String url;

    long id;

    public Article(
        String title,
        String source,
        String publishedAt,
        String description,
        String urlToImage,
        String url,
        long id
    ) {
        this.title = title == null ? "" : title;
        this.source = source == null ? "" : source;
        this.publishedAt = publishedAt == null ? "" : publishedAt;
        this.description = description == null ? "" : description;
        this.urlToImage = urlToImage == null ? "" : urlToImage;
        this.url = url == null ? "" : url;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Article)) {
            return false;
        }
        Article article = (Article) o;
        return article.id == id
                && article.source.equals(source)
                && article.description.equals(description)
                && article.url.equals(url)
                && article.title.equals(title)
                && article.publishedAt.equals(publishedAt)
                && article.urlToImage.equals(urlToImage);
    }
}
