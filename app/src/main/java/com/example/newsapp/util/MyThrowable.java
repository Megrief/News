package com.example.newsapp.util;

public class MyThrowable extends Throwable {
    public final ErrorType errorType;

    public MyThrowable(
            ErrorType errorType
    ) {
        this.errorType = errorType;
    }

}
