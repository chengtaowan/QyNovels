package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.ui.fragment.GxFragment;
import com.jdhd.qynovels.ui.fragment.RdFragment;
import com.jdhd.qynovels.utils.StatusBarUtil;

public class MorePhbActivity extends AppCompatActivity implements View.OnClickListener ,RadioGroup.OnCheckedChangeListener{
    private ImageView back,search;
    private RadioGroup rg;
    private RadioButton rd,gx;
    private TextView type;
    private RdFragment rdFragment;
    private GxFragment gxFragment;
    private int ptype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_phb);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
        Intent intent=getIntent();
        ptype=intent.getIntExtra("more",1);
    }

    private void init() {
        back=findViewById(R.id.more_back);
        search=findViewById(R.id.more_ss);
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        rg=findViewById(R.id.more_rg);
        rd=findViewById(R.id.more_rd);
        gx=findViewById(R.id.more_gx);
        type=findViewById(R.id.more_type);
        rd.setChecked(true);
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        if(rdFragment==null){
            rdFragment=new RdFragment();
            transaction.replace(R.id.more_ll,rdFragment);
        }
        else{
            transaction.replace(R.id.more_ll,rdFragment);
            transaction.addToBackStack(null);
        }
        transaction.commit();
        rg.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        if(R.id.more_ss==view.getId()){
            Intent intent=new Intent(MorePhbActivity.this,SsActivity.class);
            startActivity(intent);
        }
        else if(R.id.more_back==view.getId()){
            if(ptype==1){
                Intent intent=new Intent(MorePhbActivity.this,FlActivity.class);
                startActivity(intent);
            }
            else if(ptype==2){
                Intent intent=new Intent(MorePhbActivity.this,WjjpActivity.class);
                startActivity(intent);
            }

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        if(radioGroup.getCheckedRadioButtonId()==R.id.more_rd){
            if(rdFragment==null){
                rdFragment=new RdFragment();
                transaction.replace(R.id.more_ll,rdFragment);
            }
            else{
                transaction.replace(R.id.more_ll,rdFragment);
                transaction.addToBackStack(null);
            }

        }
        else if(radioGroup.getCheckedRadioButtonId()==R.id.more_gx){
            if(gxFragment==null){
                gxFragment=new GxFragment();
                transaction.replace(R.id.more_ll,gxFragment);
            }
            else{
                transaction.replace(R.id.more_ll,gxFragment);
                transaction.addToBackStack(null);
            }
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(ptype==1){
            Intent intent=new Intent(MorePhbActivity.this,FlActivity.class);
            startActivity(intent);
        }
        else if(ptype==2){
            Intent intent=new Intent(MorePhbActivity.this,WjjpActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
