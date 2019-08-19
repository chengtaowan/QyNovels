package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.SignInVideoBean;

public interface ISingInVideoView {
    void onVideoSuccess(SignInVideoBean signInVideoBean);
    void onVideoError(String error);
}
