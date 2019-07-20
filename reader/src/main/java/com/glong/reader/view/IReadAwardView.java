package com.glong.reader.view;

import com.glong.reader.entry.BookListBean;
import com.glong.reader.entry.ReadAwardBean;

public interface IReadAwardView {
    void onReadSuccess(ReadAwardBean readAwardBean);
    void onReadError(String error);
}
