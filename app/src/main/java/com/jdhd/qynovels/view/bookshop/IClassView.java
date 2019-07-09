package com.jdhd.qynovels.view.bookshop;

import com.jdhd.qynovels.module.ClassBean;

public interface IClassView {
    void onSuccess(ClassBean classBean);
    void onError(String error);
}
