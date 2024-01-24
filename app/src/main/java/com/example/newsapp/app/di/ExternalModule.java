package com.example.newsapp.app.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.newsapp.data.db.AppDb;
import com.example.newsapp.data.db.dao.ArticleDbDao;
import com.example.newsapp.data.db.dao.ThemeItemDbDao;
import com.example.newsapp.data.network.NewsApiService;
import com.example.newsapp.data.network.network_client.NetworkClient;
import com.example.newsapp.data.network.network_client.RetrofitNetworkClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class ExternalModule {

    @Provides
    @Singleton
    public Retrofit retrofit() {
        final String baseUrl = "https://newsapi.org/v2/";
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public NewsApiService newsApiService(@NonNull Retrofit retrofit) {
        return retrofit.create(NewsApiService.class);
    }

    @Provides
    @Singleton
    public NetworkClient retrofitNetworkClient(NewsApiService newsApiService, @ApplicationContext Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return new RetrofitNetworkClient(newsApiService, connectivityManager);
    }

    @Provides
    @Singleton
    public AppDb appDb(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDb.class, "news_App_db").build();
    }

    @Provides
    @Singleton
    public ArticleDbDao articleDbDao(@NonNull AppDb appDb) {
        return appDb.articleDbDao();
    }

    @Provides
    @Singleton
    public ThemeItemDbDao themeItemDbDao(@NonNull AppDb appDb) {
        return appDb.themeItemDbDao();
    }

    @Provides
    @Singleton
    public SharedPreferences sharedPreferences(@NonNull @ApplicationContext Context context) {
        String newsPractice = "NEWS_APP";
        return context.getSharedPreferences(newsPractice, Context.MODE_PRIVATE);
    }

}
