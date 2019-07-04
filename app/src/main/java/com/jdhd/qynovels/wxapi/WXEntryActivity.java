package com.jdhd.qynovels.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rxhttp.wrapper.annotation.DefaultDomain;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    private static final String APP_ID = "wxf2f9d368f73b6719";
    private String brand,model,sv,imei;
    private int os,root,sim,network,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = MyApp.getApi();
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK://同意授权
                 if(baseResp.getType()==1){
                     String code=((SendAuth.Resp) baseResp).code;
                     Log.e("asd",code+"--");
                     getInfo();
                     Map<String,String> map=new HashMap<>();
                     map.put("code",code);
                     map.put("brand",brand);
                     map.put("model",model);
                     map.put("sv",sv);
                     map.put("os",os+"");
                     map.put("imei",imei);
                     map.put("root",root+"");
                     map.put("sim",sim+"");
                     map.put("network",network+"");
                     map.put("time",time+"");
                     StringBuffer buffer=new StringBuffer();
                     buffer.append("123456789");
                     List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
                     // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
                     Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                         public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                             return (o1.getKey()).toString().compareTo(o2.getKey());
                         }
                     });
                     for (Map.Entry<String, String> item : infoIds) {
                         if (item.getKey() != null || item.getKey() != "") {
                             String key = item.getKey();
                             String val = item.getValue();
                             buffer.append(key+val);
                         }
                     }
                     String s = md5(buffer.toString());
                     Map<String,String> map1=new HashMap<>();
                     map1=map;
                     map1.put("sign",s);



                 }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://拒绝授权

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://取消

                break;
        }
        finish();
    }

    private void getInfo(){
        brand=DeviceInfoUtils.getDeviceBrand();
        model=DeviceInfoUtils.getDeviceModel();
        sv=DeviceInfoUtils.getDeviceAndroidVersion();
        os=DeviceInfoUtils.getOs();
        imei=DeviceInfoUtils.getIMEI(this);
        root=DeviceInfoUtils.getRootAhth();
        sim=DeviceInfoUtils.getSim(this);
        network=DeviceInfoUtils.getNetWork(this);
        time=DeviceInfoUtils.getTime();
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
}
