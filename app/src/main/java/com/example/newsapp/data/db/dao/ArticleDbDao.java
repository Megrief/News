package com.example.newsapp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.newsapp.data.db.dto.ArticleDb;

import java.util.List;

import io.reactivex.rxjava3.core.Single;


@Dao
public interface ArticleDbDao {

    @Query("SELECT * FROM article_db_table ORDER BY publishedAt DESC")
    Single<List<ArticleDb>> getArticles();

    @Insert(entity = ArticleDb.class, onConflict = OnConflictStrategy.REPLACE)
    void storeArticle(ArticleDb articleDb);

    @Delete(entity = ArticleDb.class)
    void delete(ArticleDb articleDb);
}