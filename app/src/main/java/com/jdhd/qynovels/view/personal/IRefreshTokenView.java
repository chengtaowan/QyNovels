package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.RefreshTokenBean;

public interface IRefreshTokenView {
    void onSuccess(RefreshTokenBean refreshTokenBean);
    void onError(String error);
}
