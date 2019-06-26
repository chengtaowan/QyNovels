package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.ui.fragment.CaseFragment;
import com.jdhd.qynovels.ui.fragment.FuLiFragment;
import com.jdhd.qynovels.ui.fragment.ShopFragment;
import com.jdhd.qynovels.ui.fragment.WodeFragment;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup rg;
    public static RadioButton rb_case,rb_shop,rb_fl,rb_wd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        rg=findViewById(R.id.rg);
        rb_case=findViewById(R.id.rb_case);
        rb_shop=findViewById(R.id.rb_shop);
        rb_fl=findViewById(R.id.rb_fl);
        rb_wd=findViewById(R.id.rb_wd);
        rg.setOnCheckedChangeListener(this);
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.ll,new CaseFragment());
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        if(radioGroup.getCheckedRadioButtonId()==R.id.rb_case){
           transaction.replace(R.id.ll,new CaseFragment());
        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.rb_shop){
            transaction.replace(R.id.ll,new ShopFragment());
        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.rb_fl){
            transaction.replace(R.id.ll,new FuLiFragment());
        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.rb_wd){
            transaction.replace(R.id.ll,new WodeFragment());
        }
        transaction.commit();
    }
}
