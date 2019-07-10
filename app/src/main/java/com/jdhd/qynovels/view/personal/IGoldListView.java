package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.GoldListBean;

public interface IGoldListView {
    void onSuccess(GoldListBean goldListBean);
    void onError(String error);
}
