package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.ShareImgBean;

public interface IShareImgView {
    void onShareSuccess(String string);
    void onShareError(String error);
}
