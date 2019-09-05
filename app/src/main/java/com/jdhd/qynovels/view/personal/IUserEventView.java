package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.UserEventBean;

public interface IUserEventView {
    void onUserEventSuccess(UserEventBean userEventBean);
    void onUserEventError(String error);
}
