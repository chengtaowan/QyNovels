package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.VisitorBean;

public interface IVisitorView {
    void onVisitorSuccess(VisitorBean avatarBean);
    void onVisitorError(String error);
}
