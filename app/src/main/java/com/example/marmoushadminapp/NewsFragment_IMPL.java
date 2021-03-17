package com.example.marmoushadminapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public interface NewsFragment_IMPL {
    void onCreate(Bundle savedInstanceState);

    View onCreateView(LayoutInflater inflater, ViewGroup container,
                      Bundle savedInstanceState);

    void showNews(List<News> response);

    void removedNews(News news);
}
