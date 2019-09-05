package com.jdhd.qynovels.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.module.personal.WithDrawBean;
import com.jdhd.qynovels.persenter.impl.personal.IWithDrawrPresenterImpl;
import com.jdhd.qynovels.view.personal.IWithDrawView;

public class CustomPopWindow extends PopupWindow implements IWithDrawView {
    private Activity context;
    private View.OnClickListener itemClick;
    private View view;
    private RelativeLayout  rl;
    private TextView pop_num,pop_wx,ksdz,ptdz,kxsp,gzdz;
    private LinearLayout pop_ks,pop_pt;
    private RelativeLayout pop_close;
    private String wxname;
    private int num;
    private int gold,time;
    private int yue;
    private IWithDrawrPresenterImpl withDrawrPresenter;
    public CustomPopWindow(Activity context, View.OnClickListener itemClick,String wxname,int num,int gold,int yue,int time) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.widget_popupwindow, null);//alt+ctrl+f
//        rl=view.findViewById(R.id.rl);
//        rl.getBackground().setAlpha(153);
        this.itemClick = itemClick;
        this.context = context;
        this.wxname=wxname;
        this.num=num;
        this.gold=gold;
        this.yue=yue;
        this.time=time;
        initView();
        initPopWindow();
    }

    private void initPopWindow() {
        withDrawrPresenter=new IWithDrawrPresenterImpl(this,context);
        this.setContentView(view);
        // 设置弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击()
        this.setFocusable(true);
        //this.setOutsideTouchable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00FFFFFF);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        backgroundAlpha(context, 0.6f);//0.0-1.0

    }
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }


    private void initView() {
        pop_num=view.findViewById(R.id.pop_num);
        pop_ks=view.findViewById(R.id.pop_ks);
        pop_pt=view.findViewById(R.id.pop_pt);
        pop_wx=view.findViewById(R.id.pop_wx);
        pop_close=view.findViewById(R.id.pop_close);
        ksdz=view.findViewById(R.id.ksdz);
        kxsp=view.findViewById(R.id.kxsp);
        ptdz=view.findViewById(R.id.ptdz);
        gzdz=view.findViewById(R.id.gzdz);
        pop_wx.setText("微信昵称："+wxname);
        pop_num.setText(num+"");
        pop_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        pop_ks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop_ks.setBackgroundResource(R.drawable.shape_ks_on);
                ksdz.setTextColor(Color.parseColor("#FFFFFF"));
                kxsp.setTextColor(Color.parseColor("#FFFFFF"));
                pop_pt.setBackgroundResource(R.drawable.shape_pt);
                ptdz.setTextColor(Color.parseColor("#E8564E"));
                gzdz.setTextColor(Color.parseColor("#E8564E"));
                Log.e("time",time+"--");
                if(time<30){
                    Toast.makeText(context,"阅读满30分钟才可提现！",Toast.LENGTH_SHORT).show();
                }
                else if(yue<num*10000){
                    Toast.makeText(context,"金币还不够呦",Toast.LENGTH_SHORT).show();
                }
                else{
                    withDrawrPresenter.setGold(num*10000);
                    withDrawrPresenter.setRapid(10);
                    withDrawrPresenter.loadData();
                }

                //dismiss();
            }
        });
        pop_pt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop_ks.setBackgroundResource(R.drawable.shape_ks);
                ksdz.setTextColor(Color.parseColor("#FFFFFF"));
                kxsp.setTextColor(Color.parseColor("#FFFFFF"));
                pop_pt.setBackgroundResource(R.drawable.shape_pt_on);
                ptdz.setTextColor(Color.parseColor("#E8564E"));
                gzdz.setTextColor(Color.parseColor("#E8564E"));
                if((time/60)<30){
                    Toast.makeText(context,"阅读满30分钟才可提现！",Toast.LENGTH_SHORT).show();
                }
                else if(yue<num*10000){
                    Toast.makeText(context,"金币还不够呦",Toast.LENGTH_SHORT).show();
                }
                else{
                    withDrawrPresenter.setGold(num*10000);
                    withDrawrPresenter.setRapid(20);
                    withDrawrPresenter.loadData();
                }

                //dismiss();
            }
        });
    }


    @Override
    public void onWithSuccess(WithDrawBean withDrawBean) {
       context.runOnUiThread(new Runnable() {
           @Override
           public void run() {
               Toast.makeText(context,withDrawBean.getMsg(),Toast.LENGTH_SHORT).show();
           }
       });
    }

    @Override
    public void onWithError(String error) {
        Log.e("txerror",error);
    }
}
