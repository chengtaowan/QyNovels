package com.jdhd.qynovels.readerview;

import com.jdhd.qynovels.entry.BookListBean;

public interface IBookListView {
    void onSuccess(BookListBean bookListBean);
    void onError(String error);
}
