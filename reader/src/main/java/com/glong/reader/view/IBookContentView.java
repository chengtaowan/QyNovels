package com.glong.reader.view;

import com.glong.reader.entry.BookContentBean;
import com.glong.reader.entry.BookListBean;

public interface IBookContentView {
    void onBookSuccess(BookContentBean bookContentBean);
    void onBookError(String error);
}
