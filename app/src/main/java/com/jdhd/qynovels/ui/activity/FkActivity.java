package com.jdhd.qynovels.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.personal.FeedBackBean;
import com.jdhd.qynovels.persenter.impl.personal.IFeedBackPresenterImpl;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.personal.IFeedBackView;
import com.umeng.analytics.MobclickAgent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class FkActivity extends AppCompatActivity implements View.OnClickListener , IFeedBackView {
    private ImageView back,img1,img2,img3,img4,img5,img6;
    private ImageView close1,close2,close3,close4,close5,close6;
    private RelativeLayout rimg1,rimg2,rimg3,rimg4,rimg5,rimg6;
    private EditText question,wx,qq,zfb;
    private TextView zs,tps,fk;
    private List<ImageView> imglist=new ArrayList<>();
    private List<ImageView> closelist=new ArrayList<>();
    private List<RelativeLayout> rlist=new ArrayList<>();
    private LinearLayout ll2;
    private String token;
    private IFeedBackPresenterImpl feedBackPresenter;
    private List<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fk);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);
        //AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content));

        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        MainActivity.mSelectPath.clear();
        Intent intent = getIntent();
        token=intent.getStringExtra("token");
        feedBackPresenter=new IFeedBackPresenterImpl(this,this);
        init();
    }

    private void init() {
        back=findViewById(R.id.fk_back);
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);
        img4=findViewById(R.id.img4);
        img5=findViewById(R.id.img5);
        img6=findViewById(R.id.img6);
        ll2=findViewById(R.id.ll2);
        close1=findViewById(R.id.close1);
        close2=findViewById(R.id.close2);
        close3=findViewById(R.id.close3);
        close4=findViewById(R.id.close4);
        close5=findViewById(R.id.close5);
        close6=findViewById(R.id.close6);
        rimg1=findViewById(R.id.rimg1);
        rimg2=findViewById(R.id.rimg2);
        rimg3=findViewById(R.id.rimg3);
        rimg4=findViewById(R.id.rimg4);
        rimg5=findViewById(R.id.rimg5);
        rimg6=findViewById(R.id.rimg6);
        question=findViewById(R.id.question);
        wx=findViewById(R.id.wx);
        qq=findViewById(R.id.qq);
        zfb=findViewById(R.id.zfb);
        zs=findViewById(R.id.fk_zs);
        tps=findViewById(R.id.fk_tps);
        fk=findViewById(R.id.fk);
        back.setOnClickListener(this);
        imglist.add(img1);
        imglist.add(img2);
        imglist.add(img3);
        imglist.add(img4);
        imglist.add(img5);
        imglist.add(img6);
        closelist.add(close1);
        closelist.add(close2);
        closelist.add(close3);
        closelist.add(close4);
        closelist.add(close5);
        closelist.add(close6);
        rlist.add(rimg1);
        rlist.add(rimg2);
        rlist.add(rimg3);
        rlist.add(rimg4);
        rlist.add(rimg5);
        rlist.add(rimg6);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);
        img6.setOnClickListener(this);
        close1.setOnClickListener(this);
        close2.setOnClickListener(this);
        close3.setOnClickListener(this);
        close4.setOnClickListener(this);
        close5.setOnClickListener(this);
        close6.setOnClickListener(this);
        fk.setOnClickListener(this);

        question.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                zs.setText(question.length()+"/200");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(R.id.img1==view.getId()){
            if(close1.getVisibility()==View.VISIBLE){
                img1.setClickable(false);
            }
            else{
                show();
            }

        }
        else if(R.id.img2==view.getId()){
            if(close2.getVisibility()==View.VISIBLE){
                img2.setClickable(false);
            }
            else{
                show();
            }
        }
        else if(R.id.img3==view.getId()){
            if(close3.getVisibility()==View.VISIBLE){
                img3.setClickable(false);
            }
            else{
                show();
            }
        }
        else if(R.id.img4==view.getId()){
            if(close4.getVisibility()==View.VISIBLE){
                img4.setClickable(false);
            }
            else{
                show();
            }
        }
        else if(R.id.img5==view.getId()){
            if(close5.getVisibility()==View.VISIBLE){
                img5.setClickable(false);
            }
            else{
                show();
            }
        }
        else if(R.id.img6==view.getId()){
            if(close6.getVisibility()==View.VISIBLE){
                img6.setClickable(false);
            }
            else{
                show();
            }
        }
        else if(R.id.close1==view.getId()){
           MainActivity.mSelectPath.remove(0);
           refresh();
        }
        else if(R.id.close2==view.getId()){
            MainActivity.mSelectPath.remove(1);
            refresh();
        }
        else if(R.id.close3==view.getId()){
            MainActivity.mSelectPath.remove(2);
            refresh();
        }
        else if(R.id.close4==view.getId()){
            MainActivity.mSelectPath.remove(3);
            refresh();
        }
        else if(R.id.close5==view.getId()){
            MainActivity.mSelectPath.remove(4);
            refresh();
        }
        else if(R.id.close6==view.getId()){
            MainActivity.mSelectPath.remove(5);
            refresh();
        }
        else if(R.id.fk==view.getId()){
          String content=question.getText().toString();
          if(content.equals("")){
              Toast.makeText(FkActivity.this,"请输入您的问题",Toast.LENGTH_SHORT).show();
              return;
          }
          String q=qq.getText().toString();
          String w=wx.getText().toString();
          String z=zfb.getText().toString();
          String imgjson=changejson();
          feedBackPresenter.setContent(content);
          feedBackPresenter.setAli(z);
          feedBackPresenter.setQq(q);
          feedBackPresenter.setWx(w);
          feedBackPresenter.setImgjson(imgjson);
          feedBackPresenter.setList(list);
          feedBackPresenter.loadData();
          list.clear();
            Toast.makeText(FkActivity.this,"感谢您的反馈",Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(R.id.fk_back==view.getId()){
           finish();
        }
    }
    private void show(){
        Intent intent=new Intent(FkActivity.this, MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,false);
        //设置获取最大图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT,6);
        // 设置模式（单选 MODE_SINGLE /多选MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE,MultiImageSelectorActivity.MODE_MULTI);
        startActivityForResult(intent,0);
    }
    private String changejson(){
        for(int i=0;i<MainActivity.mSelectPath.size();i++){
            Bitmap bt = BitmapFactory.decodeFile(MainActivity.mSelectPath.get(i).toString());
            String  imageToBase64= "data:image/p" +
                    "ng;base64,"+changeToBase64(bt);
            list.add(imageToBase64);
        }
        Gson gson=new Gson();
        String s = gson.toJson(list);
        File file = new File(Environment.getExternalStorageDirectory(),  "a.txt");
        Log.e("data",Environment.getExternalStorageDirectory()+"");
        FileOutputStream os=null;
        try {
            os=new FileOutputStream(file);
            os.write(s.getBytes());
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  s;
    }
    public static String changeToBase64(Bitmap bitmap){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //参数2：压缩率，40表示压缩掉60%; 如果不压缩是100，表示压缩率为0
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if(data!=null){
                for(int i=0;i<data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT).size();i++){
                    MainActivity.mSelectPath.add(data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT).get(i));
                }
                refresh();
            }
        }
    }

    public void refresh(){
        if(MainActivity.mSelectPath.size()==0){
           ll2.setVisibility(View.GONE);
           for(int i=1;i<rlist.size();i++){
               rlist.get(i).setVisibility(View.GONE);
           }
           close1.setVisibility(View.GONE);
           img1.setImageResource(R.mipmap.fk_tj);
           img1.setClickable(true);
        }
        for(int i=0;i<MainActivity.mSelectPath.size();i++){
            if(MainActivity.mSelectPath.size()>=4){
                ll2.setVisibility(View.VISIBLE);
            }
            else{
                ll2.setVisibility(View.GONE);
            }
            Bitmap bt = BitmapFactory.decodeFile(MainActivity.mSelectPath.get(i).toString());
            imglist.get(i).setImageBitmap(bt);
            for(int j=0;j<rlist.size();j++){
                if(j>=i+1){
                    rlist.get(j).setVisibility(View.GONE);
                }
                else{
                    rlist.get(j).setVisibility(View.VISIBLE);
                }

            }
            closelist.get(i).setVisibility(View.VISIBLE);

            if(i==MainActivity.mSelectPath.size()-1){
                if(MainActivity.mSelectPath.size()<6){
                    imglist.get(i+1).setImageResource(R.mipmap.fk_tj);
                    rlist.get(i+1).setVisibility(View.VISIBLE);
                    closelist.get(i+1).setVisibility(View.GONE);
                }
            }
        }
        tps.setText(MainActivity.mSelectPath.size()+"/6");
    }


    @Override
    public void onBackSuccess(FeedBackBean feedBackBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FkActivity.this,"感谢您的反馈",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onBackError(String error) {
        Log.e("backerror",error);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
