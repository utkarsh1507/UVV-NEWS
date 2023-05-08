package com.mrj.quicknews;

import com.mrj.quicknews.models.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {


    void onFetchData(List<NewsHeadlines> list, String message);
    void onError(String message);


}
