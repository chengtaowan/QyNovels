package com.jdhd.qynovels.view.bookcase;

import com.jdhd.qynovels.module.HotSearchBean;

public interface IHotSearchView {
    void onSuccess(HotSearchBean hotSearchBean);
    void onError(String error);
}
