package com.jdhd.qynovels.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.JxAdapter;
import com.jdhd.qynovels.module.bookshop.ShopBean;
import com.jdhd.qynovels.module.personal.ChangeSexBean;
import com.jdhd.qynovels.persenter.impl.bookshop.IJxPresenterImpl;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.view.bookshop.IJxView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class JxFragment extends Fragment implements IJxView {
    private RecyclerView rv;
    private  int lastOffset;//距离

    private  int lastPosition;//第几个item

    private  SharedPreferences sharedPreferences;
    private int type;
    private IJxPresenterImpl jxPresenter;
    private JxAdapter adapter;
    private RelativeLayout jz;
    private ImageView gif;
    private SmartRefreshLayout sr;
    private boolean hasNetWork;
    private String sex;
    private int psex=20;

    public void setType(int type) {
        this.type = type;
    }

    public JxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_jx, container, false);

        sharedPreferences=getContext().getSharedPreferences("sex", Context.MODE_PRIVATE);
        sex = sharedPreferences.getString("sex", "");
        Log.e("shopbean",sex);
        if(sex.equals("女")){
           psex=30;
        }
        else{
            psex=20;
        }
        Log.e("shopbean",psex+"---");
        jxPresenter=new IJxPresenterImpl(this,1,getContext());
        jxPresenter.setSex(psex);
        jxPresenter.loadData();
        hasNetWork = DeviceInfoUtils.hasNetWork(getContext());
        init(view);
        Log.e("shopbean","oncreate");
        return view;
    }

    private void init(View view) {
        sr=view.findViewById(R.id.sr);
        jz=view.findViewById(R.id.jz);
        gif=view.findViewById(R.id.case_gif);
        Glide.with(getContext()).load(R.mipmap.re).into(gif);
        rv=view.findViewById(R.id.jx_rv);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        adapter=new JxAdapter(getContext());
        rv.setAdapter(adapter);
        sr.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if(!hasNetWork){
                    Toast.makeText(getContext(),"网络连接不可用",Toast.LENGTH_SHORT).show();
                    sr.finishRefresh();
                }
                else{
                    jxPresenter.loadData();
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        //getPositionAndOffset();
    }

    @Override
    public void onResume() {
        super.onResume();
        //scrollToPosition();
    }

    @Override
    public void onSuccess(ShopBean shopBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("shopbean",shopBean.getCode()+"--"+shopBean.getMsg());
                if(shopBean.getCode()==200){
                    sr.finishRefresh();
                    jz.setVisibility(View.GONE);
                    adapter.refresh(shopBean.getData().getList());
                }
                else{
                    Toast.makeText(getContext(),shopBean.getMsg(),Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onError(String error) {
        Log.e("jxerror",error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
