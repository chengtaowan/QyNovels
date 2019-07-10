package com.jdhd.qynovels.view.bookshop;

import com.jdhd.qynovels.module.bookshop.ShopBean;

public interface IJxView {
    void onSuccess(ShopBean shopBean);
    void onError(String error);
}
