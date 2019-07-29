package com.jdhd.qynovels.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
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
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.AvatarBean;
import com.jdhd.qynovels.module.personal.SexBean;
import com.jdhd.qynovels.persenter.impl.personal.IAvatarPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.ISexPresenterImpl;
import com.jdhd.qynovels.utils.ImageUtil;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.utils.getPhotoFromPhotoAlbum;
import com.jdhd.qynovels.view.personal.IAvatarView;
import com.jdhd.qynovels.view.personal.ISexView;
import com.jdhd.qynovels.widget.PhotoPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private TextView bd;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grzl);
        getPermission();
        MyApp.addActivity(this);
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
        Log.e("wxname",wxname);
        avatarPresenter=new IAvatarPresenterImpl(this,GrzlActivity.this);
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
        bd=findViewById(R.id.zh);
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
        if(!avatar.equals("http://api.damobi.cn")){
           Glide.with(this).load(avatar).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(tx);
        }
        else{
            tx.setImageResource(R.mipmap.my_touxiang);
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
        if(uid!=0&&!mobile.equals("")&&!wxname.equals("")){
            bd.setText("已绑定");
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
                intent.putExtra("type",2);
                startActivity(intent);
            }

        }
        else if(R.id.xg_tx==view.getId()){
            mPhotoPopupWindow = new PhotoPopupWindow(GrzlActivity.this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //相册
                    goPhotoAlbum();
                    mPhotoPopupWindow.dismiss();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 拍照
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
        //步骤三：获取文件Uri
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

               // 获取相机返回的数据，并转换为Bitmap图片格式，这是缩略图
               Bitmap bitmap = null;
               try {
                   bitmap = getBitmapFormUri(uri);
               } catch (FileNotFoundException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               Bitmap roundedCornerBitmap = getRoundedCornerBitmap(bitmap);
               tx.setImageBitmap(roundedCornerBitmap);

               String  imageToBase64= "data:image/p" +
                       "ng;base64,"+changeToBase64(bitmap);
               avatarPresenter.setFile(imageToBase64);
               avatarPresenter.loadData();

           }
           else if(requestCode==2){
               photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
               Glide.with(GrzlActivity.this).load(photoPath)
                       .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                       .into(tx);
               ImageUtil imageUtil= ImageUtil.getIntance();
               imageUtil.getPicTypeByUrl(photoPath);
               Bitmap getimage = imageUtil.getimage(photoPath);

               String imageToBase64 = "data:image/p" +
                       "ng;base64,"+changeToBase64(getimage);
               avatarPresenter.setFile(imageToBase64);
               avatarPresenter.loadData();
           }
        }

    }
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;

    }
    public Bitmap getBitmapFormUri(Uri uri) throws FileNotFoundException, IOException {
        InputStream input = getContentResolver().openInputStream(uri);

        //这一段代码是不加载文件到内存中也得到bitmap的真是宽高，主要是设置inJustDecodeBounds为true
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;//不加载到内存
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;

        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比，由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        input = getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }

    public Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            if (options<=0)
                break;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
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
                SharedPreferences sharedPreferences=getSharedPreferences("sex",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                if(chosesex==0){
                    xgxb.setText("未知");
                    editor.putString("sex","男");
                }
                else if(chosesex==20){
                    xgxb.setText("男");
                    editor.putString("sex","男");
                }
                else if(chosesex==30){
                    xgxb.setText("女");
                    editor.putString("sex","女");
                }
                editor.commit();
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

    public static String changeToBase64(Bitmap bitmap){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //参数2：压缩率，40表示压缩掉60%; 如果不压缩是100，表示压缩率为0
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);

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
                Log.e("imgurl",imgurl);
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
