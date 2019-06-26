package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.jdhd.qynovels.ui.fragment.Ss_LxFragment;
import com.jdhd.qynovels.ui.fragment.Ss_NrFragment;
import com.jdhd.qynovels.ui.fragment.Ss_RsFragment;

public class SsActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private ImageView back;
    private TextView ss;
    private EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ss);
        init();
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
            finish();
        }
        else if(R.id.ss_ss==view.getId()){
            if(name.getText().length()>0){
                FragmentManager manager=getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.ss_ll,new Ss_NrFragment());
                transaction.commit();
            }
            else{
                Toast.makeText(SsActivity.this,"请输入书名",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length()>0){
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction transaction=manager.beginTransaction();
            transaction.replace(R.id.ss_ll,new Ss_LxFragment());
            transaction.commit();
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
}
