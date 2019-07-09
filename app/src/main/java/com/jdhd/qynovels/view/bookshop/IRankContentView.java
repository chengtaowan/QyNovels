package com.jdhd.qynovels.view.bookshop;

import com.jdhd.qynovels.module.RankContentBean;

public interface IRankContentView {
    void onSuccess(RankContentBean rankContentBean);
    void onError(String error);
}
