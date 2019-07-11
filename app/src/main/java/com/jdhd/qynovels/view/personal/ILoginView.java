package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.CaptchaBean;
import com.jdhd.qynovels.module.personal.TokenBean;

public interface ILoginView {
    void onTokenSuccess(TokenBean tokenBean);
    void onTokenError(String error);
}
