package com.example.newsapp.data.network.response;

import com.example.newsapp.util.ErrorType;

public class ErrorResponse implements Response {
    public ErrorType errorType;

    public ErrorResponse(
            ErrorType errorType
    ) {
        this.errorType = errorType;
    }

}
