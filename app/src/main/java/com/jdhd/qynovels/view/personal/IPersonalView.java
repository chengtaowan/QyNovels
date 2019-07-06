package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.UserBean;

public interface IPersonalView {
    void onSuccess(UserBean userBean);
    void onError(String error);
}
