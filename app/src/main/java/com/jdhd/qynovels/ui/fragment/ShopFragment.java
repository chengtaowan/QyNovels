package com.jdhd.qynovels.ui.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.ShopAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookshop.ExitBean;
import com.jdhd.qynovels.module.bookshop.ModuleBean;
import com.jdhd.qynovels.module.personal.ChangeSexBean;
import com.jdhd.qynovels.module.personal.EventBean;
import com.jdhd.qynovels.module.personal.TokenBean;
import com.jdhd.qynovels.module.personal.UserEventBean;
import com.jdhd.qynovels.module.personal.VisitorBean;
import com.jdhd.qynovels.persenter.impl.bookcase.ICasePresenterImpl;
import com.jdhd.qynovels.persenter.impl.bookshop.IModulePresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IUserEventPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IVisitorPresenterImpl;
import com.jdhd.qynovels.ui.activity.LoginActivity;
import com.jdhd.qynovels.ui.activity.MainActivity;
import com.jdhd.qynovels.ui.activity.SsActivity;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.utils.EventDbUtils;
import com.jdhd.qynovels.view.bookshop.IModuleView;
import com.jdhd.qynovels.view.personal.IUserEventView;
import com.jdhd.qynovels.view.personal.IVisitorView;
import com.jdhd.qynovels.widget.GetMoneyPopWindow;
import com.tencent.mm.opensdk.utils.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends BaseFragment implements TabLayout.OnTabSelectedListener,View.OnClickListener, IModuleView , IVisitorView, IUserEventView {

    private ImageView search;
    private ViewPager home_vp;
    private List<Fragment> list=new ArrayList<>();
    private int type;
    private RelativeLayout jz;
    private ImageView gif;
    private IModulePresenterImpl modulePresenter;
    private JxFragment jxFragment=new JxFragment();
    private ManFragment manFragment=new ManFragment();
    private WmanFragment wmanFragment=new WmanFragment();
    private IVisitorPresenterImpl visitorPresenter;
    public static ImageView lhb;
    private String token="";
    private String islogin="";
    private RelativeLayout rl;
    int REQUEST_CALL_PHONE_PERMISSION=0;
    private boolean upvisitor;
    private IUserEventPresenterImpl iUserEventPresenter;
    private DbUtils dbUtils;
    private SQLiteDatabase database;

    private static final String TAG = "ShopFragment";
    private View rootView;
    //标志位，标志已经初始化完成
    private boolean isPrepared;
    //是否已被加载过一次，第二次就不再去请求数据了
    private boolean mHasLoadedOnce;
    private int startTime,endTime;


    private TabLayout tab;
    private List<RadioButton> rblist=new ArrayList<>();
    public ShopFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        startTime=DeviceInfoUtils.getTime();
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("token",Context.MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        islogin=sharedPreferences.getString("islogin","");
        if((!token.equals("")&&islogin.equals("0"))||token.equals("")){
            lhb.setVisibility(View.VISIBLE);
        }
        else{
            lhb.setVisibility(View.GONE);
        }
        lazyLoad();
        Log.e(TAG,"onstart");
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getToken(TokenBean tokenBean){
        lhb.setVisibility(View.GONE);
        closePopWindow();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView==null){
            rootView= inflater.inflate(R.layout.fragment_shop, container, false);
            init(rootView);
            isPrepared = true;

        }

        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        dbUtils=new DbUtils(getContext());
        iUserEventPresenter=new IUserEventPresenterImpl(this,getContext());
        Intent intent=getActivity().getIntent();
        type=intent.getIntExtra("lx",1);
        SharedPreferences upsharedPreferences=getActivity().getSharedPreferences("upvisitor",MODE_PRIVATE);
        String str=upsharedPreferences.getString("upvisitor","");
        if(!str.equals("true")){
            //如果有权限直接执行
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED){
                visitorPresenter=new IVisitorPresenterImpl(this,getContext());
                visitorPresenter.loadData();
                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("upvisitor",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("upvisitor","true");
                editor.commit();
            }
            //如果没有权限那么申请权限
            else{
                ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.CALL_PHONE},REQUEST_CALL_PHONE_PERMISSION);
            }
        }

        return rootView;
    }
    private void init(View view) {
        if(jxFragment!=null){
            jxFragment=new JxFragment();
        }
        if(manFragment!=null){
            manFragment=new ManFragment();

        }
        if(wmanFragment!=null){
            wmanFragment=new WmanFragment();
        }
        lhb=view.findViewById(R.id.lhb);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("token",Context.MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        islogin=sharedPreferences.getString("islogin","");
        if((!token.equals("")&&islogin.equals("0"))||token.equals("")){
            lhb.setVisibility(View.VISIBLE);
        }
        else{
            lhb.setVisibility(View.GONE);
        }
        rl=view.findViewById(R.id.rl);
        lhb.setOnClickListener(this);
        jz=view.findViewById(R.id.jz);
        gif=view.findViewById(R.id.case_gif);
        search=view.findViewById(R.id.search);
        search.setOnClickListener(this);
        tab=view.findViewById(R.id.tab);
        tab.setSelectedTabIndicatorHeight(0);
        tab.addOnTabSelectedListener(this);
        home_vp=view.findViewById(R.id.home_vp);
        list.clear();
        list.add(jxFragment);
        list.add(manFragment);
        list.add(wmanFragment);
        ShopAdapter adapter=new ShopAdapter(getChildFragmentManager());
        adapter.refresh(list);
        home_vp.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void get(ExitBean exitBean){
        lhb.setVisibility(View.VISIBLE);
    }


    @Override
    public void onResume() {
        super.onResume();
        changeFragment();

    }
    private void changeFragment(){
        if(type==2){
           home_vp.setCurrentItem(1);
        }
        else if(type==3){
            home_vp.setCurrentItem(2);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        home_vp.setCurrentItem(tab.getPosition());
        View view=LayoutInflater.from(getContext()).inflate(R.layout.item_tabtex,null);
        TextView textView = view.findViewById(R.id.tabtex);
        float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 21, getResources().getDisplayMetrics());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);
        textView.setTextColor(Color.parseColor("#E8564E"));
        textView.setText(tab.getText());
        tab.setCustomView(textView);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tab.setCustomView(null);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View view) {
        if(R.id.lhb==view.getId()){
           showPopWindow(rl);
           lhb.setVisibility(View.GONE);
        }
        else{
            int time= DeviceInfoUtils.getTime();
            EventDbUtils eventDbUtils=new EventDbUtils(getContext());
            List<EventBean.DataBean> updata = eventDbUtils.updata(MyApp.kQYDataAnalysisEventType.kQYDataAnalysisTargetEvent, time, 0, MyApp.kQYoperationType.kQYSoperationTypeOpen, MyApp.kQYTargetDataAnalysis.kQYTargetDataAnalysisBookCity_search);
            if(updata.size()==20){
                Gson gson=new Gson();
                String s=gson.toJson(updata);
                iUserEventPresenter.setJson(s);
                iUserEventPresenter.loadData();
            }
            Intent intent=new Intent(getContext(), SsActivity.class);
            intent.putExtra("ss",2);
            startActivity(intent);
        }

    }
    public  static GetMoneyPopWindow customPopWindow;
    private void showPopWindow(View v){
        customPopWindow=new GetMoneyPopWindow(getActivity());
        customPopWindow.showAtLocation(v,
                Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        customPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                customPopWindow.backgroundAlpha(getActivity(), 1f);
            }
        });

    }

    public static void closePopWindow(){
        if(customPopWindow!=null){
            customPopWindow.dismiss();
        }

    }


    @Override
    public void onSuccess(ModuleBean moduleBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                jxFragment.setType(moduleBean.getData().getList().get(0).getId());
                manFragment.setType(moduleBean.getData().getList().get(1).getId());
                wmanFragment.setType(moduleBean.getData().getList().get(2).getId());
                jz.setVisibility(View.GONE);
                for(int i=0;i<moduleBean.getData().getList().size();i++){
                    tab.addTab(tab.newTab().setText(moduleBean.getData().getList().get(i).getModuleName()));
                }
                tab.getTabAt(0).select();
                home_vp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
            }
        });
    }

    @Override
    public void onError(String error) {
        Log.e("moduleerror",error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce){
            return;
        }
        modulePresenter=new IModulePresenterImpl(this,getContext(),10);
        modulePresenter.loadData();
        Log.e(TAG,TAG+"加载数据");
        mHasLoadedOnce = true;
    }

    @Override
    public void onVisitorSuccess(VisitorBean avatarBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(avatarBean.getCode()==4010){
                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("upvisitor",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("upvisitor","false");
                    editor.commit();
                    Log.e("avatarbean",avatarBean.getMsg());
                }
                else {
                    SharedPreferences preferences=getContext().getSharedPreferences("token",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("token",avatarBean.getData().getToken());
                    editor.putString("login","fail");
                    editor.putString("islogin",avatarBean.getData().getIs_login()+"");
                    editor.commit();
                    android.util.Log.e("islogin",avatarBean.getData().getIs_login()+"....");
                }

            }
        });
        android.util.Log.e("vivisot",avatarBean.getMsg());
    }

    @Override
    public void onVisitorError(String error) {
        android.util.Log.e("vivisoterror",error);
    }

    /*
55     * 当请求获取权限后会执行此回调方法，来执行自己的业务逻辑
56     * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==this.REQUEST_CALL_PHONE_PERMISSION){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                visitorPresenter=new IVisitorPresenterImpl(this,getContext());
                visitorPresenter.loadData();
            }else{
                Toast.makeText(getContext(), "拒绝了权限", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public void onPause() {
        super.onPause();
        endTime=DeviceInfoUtils.getTime();
        EventDbUtils eventDbUtils=new EventDbUtils(getContext());
        if(dbUtils!=null){
            List<EventBean.DataBean> updata = eventDbUtils.updata(MyApp.kQYDataAnalysisEventType.kQYDataAnalysisPageEvent, startTime, endTime, MyApp.kQYoperationType.kQYSoperationTypeOpen, MyApp.kQYPageDataAnalysis.kQYPageDataAnalysisBookCity);
            if(updata.size()==20){
                Gson gson=new Gson();
                String s=gson.toJson(updata);
                iUserEventPresenter.setJson(s);
                iUserEventPresenter.loadData();
            }
        }

    }

    @Override
    public void onUserEventSuccess(UserEventBean userEventBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(userEventBean.getCode()==200){
                    database=dbUtils.getWritableDatabase();
                    database.execSQL("delete from userevent");
                }
            }
        });
    }

    @Override
    public void onUserEventError(String error) {

    }
}
