package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.DrawListBean;

public interface IDrawListView {
    void onSuccess(DrawListBean drawListBean);
    void onError(String error);
}
