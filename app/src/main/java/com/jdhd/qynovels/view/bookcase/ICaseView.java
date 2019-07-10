package com.jdhd.qynovels.view.bookcase;

import com.jdhd.qynovels.module.bookcase.CaseBean;

public interface ICaseView {
    void onSuccess(CaseBean caseBean);
    void onError(String error);
}
