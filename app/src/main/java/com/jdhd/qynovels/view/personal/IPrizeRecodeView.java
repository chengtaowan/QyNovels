package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.PrizeRecodeBean;

public interface IPrizeRecodeView {
    void onRecodeSuccess(String str);
    void onRecodeError(String error);
}
