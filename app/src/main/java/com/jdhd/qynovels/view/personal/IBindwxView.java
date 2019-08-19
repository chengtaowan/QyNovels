package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.BindWxBean;

public interface IBindwxView {
    void onBindwxSuccess(BindWxBean bindWxBean);
    void onBindwxError(String error);
}
