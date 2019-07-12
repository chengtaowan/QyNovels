package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.NickNameBean;
import com.jdhd.qynovels.module.personal.SexBean;

public interface INickNameView {
    void onNickNameSuccess(NickNameBean nickNameBean);
    void onNickNameError(String error);
}
