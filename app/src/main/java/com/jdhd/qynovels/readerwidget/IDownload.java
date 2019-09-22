package com.jdhd.qynovels.readerwidget;

import com.jdhd.qynovels.readerutil.Request;
import com.jdhd.qynovels.readerview.BookContentCallBack;

/**
 * Created by Garrett on 2018/11/17.
 * contact me krouky@outlook.com
 */
interface IDownload<K, T> {
    /**
     * 同步下载
     *
     * @param k 下载参数
     * @return 下载内容
     */
    void  downLoad(K k, BookContentCallBack bookContentCallBack);

    /**
     * 获取同步下载参数
     * 如果这个方法返回不为null，则下载使用该方法，忽略{@link #downLoad(Object)}方法，即使它被实现。
     * 如果该方法返回为null，则使用{@link #downLoad(Object)}方法下载。
     *
     * @param k 下载参数
     * @return 下载参数
     */
    Request requestParams(K k);
}
