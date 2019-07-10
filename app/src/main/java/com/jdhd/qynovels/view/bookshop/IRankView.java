package com.jdhd.qynovels.view.bookshop;

import com.jdhd.qynovels.module.bookshop.RankBean;

public interface IRankView {
    void onSuccess(RankBean rankBean);
    void onError(String error);
}
