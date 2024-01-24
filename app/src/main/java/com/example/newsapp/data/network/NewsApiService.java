package com.example.newsapp.data.network;


import com.example.newsapp.data.network.response.SuccessResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {

    @GET("everything?sortBy=publishedAt&pageSize=50&apiKey=acadfab60e91425d9f2bcaa2ce1649ae")
    Single<SuccessResponse> getArticles(@Query("q") String query);
}
