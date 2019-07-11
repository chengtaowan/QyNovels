package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.DrawListBean;
import com.jdhd.qynovels.module.personal.DrawSetBean;

public interface IDrawSetView {
    void onSuccess(DrawSetBean drawSetBean);
    void onError(String error);
}
