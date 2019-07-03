package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.ui.fragment.CaseFragment;
import com.jdhd.qynovels.ui.fragment.FuLiFragment;
import com.jdhd.qynovels.ui.fragment.ShopFragment;
import com.jdhd.qynovels.ui.fragment.WodeFragment;
import com.jdhd.qynovels.utils.StatusBarUtil;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup rg;
    public static RadioButton rb_case,rb_shop,rb_fl,rb_wd;
    public static LinearLayout ll;
    private Fragment currentFragment=new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
        changeFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void changeFragment(){
        Intent intent=getIntent();
        int fragmentFlag=intent.getIntExtra("fragment_flag",1);
        Log.e("asd",fragmentFlag+"");
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        switch (fragmentFlag){
            case 1:
                transaction.replace(R.id.ll,new CaseFragment(),"case").addToBackStack("case");
                rb_case.setChecked(true);
                break;
            case 2:
                transaction.replace(R.id.ll,new ShopFragment(),"shop").addToBackStack("shop");
                rb_shop.setChecked(true);
                break;
            case 3:
                transaction.replace(R.id.ll,new FuLiFragment(),"fuli").addToBackStack("fuli");
                rb_fl.setChecked(true);
                break;
            case 4:
                transaction.replace(R.id.ll,new WodeFragment(),"wode").addToBackStack("wode");
                rb_wd.setChecked(true);
                break;
        }
        transaction.commit();
    }

    private void init() {
        ll=findViewById(R.id.ll);
        rg=findViewById(R.id.rg);
        rb_case=findViewById(R.id.rb_case);
        rb_shop=findViewById(R.id.rb_shop);
        rb_fl=findViewById(R.id.rb_fl);
        rb_wd=findViewById(R.id.rb_wd);
        rg.setOnCheckedChangeListener(this);
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.ll,new CaseFragment()).addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        if(radioGroup.getCheckedRadioButtonId()==R.id.rb_case){
           transaction.replace(R.id.ll,new CaseFragment(),"case").addToBackStack("case");
        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.rb_shop){
            transaction.replace(R.id.ll,new ShopFragment(),"shop").addToBackStack("shop");
        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.rb_fl){
            transaction.replace(R.id.ll,new FuLiFragment(),"fuli").addToBackStack("fuli");
        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.rb_wd){
            transaction.replace(R.id.ll,new WodeFragment(),"wode").addToBackStack("wode");
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences  sharedPreferencesjx= getSharedPreferences("jx", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editorjx =sharedPreferencesjx.edit();
        editorjx.putInt("lastOffset",0);
        editorjx.putInt("lastPosition",0);
        editorjx.commit();

        SharedPreferences  sharedPreferencesman= getSharedPreferences("man", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editorman =sharedPreferencesman.edit();
        editorman.putInt("lastOffset",0);
        editorman.putInt("lastPosition",0);
        editorman.commit();

        SharedPreferences  sharedPreferenceswm= getSharedPreferences("wm", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editorwm =sharedPreferenceswm.edit();
        editorwm.putInt("lastOffset",0);
        editorwm.putInt("lastPosition",0);
        editorwm.commit();

        SharedPreferences  sharedPreferencesbook= getSharedPreferences("book", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editorbook =sharedPreferencesbook.edit();
        editorbook.putInt("lastOffset",0);
        editorbook.putInt("lastPosition",0);
        editorbook.commit();
        System.exit(0);
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        // 判断当前按键是返回键

            // 判断当前按键是返回键
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                // 获取当前回退栈中的Fragment个数
                int backStackEntryCount = fragmentManager.getBackStackEntryCount();
                // 回退栈中至少有多个fragment,栈底部是首页
                if (backStackEntryCount > 1) {
                    // 如果回退栈中Fragment个数大于一.一直退出
                    while (fragmentManager.getBackStackEntryCount() > 1) {
                        fragmentManager.popBackStackImmediate();
                        //选中第一个界面
                        rb_case.setChecked(true);
                    }
                } else {
                    finish();
                }

                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    SharedPreferences  sharedPreferencesjx= getSharedPreferences("jx", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editorjx =sharedPreferencesjx.edit();
                    editorjx.putInt("lastOffset",0);
                    editorjx.putInt("lastPosition",0);
                    editorjx.commit();

                    SharedPreferences  sharedPreferencesman= getSharedPreferences("man", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editorman =sharedPreferencesman.edit();
                    editorman.putInt("lastOffset",0);
                    editorman.putInt("lastPosition",0);
                    editorman.commit();

                    SharedPreferences  sharedPreferenceswm= getSharedPreferences("wm", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editorwm =sharedPreferenceswm.edit();
                    editorwm.putInt("lastOffset",0);
                    editorwm.putInt("lastPosition",0);
                    editorwm.commit();

                    SharedPreferences  sharedPreferencesbook= getSharedPreferences("book", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editorbook =sharedPreferencesbook.edit();
                    editorbook.putInt("lastOffset",0);
                    editorbook.putInt("lastPosition",0);
                    editorbook.commit();
                    Intent home = new Intent(Intent.ACTION_MAIN);
                    home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    home.addCategory(Intent.CATEGORY_HOME);
                    startActivity(home);

                }
        }
            else if(keyCode==KeyEvent.KEYCODE_MENU){
                System.exit(0);
                SharedPreferences  sharedPreferencesjx= getSharedPreferences("jx", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editorjx =sharedPreferencesjx.edit();
                editorjx.putInt("lastOffset",0);
                editorjx.putInt("lastPosition",0);
                editorjx.commit();

                SharedPreferences  sharedPreferencesman= getSharedPreferences("man", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editorman =sharedPreferencesman.edit();
                editorman.putInt("lastOffset",0);
                editorman.putInt("lastPosition",0);
                editorman.commit();

                SharedPreferences  sharedPreferenceswm= getSharedPreferences("wm", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editorwm =sharedPreferenceswm.edit();
                editorwm.putInt("lastOffset",0);
                editorwm.putInt("lastPosition",0);
                editorwm.commit();

                SharedPreferences  sharedPreferencesbook= getSharedPreferences("book", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editorbook =sharedPreferencesbook.edit();
                editorbook.putInt("lastOffset",0);
                editorbook.putInt("lastPosition",0);
                editorbook.commit();
            }
        return true;

    }

}
