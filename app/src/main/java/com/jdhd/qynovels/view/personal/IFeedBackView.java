package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.FeedBackBean;

public interface IFeedBackView {
    void onBackSuccess(FeedBackBean feedBackBean);
    void onBackError(String error);
}
