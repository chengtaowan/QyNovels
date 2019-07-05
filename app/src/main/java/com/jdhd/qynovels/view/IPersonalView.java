package com.jdhd.qynovels.view;

import com.jdhd.qynovels.module.UserBean;

public interface IPersonalView {
    void onSuccess(UserBean userBean);
    void onError(String error);
}
