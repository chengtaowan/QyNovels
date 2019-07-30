package com.jdhd.qynovels.view.bookcase;

import com.jdhd.qynovels.module.bookcase.AddBookBean;
import com.jdhd.qynovels.module.bookcase.ReadEndBean;

public interface IReadEndView {
    void onEndSuccess(ReadEndBean readEndBean);
    void onEndError(String error);
}
