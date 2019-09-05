package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.StatusBarUtil;

public class ChoseSexActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout go;
    private ImageView boy,girl;
    private TextView boytex,girltex;
    private LinearLayout boyll,girlll;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_sex);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);
        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
    }

    private void init() {
        go=findViewById(R.id.go);
        boy=findViewById(R.id.boy);
        girl=findViewById(R.id.girl);
        boytex=findViewById(R.id.boy_tex);
        girltex=findViewById(R.id.girl_tex);
        boyll=findViewById(R.id.boyll);
        girlll=findViewById(R.id.girlll);
        boyll.setOnClickListener(this);
        girlll.setOnClickListener(this);
        go.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        sharedPreferences=getSharedPreferences("sex",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(R.id.boyll==view.getId()){
           editor.putString("sex","男");
           Intent intent=new Intent(ChoseSexActivity.this,MainActivity.class);
           intent.putExtra("page",1);
           startActivity(intent);
        }
        else if(R.id.girlll==view.getId()){
            editor.putString("sex","女");
            Intent intent=new Intent(ChoseSexActivity.this,MainActivity.class);
            intent.putExtra("page",1);
            startActivity(intent);
        }
        else if(R.id.go==view.getId()){
            editor.putString("sex","男");
            Intent intent=new Intent(ChoseSexActivity.this,MainActivity.class);
            intent.putExtra("page",1);
            startActivity(intent);
        }
        editor.commit();
    }
}
