package com.jdhd.qynovels.view.bookcase;

import com.jdhd.qynovels.module.CaseBean;

public interface ICaseView {
    void onSuccess(CaseBean caseBean);
    void onError(String error);
}
