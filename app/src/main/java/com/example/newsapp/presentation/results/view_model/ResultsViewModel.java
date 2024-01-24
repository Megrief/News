package com.example.newsapp.presentation.results.view_model;

import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.app.di.RepositoryModule;
import com.example.newsapp.domain.entity.Article;
import com.example.newsapp.domain.entity.ThemeItem;
import com.example.newsapp.domain.repository.DeleteDataRepository;
import com.example.newsapp.domain.repository.GetDataByKeyRepository;
import com.example.newsapp.domain.repository.GetDataRepository;
import com.example.newsapp.domain.repository.StoreDataRepository;
import com.example.newsapp.presentation.results.screen_state.ContentState;
import com.example.newsapp.presentation.results.screen_state.ErrorState;
import com.example.newsapp.presentation.results.screen_state.ResultsScreenState;
import com.example.newsapp.util.ErrorType;
import com.example.newsapp.util.FinalError;
import com.example.newsapp.util.result.Error;
import com.example.newsapp.util.result.Result;
import com.example.newsapp.util.result.Success;
import com.example.newspractice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ResultsViewModel extends ViewModel {

    private final GetDataRepository<Single<List<Article>>> getArticlesRepo;
    private final DeleteDataRepository deleteArticlesRepo;
    private final StoreDataRepository<List<Article>> storeArticlesRepo;
    private final GetDataByKeyRepository<String, Single<Result>> getArticlesNetworkRepo;
    private final GetDataRepository<Single<List<ThemeItem>>> getThemeItemDbRepo;
    private final GetDataRepository<Single<Long>> getLastSelectedRepo;
    private final StoreDataRepository<Long> storeLastSelectedRepo;
    private final GetDataByKeyRepository<Long, Single<ThemeItem>> getThemeItemByKeyDbRepo;

    // Results ScreenState
    private final MutableLiveData<ResultsScreenState> screenState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> filtersState = new MutableLiveData<>();

    // CompositeDisposable
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public ResultsViewModel(
            @Named(RepositoryModule.GET_ARTICLES_DB_REPO)
            GetDataRepository<Single<List<Article>>> getArticlesRepo,
            @Named(RepositoryModule.DELETE_ARTICLES_DB_REPO)
            DeleteDataRepository deleteArticlesRepo,
            @Named(RepositoryModule.STORE_ARTICLES_DB_REPO)
            StoreDataRepository<List<Article>> storeArticlesRepo,
            @Named(RepositoryModule.GET_ARTICLES_NETWORK_REPO)
            GetDataByKeyRepository<String, Single<Result>> getArticlesNetworkRepo,
            @Named(RepositoryModule.GET_THEME_ITEM_DB_REPO)
            GetDataRepository<Single<List<ThemeItem>>> getThemeItemDbRepo,
            @Named(RepositoryModule.GET_LAST_SELECTED_REPO)
            GetDataRepository<Single<Long>> getLastSelectedRepo,
            @Named(RepositoryModule.STORE_LAST_SELECTED_REPO)
            StoreDataRepository<Long> storeLastSelectedRepo,
            @Named(RepositoryModule.GET_THEME_ITEM_BY_ID_DB_REPO)
            GetDataByKeyRepository<Long, Single<ThemeItem>> getThemeItemByKeyDbRepo
    ) {
        this.getArticlesRepo = getArticlesRepo;
        this.deleteArticlesRepo = deleteArticlesRepo;
        this.storeArticlesRepo = storeArticlesRepo;
        this.getArticlesNetworkRepo = getArticlesNetworkRepo;
        this.getThemeItemDbRepo = getThemeItemDbRepo;
        this.getLastSelectedRepo = getLastSelectedRepo;
        this.storeLastSelectedRepo = storeLastSelectedRepo;
        this.getThemeItemByKeyDbRepo = getThemeItemByKeyDbRepo;

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    public LiveData<ResultsScreenState> getScreenState() {
        return screenState;
    }
    public LiveData<Boolean> getFiltersState() {
        return filtersState;
    }

    public void showFiltersDialog(Context context) {
        getThemeItemDbRepo.get()
                .zipWith(getLastSelectedRepo.get(), (themeItemList, lastSelected) ->
                        provideAlertDialog(themeItemList, lastSelected, context)
                ).observeOn(AndroidSchedulers.mainThread())
                .flatMapCompletable(builder -> Completable.fromRunnable(builder::show))
                .subscribeOn(Schedulers.io())
                .subscribe();

    }

    public void refreshArticles() {
        Disposable refreshChain = getLastSelectedRepo.get()
                .flatMap(id -> {
                    filtersState.postValue(id != -1);
                    if (id == -1) {
                        return noFilters();
                    } else {
                        return withFilter();
                    }
                }).flatMapCompletable(storeArticlesRepo::store)
                .andThen(getArticlesRepo.get())
                .subscribeOn(Schedulers.io())
                .subscribe((articles, throwable) -> {
                    if (throwable != null) {
                        if (throwable instanceof FinalError) {
                            screenState.postValue(new ErrorState(((FinalError) throwable).errorType));
                        } else {
                            screenState.postValue(new ErrorState(ErrorType.SERVER_ERROR));
                        }
                    } else {
                        handleSuccess(articles);
                    }
                });
        compositeDisposable.add(refreshChain);
    }

    private Single<List<Article>> noFilters() {
        return deleteArticlesRepo.delete().andThen(getThemeItemDbRepo.get())
                .map(this::mapThemes)
                .flatMapObservable(Observable::fromIterable)
                .doOnError(this::handleError)
                .flatMapSingle(getArticlesNetworkRepo::getByKey)
                .collect(Collectors.toList())
                .map(this::handleResults)
                .doOnError(this::handleError);
    }

    private Single<List<Article>> withFilter() {
        return deleteArticlesRepo.delete().andThen(getLastSelectedRepo.get())
                .flatMap(getThemeItemByKeyDbRepo::getByKey)
                .flatMap(themeItem -> getArticlesNetworkRepo.getByKey(themeItem.theme))
                .map(result -> {
                    if (result instanceof Success) {
                        List<Article> articles = ((Success) result).results;
                        if (articles.isEmpty()) {
                            throw new FinalError(ErrorType.NO_RESULTS);
                        } else {
                            return articles;
                        }
                    } else {
                        assert result instanceof Error;
                        throw new FinalError(((Error) result).errorType);
                    }
                }).doOnError(this::handleError);
    }

    @NonNull
    private List<String> mapThemes(@NonNull List<ThemeItem> themes) {
        List<String> distinct = new ArrayList<>();
        for (ThemeItem theme : themes) {
            if (!distinct.contains(theme.theme)) {
                distinct.add(theme.theme);
            }
        }
        return distinct;
    }

    @NonNull
    private List<Article> handleResults(@NonNull List<Result> results) throws FinalError {
        List<Article> content = new ArrayList<>();
        for (Result result : results) {
            if (result instanceof Error) {
                ErrorType errorType = ((Error) result).errorType;
                if (errorType != ErrorType.NO_RESULTS) {
                    throw new FinalError(errorType);
                }
            } else {
                assert result instanceof Success;
                content.addAll(((Success) result).results);
            }
        }
        return content;
    }

    private void handleSuccess(@NonNull List<Article> articles) {
        if (articles.isEmpty()) {
            screenState.postValue(new ErrorState(ErrorType.NO_RESULTS));
        } else {
            screenState.postValue(new ContentState(articles));
        }
    }

    private void handleError(Throwable error) {
        if (error instanceof FinalError) {
            screenState.postValue(new ErrorState(((FinalError) error).errorType));
        } else {
            screenState.postValue(new ErrorState(ErrorType.SERVER_ERROR));
        }
    }

    private AlertDialog.Builder provideAlertDialog(@NonNull List<ThemeItem> themes, Long lastSelected, Context context) {
        CharSequence[] preparedArray = new String[themes.size()];
        int lastSelectedIndex = -1;
        for (int ind = 0; ind < themes.size(); ind += 1) {
            if (themes.get(ind).id == lastSelected) {
                lastSelectedIndex = ind;
            }
            String name = themes.get(ind).theme;
            preparedArray[ind] = name;
        }

        int finalLastSelectedIndex = lastSelectedIndex;
        return new AlertDialog.Builder(context)
                .setTitle(R.string.choose_theme)
                .setSingleChoiceItems(preparedArray, lastSelectedIndex, (dialog, which) -> {
                    if (which != finalLastSelectedIndex) {
                        ThemeItem selected = themes.get(which);
                        compositeDisposable.add(storeLastSelectedRepo
                                .store(selected.id)
                                .doOnComplete(this::refreshArticles)
                                .subscribeOn(Schedulers.io())
                                .subscribe());
                        filtersState.postValue(true);
                    }
                    dialog.dismiss();
                }).setNegativeButton(R.string.clear_filter, (dialog, which) -> {
                    boolean filtersOn = Boolean.TRUE.equals(filtersState.getValue());
                    if (filtersOn) {
                        compositeDisposable.add(storeLastSelectedRepo.store(-1L)
                                .doOnComplete(this::refreshArticles)
                                .subscribeOn(Schedulers.io())
                                .subscribe());
                        filtersState.postValue(false);
                    }
                    dialog.dismiss();
                });
    }
}