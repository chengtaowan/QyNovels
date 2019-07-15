package com.glong.reader.view;

import com.glong.reader.entry.BookListBean;

public interface IBookListView {
    void onSuccess(BookListBean bookListBean);
    void onError(String error);
}
