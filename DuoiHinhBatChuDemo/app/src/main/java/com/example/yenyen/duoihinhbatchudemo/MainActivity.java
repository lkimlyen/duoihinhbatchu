package com.example.yenyen.duoihinhbatchudemo;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button, btChoiThu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        btChoiThu = (Button) findViewById(R.id.btChoiThu;

        Typeface typeface = Typeface.createFromAsset(MainActivity.this.getAssets(), "fonts/UTM_Cookies_0.ttf");
        button.setTypeface(typeface);
        btChoiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });

    }
}
