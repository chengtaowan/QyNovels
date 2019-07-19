package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.Ss_NrAdapter;
import com.jdhd.qynovels.ui.fragment.Ss_LxFragment;
import com.jdhd.qynovels.ui.fragment.Ss_NrFragment;
import com.jdhd.qynovels.ui.fragment.Ss_RsFragment;
import com.jdhd.qynovels.utils.StatusBarUtil;

public class SsActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private ImageView back;
    private TextView ss;
    private EditText name;
    private Ss_NrFragment ss_nrFragment=new Ss_NrFragment();
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ss);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
        Intent intent=getIntent();
        type=intent.getIntExtra("ss",1);
    }

    private void init() {
        back=findViewById(R.id.ss_back);
        ss=findViewById(R.id.ss_ss);
        name=findViewById(R.id.ss_name);
        back.setOnClickListener(this);
        ss.setOnClickListener(this);
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.ss_ll,new Ss_RsFragment());
        transaction.commit();
        name.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        if(R.id.ss_back==view.getId()){
            if(type==1){
                Intent intent=new Intent(SsActivity.this,MainActivity.class);
                intent.putExtra("page", 0);
                startActivity(intent);
            }
            else if(type==2){
                Intent intent=new Intent(SsActivity.this,MainActivity.class);
                intent.putExtra("page", 1);
                startActivity(intent);
            }
            else if(type==3){
                Intent intent=new Intent(SsActivity.this,FlActivity.class);
                startActivity(intent);
            }
        }
        else if(R.id.ss_ss==view.getId()){
            if(name.getText().length()>0){
                ss_nrFragment.setContent(name.getText().toString());
                FragmentManager manager=getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.ss_ll,ss_nrFragment);
                transaction.commit();
            }
            else{
                Toast.makeText(SsActivity.this,"请输入书名",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("asd",type+"---");
        if(type==1){
            Intent intent=new Intent(SsActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 1);
            startActivity(intent);
        }
        else if(type==2){
            Intent intent=new Intent(SsActivity.this,MainActivity.class);
            intent.putExtra("fragment_flag", 2);
            startActivity(intent);
        }
        else if(type==3){
            Intent intent=new Intent(SsActivity.this,FlActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length()>0){
//            FragmentManager manager=getSupportFragmentManager();
//            FragmentTransaction transaction=manager.beginTransaction();
//            transaction.replace(R.id.ss_ll,new Ss_LxFragment());
//            transaction.commit();
        }
        else{
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.ss_ll,new Ss_RsFragment());
            transaction.commit();
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
