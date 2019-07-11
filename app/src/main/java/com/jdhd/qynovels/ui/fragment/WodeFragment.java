package com.jdhd.qynovels.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.personal.UserBean;
import com.jdhd.qynovels.persenter.impl.personal.IPersonalPresenterImpl;
import com.jdhd.qynovels.ui.activity.CjwtActivity;
import com.jdhd.qynovels.ui.activity.GrzlActivity;
import com.jdhd.qynovels.ui.activity.JbActivity;
import com.jdhd.qynovels.ui.activity.LoginActivity;
import com.jdhd.qynovels.ui.activity.LsActivity;
import com.jdhd.qynovels.ui.activity.SzActivity;
import com.jdhd.qynovels.ui.activity.TxActivity;
import com.jdhd.qynovels.view.personal.IPersonalView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private String avatar,sex,nickname,red_code;
    private int uid,total_gold,today_gold,read_time,balance,message_count,bind_show;
    private float money;
//    private ImageView gif;
//    private RelativeLayout jz;
    private IPersonalPresenterImpl personalPresenter;
    public WodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wode, container, false);
        EventBus.getDefault().register(this);
        personalPresenter=new IPersonalPresenterImpl(this,getContext());
        personalPresenter.loadData();
        init(view);
        Intent intent=getActivity().getIntent();
        action=intent.getIntExtra("action",1);
        SharedPreferences preferences=getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = preferences.getString("token", "");
        String type=preferences.getString("login","");
        if(action==0|| type.equals("success")){
            wd_lb.setVisibility(View.GONE);
            wd_yq.setVisibility(View.GONE);
            wd_xj.setVisibility(View.GONE);
            wo_dl.setVisibility(View.GONE);
            wd_name.setVisibility(View.VISIBLE);
            wd_hbm.setVisibility(View.VISIBLE);
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
        //Glide.with(getContext()).load(R.mipmap.re).into(gif);
        if(user.getData()!=null){
            Log.e("avatar1",user.getData().getAvatar());
            if(user.getData().getAvatar()!=null){
                Glide.with(getContext())
                        .load(user.getData().getAvatar())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(wd_toux);
            }else{
                wd_toux.setImageResource(R.mipmap.my_touxiang);
            }
            wd_name.setText(user.getData().getNickname());
            wd_hbm.setText("红包码："+user.getData().getRed_code());
            wd_jb.setText(user.getData().getTotal_gold()+"");
            wd_jrjb.setText(user.getData().getToday_gold()+"");
            wd_ydsj.setText(user.getData().getRead_time()+"");
            if(user.getData().getMessage_count()>0){
                wd_xx.setImageResource(R.mipmap.my_xx_on);
            }
            else{
                wd_xx.setImageResource(R.mipmap.my_xx);
            }
        }
        else{
            Log.e("www","111");
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
            Intent intent=new Intent(getContext(), TxActivity.class);
            startActivity(intent);
        }
        else if(R.id.wd_fk==view.getId()){
            Intent intent=new Intent(getContext(), CjwtActivity.class);
            startActivity(intent);
        }
        else if(R.id.wd_sz==view.getId()){
            Intent intent=new Intent(getContext(), SzActivity.class);
            startActivity(intent);
        }
        else if(R.id.wd_toux==view.getId()){
            Intent intent=new Intent(getContext(), GrzlActivity.class);
            startActivity(intent);
        }
        else if(R.id.wd_wdjb==view.getId()){
            Intent intent=new Intent(getContext(), JbActivity.class);
            intent.putExtra("ye",user.getData().getBalance());
            intent.putExtra("money",user.getData().getMoney());
            intent.putExtra("today",user.getData().getToday_gold());
            intent.putExtra("total",user.getData().getTotal_gold());
            startActivity(intent);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void notifyData(UserBean userBean){
        wd_lb.setVisibility(View.GONE);
        wd_yq.setVisibility(View.GONE);
        wd_xj.setVisibility(View.GONE);
        wo_dl.setVisibility(View.GONE);
        wd_name.setVisibility(View.VISIBLE);
        wd_hbm.setVisibility(View.VISIBLE);
        Log.e("userbean",(userBean.getData()==null)+"");
        if(userBean.getData()!=null) {
            Log.e("avatar2",userBean.getData().getAvatar());
            if (userBean.getData().getAvatar() != null) {
                Glide.with(getContext())
                        .load(userBean.getData().getAvatar())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(wd_toux);
            } else {
                wd_toux.setImageResource(R.mipmap.my_touxiang);
            }

            wd_name.setText(userBean.getData().getNickname());
            wd_hbm.setText("红包码：" + userBean.getData().getRed_code());
            wd_jb.setText(userBean.getData().getTotal_gold() + "");
            wd_jrjb.setText(userBean.getData().getToday_gold() + "");
            wd_ydsj.setText(userBean.getData().getRead_time() + "");
            if (userBean.getData().getMessage_count() > 0) {
                wd_xx.setImageResource(R.mipmap.my_xx_on);
            } else {
                wd_xx.setImageResource(R.mipmap.my_xx);
            }
        }
        else{
            Log.e("www","222");
        }
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
                user=userBean;
                if(user.getData()!=null) {
                    Log.e("avatar3",user.getData().getAvatar());
                    if (user.getData().getAvatar() != null) {
                        Glide.with(getContext())
                                .load(user.getData().getAvatar())
                                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                .into(wd_toux);
                    } else {
                        wd_toux.setImageResource(R.mipmap.my_touxiang);
                    }

                wd_name.setText(user.getData().getNickname());
                wd_hbm.setText("红包码："+user.getData().getRed_code());
                wd_jb.setText(user.getData().getBalance()+"");
                wd_jrjb.setText(user.getData().getToday_gold()+"");
                wd_ydsj.setText(user.getData().getRead_time()+"");
                if(user.getData().getMessage_count()>0){
                    wd_xx.setImageResource(R.mipmap.my_xx_on);
                }
                else{
                    wd_xx.setImageResource(R.mipmap.my_xx);
                }
            }
                else{
                    Log.e("www","333");
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
