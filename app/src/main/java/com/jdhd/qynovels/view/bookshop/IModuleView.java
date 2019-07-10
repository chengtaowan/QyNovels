package com.jdhd.qynovels.view.bookshop;

import com.jdhd.qynovels.module.bookshop.ModuleBean;

public interface IModuleView {
    void onSuccess(ModuleBean moduleBean);
    void onError(String error);
}
