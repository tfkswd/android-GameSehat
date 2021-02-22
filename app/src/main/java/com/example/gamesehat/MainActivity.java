package com.example.gamesehat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamesehat.R;

public class MainActivity extends AppCompatActivity {
    Chronometer chronometer;
    ImageButton btStart, btStop;
    private boolean isResume;
    Handler handler;
    long tMiliSec, tStart, tBuff, tUpdate = 0L;
    int sec, min, milisec;

    TextView showValue;
    TextView showValue1;
    int counter = 0;
    int counter1 = 0;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        btStart = findViewById(R.id.bt_start);
        btStop = findViewById(R.id.bt_stop);
        handler = new Handler();

        showValue = (TextView) findViewById(R.id.sitTxt);
        showValue1 = (TextView) findViewById(R.id.pushTxt);

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume) {
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runable, 0);
                    chronometer.start();
                    isResume = true;
                    btStop.setVisibility(View.GONE);
                    btStart.setImageDrawable(getResources().getDrawable(
                            R.drawable.ic_pause
                    ));
                } else {
                    tBuff += tMiliSec;
                    handler.removeCallbacks(runable);
                    chronometer.stop();
                    isResume = false;
                    btStop.setVisibility(View.VISIBLE);
                    btStart.setImageDrawable(getResources().getDrawable(
                            R.drawable.ic_play
                    ));
                }
            }
        });
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume) {
                    btStart.setImageDrawable(getResources().getDrawable(
                            R.drawable.ic_play
                    ));
                    tMiliSec = 0L;
                    tStart = 0L;
                    tBuff = 0L;
                    tUpdate = 0L;
                    sec = 0;
                    min = 0;
                    milisec = 0;
                    chronometer.setText("00:00:00");
                }
            }
        });
    }
    @Override
    public void onBackPressed(){

        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else{
            backToast= Toast.makeText(getBaseContext(),"Tekan kembali lagi untuk keluar!",Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
    public Runnable runable = new Runnable() {
        @Override
        public void run() {
            tMiliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMiliSec;
            sec = (int) (tUpdate / 1000);
            min = sec / 60;
            sec = sec % 60;
            milisec = (int) (tUpdate % 100);
            chronometer.setText(String.format("%02d", min) + ":"
                    + String.format("%02d", sec) + ":" + String.format("%02d", milisec));
            handler.postDelayed(this, 60);
        }
    };


    public  void countIN(View view){
        counter++;
        showValue.setText(Integer.toString(counter));
    }
    public void countDE(View v){
        counter--;
        showValue.setText(Integer.toString(counter));
    }
    public void resetCount(View view){
        counter = 0;
        showValue.setText(String.valueOf(counter));
    }

    public void countIN1(View view){
        counter1++;
        showValue1.setText(Integer.toString(counter1));
    }
    public void countDE1(View v){
        counter1--;
        showValue1.setText(Integer.toString(counter1));
    }
    public void resetCount1(View view){
        counter1=0;
        showValue1.setText(String.valueOf(counter1));
    }

}
