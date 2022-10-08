package com.example.newsapp;

import com.example.newsapp.models.Newsheadline;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<Newsheadline>list, String message);
    void onError(String message);
}
