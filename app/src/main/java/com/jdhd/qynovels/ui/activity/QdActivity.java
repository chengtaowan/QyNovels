package com.jdhd.qynovels.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.utils.StatusBarUtil;

public class QdActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout qd;
    private TextView ljqd,cg,sp;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qd);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        init();
    }

    private void init() {
        qd=findViewById(R.id.qd_qd);
        qd.setOnClickListener(this);
        ljqd=findViewById(R.id.qd_ljqd);
        cg=findViewById(R.id.qd_cg);
        sp=findViewById(R.id.qd_sp);
        qd.setOnClickListener(this);
        back=findViewById(R.id.qd_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(R.id.qd_back==view.getId()){
            finish();
        }
        else{
            qd.setBackgroundResource(R.drawable.shape_qd_on);
            ljqd.setVisibility(View.GONE);
            cg.setVisibility(View.VISIBLE);
            sp.setVisibility(View.VISIBLE);
        }

    }
}
