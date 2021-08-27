package com.example.myproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

public class HandlerExample extends AppCompatActivity {
    final int maxThreadNumber = 20;
    private Button btnStart;
    private EditText txtThreadNum;
    private Handler handler;
    private TextView txt_display;
    private HashMap<Integer,String> hashMap = new HashMap<Integer,String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_example);
        findView();
        setOnClick();
        processHandler();
    }
    private void findView(){
        btnStart = findViewById(R.id.btn_start);
        txtThreadNum = findViewById(R.id.txt_thread_number);
        txt_display = findViewById(R.id.txtview_display);
    }
    private void setOnClick(){
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int threadNum = Integer.parseInt(txtThreadNum.getText().toString());
                if (threadNum > maxThreadNumber) threadNum = maxThreadNumber;
                for (int i = 1; i <=threadNum; i++){
                    threadJob(i);
                }
            }
        });
    }
    private void sleep(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void processHandler(){
        handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (hashMap.containsKey(msg.arg1)){
                    hashMap.replace(msg.arg1,msg.obj.toString());
                }else {
                    hashMap.put(msg.arg1,msg.obj.toString());
                }
                String dpString = "";
                for (Integer i : hashMap.keySet()) {
                    dpString += "Thread " + i + ":" + hashMap.get(i).toString()+"\n";
                }
                txt_display.setText(dpString);
            }
        };
    }
    private void threadJob(int threadNumber){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int percent = 0;
                Random rand = new Random();
                Message msg = new Message();
                msg.arg1 = threadNumber;
                msg.obj = "Preparing...";
                handler.sendMessage(msg);
                sleep(rand.nextInt(5000)+1);
                msg = new Message();
                msg.arg1 = threadNumber;
                msg.obj = "Starting...";
                handler.sendMessage(msg);
                sleep(2000);
                msg = new Message();
                msg.arg1 = threadNumber;
                msg.obj = "0%";
                handler.sendMessage(msg);
                do {
                    sleep(1000);
                    msg = new Message();
                    msg.arg1 = threadNumber;
                    percent += rand.nextInt(10)+1;
                    if(percent < 100){
                        msg.obj = String.format("%d",percent)+"%";
                    }else {
                        msg.obj = String.format("%d",100)+"%";
                    }
                    handler.sendMessage(msg);
                }while (percent < 100);
                msg = new Message();
                msg.arg1 = threadNumber;
                msg.obj = "Done";
                handler.sendMessage(msg);
            }
        }).start();

    }

}