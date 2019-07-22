package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.utils.ImageUtils;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShareActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ewm;
    private LinearLayout save;
    private RelativeLayout photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();

    }

    private void init() {
        ewm=findViewById(R.id.fx_ewm);
        Bitmap qr = CodeUtils.createImage("http://www.qubaobei.com//ios//cf//uploadfile//132//9//8289.jpg", 90, 90, null);
        ewm.setImageBitmap(qr);
        save=findViewById(R.id.fx_save);
        photo=findViewById(R.id.photo);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(R.id.fx_save==view.getId()){
            ImageUtils.viewSaveToImage(photo,"photo",ShareActivity.this);
        }
    }


}
