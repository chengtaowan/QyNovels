package com.jdhd.qynovels.view.bookshop;

import com.jdhd.qynovels.module.bookshop.ClassBean;
import com.jdhd.qynovels.module.bookshop.ClassContentBean;

public interface IClassContentView {
    void onSuccess(ClassContentBean classContentBean);
    void onError(String error);
}
