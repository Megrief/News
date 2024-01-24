package com.example.newsapp.data.db.dto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "article_db_table")
public class ArticleDb {
    @PrimaryKey(autoGenerate = true)
    public final long id;

    @ColumnInfo(name = "title")
    public final String title;

    @ColumnInfo(name = "description")
    public final String description;

    @ColumnInfo(name = "source")
    public final String source;

    @ColumnInfo(name = "publishedAt")
    public final String publishedAt;

    @ColumnInfo(name = "url")
    public final String url;

    @ColumnInfo(name = "urlToImage")
    public final String urlToImage;

    public ArticleDb(
            long id,
            String title,
            String description,
            String source,
            String publishedAt,
            String url,
            String urlToImage
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.source = source;
        this.publishedAt = publishedAt;
        this.url = url;
        this.urlToImage = urlToImage;
    }
}
