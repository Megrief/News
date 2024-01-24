package com.example.newsapp.domain.repository;

import io.reactivex.rxjava3.core.Single;

public interface GetDataByKeyRepository<K, T> {
    T getByKey(K key);
}
