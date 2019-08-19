package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.BookInfoBean;
import com.jdhd.qynovels.persenter.impl.bookcase.IBookInfoPresenterImpl;
import com.jdhd.qynovels.ui.fragment.FuLiFragment;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.utils.ImageUtils;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.bookcase.IBookInfoView;
import com.jdhd.qynovels.widget.RatingBar;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.utils.Log;
import com.umeng.analytics.MobclickAgent;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import static com.jdhd.qynovels.ui.fragment.FuLiFragment.createBitmapThumbnail;

public class ShareActivity extends AppCompatActivity implements View.OnClickListener, IBookInfoView {
    private ImageView ewm,book;
    private LinearLayout save;
    private RelativeLayout photo;
    private int id;
    private String nickname;
    private TextView name,user,time,rq,zd,pf,type,des;
    private LinearLayout wxfx,pyqfx;
    private IBookInfoPresenterImpl bookInfoPresenter;
    private RatingBar start;
    private Bitmap bitmap;
    private TextView wan;
    private String url="";
    private Bitmap qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);


        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        Intent intent = getIntent();
        id=intent.getIntExtra("id",0);
        url=intent.getStringExtra("url");
        SharedPreferences sharedPreferences=getSharedPreferences("nickname",MODE_PRIVATE);
        nickname=sharedPreferences.getString("nickname","");
        bookInfoPresenter=new IBookInfoPresenterImpl(this,this);
        bookInfoPresenter.setId(id);
        bookInfoPresenter.loadData();
        init();

    }

    private void init() {
        ewm=findViewById(R.id.fx_ewm);
        if(!url.equals("")){
            qr = CodeUtils.createImage(url, 90, 90, null);
        }
        ewm.setImageBitmap(qr);
        save=findViewById(R.id.fx_save);
        photo=findViewById(R.id.photo);
        save.setOnClickListener(this);
        name=findViewById(R.id.fx_name);
        user=findViewById(R.id.fx_user);
        time=findViewById(R.id.fx_time);
        rq=findViewById(R.id.fx_rq);
        zd=findViewById(R.id.zd);
        pf=findViewById(R.id.pf);
        type=findViewById(R.id.type);
        des=findViewById(R.id.fx_des);
        wxfx=findViewById(R.id.wxfx);
        pyqfx=findViewById(R.id.pyqfx);
        book=findViewById(R.id.fx_book);
        start=findViewById(R.id.xq_start);
        wxfx.setOnClickListener(this);
        pyqfx.setOnClickListener(this);
        wan=findViewById(R.id.wan);

    }

    @Override
    public void onClick(View view) {
        photo.setDrawingCacheEnabled(true);
        photo.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        //设置绘制缓存背景颜色
        photo.setDrawingCacheBackgroundColor(Color.WHITE);
        // 把一个View转换成图片
        bitmap = loadBitmapFromView(photo);
        photo.setDrawingCacheEnabled(false);
        if(R.id.fx_save==view.getId()){
            ImageUtils.viewSaveToImage(photo,"photo",ShareActivity.this);
        }
        else if(R.id.wxfx==view.getId()){
          sharePicture(bitmap, SendMessageToWX.Req.WXSceneSession);
        }
        else if(R.id.pyqfx==view.getId()){
            sharePicture(bitmap,SendMessageToWX.Req.WXSceneTimeline);
        }
    }


    @Override
    public void onBookinfoSuccess(BookInfoBean bookInfoBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(bookInfoBean.getData().getBook().getImage()!=null){
                    GlideUrl url = DeviceInfoUtils.getUrl(bookInfoBean.getData().getBook().getImage());
                    Glide.with(ShareActivity.this).load(url).into(book);
                }
                name.setText(bookInfoBean.getData().getBook().getName());
                user.setText(nickname);
                if(bookInfoBean.getData().getBook().getHot()<10000){
                    rq.setText(bookInfoBean.getData().getBook().getHot()+"");
                    wan.setVisibility(View.GONE);
                }
                else{
                    DecimalFormat df = new java.text.DecimalFormat("#.0");
                    String format = df.format(bookInfoBean.getData().getBook().getHot() / 10000);
                    rq.setText(format+"");
                }
                zd.setText(bookInfoBean.getData().getBook().getReading()+"");
                pf.setText(bookInfoBean.getData().getBook().getGrade()+"");
                type.setText(bookInfoBean.getData().getBook().getClassName());
                des.setText(bookInfoBean.getData().getBook().getIntro());
                BigDecimal bigDecimal=new BigDecimal(pf.getText().toString());
                BigDecimal pf = bigDecimal.setScale(0, BigDecimal.ROUND_UP);
                int zs= (Integer.parseInt(pf.toString())/2);
                int ys=(Integer.parseInt(pf.toString())%2);
                start.setSelected(false);
                start.setStartTotalNumber(zs+ys);
                if(ys==0){
                    start.setSelectedNumber(Float.parseFloat(zs+0.8+""));
                }
                else if(ys==1){
                    start.setSelectedNumber(Float.parseFloat(zs+0.5+""));
                }
                int t = DeviceInfoUtils.getTime();
                String s = DeviceInfoUtils.changeData(t + "");
                time.setText("于"+s);

            }
        });
    }

    private static Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        /** 如果不设置canvas画布为白色，则生成透明 *///
        c.drawColor(Color.WHITE);
        v.layout(60, 90, w+60, h+90);
        v.draw(c);
        return bmp;
    }

    @Override
    public void onBookinfoError(String error) {

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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 不能遗漏
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 不能遗漏
    }
}
