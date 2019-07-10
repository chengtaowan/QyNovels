package com.jdhd.qynovels.view.bookcase;

import com.jdhd.qynovels.module.bookcase.BookListBean;

public interface IBookListView {
    void onSuccess(BookListBean bookListBean);
    void onError(String error);
}
