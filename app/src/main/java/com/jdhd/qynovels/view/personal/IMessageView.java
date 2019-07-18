package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.MessageBean;

public interface IMessageView {
    void onMessageSuccess(MessageBean messageBean);
    void onMessageError(String error);
}
