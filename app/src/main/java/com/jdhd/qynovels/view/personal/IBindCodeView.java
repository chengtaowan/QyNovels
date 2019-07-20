package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.BindCodeBean;

public interface IBindCodeView {
    void onAvatarSuccess(BindCodeBean bindCodeBean);
    void onAvatarError(String error);
}
