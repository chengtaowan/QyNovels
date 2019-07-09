package com.jdhd.qynovels.view.bookshop;

import com.jdhd.qynovels.module.SearchContentBean;

public interface ISearchContentView {
    void onSuccess(SearchContentBean searchContentBean);
    void onError(String error);
}
