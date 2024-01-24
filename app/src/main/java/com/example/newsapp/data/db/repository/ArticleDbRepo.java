package com.example.newsapp.data.db.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.newsapp.data.db.dao.ArticleDbDao;
import com.example.newsapp.data.db.dto.ArticleDb;
import com.example.newsapp.domain.entity.Article;
import com.example.newsapp.domain.repository.DeleteDataRepository;
import com.example.newsapp.domain.repository.GetDataRepository;
import com.example.newsapp.domain.repository.StoreDataRepository;
import com.example.newsapp.util.Mapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class ArticleDbRepo implements GetDataRepository<Single<List<Article>>>,
        StoreDataRepository<List<Article>>,
        DeleteDataRepository {

    private final ArticleDbDao dao;

    public ArticleDbRepo(
            ArticleDbDao dao
    ) {
        this.dao = dao;
    }

    @Override
    @NonNull
    public Single<List<Article>> get() {
        return dao.getArticles()
                .map(articlesDb -> {
                    List<Article> result = new ArrayList<>();
                    for (ArticleDb articleDb : articlesDb) {
                        result.add(Mapper.toArticle(articleDb));
                    }
                    return result;
                });
    }

    @Override
    @NonNull
    public Completable delete() {
        return Completable.fromRunnable(() -> {
            Log.wtf("AAAAA", "-> in deleteArticles");
            dao.getArticles().doOnSuccess(articlesDb -> {
                Log.wtf("AAAAA", "-> articlesDb getArticles onSuccess");
                for (ArticleDb articleDb : articlesDb) {
                    dao.delete(articleDb);
                }
            }).subscribe();
        });
    }

    @Override
    @NonNull
    public Completable store(@NonNull List<Article> articles) {
        return Completable.fromRunnable(() -> {
            for (Article article : articles) {
                dao.storeArticle(Mapper.toArticleDb(article));
            }
        });
    }
}
