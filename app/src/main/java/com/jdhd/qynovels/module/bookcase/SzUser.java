package com.jdhd.qynovels.module.bookcase;

import com.jdhd.qynovels.module.personal.UserBean;

public class SzUser {
    private UserBean userBean;

    public SzUser(UserBean userBean) {
        this.userBean = userBean;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
