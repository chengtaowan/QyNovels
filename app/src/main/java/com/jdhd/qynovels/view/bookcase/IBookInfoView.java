package com.jdhd.qynovels.view.bookcase;

import com.jdhd.qynovels.module.bookcase.BookInfoBean;

public interface IBookInfoView {
    void onBookinfoSuccess(BookInfoBean bookInfoBean);
    void onBookinfoError(String error);
}
