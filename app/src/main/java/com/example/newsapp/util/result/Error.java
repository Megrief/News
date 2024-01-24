package com.example.newsapp.util.result;

import com.example.newsapp.util.ErrorType;

public class Error implements Result {
    public final ErrorType errorType;

    public Error(
            ErrorType errorType
    ) {
        this.errorType = errorType;
    }

}
