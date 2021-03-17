package com.example.marmoushadminapp;

import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;

public class NewsFragmentPresenter implements NewsFragmentPresenterIMPL {
    NewsFragment_IMPL fragment;

    public NewsFragmentPresenter(NewsFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void getNews(){
        Backendless.Data.of(News.class).find(new AsyncCallback<List<News>>() {

            @Override
            public void handleResponse(List<News> response) {
                fragment.showNews(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    @Override
    public void removenews(News news) {


        Backendless.Data.of(News.class).remove(news, new AsyncCallback<Long>() {
            @Override
            public void handleResponse(Long response) {

                fragment.removedNews(news);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}
