package com.example.newsapp.presentation.results.screen_state;

import com.example.newsapp.util.ErrorType;

public class ErrorState implements ResultsScreenState {
    public final ErrorType errorType;

    public ErrorState(
            ErrorType errorType
    ) {
        this.errorType = errorType;
    }
}
