package com.example.myproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_handler;
    private Button btn_AsyncTask_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setOnClick();
    }

    private void findView(){
        btn_handler = findViewById(R.id.btn_handler_activity);
        btn_AsyncTask_activity = findViewById(R.id.btn_AsyncTask_activity);
    }
    private void setOnClick(){
        btn_handler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HandlerExample.class));
            }
        });
        btn_AsyncTask_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AsyncTaskExample.class));
            }
        });
    }



}