package com.jdhd.qynovels.view.personal;

import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.VideoflBean;

public interface IVideoflView {
    void onVideoSuccess(VideoflBean videoflBean);
    void onVideoError(String error);
}
