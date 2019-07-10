package com.jdhd.qynovels.view.bookcase;

import com.jdhd.qynovels.module.bookcase.HotSearchBean;

public interface IHotSearchView {
    void onSuccess(HotSearchBean hotSearchBean);
    void onError(String error);
}
