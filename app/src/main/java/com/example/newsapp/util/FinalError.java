package com.example.newsapp.util;

public class FinalError extends Throwable {
    public final ErrorType errorType;

    public FinalError(
            ErrorType errorType
    ) {
        this.errorType = errorType;
    }

}
