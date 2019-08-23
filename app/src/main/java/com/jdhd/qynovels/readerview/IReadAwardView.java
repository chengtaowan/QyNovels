package com.jdhd.qynovels.readerview;

import com.jdhd.qynovels.entry.ReadAwardBean;

public interface IReadAwardView {
    void onReadSuccess(ReadAwardBean readAwardBean);
    void onReadError(String error);
}
