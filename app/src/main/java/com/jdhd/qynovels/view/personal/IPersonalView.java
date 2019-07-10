package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.UserBean;

public interface IPersonalView {
    void onSuccess(UserBean userBean);
    void onError(String error);
}
