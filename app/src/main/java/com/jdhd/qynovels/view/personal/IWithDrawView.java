package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.WithDrawBean;

public interface IWithDrawView {
    void onWithSuccess(WithDrawBean withDrawBean);
    void onWithError(String error);
}
