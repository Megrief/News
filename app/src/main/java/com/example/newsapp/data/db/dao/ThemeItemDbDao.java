package com.example.newsapp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.newsapp.data.db.dto.ThemeItemDb;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface ThemeItemDbDao {

    @Query("SELECT * FROM theme_item_db_table")
    Single<List<ThemeItemDb>> getThemes();

    @Query("SELECT * FROM theme_item_db_table WHERE id = :id")
    Single<ThemeItemDb> getTheme(long id);

    @Delete(entity = ThemeItemDb.class)
    void deleteTheme(ThemeItemDb themeItemDb);

    @Insert(entity = ThemeItemDb.class, onConflict = OnConflictStrategy.REPLACE)
    void storeThemeItem(ThemeItemDb themeItemDb);
}
