package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.AvatarBean;

public interface IAvatarView {
    void onAvatarSuccess(AvatarBean avatarBean);
    void onAvatarError(String error);
}
