package com.example.newsapp.data.db.dto;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "theme_item_db_table")
public class ThemeItemDb {

    @ColumnInfo(name = "theme")
    @NonNull
    public final String theme;

    @PrimaryKey(autoGenerate = true)
    public final long id;

    public ThemeItemDb(
            @NonNull
            String theme,
            long id
    ) {
        this.theme = theme;
        this.id = id;
    }

}
