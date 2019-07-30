package com.jdhd.qynovels.view.bookcase;

import com.jdhd.qynovels.module.bookcase.AddBookBean;
import com.jdhd.qynovels.module.bookcase.BookGradeBean;

public interface IBookGradeView {
    void onGradeSuccess(BookGradeBean bookGradeBean);
    void onGradeError(String error);
}
