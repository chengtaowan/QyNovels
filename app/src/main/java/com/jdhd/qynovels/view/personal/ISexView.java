package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.CaptchaBean;
import com.jdhd.qynovels.module.personal.SexBean;

public interface ISexView {
    void onSexSuccess(SexBean sexBean);
    void onSexError(String error);
}
