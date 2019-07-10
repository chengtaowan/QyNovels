package com.jdhd.qynovels.view.bookcase;

import com.jdhd.qynovels.module.bookcase.AddBookBean;
public interface IAddBookRankView {
    void onSuccess(AddBookBean addBookBean);
    void onAddError(String error);
}
