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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jdhd.qynovels.R;
import com.jdhd.qynovels.adapter.Ss_NrAdapter;
import com.jdhd.qynovels.app.MyApp;
import com.jdhd.qynovels.module.bookcase.SearchContentBean;
import com.jdhd.qynovels.module.bookcase.SsBean;
import com.jdhd.qynovels.persenter.impl.bookcase.ISearchContentPresenterImpl;
import com.jdhd.qynovels.ui.fragment.Ss_LxFragment;
import com.jdhd.qynovels.ui.fragment.Ss_NrFragment;
import com.jdhd.qynovels.ui.fragment.Ss_RsFragment;
import com.jdhd.qynovels.utils.AndroidBug54971Workaround;
import com.jdhd.qynovels.utils.DeviceInfoUtils;
import com.jdhd.qynovels.utils.StatusBarUtil;
import com.jdhd.qynovels.view.bookshop.ISearchContentView;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SsActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher , ISearchContentView {
    private ImageView back;
    private TextView ss;
    private EditText name;
    private Ss_NrFragment ss_nrFragment=new Ss_NrFragment();
    private ISearchContentPresenterImpl searchContentPresenter;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ss);
        AndroidBug54971Workaround.assistActivity(findViewById(android.R.id.content),this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        EventBus.getDefault().register(this);
        MyApp.addActivity(this);
        StatusBarUtil.setStatusBarMode(this, true, R.color.c_ffffff);
        searchContentPresenter=new ISearchContentPresenterImpl(this,this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStr(SsBean ssBean){
        name.setText(ssBean.getName());
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

    @Override
    public void onClick(View view) {
        if(R.id.ss_back==view.getId()){
//            if(type==1){
//                Intent intent=new Intent(SsActivity.this,MainActivity.class);
//                intent.putExtra("page", 0);
//                startActivity(intent);
//            }
//            else if(type==2){
//                Intent intent=new Intent(SsActivity.this,MainActivity.class);
//                intent.putExtra("page", 1);
//                startActivity(intent);
//            }
//            else if(type==3){
//                Intent intent=new Intent(SsActivity.this,FlActivity.class);
//                startActivity(intent);
//            }
            finish();
        }
        else if(R.id.ss_ss==view.getId()){
            if(name.getText().length()>0){
                DeviceInfoUtils.KeyBoardCancle(SsActivity.this);
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
//        Log.e("asd",type+"---");
//        if(type==1){
//            Intent intent=new Intent(SsActivity.this,MainActivity.class);
//            intent.putExtra("fragment_flag", 1);
//            startActivity(intent);
//        }
//        else if(type==2){
//            Intent intent=new Intent(SsActivity.this,MainActivity.class);
//            intent.putExtra("fragment_flag", 2);
//            startActivity(intent);
//        }
//        else if(type==3){
//            Intent intent=new Intent(SsActivity.this,FlActivity.class);
//            startActivity(intent);
//        }
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length()>0){
            searchContentPresenter.setContent(charSequence.toString());
            searchContentPresenter.loadData();

        }
//        else{
//            FragmentManager manager=getSupportFragmentManager();
//            FragmentTransaction transaction=manager.beginTransaction();
//            transaction.replace(R.id.ss_ll,new Ss_RsFragment());
//            transaction.commit();
//        }

    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 不能遗漏
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 不能遗漏
    }

    @Override
    public void onSuccess(SearchContentBean searchContentBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(searchContentBean.getData().getList()!=null&&searchContentBean.getData().getList().size()!=0){
                    FragmentManager manager=getSupportFragmentManager();
                    FragmentTransaction transaction=manager.beginTransaction();
                    transaction.replace(R.id.ss_ll,new Ss_LxFragment(searchContentBean.getData().getList()));
                    transaction.commit();
                }
//                else{
//                    FragmentManager manager=getSupportFragmentManager();
//                    FragmentTransaction transaction=manager.beginTransaction();
//                    transaction.replace(R.id.ss_ll,new Ss_RsFragment());
//                    transaction.commit();
//                }

            }
        });
    }

    @Override
    public void onError(String error) {
        Log.e("searchcontenterror",error);
    }
}
