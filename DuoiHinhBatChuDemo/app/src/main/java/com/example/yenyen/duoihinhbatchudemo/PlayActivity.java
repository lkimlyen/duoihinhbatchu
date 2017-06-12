package com.example.yenyen.duoihinhbatchudemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class PlayActivity extends AppCompatActivity {

    LinearLayout layout1, layout2, layout3;
    ImageView ivImage;
    DatabaseHelper helper;
    int socau = 10;
    int index = 0;
    CauHoi cauHoi;
    private ArrayList<CauHoi> dsCauHoi;
    String[] kyTu = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    ArrayList<String> dsItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        helper = new DatabaseHelper(PlayActivity.this);
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.close();
        helper = new DatabaseHelper(PlayActivity.this);
        dsCauHoi = new ArrayList<CauHoi>();
        dsCauHoi = helper.getRandomQuestion(socau);

        layout1 = (LinearLayout) findViewById(R.id.frameLayout4);
        layout2 = (LinearLayout) findViewById(R.id.frameLayout5);
        layout3 = (LinearLayout) findViewById(R.id.frameLayout6);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        hienthi(index);


    }

    public void hienthi(int vitri) {
        LayoutInflater inf = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cauHoi = dsCauHoi.get(vitri);
        try {
            InputStream inputStream = getAssets().open(dsCauHoi.get(vitri).imagePath);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            ivImage.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String shortAnswer = dsCauHoi.get(index).shortAnswer;
        String[] getShortAnswer = shortAnswer.split(",");
        for (int i = 0; i < getShortAnswer.length; i++) {
            dsItem.add(getShortAnswer[i]);
        }
        int count = (16 - getShortAnswer.length);
        for (int j = 0; j < count; j++) {
            int rd = (int) (Math.random() * (26));
            dsItem.add(kyTu[rd]);
        }
        Collections.shuffle(dsItem);
        for (int i = 0; i < getShortAnswer.length; i++) {

            View rowview = inf.inflate(R.layout.layout_item_choose, null);

            TextView textview = (TextView) rowview.findViewById(R.id.tvKyTu2);
            ImageView imageview = (ImageView) rowview.findViewById(R.id.ivTileEmpty);

            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, r.getDisplayMetrics());
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams((int) px, LinearLayout.LayoutParams.WRAP_CONTENT);
            param.gravity = Gravity.CENTER;
            rowview.setLayoutParams(param);
            layout3.addView(rowview);

        }

        for (int i = 0; i < 8; i++) {
            View rowview = inf.inflate(R.layout.layout_item_choose1, null);
            TextView textview = (TextView) rowview.findViewById(R.id.tvKyTu);
            textview.setText(dsItem.get(i));
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            param.gravity = Gravity.CENTER;
            rowview.setLayoutParams(param);
            layout1.addView(rowview);
        }
        for (int j = 8; j < 16; j++) {
            View rowview = inf.inflate(R.layout.layout_item_choose1, null);
            TextView textview = (TextView) rowview.findViewById(R.id.tvKyTu);
            textview.setText(dsItem.get(j));
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            param.gravity = Gravity.CENTER;
            rowview.setLayoutParams(param);
            layout2.addView(rowview);
        }
    }

}
