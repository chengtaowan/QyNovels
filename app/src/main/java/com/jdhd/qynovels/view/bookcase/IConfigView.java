package com.jdhd.qynovels.view.bookcase;

import com.jdhd.qynovels.module.bookcase.AddBookBean;
import com.jdhd.qynovels.module.bookcase.ConfigBean;

public interface IConfigView {
    void onConfigSuccess(ConfigBean configBean);
    void onConfigError(String error);
}
