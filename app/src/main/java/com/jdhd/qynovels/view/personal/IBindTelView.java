package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.BindTelBean;
import com.jdhd.qynovels.module.personal.SexBean;

public interface IBindTelView {
    void onBindtelSuccess(BindTelBean bindTelBean);
    void onBindtelError(String error);
}
