package com.example.newsapp.data.network;


import com.example.newsapp.data.network.response.SuccessResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {

    @GET("everything?sortBy=publishedAt&pageSize=50&apiKey=66e0e482d2ef4277b6aa7565b72be7a8")
    Single<SuccessResponse> getArticles(@Query("q") String query);
}
