package com.example.newsapp.data.shared_prefs;

import android.content.SharedPreferences;

import com.example.newsapp.domain.repository.GetDataRepository;
import com.example.newsapp.domain.repository.StoreDataRepository;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class
FilterThemeRepo implements GetDataRepository<Single<Long>>, StoreDataRepository<Long> {
    private final SharedPreferences sharedPreferences;
    private final static String LAST_SELECTED_KEY = "LAST_SELECTED_KEY";

    public FilterThemeRepo(
            SharedPreferences sharedPreferences
    ) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Single<Long> get() {
        return Single.just(sharedPreferences.getLong(LAST_SELECTED_KEY, -1L));
    }

    @Override
    public Completable store(Long data) {
        return Completable.fromRunnable(() ->
                sharedPreferences.edit().putLong(LAST_SELECTED_KEY, data).apply()
        );
    }
}
