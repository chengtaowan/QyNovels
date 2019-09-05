package com.jdhd.qynovels.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.personal.EventBean;
import com.jdhd.qynovels.module.personal.UserEventBean;
import com.jdhd.qynovels.persenter.impl.personal.IUserEventPresenterImpl;
import com.jdhd.qynovels.persenter.impl.personal.IWithDrawrPresenterImpl;
import com.jdhd.qynovels.ui.activity.LoginActivity;
import com.jdhd.qynovels.ui.fragment.ShopFragment;
import com.jdhd.qynovels.utils.DbUtils;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.utils.EventDbUtils;
import com.jdhd.qynovels.view.personal.IUserEventView;

import java.util.List;

public class GetMoneyPopWindow extends PopupWindow implements View.OnClickListener, IUserEventView {
    private Activity context;
    private View view;
    private ImageView login1;
    private TextView login2;
    private ImageView close;
    private DbUtils dbUtils;
    private SQLiteDatabase database;
    private IUserEventPresenterImpl iUserEventPresenter;
    public GetMoneyPopWindow(Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_getmoney, null);//alt+ctrl+f
//        rl=view.findViewById(R.id.rl);
//        rl.getBackground().setAlpha(153);
        this.context = context;
        dbUtils=new DbUtils(context);
        iUserEventPresenter=new IUserEventPresenterImpl(this,context);
        initView();
        initPopWindow();

    }

    private void initView() {
       login1=view.findViewById(R.id.login1);
       login2=view.findViewById(R.id.login2);
       close=view.findViewById(R.id.close);
       close.setOnClickListener(this);
       login1.setOnClickListener(this);
       login2.setOnClickListener(this);
    }

    private void initPopWindow() {
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

    @Override
    public void onClick(View view) {
        if(R.id.close==view.getId()){
            ShopFragment.lhb.setVisibility(View.VISIBLE);
            dismiss();
        }
        else{
            int time= DeviceInfoUtils.getTime();
            EventDbUtils eventDbUtils=new EventDbUtils(context);
            List<EventBean.DataBean> updata = eventDbUtils.updata(MyApp.kQYDataAnalysisEventType.kQYDataAnalysisTargetEvent, time, 0, MyApp.kQYoperationType.kQYSoperationTypeOpen, MyApp.kQYTargetDataAnalysis.kQYTargetDataAnalysisActivity_login);
            if(updata.size()==20){
                Gson gson=new Gson();
                String s=gson.toJson(updata);
                iUserEventPresenter.setJson(s);
                iUserEventPresenter.loadData();
            }
            Intent intent=new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }

    }

    @Override
    public void onUserEventSuccess(UserEventBean userEventBean) {
        context.runOnUiThread(new Runnable() {
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
