package com.example.newsapp.data.network.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.newsapp.data.network.dto.ArticleNetworkDto;
import com.example.newsapp.data.network.network_client.NetworkClient;
import com.example.newsapp.data.network.response.ErrorResponse;
import com.example.newsapp.data.network.response.SuccessResponse;
import com.example.newsapp.domain.entity.Article;
import com.example.newsapp.domain.repository.GetDataByKeyRepository;
import com.example.newsapp.util.Mapper;
import com.example.newsapp.util.result.Error;
import com.example.newsapp.util.result.Result;
import com.example.newsapp.util.result.Success;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class GetDataFromApiRepo implements GetDataByKeyRepository<String, Single<Result>> {

    private final NetworkClient networkClient;

    public GetDataFromApiRepo(
            NetworkClient networkClient
    ) {
        this.networkClient = networkClient;
    }

    @Override
    public Single<Result> getByKey(String query) {
        Log.wtf("AAAAA", "-> in getByKey network");
        return networkClient.doRequest(query).map(response -> {
            Log.wtf("AAAAA", "-> in getByKey response");
            if (response instanceof ErrorResponse) {
                return new Error(((ErrorResponse) response).errorType);
            } else {
                assert response instanceof SuccessResponse;
                List<ArticleNetworkDto> dtoList = ((SuccessResponse) response).articles;
                return new Success(mapArticlesNetworkDto(dtoList));
            }
        });
    }

    @NonNull
    private List<Article> mapArticlesNetworkDto(@NonNull List<ArticleNetworkDto> dtos) {
        List<Article> result = new ArrayList<>();
        for (ArticleNetworkDto dto : dtos) {
            result.add(Mapper.toArticle(dto));
        }
        return result;
    }
}