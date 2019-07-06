package com.jdhd.qynovels.view.bookshop;

import com.jdhd.qynovels.module.ModuleBean;

public interface IModuleView {
    void onSuccess(ModuleBean moduleBean);
    void onError(String error);
}
