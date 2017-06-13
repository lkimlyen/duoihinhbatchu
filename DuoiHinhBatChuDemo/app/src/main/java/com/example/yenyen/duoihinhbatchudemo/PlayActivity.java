package com.example.yenyen.duoihinhbatchudemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class PlayActivity extends AppCompatActivity {

    LinearLayout layout, layout1, layout2, layout3;
    ImageView ivImage, imageView1, imageView2;
    Button btHint;
    DatabaseHelper helper;
    int socau = 10;
    int index = 0;
    int vitri = 0;
    CauHoi cauHoi;
    private ArrayList<CauHoi> dsCauHoi;
    String[] kyTu = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    ArrayList<String> dsItem = new ArrayList<>();
    ArrayList<TextView> dsODapAn = new ArrayList<>();
    ArrayList<ImageView> dsIVDapAn = new ArrayList<>();
    StringBuilder chuoikq;
    String goiy;
    TextView textview1, textview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        helper = new DatabaseHelper(PlayActivity.this);
        try {
            helper.copyDatabaseFromAsset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //helper.close();
        //helper = new DatabaseHelper(PlayActivity.this);

        dsCauHoi = new ArrayList<CauHoi>();
        dsCauHoi = helper.getRandomQuestion(socau);

        layout = (LinearLayout) findViewById(R.id.frameLayout7);
        layout1 = (LinearLayout) findViewById(R.id.frameLayout4);
        layout2 = (LinearLayout) findViewById(R.id.frameLayout5);
        layout3 = (LinearLayout) findViewById(R.id.frameLayout6);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        btHint = (Button) findViewById(R.id.btnhint);
        hienthi();
        btHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogHint dialogHint = new CustomDialogHint();
                dialogHint.setCancelable(false);
                dialogHint.show(getFragmentManager(), "abc");
                dialogHint.setText(goiy);
            }
        });


    }

    public void hienthi() {
        LayoutInflater inf = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        chuoikq = new StringBuilder();
        cauHoi = dsCauHoi.get(vitri);
        goiy = dsCauHoi.get(vitri).description;
        try {
            InputStream inputStream = getAssets().open(dsCauHoi.get(vitri).imagePath);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            ivImage.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String shortAnswer = dsCauHoi.get(vitri).shortAnswer;
        final String[] getShortAnswer = shortAnswer.split(",");
        for (int i = 0; i < getShortAnswer.length; i++) {
            dsItem.add(getShortAnswer[i]);
        }
        int count = (16 - getShortAnswer.length);
        for (int j = 0; j < count; j++) {
            int rd = (int) (Math.random() * (26));
            dsItem.add(kyTu[rd]);
        }
        Collections.shuffle(dsItem);
        //chỗ này show ra các item ở trên
        if (getShortAnswer.length > 7) {

            for (int i = 0; i < 7; i++) {

                View rowview = inf.inflate(R.layout.layout_item_choose, null);
                TextView textview = (TextView) rowview.findViewById(R.id.tvKyTu2);
                ImageView imageview = (ImageView) rowview.findViewById(R.id.ivTileEmpty);
                dsODapAn.add(textview);
                dsIVDapAn.add(imageview);
                Resources r = getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams((int) px, LinearLayout.LayoutParams.WRAP_CONTENT);
                param.gravity = Gravity.CENTER;
                rowview.setLayoutParams(param);

                layout3.addView(rowview);
                layout3.setBackgroundColor(getResources().getColor(android.R.color.white));

            }
            for (int i = 7; i < getShortAnswer.length; i++) {

                View rowview = inf.inflate(R.layout.layout_item_choose, null);
                TextView textview = (TextView) rowview.findViewById(R.id.tvKyTu2);
                ImageView imageview = (ImageView) rowview.findViewById(R.id.ivTileEmpty);
                dsODapAn.add(textview);
                dsIVDapAn.add(imageview);
                Resources r = getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams((int) px, LinearLayout.LayoutParams.WRAP_CONTENT);
                param.gravity = Gravity.CENTER;
                rowview.setLayoutParams(param);
                layout.addView(rowview);

                layout.setBackgroundColor(getResources().getColor(android.R.color.black));

            }
        } else {
            for (int i = 0; i < getShortAnswer.length; i++) {

                View rowview = inf.inflate(R.layout.layout_item_choose, null);
                TextView textview = (TextView) rowview.findViewById(R.id.tvKyTu2);
                ImageView imageview = (ImageView) rowview.findViewById(R.id.ivTileEmpty);
                dsODapAn.add(textview);
                dsIVDapAn.add(imageview);
                Resources r = getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, r.getDisplayMetrics());
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams((int) px, LinearLayout.LayoutParams.WRAP_CONTENT);
                param.gravity = Gravity.CENTER;
                rowview.setLayoutParams(param);
                layout3.addView(rowview);

            }
        }
        final String s = shortAnswer.replace(",", "");
        ////chỗ này show ra các để chọn
        for (int i = 0; i < 8; i++) {
            View rowview = inf.inflate(R.layout.layout_item_choose1, null);
            textview1 = (TextView) rowview.findViewById(R.id.tvKyTu);
            imageView1 = (ImageView) rowview.findViewById(R.id.ivTileHover);
            textview1.setTag(imageView1);
            textview1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (index < dsODapAn.size()) {

                        String chuoi = ((TextView) v).getText().toString();
                        chuoikq.append(chuoi);
                        dsODapAn.get(index).setText(chuoi);
                        dsIVDapAn.get(index).setImageResource(R.drawable.tilehover);
                        index++;
                        ((TextView) v).setText("");
                        v.setClickable(false);
                        ((ImageView) v.getTag()).setVisibility(View.INVISIBLE);
                    }
                    if (index == dsODapAn.size()) {
                        if (s.equals(chuoikq.toString())) {
                            for (int j = 0; j < dsODapAn.size(); j++) {
                                dsIVDapAn.get(j).setImageResource(R.drawable.tiletrue);
                            }
                        } else {

                            for (int j = 0; j < dsODapAn.size(); j++) {
                                dsIVDapAn.get(j).setImageResource(R.drawable.tilefalse);

                            }
                        }
                    }
                }
            });
            textview1.setText(dsItem.get(i));
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            param.gravity = Gravity.CENTER;
            rowview.setLayoutParams(param);
            layout1.addView(rowview);
        }

        for (int j = 8; j < 16; j++) {
            View rowview = inf.inflate(R.layout.layout_item_choose1, null);
            textview2 = (TextView) rowview.findViewById(R.id.tvKyTu);
            imageView2 = (ImageView) rowview.findViewById(R.id.ivTileHover);
            textview2.setTag(imageView2);
            textview2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (index < dsODapAn.size()) {
                        String chuoi = ((TextView) v).getText().toString();
                        chuoikq.append(chuoi);
                        dsODapAn.get(index).setText(chuoi);
                        dsIVDapAn.get(index).setImageResource(R.drawable.tilehover);
                        index++;
                        ((TextView) v).setText("");
                        v.setClickable(false);
                        ((ImageView) v.getTag()).setVisibility(View.INVISIBLE);

                    }
                    if (index == dsODapAn.size()) {
                        if (s.equals(chuoikq.toString())) {
                            for (int j = 0; j < dsODapAn.size(); j++) {
                                dsIVDapAn.get(j).setImageResource(R.drawable.tiletrue);
                            }
                            Intent intent = new Intent(PlayActivity.this,ResultActivity.class);
                            intent.putExtra("kq",dsCauHoi.get(vitri).fullAnswer);
                            startActivity(intent);

                        } else {

                            for (int j = 0; j < dsODapAn.size(); j++) {
                                dsIVDapAn.get(j).setImageResource(R.drawable.tilefalse);
                            }

                        }
                    }

                }
            });
            textview2.setText(dsItem.get(j));
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            param.gravity = Gravity.CENTER;
            rowview.setLayoutParams(param);
            layout2.addView(rowview);
        }
    }

}
