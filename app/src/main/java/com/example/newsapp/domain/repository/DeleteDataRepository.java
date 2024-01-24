package com.example.newsapp.domain.repository;

import io.reactivex.rxjava3.core.Completable;

public interface DeleteDataRepository {
    Completable delete();
}
