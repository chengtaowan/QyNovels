package com.jdhd.qynovels.view.bookcase;

import com.jdhd.qynovels.module.bookcase.BookInfoBean;

public interface IBookInfoView {
    void onSuccess(BookInfoBean bookInfoBean);
    void onError(String error);
}
