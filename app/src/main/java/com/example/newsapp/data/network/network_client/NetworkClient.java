package com.example.newsapp.data.network.network_client;

import com.example.newsapp.data.network.response.Response;

import io.reactivex.rxjava3.core.Single;

public interface NetworkClient {

    Single<Response> doRequest(String query);
}
