package com.jdhd.qynovels.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jdhd.qynovels.R;

public class ChangeBarUtils {
//    public void initSystemBar(Boolean isLight, Activity activity) {
//        if (Build.VERSION.SDK_INT >= 21) {
//            //LAYOUT_FULLSCREEN 、LAYOUT_STABLE：让应用的主体内容占用系统状态栏的空间；//
//            // View decorView = getWindow().getDecorView();//
//            // int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//
//            // | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;//
//            // decorView.setSystemUiVisibility(option);//
//            // getWindow().setStatusBarColor(Color.TRANSPARENT);
//             Window window =activity.getWindow();
//            // 取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
//             window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//             window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            // 设置状态栏颜色
//             if (isLight) {
//              window.setStatusBarColor(activity.getResources().getColor(R.color.white));
//              } else {
//             window.setStatusBarColor(activity.getResources().getColor(R.color.common_title_bg));
//             }
//             //状态栏颜色接近于白色，文字图标变成黑色
//             View decor = window.getDecorView();
//             int ui = decor.getSystemUiVisibility();
//             if (isLight) {
//             //light --> a|=b的意思就是把a和b按位或然后赋值给a,   按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
//             ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//             } else {
//             //dark  --> &是位运算里面，与运算,  a&=b相当于 a = a&b,  ~非运算符
//             ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//             }            decor.setSystemUiVisibility(ui);
//             }
//             }
//
//        }
//    }
}