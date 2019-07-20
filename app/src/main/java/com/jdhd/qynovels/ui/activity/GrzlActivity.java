package com.jdhd.qynovels.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
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
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.SexBean;
import com.jdhd.qynovels.persenter.impl.personal.IAvatarPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.ISexPresenterImpl;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.utils.getPhotoFromPhotoAlbum;
import com.jdhd.qynovels.view.personal.IAvatarView;
import com.jdhd.qynovels.view.personal.ISexView;
import com.jdhd.qynovels.widget.PhotoPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class GrzlActivity extends AppCompatActivity implements View.OnClickListener,EasyPermissions.PermissionCallbacks , ISexView, IAvatarView {
    private ImageView back,tx;
    private RelativeLayout nc,xb,zh;
    private TextView xgnc,xgxb;
    private PhotoPopupWindow mPhotoPopupWindow;
    private File cameraSavePath;//拍照照片路径
    private Uri uri;//照片uri
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final String FILE_PATH = "/sdcard/syscamera.jpg";
    private String avatar,nickname,sex,mobile,wxname;
    private int uid,bindwx;
    private int chosesex=0;
    private ISexPresenterImpl sexPresenter;
    private String xgname;
    private IAvatarPresenterImpl avatarPresenter;
    private String imgurl;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grzl);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        EventBus.getDefault().register(this);
        Intent perintent=getIntent();
        nickname=perintent.getStringExtra("name");
        mobile=perintent.getStringExtra("mobile");
        avatar=perintent.getStringExtra("avatar");
        sex=perintent.getStringExtra("sex");
        Log.e("wdsex",sex+"---");
        uid=perintent.getIntExtra("uid",0);
        bindwx=perintent.getIntExtra("bindwx",0);
        wxname=perintent.getStringExtra("wxname");
        type=perintent.getIntExtra("type",1);
        init();
        xgnc.setText(nickname);
        Intent intent=getIntent();
        String action = intent.getAction();
        if(intent!=null&&action!=null){
            Log.e("action",action);
            if(action.equals("xgnc")){
                xgname=intent.getStringExtra("nc");
                xgnc.setText(xgname);
            }
        }
    }

    private void init() {
        back=findViewById(R.id.zl_back);
        tx=findViewById(R.id.xg_tx);
        nc=findViewById(R.id.zl_nc);
        xb=findViewById(R.id.zl_xb);
        zh=findViewById(R.id.zl_zh);
        xgnc=findViewById(R.id.nc);
        xgxb=findViewById(R.id.xb);
        back.setOnClickListener(this);
        tx.setOnClickListener(this);
        nc.setOnClickListener(this);
        xb.setOnClickListener(this);
        zh.setOnClickListener(this);
        if(!avatar.equals("")){
           Glide.with(this).load(avatar).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(tx);
        }

        xgxb.setText(sex);
        if(sex.equals("未知")){
            chosesex=0;
        }
        else if(sex.equals("男")){
            chosesex=20;
        }
        else if(sex.equals("女")){
            chosesex=30;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(GrzlActivity.this,MainActivity.class);
        intent.putExtra("fragment_flag", 4);
        intent.putExtra("nickname",xgname);
        intent.setAction("name");
        startActivity(intent);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changename(String name){
        xgnc.setText(name);
    }

    @Override
    public void onClick(View view) {
        if(R.id.zl_back==view.getId()){
            if(type==1){
                Intent intent=new Intent(GrzlActivity.this,MainActivity.class);
                intent.putExtra("page", 3);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent(GrzlActivity.this,SzActivity.class);
                intent.putExtra("name",nickname);
                intent.putExtra("avatar",avatar);
                intent.putExtra("sex",sex);
                intent.putExtra("uid",uid);
                intent.putExtra("mobile",mobile+"");
                intent.putExtra("bindwx",bindwx);
                intent.putExtra("wxname",wxname);
                startActivity(intent);
            }

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
            int i = change_sex();
            if(i!=0){
                sexPresenter=new ISexPresenterImpl(this,GrzlActivity.this,i);
                sexPresenter.loadData();
            }
        }
        else if(R.id.zl_zh==view.getId()){
           Intent intent=new Intent(GrzlActivity.this,ZhglActivity.class);
           intent.putExtra("uid",uid);
           intent.putExtra("mobile",mobile);
           intent.putExtra("wxname",wxname);
           intent.putExtra("type",1);
           startActivity(intent);
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
        if(mPhotoPopupWindow!=null){
            mPhotoPopupWindow.dismiss();
        }
        if(sexPresenter!=null){
            sexPresenter.destoryView();
        }

        EventBus.getDefault().unregister(this);
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
               String imageToBase64 = "data:image/png;base64,"+imageToBase64(FILE_PATH);
               avatarPresenter=new IAvatarPresenterImpl(this,GrzlActivity.this,imageToBase64);
               avatarPresenter.loadData();

           }
           else if(requestCode==2){
               photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
               Glide.with(GrzlActivity.this).load(photoPath)
                       .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                       .into(tx);
               String imageToBase64 = "data:image/p" +
                       "ng;base64,"+imageToBase64(photoPath);
               avatarPresenter=new IAvatarPresenterImpl(this,GrzlActivity.this,imageToBase64);
               avatarPresenter.loadData();
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

    public int change_sex(){

        AlertDialog.Builder builder=new AlertDialog.Builder(GrzlActivity.this);
        builder.setMessage("请选择性别");
        builder.setPositiveButton("男", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chosesex=20;
            }
        });
        builder.setNegativeButton("女", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               chosesex=30;
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(chosesex==0){
                    xgxb.setText("未知");
                }
                else if(chosesex==20){
                    xgxb.setText("男");
                }
                else if(chosesex==30){
                    xgxb.setText("女");
                }
                Toast.makeText(GrzlActivity.this,"性别修改成功",Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
        return chosesex;
    }

    @Override
    public void onSexSuccess(SexBean sexBean) {
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               xgxb.setText(sexBean.getData().getSex());
           }
       });
    }

    @Override
    public void onSexError(String error) {
       Log.e("sexerror",error);
    }


    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        InputStream is = null;
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            is = new FileInputStream(path);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            if (bitmap != null) {

                baos = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                baos.flush();

                baos.close();

                byte[] bitmapBytes = baos.toByteArray();

                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }}
        catch(IOException e){
                e.printStackTrace();
            }finally{
                if (null != is) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

    @Override
    public void onAvatarSuccess(AvatarBean avatarBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imgurl=avatarBean.getData().getAvatar();
                EventBus.getDefault().post(imgurl);
            }
        });
    }

    @Override
    public void onAvatarError(String error) {
        Log.e("avatarerror",error);
    }
    public static void i(String tag, String msg) {  //信息太长,分段打印

        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，

        //  把4*1024的MAX字节打印长度改为2001字符数

        int max_str_length = 2001 - tag.length();

        //大于4000时

        while (msg.length() > max_str_length) {

            Log.i(tag, msg.substring(0, max_str_length));

            msg = msg.substring(max_str_length);

        }

        //剩余部分

        Log.e(tag, msg);

    }

}
