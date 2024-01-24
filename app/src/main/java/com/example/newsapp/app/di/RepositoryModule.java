package com.example.newsapp.app.di;

import android.content.SharedPreferences;

import com.example.newsapp.data.db.dao.ArticleDbDao;
import com.example.newsapp.data.db.dao.ThemeItemDbDao;
import com.example.newsapp.data.db.repository.ArticleDbRepo;
import com.example.newsapp.data.db.repository.ThemeItemDbRepo;
import com.example.newsapp.data.network.network_client.NetworkClient;
import com.example.newsapp.data.network.repository.GetDataFromApiRepo;
import com.example.newsapp.data.shared_prefs.FilterThemeRepo;
import com.example.newsapp.domain.entity.Article;
import com.example.newsapp.domain.entity.ThemeItem;
import com.example.newsapp.domain.repository.DeleteDataByKeyRepository;
import com.example.newsapp.domain.repository.DeleteDataRepository;
import com.example.newsapp.domain.repository.GetDataByKeyRepository;
import com.example.newsapp.domain.repository.GetDataRepository;
import com.example.newsapp.domain.repository.StoreDataRepository;
import com.example.newsapp.util.result.Result;

import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import io.reactivex.rxjava3.core.Single;

@Module
@InstallIn(ViewModelComponent.class)
public class RepositoryModule {
    // Constants
    public static final String GET_ARTICLES_DB_REPO = "GET_ARTICLES_DB_REPO";
    public static final String STORE_ARTICLES_DB_REPO = "STORE_ARTICLES_DB_REPO";
    public static final String DELETE_ARTICLES_DB_REPO = "DELETE_ARTICLES_DB_REPO";
    public static final String GET_THEME_ITEM_DB_REPO = "GET_THEME_ITEM_DB_REPO";
    public static final String STORE_THEME_ITEM_DB_REPO = "STORE_THEME_ITEM_DB_REPO";
    public static final String DELETE_THEME_ITEM_DB_REPO = "DELETE_THEME_ITEM_DB_REPO";
    public static final String GET_THEME_ITEM_BY_ID_DB_REPO = "GET_THEME_ITEM_BY_ID_DB_REPO";
    public static final String GET_ARTICLES_NETWORK_REPO = "GET_ARTICLES_NETWORK_REPO";
    public static final String GET_LAST_SELECTED_REPO = "GET_LAST_SELECTED_REPO";
    public static final String STORE_LAST_SELECTED_REPO = "STORE_LAST_SELECTED_REPO";

    // ArticleDbRepository
    @Provides
    public ArticleDbRepo articleDbRepo(ArticleDbDao dao) {
        return new ArticleDbRepo(dao);
    }

    @Provides
    @Named(GET_ARTICLES_DB_REPO)
    public GetDataRepository<Single<List<Article>>> getArticlesDbRepo(ArticleDbRepo articleDbRepo) {
        return articleDbRepo;
    }

    @Provides
    @Named(STORE_ARTICLES_DB_REPO)
    public StoreDataRepository<List<Article>> storeArticlesDbRepo(ArticleDbRepo articleDbRepo) {
        return articleDbRepo;
    }

    @Provides
    @Named(DELETE_ARTICLES_DB_REPO)
    public DeleteDataRepository deleteArticleDbRepo(ArticleDbRepo articleDbRepo) {
        return articleDbRepo;
    }

    // ThemeItemDbRepository
    @Provides
    public ThemeItemDbRepo themeItemDbRepo(ThemeItemDbDao dao) {
        return new ThemeItemDbRepo(dao);
    }

    @Provides
    @Named(GET_THEME_ITEM_DB_REPO)
    public GetDataRepository<Single<List<ThemeItem>>> getThemeItemDbRepo(ThemeItemDbRepo themeItemDbRepo) {
        return themeItemDbRepo;
    }

    @Provides
    @Named(GET_THEME_ITEM_BY_ID_DB_REPO)
    public GetDataByKeyRepository<Long, Single<ThemeItem>> getThemeItemByIdRepo(ThemeItemDbRepo themeItemDbRepo) {
        return themeItemDbRepo;
    }

    @Provides
    @Named(STORE_THEME_ITEM_DB_REPO)
    public StoreDataRepository<ThemeItem> storeThemeItemDbRepo(ThemeItemDbRepo themeItemDbRepo) {
        return themeItemDbRepo;
    }

    @Provides
    @Named(DELETE_THEME_ITEM_DB_REPO)
    public DeleteDataByKeyRepository<ThemeItem> deleteThemeItemDbRepo(ThemeItemDbRepo themeItemDbRepo) {
        return themeItemDbRepo;
    }

    // Network

    @Provides
    @Named(GET_ARTICLES_NETWORK_REPO)
    public GetDataByKeyRepository<String, Single<Result>> retrofitNetworkClient(
            NetworkClient networkClient) {
        return new GetDataFromApiRepo(networkClient);
    }

    // SharedPreferences

    @Provides
    public FilterThemeRepo lastSelectedThemeRepo(SharedPreferences sharedPreferences) {
        return new FilterThemeRepo(sharedPreferences);
    }

    @Provides
    @Named(GET_LAST_SELECTED_REPO)
    public GetDataRepository<Single<Long>> getLastSelectedRepo(FilterThemeRepo filterThemeRepo) {
        return filterThemeRepo;
    }

    @Provides
    @Named(STORE_LAST_SELECTED_REPO)
    public StoreDataRepository<Long> storeLastSelectedRepo(FilterThemeRepo filterThemeRepo) {
        return filterThemeRepo;
    }

}
