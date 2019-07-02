package com.jdhd.qynovels.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.ui.fragment.GrzlFragment;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.utils.getPhotoFromPhotoAlbum;
import com.jdhd.qynovels.widget.PhotoPopupWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class GrzlActivity extends AppCompatActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks {
    private ImageView back,tx;
    private RelativeLayout nc,xb,zh;
    private TextView xgnc;
    private PhotoPopupWindow mPhotoPopupWindow;
    private File cameraSavePath;//拍照照片路径
    private Uri uri;//照片uri
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final String FILE_PATH = "/sdcard/syscamera.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grzl);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
        Intent intent=getIntent();
        String name=intent.getStringExtra("nc");
        xgnc.setText(name);
    }

    private void init() {

        back=findViewById(R.id.zl_back);
        tx=findViewById(R.id.xg_tx);
        nc=findViewById(R.id.zl_nc);
        xb=findViewById(R.id.zl_xb);
        zh=findViewById(R.id.zl_zh);
        xgnc=findViewById(R.id.nc);
        back.setOnClickListener(this);
        tx.setOnClickListener(this);
        nc.setOnClickListener(this);
        xb.setOnClickListener(this);
        zh.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(GrzlActivity.this,MainActivity.class);
        intent.putExtra("fragment_flag", 4);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(R.id.zl_back==view.getId()){
            Intent intent=new Intent(GrzlActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 4);
            startActivity(intent);
        }
        else if(R.id.xg_tx==view.getId()){
            mPhotoPopupWindow = new PhotoPopupWindow(GrzlActivity.this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //相册
                    getPermission();
                    goPhotoAlbum();
                    mPhotoPopupWindow.dismiss();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 拍照
                    getPermission();
                    goCamera();
                    mPhotoPopupWindow.dismiss();
                }
            });
            mPhotoPopupWindow.showAtLocation(view,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        else if(R.id.zl_nc==view.getId()){
          Intent intent=new Intent(GrzlActivity.this,XgncActivity.class);
          startActivity(intent);
        }
        else if(R.id.zl_xb==view.getId()){

        }
        else if(R.id.zl_zh==view.getId()){

        }
    }

    //激活相机操作
    private void goCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
        uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        GrzlActivity.this.startActivityForResult(intent, 1);
    }

    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        GrzlActivity.this.startActivityForResult(intent, 2);
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPhotoPopupWindow.dismiss();
    }
    //获取权限
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 回调成功
        Log.e("asd",requestCode+"---");
        String photoPath;
        if(resultCode==RESULT_OK){
           if(requestCode==1){
               File file = new File(FILE_PATH);
               Uri uri = Uri.fromFile(file);
               tx.setImageURI(uri);
               Glide.with(GrzlActivity.this).load(uri)
                       .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                       .into(tx);


           }
           else if(requestCode==2){
               photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
               Glide.with(GrzlActivity.this).load(photoPath)
                       .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                       .into(tx);
           }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "相关权限获取成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "请同意相关权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
