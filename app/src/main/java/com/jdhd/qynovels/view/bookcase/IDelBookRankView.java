package com.jdhd.qynovels.view.bookcase;

import com.jdhd.qynovels.module.bookcase.DelBookRackBean;

public interface IDelBookRankView {
    void onSuccess(DelBookRackBean delBookRackBean);
    void onAddError(String error);
}
