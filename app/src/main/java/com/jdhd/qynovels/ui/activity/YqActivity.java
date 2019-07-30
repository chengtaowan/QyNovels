package com.jdhd.qynovels.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.FunctionBean;
import com.jdhd.qynovels.persenter.impl.personal.IShareImgPresenterImpl;
import com.jdhd.qynovels.ui.fragment.FuLiFragment;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IShareImgView;
import com.just.agentweb.AgentWeb;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.utils.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.jdhd.qynovels.ui.fragment.FuLiFragment.createBitmapThumbnail;

public class YqActivity extends AppCompatActivity implements View.OnClickListener , IShareImgView {
    private TextView tex,mx;
    private ImageView back;
    private LinearLayout webView;
    private String title,path;
    private AgentWeb web;
    private int type;
    private FunctionBean functionBean;
    private String listtitle,listpath,listpage,listlimit;
    private IShareImgPresenterImpl shareImgPresenter;
    private Bitmap map;
    private SmartRefreshLayout sr;
    private boolean hasNetWork;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int what=msg.what;
            functionBean= (FunctionBean) msg.obj;
            Log.e("what",what+"");
            switch (what){
                case 6:
                    String type=functionBean.getType();
                    String scene=functionBean.getScene();
                    String img=functionBean.getShare_img();
                    if(type.equals("1")&&scene.equals("0")){

                        getBitmap(YqActivity.this, img, new FuLiFragment.GlideLoadBitmapCallback() {
                            @Override
                            public void getBitmapCallback(Bitmap bitmap) {
                                map=bitmap;
                                Log.e("asd","mapmapmap");
                                sharePicture(map, SendMessageToWX.Req.WXSceneSession);
                            }
                        });

                    }
                    else if(type.equals("1")&&scene.equals("1")){
                        getBitmap(YqActivity.this, img, new FuLiFragment.GlideLoadBitmapCallback() {
                            @Override
                            public void getBitmapCallback(Bitmap bitmap) {
                                map=bitmap;
                                Log.e("asd","mapmapmap");
                                sharePicture(map,SendMessageToWX.Req.WXSceneTimeline);
                            }
                        });

                    }
                    else if(type.equals("0")){
                        getBitmap(YqActivity.this, img, new FuLiFragment.GlideLoadBitmapCallback() {
                            @Override
                            public void getBitmapCallback(Bitmap bitmap) {
                                map=bitmap;
                                Log.e("asd","mapmapmap");
                                saveImageToGallery(YqActivity.this,map);
                            }
                        });
                    }
                    break;
                case 5:
                    String str=functionBean.getCode();
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", str);
// 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    Toast.makeText(YqActivity.this,"复制到剪切板",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yq);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        hasNetWork = DeviceInfoUtils.hasNetWork(this);
        Intent intent = getIntent();
        type=intent.getIntExtra("type",0);
        title=intent.getStringExtra("title");
        path=intent.getStringExtra("path");
        Log.e("path",path);
        shareImgPresenter=new IShareImgPresenterImpl(this,this);
        shareImgPresenter.loadData();
        init();
    }

    private void init() {
        sr=findViewById(R.id.sr);
        mx=findViewById(R.id.mx);
        mx.setOnClickListener(this);
        tex=findViewById(R.id.title);
        back=findViewById(R.id.ls_back);
        back.setOnClickListener(this);
        webView=findViewById(R.id.ll);
        tex.setText(title);
        web=AgentWeb.with(this)

                .setAgentWebParent(webView, new LinearLayout.LayoutParams(-1, -1))

                .useDefaultIndicator()//进度条

                .createAgentWeb()
                .ready()

                .go(MyApp.Url.webbaseUrl+path);
        web.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(web, this));
        sr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if(!hasNetWork){
                    Toast.makeText(YqActivity.this,"网络连接不可用",Toast.LENGTH_SHORT).show();
                    sr.finishRefresh();
                }
                else{
                    web=AgentWeb.with(YqActivity.this)

                            .setAgentWebParent(webView, new LinearLayout.LayoutParams(-1, -1))

                            .useDefaultIndicator()//进度条

                            .createAgentWeb()
                            .ready()

                            .go(MyApp.Url.webbaseUrl+path);
                    sr.finishRefresh();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(R.id.mx==view.getId()){
            Intent intent=new Intent(YqActivity.this,FriendListActivity.class);
            intent.putExtra("title",title);
            intent.putExtra("path",path);
            startActivity(intent);
        }
        else{
            Intent intent=new Intent(YqActivity.this,MainActivity.class);
            intent.putExtra("page",2);
            startActivity(intent);
        }

    }

    @Override
    public void onShareSuccess(String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("share",string);
                web.getJsAccessEntrace().quickCallJs("shareImg", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Log.e("data",s);
                    }
                },string);
            }
        });
    }

    @Override
    public void onShareError(String error) {
      Log.e("shareimgerror",error);
    }

    public class AndroidInterface {
        private AgentWeb agent;
        private Context context;

        public AndroidInterface(AgentWeb agent, Context context) {
            this.agent = agent;
            this.context = context;
        }

        @JavascriptInterface
        public void GGScriptMessageCommon(String str) {
            Log.e("name",str);
            Gson gson=new Gson();
            FunctionBean functionBean = gson.fromJson(str, FunctionBean.class);
            switch (functionBean.getFunctionName()){
                case "dailyShare":
                    Message message6=handler.obtainMessage(6);
                    message6.obj=functionBean;
                    handler.sendMessage(message6);
                    break;
                case "clipboard":
                    Message message5=handler.obtainMessage(5);
                    message5.obj=functionBean;
                    handler.sendMessage(message5);
                    break;
            }
        }
    }

    public void getBitmap(Context context, String uri, final FuLiFragment.GlideLoadBitmapCallback callback) {
        Glide.with(context)
                .load(uri)
                //.override(80, 80)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        Bitmap bitmap = Bitmap.createBitmap(
                                resource.getIntrinsicWidth(),
                                resource.getIntrinsicHeight(),
                                resource.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                        : Bitmap.Config.RGB_565);
                        Canvas canvas = new Canvas(bitmap);
                        //canvas.setBitmap(bitmap);
                        resource.setBounds(0, 0, resource.getIntrinsicWidth(), resource.getIntrinsicHeight());
                        resource.draw(canvas);
                        callback.getBitmapCallback(createBitmapThumbnail(bitmap, false));
                    }
                });

    }

    private void sharePicture(Bitmap bitmap, int shareType) {
        WXImageObject imgObj = new WXImageObject(bitmap);


        WXMediaMessage msgs = new WXMediaMessage();
        msgs.mediaObject = imgObj;

//构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msgs;
        req.scene = shareType;
        //req.userOpenId = getOpenId();
//调用api接口，发送数据到微信
        MyApp.getApi().sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            Toast.makeText(context,"保存到本地",Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //web.clearWebCache();
    }
}
