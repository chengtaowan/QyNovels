package com.glong.reader;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.glong.reader.activities.CustomReaderActivity;
import com.glong.reader.activities.ExtendReaderActivity;
import com.glong.reader.activities.NormalReaderActivity;
import com.glong.reader.activities.PaperActivity;
import com.glong.reader.activities.SimpleReaderActivity;
import com.glong.sample.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.simple_reader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SimpleReaderActivity.class));
            }
        });

        findViewById(R.id.normal_reader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NormalReaderActivity.class));
            }
        });

        findViewById(R.id.extend_reader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExtendReaderActivity.class));
            }
        });

        findViewById(R.id.custom_reader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomReaderActivity.class));
            }
        });

        findViewById(R.id.paper_reader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PaperActivity.class));
            }
        });
    }
}
