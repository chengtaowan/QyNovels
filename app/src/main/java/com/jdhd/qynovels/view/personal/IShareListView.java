package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.ShareListBean;

public interface IShareListView {
    void onShareListSuccess(String str);
    void onShareListError(String error);
}
