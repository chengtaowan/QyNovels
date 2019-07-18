package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.SignSetingBean;

public interface ISignSetingView {
    void onSetingSuccess(SignSetingBean signSetingBean);
    void onSetingError(String error);
}
