package com.jdhd.qynovels.textconvert;

import android.graphics.RectF;

/**
 * 可见字符数据封装
 */
public class ShowChar {
    /**
     * 字符数据
     */
    public char charData;

    /**
     * 当前字符是否被选中
     */
    public boolean selected = false;

    /**
     * 字符宽度
     */
    public float charWidth = 0;

    /**
     * 当前字符在当前章节中的索引
     */
    public int indexInChapter;

    /**
     * 当前字符 上下左右四个位置
     */
    public RectF rectF;

    public void setCharData(char charData) {
        this.charData = charData;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setCharWidth(float charWidth) {
        this.charWidth = charWidth;
    }

    public void setIndexInChapter(int indexInChapter) {
        this.indexInChapter = indexInChapter;
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    @Override
    public String toString() {
        return "ShowChar{" +
                "charData=" + charData +
                ", selected=" + selected +
                ", charWidth=" + charWidth +
                ", indexInChapter=" + indexInChapter +
                ", rectF=" + rectF +
                '}';
    }
    //    public Point topLeftPosition = null;
//    public Point topRightPosition = null;
//    public Point bottomLeftPosition = null;
//    public Point bottomRightPosition = null;

}