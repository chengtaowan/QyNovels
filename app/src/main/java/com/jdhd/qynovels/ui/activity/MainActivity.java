package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

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
import com.jdhd.qynovels.adapter.ShopAdapter;
import com.jdhd.qynovels.ui.fragment.CaseFragment;
import com.jdhd.qynovels.ui.fragment.FuLiFragment;
import com.jdhd.qynovels.ui.fragment.ShopFragment;
import com.jdhd.qynovels.ui.fragment.WodeFragment;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    public static RadioGroup rg;
    public static RadioButton rb_case,rb_shop,rb_fl,rb_wd;
    public static NoScrollViewPager vp;
    private CaseFragment caseFragment=new CaseFragment();
    private ShopFragment shopFragment=new ShopFragment();
    private FuLiFragment fuLiFragment=new FuLiFragment();
    private WodeFragment wodeFragment=new WodeFragment();
    private List<Fragment> list=new ArrayList<>();
    private List<RadioButton> rblist=new ArrayList<>();
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
        Intent intent=getIntent();
        if(intent!=null){
          page=intent.getIntExtra("page",0);
          vp.setCurrentItem(page);
          rblist.get(page).setChecked(true);
        }
    }
    public interface Fragment2Fragment{
        public void gotoFragment(ViewPager viewPager);
    }
    private  Fragment2Fragment fragment2Fragment;
    public void setFragment2Fragment(Fragment2Fragment fragment2Fragment){
        this.fragment2Fragment = fragment2Fragment;
    }

    public void forSkip(){
        if(fragment2Fragment!=null){
            fragment2Fragment.gotoFragment(vp);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    private void init() {
       vp=findViewById(R.id.vp);
       rg=findViewById(R.id.rg);
       rb_case=findViewById(R.id.rb_case);
       rb_shop=findViewById(R.id.rb_shop);
       rb_fl=findViewById(R.id.rb_fl);
       rb_wd=findViewById(R.id.rb_wd);
       rg.setOnCheckedChangeListener(this);
       list.add(caseFragment);
       list.add(shopFragment);
       list.add(fuLiFragment);
       list.add(wodeFragment);
       rblist.add(rb_case);
       rblist.add(rb_shop);
       rblist.add(rb_fl);
       rblist.add(rb_wd);
       ShopAdapter adapter=new ShopAdapter(getSupportFragmentManager(),list);
       vp.setAdapter(adapter);
       vp.setCurrentItem(0);
       rb_case.setChecked(true);
            }

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId()==R.id.rb_case){
                    vp.setCurrentItem(0);
                }
                else if(radioGroup.getCheckedRadioButtonId()==R.id.rb_shop){
                    vp.setCurrentItem(1);
                }
                else if(radioGroup.getCheckedRadioButtonId()==R.id.rb_fl){
                    vp.setCurrentItem(2);
                }
                else if(radioGroup.getCheckedRadioButtonId()==R.id.rb_wd){
                    vp.setCurrentItem(3);
                }

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
                    if(vp.getCurrentItem()!=0){
                        vp.setCurrentItem(0);
                        rb_case.setChecked(true);
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
                        System.exit(0);

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
