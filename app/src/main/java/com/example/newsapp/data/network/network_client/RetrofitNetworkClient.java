
package com.example.newsapp.data.network.network_client;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

import com.example.newsapp.data.network.NewsApiService;
import com.example.newsapp.data.network.response.ErrorResponse;
import com.example.newsapp.data.network.response.Response;
import com.example.newsapp.util.ErrorType;

import io.reactivex.rxjava3.core.Single;

public class RetrofitNetworkClient implements NetworkClient {

    private final NewsApiService newsApiService;
    private final ConnectivityManager connectivityManager;

    public RetrofitNetworkClient(
            NewsApiService newsApiService,
            ConnectivityManager connectivityManager
    ) {
        this.newsApiService = newsApiService;
        this.connectivityManager = connectivityManager;
    }

    @Override
    public Single<Response> doRequest(String query) {
        Log.wtf("AAAAA", "-> in networkClient");
        ErrorType errorType;
        if (!isConnected()) {
            errorType = ErrorType.NO_INTERNET;
        } else {
            try {
                return newsApiService.getArticles(query)
                        .map(successResponse -> successResponse);
            } catch (Exception Exception) {
                errorType = ErrorType.SERVER_ERROR;
            }
        }
        return Single.just(new ErrorResponse(errorType));
    }

    private boolean isConnected() {
        Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
        return networkCapabilities != null
                && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
    }
}