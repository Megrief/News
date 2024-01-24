package com.example.newsapp.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.newsapp.data.db.dao.ArticleDbDao;
import com.example.newsapp.data.db.dao.ThemeItemDbDao;
import com.example.newsapp.data.db.dto.ArticleDb;
import com.example.newsapp.data.db.dto.ThemeItemDb;


@Database(
        entities = {
                ArticleDb.class,
                ThemeItemDb.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDb extends RoomDatabase {

    public abstract ArticleDbDao articleDbDao();

    public abstract ThemeItemDbDao themeItemDbDao();
}
