package com.jdhd.qynovels.readerview;

import com.jdhd.qynovels.entry.BookContentBean;

public interface BookContentCallBack {
    void onBookContentSuccess(BookContentBean.DataBean bookContentBean);
    void onBookError(String error);
}
