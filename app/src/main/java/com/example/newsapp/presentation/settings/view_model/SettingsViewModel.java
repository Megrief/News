package com.example.newsapp.presentation.settings.view_model;

import android.app.AlertDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.app.di.RepositoryModule;
import com.example.newsapp.domain.entity.ThemeItem;
import com.example.newsapp.domain.repository.DeleteDataByKeyRepository;
import com.example.newsapp.domain.repository.GetDataRepository;
import com.example.newsapp.domain.repository.StoreDataRepository;
import com.example.newspractice.R;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class SettingsViewModel extends ViewModel {

    private final GetDataRepository<Single<List<ThemeItem>>> getThemeItemList;
    private final StoreDataRepository<ThemeItem> storeThemeItem;
    private final DeleteDataByKeyRepository<ThemeItem> deleteThemeItem;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<List<ThemeItem>> screenState = new MutableLiveData<>();
    private final MutableLiveData<Boolean> needToRefresh = new MutableLiveData<>(false);

    private ThemeItem editingTheme = null;

    @Inject
    public SettingsViewModel(
            @Named(RepositoryModule.GET_THEME_ITEM_DB_REPO)
            GetDataRepository<Single<List<ThemeItem>>> getThemeItemList,
            @Named(RepositoryModule.STORE_THEME_ITEM_DB_REPO)
            StoreDataRepository<ThemeItem> storeThemeItem,
            @Named(RepositoryModule.DELETE_THEME_ITEM_DB_REPO)
            DeleteDataByKeyRepository<ThemeItem> deleteThemeItem
    ) {
        this.getThemeItemList = getThemeItemList;
        this.storeThemeItem = storeThemeItem;
        this.deleteThemeItem = deleteThemeItem;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
    }

    public LiveData<List<ThemeItem>> getScreenState() {
        return screenState;
    }

    public LiveData<Boolean> getNeedToRefresh() {
        return needToRefresh;
    }

    public void saveEditing(ThemeItem themeItem) {
        editingTheme = themeItem;
    }

    public void saveTheme(String value) {
        ThemeItem themeItem;
        if (editingTheme != null) {
            themeItem = new ThemeItem(value, editingTheme.id);
            editingTheme = null;
        } else {
            themeItem = new ThemeItem(value, 0);
        }
        compositeDisposable.add(
                storeThemeItem.store(themeItem)
                        .doOnComplete(this::refreshThemeList)
                        .subscribeOn(Schedulers.io())
                        .subscribe()
        );
        needToRefresh.postValue(true);
    }

    public boolean checkForRepeats(String themeValue) {
        List<ThemeItem> themes = screenState.getValue();
        if (themes != null) {
            return themes.stream().noneMatch(themeItem -> themeItem.theme.equals(themeValue));
        } else return true;
    }

    public void deleteTheme(ThemeItem theme) {
        compositeDisposable.add(
                deleteThemeItem.delete(theme)
                        .doOnComplete(this::refreshThemeList)
                        .subscribeOn(Schedulers.io())
                        .subscribe()
        );
        needToRefresh.postValue(true);
    }

    public void refreshThemeList() {
        Disposable refreshList = getThemeItemList.get()
                .doOnSuccess(screenState::postValue)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(refreshList);
    }

    public AlertDialog.Builder showDeleteDialog(Context context, @NonNull ThemeItem themeItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());
        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            deleteTheme(themeItem);
            dialog.dismiss();
        });
        builder.setMessage(themeItem.theme);
        builder.setTitle(R.string.you_sure);
        builder.create();
        return builder;
    }
}
