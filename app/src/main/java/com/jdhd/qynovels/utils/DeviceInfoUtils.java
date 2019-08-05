package com.jdhd.qynovels.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.google.gson.Gson;
import com.jdhd.qynovels.app.MyApp;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DeviceInfoUtils {
    /**
     * 获取手机品牌
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机Android 版本
     *
     * @return
     */
    public static String getDeviceAndroidVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取系统平台
     */
    public static int getOs(){
        return 10;
    }

    /**
     * 获取设备的唯一标识， 需要 “android.permission.READ_Phone_STATE”权限
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission")
        String deviceId = tm.getDeviceId();
        if (deviceId == null) {
            return "";
        } else {
            return deviceId;
        }
    }
    public static String getSIM(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission")
        String deviceId = tm.getSubscriberId();
        if (deviceId == null) {
            return "00000";
        } else {
            return deviceId;
        }
    }

    /**
     * 是否越狱
     * @return
     */
    public static synchronized int getRootAhth()
    {
        Process process = null;
        DataOutputStream os = null;
        try
        {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("exit\n");
            os.flush();
            int exitValue = process.waitFor();
            if (exitValue == 0)
            {
                return 20;
            } else
            {
                return 10;
            }
        } catch (Exception e)
        {
            Log.d("*** DEBUG ***", "Unexpected error - Here is what I know: "
                    + e.getMessage());
            return 10;
        } finally
        {
            try
            {
                if (os != null)
                {
                    os.close();
                }
                process.destroy();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    /**
     * 获取运营商
     */
    public static String getSim(Context context) {
        String imsi = getSIM(context);
        return imsi.substring(0,5);
    }

    /**
     * 获取网络状态
     */
    public static int getNetWork(Context context) {
        int type = 0;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            type = 0;
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            type = 10;
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = 20;
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type = 30;
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                type = 40;
            }
        }
        return type;
    }
    /**
     * 获取系统时间戳
     */
    public static int getTime(){
        int time= (int) (System.currentTimeMillis()/1000);//获取系统时间的10位的时间戳
        return time;
    }

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getCompareTo(Map map){
        StringBuffer buffer=new StringBuffer();
        String mkey="f1c66be34c32dcea0197f763853490f0";
        //String mkey="123456789";
        buffer.append(mkey);
        if(map.get("token")!=null){
            buffer.append(map.get("token"));
            map.remove("token");
        }

        List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {

            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });
        for (Map.Entry<String, Object> item : infoIds) {
                String key = item.getKey();
                Object val = item.getValue();
                if(val instanceof String[]){
                    Gson gson=new Gson();
                    String s = gson.toJson(val);
                    buffer.append(key+s);
                    Log.e("key","key---"+s);
                }
                else{
                    if(!val.equals("")){
                        buffer.append(key+val);
                    }

                }
                Log.e("buffer",buffer.toString());
        }
        return buffer.toString();
    }
    public static String NumtFormat(int num){
        String str = NumberFormat.getNumberInstance(Locale.US).format(num);
        return str;
    }
    public static double NumtoMoney(int num){
        double newnum=num/10000;
        BigDecimal bg = new BigDecimal(newnum);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }
    /**
     * 判断是否有网络
     */
    public static boolean hasNetWork(Context context){
        ConnectivityManager cwjManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cwjManager.getActiveNetworkInfo();
        if(activeNetworkInfo==null){
            return false;
        }
        else{
            return true;
        }
    }

    public static String changeData(String time){
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        String[] str = times.split("日");
        return str[0];
    }
    /***
     * 获取android默认的useragent
     * @param context
     * @return
     */
    public static String getUserAgent(Context context){
        String useragent = new WebView(context).getSettings().getUserAgentString();
        return useragent;
    }
    public static GlideUrl getUrl(String url){
        GlideUrl imgurl = new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("User-Agent", DeviceInfoUtils.getUserAgent(MyApp.getAppContext()))
                .build());
        return imgurl;
    }
}
