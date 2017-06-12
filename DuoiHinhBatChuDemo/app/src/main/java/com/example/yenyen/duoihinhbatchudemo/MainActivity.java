package com.example.yenyen.duoihinhbatchudemo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        Typeface typeface = Typeface.createFromAsset(MainActivity.this.getAssets(), "fonts/UTM_Cookies_0.ttf");
        button.setTypeface(typeface);

    }
}
