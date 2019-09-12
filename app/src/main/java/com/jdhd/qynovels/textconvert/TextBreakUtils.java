package com.jdhd.qynovels.textconvert;

import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 测量工具
 */
public class TextBreakUtils {

    public static Set<String> sParagraph = new HashSet<>();//换行符
    public static Set<String> sRetract = new HashSet<>();// 缩进符
    public static int count=0;
    public static boolean flag = false;

    static {
//        sParagraph.add("\r\r\n");
//        sParagraph.add("\r\n");
//        sParagraph.add("\r\n\r\n");
//        sParagraph.add("\r");
        sParagraph.add("\n\n");
//        sParagraph.add("<br><br>");
//        sParagraph.add("<br>");
//        sParagraph.add("</p>");
        //sParagraph.add("\n ");
        //sParagraph.add(" ");

        sRetract.add("　");// 这个类似空格但并不是空格的缩进符，长度刚好是一个汉字的长度（推荐使用这个作为缩进符）
        sRetract.add(" ");// 以空格为段开头

        sRetract.add("\t\t");// 这个类似空格但并不是空格的缩进符，长度刚好是一个汉字的长度（推荐使用这个作为缩进符）
        sRetract.add("");// 以空格为段开头
    }

    public static boolean isStartWithRetract(String src) {
        if (src == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 截取一行的Char
     *
     * @param cs           字符串源
     * @param measureWidth 行测量的最大宽度
     * @param textPadding  字符间距
     * @param paint        测量的画笔
     * @return 如果cs为空或者长度为0，返回null
     * --------------------
     * TODO
     * --------------------
     */
    public static BreakResult breakText(int fromIndex, char[] cs, float measureWidth, float textPadding, Paint paint) {
//        if (cs == null || cs.length == 0) {
//            return null;
//        }
        BreakResult breakResult = new BreakResult();
        breakResult.showChars = new ArrayList<>();

        float width = 0;

        for (int i = 0, size = cs.length; i < size; i++) {
            String m="1";
            if(i==0){
                m=String.valueOf(cs[i]).trim();
            }
            else {
                m=String.valueOf(cs[i-1]).trim();
            }
            String measureStr = String.valueOf(cs[i]).trim();
            if(measureStr.equals("")&&m.equals("")){
                continue;
            }
            float charWidth=0;
            charWidth = paint.measureText(measureStr.trim());
            boolean isLineFeed = true;
            for (String paragraph : sParagraph) {
                if (paragraph != null && paragraph.length() > 0 && size - i >= paragraph.length()) {
                    char[] paragraphArray = paragraph.toCharArray();
                    int length = paragraphArray.length;
                    for (int j = 0; j < length; j++) {
                        if (paragraphArray[j] != cs[i + j]) {
                            isLineFeed = false;
                            break;
                        }
                    }
                    if (isLineFeed) {
                        breakResult.chartNums = i + length;
                        breakResult.isFullLine = true;
                        breakResult.endWithWrapMark = true;
                        count=0;
                        flag=false;
                        return breakResult;
                    }
                }
            }

            if (width <= measureWidth && (width + textPadding + charWidth) > measureWidth) {
                breakResult.chartNums = i;
                breakResult.isFullLine = true;
                breakResult.endWithWrapMark=false;
                count=1;
                if(!flag&&count==1){
                    flag=true;
                    count=0;
                }

                return breakResult;
            }

            ShowChar showChar2 = new ShowChar();
            showChar2.charData = cs[i];
            showChar2.charWidth = charWidth;
            showChar2.indexInChapter = fromIndex + i;
            breakResult.showChars.add(showChar2);
            width += charWidth + textPadding;
        }

        breakResult.chartNums = cs.length;
        breakResult.endWithWrapMark=true;
        breakResult.isFullLine=true;
        return breakResult;
    }

    public static BreakResult breakText(int fromIndex, String text, float measureWidth, float textPadding, Paint paint) {
        String newtext=text.replace("\r\n\r\n","\n\n");
        newtext=newtext.replace("\r\r\n","\n\n");
        newtext=newtext.replace("\r\n","\n\n");
        newtext=newtext.replace("\n","\n\n");
        count=0;
        return breakText(fromIndex, newtext.toCharArray(), measureWidth, textPadding, paint);
    }

    /**
     * 测量能显示多少行
     *
     * @param measureHeight 最大高度
     * @param lineSpace     行间距
     * @param paint         画笔
     * @return 行数
     */
    public static int measureLines(float measureHeight, float lineSpace, Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        float textHeight = fm.bottom - fm.top;
        float heightEveryLine = textHeight + lineSpace;
        return (int) (measureHeight / heightEveryLine);
    }

    /**
     * 截取字符串
     *
     * @param src          源字符串
     * @param measureWidth 文字展示长度
     * @param textPadding  文字padding
     * @param paint        Paint
     * @return
     */
    public static List<ShowLine> breakToLineList(String src, float measureWidth, float textPadding, Paint paint) {
        String textData = src;
        count=0;
        List<ShowLine> showLines = new ArrayList<>();

        int lineIndex = 0;
        while (textData.length() > 0) {
            BreakResult breakResult = breakText(src.length() - textData.length(), textData, measureWidth, textPadding, paint);
            ShowLine showLine = new ShowLine();
            if(breakResult.endWithWrapMark==false&&count==0){
                ShowChar showChar=new ShowChar();
                showChar.charData=" ".charAt(0);
                showChar.charWidth=60.0f;
                showChar.indexInChapter=0;
                ShowChar showChar1=new ShowChar();
                showChar1.charData=" ".charAt(0);
                showChar1.charWidth=60.0f;
                showChar1.indexInChapter=1;
                breakResult.showChars.add(0,showChar);
                breakResult.showChars.add(1,showChar1);
            }

            showLine.charsData = breakResult.showChars;
            showLine.isFullLine = breakResult.isFullLine;
            showLine.endWithWrapMark = breakResult.endWithWrapMark;

            if (showLine.getLineFirstIndexInChapter() != -1) {
                showLine.indexInChapter = lineIndex;
                showLines.add(showLine);
                lineIndex++;
            }
            //Log.e("text1111",textData+"----"+breakResult.chartNums);
            textData = textData.substring(breakResult.chartNums).trim();
        }

//        给每个字符添加当前章节中的索引（即所有字符串中的索引）
//         11.28 将添加索引的计算放在了BreakResult的获取时。
        int indexCharInChapter = 0;
        int indexLineInChapter = 0;
        for (ShowLine everyLine : showLines) {
            everyLine.indexInChapter = indexLineInChapter;
            indexLineInChapter++;
            for (ShowChar everyChar : everyLine.charsData) {
                everyChar.indexInChapter = indexCharInChapter;
                indexCharInChapter++;
            }
        }
        return showLines;
    }
}