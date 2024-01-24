package com.example.newsapp.app;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class NewsApp extends Application {

}



/*
  TODO
   - При запуске приложения показывать результаты последнего поиска, сохраненные в ДБ
   - Поиск новостей производить автоматически
          1. при запуске приложения
          2. при переходе на главный экран после изменения списка тем
          3. после применения настроек фильтрации.
   - Если по выбранной теме новостей не найдено, выводить сообщение: "По теме <name> новостей не найдено"
 */