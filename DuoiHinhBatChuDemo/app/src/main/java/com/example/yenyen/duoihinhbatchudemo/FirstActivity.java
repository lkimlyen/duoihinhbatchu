package com.example.yenyen.duoihinhbatchudemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        new Timer().schedule(new TimerTask(){
            public void run() {
                FirstActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        startActivity(new Intent(FirstActivity.this, MainActivity.class));
                        finish();
                    }
                });
            }
        }, 2000);
    }
}
