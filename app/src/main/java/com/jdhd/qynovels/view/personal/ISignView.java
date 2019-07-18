package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.SignBean;
import com.jdhd.qynovels.module.personal.SignSetingBean;

public interface ISignView {
    void onSignSuccess(SignBean signBean);
    void onSignError(String error);
}
