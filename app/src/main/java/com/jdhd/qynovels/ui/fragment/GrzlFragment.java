package com.jdhd.qynovels.ui.fragment;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.ui.activity.GrzlActivity;
import com.jdhd.qynovels.ui.activity.MainActivity;
import com.jdhd.qynovels.ui.activity.XgncActivity;
import com.jdhd.qynovels.utils.getPhotoFromPhotoAlbum;
import com.jdhd.qynovels.widget.PhotoPopupWindow;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class GrzlFragment extends Fragment implements View.OnClickListener,EasyPermissions.PermissionCallbacks{
    private String name;
    private ImageView back,tx;
    private RelativeLayout nc,xb,zh;
    private TextView xgnc;
    private PhotoPopupWindow mPhotoPopupWindow;
    private File cameraSavePath;//拍照照片路径
    private Uri uri;//照片uri
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public GrzlFragment(String name) {
        // Required empty public constructor
        this.name=name;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_grzl, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        back=view.findViewById(R.id.zl_back);
        tx=view.findViewById(R.id.xg_tx);
        nc=view.findViewById(R.id.zl_nc);
        xb=view.findViewById(R.id.zl_xb);
        zh=view.findViewById(R.id.zl_zh);
        xgnc=view.findViewById(R.id.nc);
        back.setOnClickListener(this);
        tx.setOnClickListener(this);
        nc.setOnClickListener(this);
        xb.setOnClickListener(this);
        zh.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(R.id.zl_back==view.getId()){
            Intent intent=new Intent(getContext(), MainActivity.class);
            intent.putExtra("fragment_flag", 4);
            startActivity(intent);
        }
        else if(R.id.xg_tx==view.getId()){
            mPhotoPopupWindow = new PhotoPopupWindow(getActivity(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //相册
                    getPermission();
                    goPhotoAlbum();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 拍照
                    goCamera();
                }
            });
            mPhotoPopupWindow.showAtLocation(view,
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        else if(R.id.zl_nc==view.getId()){
            Intent intent=new Intent(getContext(), XgncActivity.class);
            startActivity(intent);
        }
        else if(R.id.zl_xb==view.getId()){

        }
        else if(R.id.zl_zh==view.getId()){

        }
    }

    //激活相机操作
    private void goCamera() {
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //第二个参数为 包名.fileprovider
            uri = FileProvider.getUriForFile(getContext(), "com.jdhd.qynovels.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        getActivity().startActivityForResult(intent, 1);
    }

    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }
    //获取权限
    private void getPermission() {
        if (EasyPermissions.hasPermissions(getContext(), permissions)) {
            //已经打开权限
            Toast.makeText(getContext(), "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 回调成功
        String photoPath;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }
            Log.d("拍照返回图片路径:", photoPath);
            Glide.with(getContext()).load(photoPath).into(tx);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(getContext(), data.getData());
            Glide.with(getContext()).load(photoPath).into(tx);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPhotoPopupWindow.dismiss();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(getContext(), "相关权限获取成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(getContext(), "请同意相关权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
