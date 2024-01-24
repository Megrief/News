package com.example.newsapp.data.db.repository;

import android.util.Log;

import com.example.newsapp.data.db.dao.ThemeItemDbDao;
import com.example.newsapp.data.db.dto.ThemeItemDb;
import com.example.newsapp.domain.entity.ThemeItem;
import com.example.newsapp.domain.repository.DeleteDataByKeyRepository;
import com.example.newsapp.domain.repository.DeleteDataRepository;
import com.example.newsapp.domain.repository.GetDataByKeyRepository;
import com.example.newsapp.domain.repository.GetDataRepository;
import com.example.newsapp.domain.repository.StoreDataRepository;
import com.example.newsapp.util.Mapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class ThemeItemDbRepo implements GetDataRepository<Single<List<ThemeItem>>>,
        StoreDataRepository<ThemeItem>,
        DeleteDataByKeyRepository<ThemeItem>,
        GetDataByKeyRepository<Long, Single<ThemeItem>> {

    private final ThemeItemDbDao dao;

    public ThemeItemDbRepo(
            ThemeItemDbDao dao
    ) {
        this.dao = dao;
    }

    @Override
    public Completable delete(ThemeItem theme) {
        return Completable.fromRunnable(() -> {
            dao.deleteTheme(Mapper.toThemeItemDb(theme));
        });
    }

    @Override
    public Single<List<ThemeItem>> get() {
        Log.wtf("AAAAA", "-> getThemes");
        return dao.getThemes().map(themeItemsDb -> {
            List<ThemeItem> results = new ArrayList<>();
            for (ThemeItemDb themeItemDb : themeItemsDb) {
                results.add(Mapper.toThemeItem(themeItemDb));
            }
            return results;
        });
    }

    @Override
    public Completable store(ThemeItem theme) {
        return Completable.fromRunnable(() -> {
            dao.storeThemeItem(Mapper.toThemeItemDb(theme));
        });
    }

    @Override
    public Single<ThemeItem> getByKey(Long key) {
        return dao.getTheme(key).map(Mapper::toThemeItem);
    }
}
