package com.jdhd.qynovels.view.bookshop;

import com.jdhd.qynovels.module.bookshop.RankContentBean;

public interface IRankContentView {
    void onSuccess(RankContentBean rankContentBean);
    void onError(String error);
}
