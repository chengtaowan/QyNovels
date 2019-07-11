package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.CaptchaBean;
import com.jdhd.qynovels.module.personal.DrawListBean;

public interface ICaptchaView {
    void onCaptchaSuccess(CaptchaBean captchaBean);
    void onCaptchaError(String error);
}
