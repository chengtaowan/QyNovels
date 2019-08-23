package com.jdhd.qynovels.readerview;

import com.jdhd.qynovels.entry.BookContentBean;

public interface IBookContentView {
    void onBookSuccess(BookContentBean bookContentBean);
    void onBookError(String error);
}
