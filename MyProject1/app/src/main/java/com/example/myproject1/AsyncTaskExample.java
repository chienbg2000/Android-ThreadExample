package com.example.myproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
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

public class AsyncTaskExample extends AppCompatActivity {
    final int maxThreadNumber = 20;
    private Button btnStart;
    private EditText txtThreadNum;
    private Handler handler;
    private TextView txt_display;
    private HashMap<Integer,String> hashMap = new HashMap<Integer,String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_example);
        findView();
        setOnClick();
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
                    new ProgressAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,i);
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

    private class AsyncStatus{
        public int id;
        public String status;
    }
    private class ProgressAsyncTask extends AsyncTask<Integer,AsyncStatus,AsyncStatus> {

        @Override
        protected void onPostExecute(AsyncStatus asyncStatus) {
            AsyncStatus as = asyncStatus;
            if (hashMap.containsKey(as.id)){
                hashMap.replace(as.id,as.status);
            }else {
                hashMap.put(as.id,as.status);
            }
            String dpString = "";
            for (Integer i : hashMap.keySet()) {
                dpString += "AsyncTask " + i + ":" + hashMap.get(i).toString()+"\n";
            }
            txt_display.setText(dpString);
        }

        @Override
        protected void onProgressUpdate(AsyncStatus... values) {
            AsyncStatus as = values[0];
            if (hashMap.containsKey(as.id)){
                hashMap.replace(as.id,as.status);
            }else {
                hashMap.put(as.id,as.status);
            }
            String dpString = "";
            for (Integer i : hashMap.keySet()) {
                dpString += "AsyncTask " + i + ":" + hashMap.get(i).toString()+"\n";
            }
            txt_display.setText(dpString);
        }

        @Override
        protected AsyncStatus doInBackground(Integer... integers) {
            int percent = 0;
            Random rand = new Random();
            AsyncStatus as = new AsyncStatus();
            as.id = integers[0];
            as.status = "Preparing...";
            publishProgress(as);
            sleep(rand.nextInt(5000)+1);
            as.status = "Starting...";
            publishProgress(as);
            sleep(2000);
            as.status = "0%";
            publishProgress(as);
            do {
                sleep(1000);

                percent += rand.nextInt(10)+1;
                if(percent < 100){
                    as.status = String.format("%d",percent)+"%";
                }else {
                    as.status = String.format("%d",100)+"%";
                }
                publishProgress(as);
            }while (percent < 100);
            as.status="Done";
            return as;
        }
    }

}