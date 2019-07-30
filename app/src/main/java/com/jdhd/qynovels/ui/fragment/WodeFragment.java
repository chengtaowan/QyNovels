package com.jdhd.qynovels.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.personal.UserBean;
import com.jdhd.qynovels.persenter.impl.personal.IPersonalPresenterImpl;
import com.jdhd.qynovels.ui.activity.BindCodeActivity;
import com.jdhd.qynovels.ui.activity.CjwtActivity;
import com.jdhd.qynovels.ui.activity.FkActivity;
import com.jdhd.qynovels.ui.activity.GrzlActivity;
import com.jdhd.qynovels.ui.activity.JbActivity;
import com.jdhd.qynovels.ui.activity.LoginActivity;
import com.jdhd.qynovels.ui.activity.LsActivity;
import com.jdhd.qynovels.ui.activity.SzActivity;
import com.jdhd.qynovels.ui.activity.TxActivity;
import com.jdhd.qynovels.ui.activity.XxActivity;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.personal.IPersonalView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class WodeFragment extends Fragment implements View.OnClickListener,IPersonalView{

    private TextView wo_dl,wd_name,wd_hbm,wd_jb,wd_jrjb,wd_ydsj;
    private RelativeLayout wd_ls,wd_tx,wd_fk,wd_sz,wd_lb,wd_yq,wd_xj;
    private ImageView wd_toux,wd_xx;
    private int action;
    private String token;
    private LinearLayout wdjb;
    public static UserBean user=new UserBean();
    private String avatar,sex,nickname,red_code,wxname,mobel;
    private int bindwx;
    private int uid,total_gold,today_gold,read_time,balance,message_count,bind_show;
    private float money;
//    private ImageView gif;
//    private RelativeLayout jz;
    private IPersonalPresenterImpl personalPresenter;
    public WodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        personalPresenter=new IPersonalPresenterImpl(this,getContext());
        personalPresenter.loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wode, container, false);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

        personalPresenter=new IPersonalPresenterImpl(this,getContext());
        personalPresenter.loadData();
        init(view);
        Intent intent=getActivity().getIntent();
        action=intent.getIntExtra("action",1);
        SharedPreferences preferences=getActivity().getSharedPreferences("token", MODE_PRIVATE);
        token = preferences.getString("token", "");
        if(token.equals("")){
            Glide.with(getContext()).clear(wd_toux);
            wd_toux.setImageResource(R.mipmap.my_touxiang);
            wd_jb.setText("0");
            wd_jrjb.setText("0");
            wd_ydsj.setText("0");
        }
        String type=preferences.getString("login","");
        if(action==0|| type.equals("success")){
            wd_lb.setVisibility(View.GONE);
            wd_yq.setVisibility(View.GONE);
            //wd_xj.setVisibility(View.GONE);
            wo_dl.setVisibility(View.GONE);
            wd_name.setVisibility(View.VISIBLE);
            wd_hbm.setVisibility(View.VISIBLE);
        }
        Intent nameintent=getActivity().getIntent();
        String action = nameintent.getAction();
        if(nameintent!=null&&action!=null){
            if(action.equals("name")){
                String name=nameintent.getStringExtra("nickname");
                wd_name.setText(name);
            }else{

            }

        }
        return view;
    }

    private void init(View view) {
        wd_xx=view.findViewById(R.id.wd_xx);
        wo_dl=view.findViewById(R.id.wd_dl);
        wd_ls=view.findViewById(R.id.wd_ls);
        wd_tx=view.findViewById(R.id.wd_tx);
        wd_fk=view.findViewById(R.id.wd_fk);
        wd_sz =view.findViewById(R.id.wd_sz);
        wd_lb=view.findViewById(R.id.wd_lb);
        wd_yq=view.findViewById(R.id.wd_yq);
        wd_xj=view.findViewById(R.id.wd_xj);
        wd_lb.setOnClickListener(this);
        wd_yq.setOnClickListener(this);
//        jz=view.findViewById(R.id.jz);
//        gif=view.findViewById(R.id.case_gif);
        wd_toux=view.findViewById(R.id.wd_toux);
        wd_name=view.findViewById(R.id.wd_name);
        wd_hbm=view.findViewById(R.id.wd_hbm);
        wdjb=view.findViewById(R.id.wd_wdjb);
        wd_jb=view.findViewById(R.id.wd_jb);
        wd_jrjb=view.findViewById(R.id.wd_jrjb);
        wd_ydsj=view.findViewById(R.id.wd_ydsj);
        wdjb.setOnClickListener(this);
        wo_dl.setOnClickListener(this);
        wd_ls.setOnClickListener(this);
        wd_tx.setOnClickListener(this);
        wd_fk.setOnClickListener(this);
        wd_sz.setOnClickListener(this);
        wd_toux.setOnClickListener(this);
        wd_xx.setOnClickListener(this);
        wd_xj.setOnClickListener(this);
        //Glide.with(getContext()).load(R.mipmap.re).into(gif);
        if(user.getData()!=null){
            avatar=user.getData().getAvatar();
            sex=user.getData().getSex();
            nickname=user.getData().getNickname();
            uid=user.getData().getUid();
            mobel=user.getData().getMobile();
            bindwx=user.getData().getBind_wx();
            wxname=user.getData().getWx_name();
            if(!user.getData().getAvatar().equals("http://api.damobi.cn")){
                Glide.with(getContext())
                        .load(user.getData().getAvatar())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(wd_toux);
            }
            else{
                wd_toux.setImageResource(R.mipmap.my_touxiang);
            }
            wd_name.setText(user.getData().getNickname());
            wd_hbm.setText("红包码："+user.getData().getRed_code());
            wd_jb.setText(user.getData().getTotal_gold()+"");
            wd_jrjb.setText(user.getData().getToday_gold()+"");
            wd_ydsj.setText(user.getData().getRead_time()/60+"");
            if(user.getData().getMessage_count()>0){
                wd_xx.setImageResource(R.mipmap.my_xx_on);
            }
            else{
                wd_xx.setImageResource(R.mipmap.my_xx);
            }
        }
    }



    @Override
    public void onClick(View view) {
        if(R.id.wd_dl==view.getId()){
            Intent intent=new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
        else if(R.id.wd_ls==view.getId()){
            Intent intent=new Intent(getContext(), LsActivity.class);
            intent.putExtra("ls",4);
            startActivity(intent);
        }
        else if(R.id.wd_tx==view.getId()){
            if(user.getData()!=null){
                Intent intent=new Intent(getContext(), TxActivity.class);
                intent.putExtra("money",user.getData().getMoney());
                intent.putExtra("wxname",wxname);
                intent.putExtra("totle",user.getData().getTotal_gold());
                intent.putExtra("jb", DeviceInfoUtils.NumtFormat(user.getData().getBalance()));
                startActivity(intent);
            }
            else{
                Toast.makeText(getContext(),"请登录",Toast.LENGTH_SHORT).show();

            }

        }
        else if(R.id.wd_fk==view.getId()){
            if(token.equals("")){
                Toast.makeText(getContext(),"请登录",Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent=new Intent(getContext(), FkActivity.class);
                intent.putExtra("token",token);
                startActivity(intent);
            }
        }
        else if(R.id.wd_sz==view.getId()){
            Intent intent=new Intent(getContext(), SzActivity.class);
            intent.putExtra("name",nickname);
            intent.putExtra("avatar",avatar);
            intent.putExtra("sex",sex);
            intent.putExtra("uid",uid);
            intent.putExtra("mobile",mobel+"");
            intent.putExtra("bindwx",bindwx);
            intent.putExtra("wxname",wxname);
            intent.putExtra("token",token);
            intent.putExtra("type",1);
            startActivity(intent);
        }
        else if(R.id.wd_toux==view.getId()){
            if(user.getData()!=null){
                Intent intent=new Intent(getContext(), GrzlActivity.class);
                intent.putExtra("name",nickname);
                intent.putExtra("avatar",avatar);
                intent.putExtra("sex",sex);
                intent.putExtra("uid",uid);
                intent.putExtra("mobile",mobel+"");
                intent.putExtra("bindwx",bindwx);
                intent.putExtra("wxname",wxname);
                intent.putExtra("type",1);
                startActivity(intent);
            }
            else{
                Toast.makeText(getContext(),"请登录",Toast.LENGTH_SHORT).show();

            }

        }
        else if(R.id.wd_wdjb==view.getId()){
            Intent intent=new Intent(getContext(), JbActivity.class);
            if(user.getData()!=null){
                intent.putExtra("ye",user.getData().getBalance());
                intent.putExtra("money",user.getData().getMoney());
                intent.putExtra("today",user.getData().getToday_gold());
                intent.putExtra("total",user.getData().getTotal_gold());
                intent.putExtra("wxname",wxname);
                startActivity(intent);
            }
            else{
                Toast.makeText(getContext(),"请登录",Toast.LENGTH_SHORT).show();
            }

        }
        else if(R.id.wd_xx==view.getId()){
           Intent intent=new Intent(getContext(), XxActivity.class);
           startActivity(intent);
        }
        else if(R.id.wd_xj==view.getId()){
            if(user.getData()!=null){
                Intent intent=new Intent(getContext(),BindCodeActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getContext(),"请登录",Toast.LENGTH_SHORT).show();
            }

        }
        else if(R.id.wd_lb==view.getId()){
            Toast.makeText(getContext(),"请登录",Toast.LENGTH_SHORT).show();
        }
        else if(R.id.wd_yq==view.getId()){
            Toast.makeText(getContext(),"请登录",Toast.LENGTH_SHORT).show();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeavatar(String url){
        Glide.with(getContext()).load(url).into(wd_toux);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void notifyData(UserBean userBean){
        if(userBean.getCode()==401){
            wd_lb.setVisibility(View.VISIBLE);
            wd_yq.setVisibility(View.VISIBLE);
            wd_xj.setVisibility(View.VISIBLE);
            wo_dl.setVisibility(View.VISIBLE);
            wd_name.setVisibility(View.GONE);
            wd_hbm.setVisibility(View.GONE);
        }
        else{
            if(userBean.getData().getBind_show()==0){
                wd_xj.setVisibility(View.GONE);
            }
            else{
                wd_xj.setVisibility(View.VISIBLE);
            }
            wd_yq.setVisibility(View.GONE);
            wd_lb.setVisibility(View.GONE);
            wo_dl.setVisibility(View.GONE);
            wd_name.setVisibility(View.VISIBLE);
            wd_hbm.setVisibility(View.VISIBLE);
            if(userBean.getData()!=null) {
                avatar=user.getData().getAvatar();
                sex=user.getData().getSex();
                nickname=user.getData().getNickname();
                uid=user.getData().getUid();
                mobel=user.getData().getMobile();
                bindwx=user.getData().getBind_wx();
                wxname=user.getData().getNickname();
                SharedPreferences sharedPreferences=getContext().getSharedPreferences("nickname", MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("nickname",nickname);
                editor.commit();
                if (!userBean.getData().getAvatar().equals("http://api.damobi.cn")) {
                    Glide.with(getContext())
                            .load(userBean.getData().getAvatar())
                            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                            .into(wd_toux);
                }
                else{
                    wd_toux.setImageResource(R.mipmap.my_touxiang);
                }

                wd_name.setText(userBean.getData().getNickname());
                wd_hbm.setText("红包码：" + userBean.getData().getRed_code());
                wd_jb.setText(userBean.getData().getTotal_gold() + "");
                wd_jrjb.setText(userBean.getData().getToday_gold() + "");
                wd_ydsj.setText(userBean.getData().getRead_time()/60 + "");
                if (userBean.getData().getMessage_count() > 0) {
                    wd_xx.setImageResource(R.mipmap.my_xx_on);
                } else {
                    wd_xx.setImageResource(R.mipmap.my_xx);
                }
        }

        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeAvatar(String url){
        Log.e("url",url);
        Glide.with(getContext()).load(url).into(wd_toux);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSuccess(UserBean userBean) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //jz.setVisibility(View.GONE);
                if(userBean==null){
                    return;
                }
                if(userBean.getCode()==401){
                    wd_lb.setVisibility(View.VISIBLE);
                    wd_yq.setVisibility(View.VISIBLE);
                    wd_xj.setVisibility(View.VISIBLE);
                    wo_dl.setVisibility(View.VISIBLE);
                    wd_name.setVisibility(View.GONE);
                    wd_hbm.setVisibility(View.GONE);
                    return;
                }
                else{
                    user=userBean;
                    Log.e("bindshow2",userBean.getData().getBind_show()+"");
                    if(userBean.getData().getBind_show()==0){
                        wd_xj.setVisibility(View.GONE);
                    }
                    else{
                        wd_xj.setVisibility(View.VISIBLE);
                    }
                    SharedPreferences preferences=getActivity().getSharedPreferences("sex",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("sex",userBean.getData().getSex());
                    editor.commit();
                    SharedPreferences sharedPreferences=getContext().getSharedPreferences("nickname", MODE_PRIVATE);
                    SharedPreferences.Editor editor1=sharedPreferences.edit();
                    editor1.putString("nickname",userBean.getData().getNickname());
                    editor1.commit();
                    wd_yq.setVisibility(View.GONE);
                    wd_lb.setVisibility(View.GONE);
                    wo_dl.setVisibility(View.GONE);
                    wd_name.setVisibility(View.VISIBLE);
                    wd_hbm.setVisibility(View.VISIBLE);
                    if(user.getData()!=null) {
                        avatar=user.getData().getAvatar();
                        sex=user.getData().getSex();
                        nickname=user.getData().getNickname();
                        uid=user.getData().getUid();
                        mobel=user.getData().getMobile();
                        bindwx=user.getData().getBind_wx();
                        wxname=user.getData().getNickname();
                        if (!user.getData().getAvatar() .equals("http://api.damobi.cn")) {
                            Glide.with(getContext())
                                    .load(user.getData().getAvatar())
                                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                    .into(wd_toux);
                        }
                        else{
                            wd_toux.setImageResource(R.mipmap.my_touxiang);
                        }

                        wd_name.setText(user.getData().getNickname());
                        wd_hbm.setText("红包码："+user.getData().getRed_code());
                        wd_jb.setText(user.getData().getBalance()+"");
                        wd_jrjb.setText(user.getData().getToday_gold()+"");
                        wd_ydsj.setText(user.getData().getRead_time()/60+"");
                        if(user.getData().getMessage_count()>0){
                            wd_xx.setImageResource(R.mipmap.my_xx_on);
                        }
                        else{
                            wd_xx.setImageResource(R.mipmap.my_xx);
                        }
                    }
                }

            }
        });
    }

    @Override
    public void onError(String error) {
       Log.e("asd",error);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        personalPresenter.destoryView();
    }
}
