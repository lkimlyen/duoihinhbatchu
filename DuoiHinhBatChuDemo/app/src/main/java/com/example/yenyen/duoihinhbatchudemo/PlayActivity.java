package com.example.yenyen.duoihinhbatchudemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {

    LinearLayout layout1,layout2,layout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        layout1 = (LinearLayout) findViewById(R.id.frameLayout4);
        layout2 = (LinearLayout) findViewById(R.id.frameLayout5);
        layout3 = (LinearLayout) findViewById(R.id.frameLayout6);

        LayoutInflater inf = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < 8; i++) {
            View rowview = inf.inflate(R.layout.layout_item_choose1, null);

            TextView textview = (TextView) rowview.findViewById(R.id.tvKyTu);
            ImageView imageview = (ImageView) rowview.findViewById(R.id.ivTileHover);

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT,1);
            param.gravity = Gravity.CENTER;
            rowview.setLayoutParams(param);
            layout1.addView(rowview);
        }
        for (int i = 0; i < 8; i++) {
            View rowview = inf.inflate(R.layout.layout_item_choose1, null);

            TextView textview = (TextView) rowview.findViewById(R.id.tvKyTu);
            ImageView imageview = (ImageView) rowview.findViewById(R.id.ivTileHover);

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT,1);
            param.gravity = Gravity.CENTER;
            rowview.setLayoutParams(param);
            layout2.addView(rowview);
        }
        for (int i = 0; i < 8; i++) {
            View rowview = inf.inflate(R.layout.layout_item_choose, null);

            TextView textview = (TextView) rowview.findViewById(R.id.tvKyTu2);
            ImageView imageview = (ImageView) rowview.findViewById(R.id.ivTileEmpty);

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT,1);
            param.gravity = Gravity.CENTER;
            rowview.setLayoutParams(param);
            layout3.addView(rowview);

        }

    }

}
