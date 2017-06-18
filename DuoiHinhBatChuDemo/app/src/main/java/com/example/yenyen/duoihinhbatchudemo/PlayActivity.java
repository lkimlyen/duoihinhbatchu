package com.example.yenyen.duoihinhbatchudemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
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

public class PlayActivity extends BaseActivity {

    LinearLayout layout, layout1, layout2, layout3;
    ImageView ivImage, imageView1, imageView2;

    Button btHint, btLuotChoi,btInvite;
    DatabaseHelper helper;
    int index = 0;
    int vitri = 0;
    int luotchoi = 5;
    CauHoi cauHoi;
    private ArrayList<CauHoi> dsCauHoi;
    String[] kyTu = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    ArrayList<String> dsItem = new ArrayList<>();
    ArrayList<TextView> dsODapAn = new ArrayList<>();
    ArrayList<ImageView> dsIVDapAn = new ArrayList<>();
    ArrayList<TextView> dsOChon = new ArrayList<>();
    StringBuilder chuoikq;
    String goiy;
    TextView textview1, textview2, tvSai, tvCauHoi, tvTien;

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
        dsCauHoi = helper.getQuestion();

        layout = (LinearLayout) findViewById(R.id.frameLayout7);
        layout1 = (LinearLayout) findViewById(R.id.frameLayout4);
        layout2 = (LinearLayout) findViewById(R.id.frameLayout5);
        layout3 = (LinearLayout) findViewById(R.id.frameLayout6);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        btHint = (Button) findViewById(R.id.btnhint);
        btLuotChoi = (Button) findViewById(R.id.btLuotChoi);
        btInvite = (Button) findViewById(R.id.btInvite);

        tvSai = (TextView) findViewById(R.id.tvSai);
        tvSai.setVisibility(View.INVISIBLE);
        tvCauHoi = (TextView) findViewById(R.id.tvCauHoi);
        tvTien = (TextView) findViewById(R.id.tvTien);
        tvTien.setText("0");
        hienthi();
        btHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogGoiY dialogGoiY = new CustomDialogGoiY();
                dialogGoiY.setCancelable(false);
                dialogGoiY.show(getFragmentManager(), "abc");
                dialogGoiY.setGoiy("Vui lòng đăng nhập để sử dụng chức năng này");
                dialogGoiY.setTieude("Thông báo");
            }
        });
        setBtInvite();
    }

    public void hienthi() {
        LayoutInflater inf = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        chuoikq = new StringBuilder();
        cauHoi = dsCauHoi.get(vitri);
        tvCauHoi.setText(String.valueOf(dsCauHoi.get(vitri).id));
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
                textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

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
            dsOChon.add(textview1);
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
                            tvSai.setText("Bạn đã chọn đáp án đúng");
                            tvSai.setVisibility(View.VISIBLE);
                            for (int j = 0; j < dsODapAn.size(); j++) {
                                dsIVDapAn.get(j).setImageResource(R.drawable.tiletrue);
                            }
                            CauHoi ch = new CauHoi(dsCauHoi.get(vitri).id, 1);
                            helper.updateStatus(ch);

                            Intent intent = new Intent(PlayActivity.this, ResultActivity.class);
                            intent.putExtra("kq", dsCauHoi.get(vitri).fullAnswer);

                            intent.putExtra("image",dsCauHoi.get(vitri).imagePath);
                            intent.putExtra("cauhoi",tvCauHoi.getText().toString());
                            startActivity(intent);
                            finish();
                        } else {
                            for (int i =0; i< dsOChon.size();i++)
                            {
                                dsOChon.get(i).setClickable(false);
                            }
                            luotchoi--;
                            btLuotChoi.setText(String.valueOf(luotchoi));
                            tvSai.setText("Bạn đã chọn đáp án sai");
                            tvSai.setVisibility(View.VISIBLE);
                            for (int j = 0; j < dsODapAn.size(); j++) {
                                dsIVDapAn.get(j).setImageResource(R.drawable.tilefalse);
                            }
                            CountDownTimer timer = new CountDownTimer(3000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    dsIVDapAn.clear();
                                    dsODapAn.clear();
                                    dsItem.clear();
                                    layout3.removeAllViews();
                                    layout2.removeAllViews();
                                    layout.removeAllViews();
                                    layout1.removeAllViews();
                                    index = 0;

                                    tvSai.setVisibility(View.INVISIBLE);
                                    hienthi();
                                }
                            };
                            timer.start();
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
            final View rowview = inf.inflate(R.layout.layout_item_choose1, null);
            textview2 = (TextView) rowview.findViewById(R.id.tvKyTu);
            imageView2 = (ImageView) rowview.findViewById(R.id.ivTileHover);
            textview2.setTag(imageView2);
            dsOChon.add(textview2);
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
                            tvSai.setText("Bạn đã chọn đáp án đúng");
                            tvSai.setVisibility(View.VISIBLE);
                            for (int j = 0; j < dsODapAn.size(); j++) {
                                dsIVDapAn.get(j).setImageResource(R.drawable.tiletrue);
                            }
                            CauHoi ch = new CauHoi(dsCauHoi.get(vitri).id, 1);
                            helper.updateStatus(ch);

                            Intent intent = new Intent(PlayActivity.this, ResultActivity.class);
                            intent.putExtra("kq", dsCauHoi.get(vitri).fullAnswer);
                            intent.putExtra("image",dsCauHoi.get(vitri).imagePath);
                            intent.putExtra("cauhoi",tvCauHoi.getText().toString());
                            startActivity(intent);
                            finish();

                        } else {
                            for (int i =0; i< dsOChon.size();i++)
                            {
                                dsOChon.get(i).setClickable(false);
                            }
                            btLuotChoi.setText(String.valueOf(luotchoi));
                            tvSai.setText("Bạn đã chọn đáp án sai");
                            tvSai.setVisibility(View.VISIBLE);
                            for (int j = 0; j < dsODapAn.size(); j++) {
                                dsIVDapAn.get(j).setImageResource(R.drawable.tilefalse);
                            }
                            CountDownTimer timer = new CountDownTimer(3000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                }

                                @Override
                                public void onFinish() {
                                    dsIVDapAn.clear();
                                    dsODapAn.clear();
                                    dsItem.clear();
                                    layout3.removeAllViews();
                                    layout2.removeAllViews();
                                    layout.removeAllViews();
                                    layout1.removeAllViews();
                                    index = 0;
                                    tvSai.setVisibility(View.INVISIBLE);
                                    hienthi();
                                }
                            };
                            timer.start();
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
    public void setLuotchoi()
    {
        CountDownTimer timer = new CountDownTimer(30000,1) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                luotchoi++;
            }
        };
    }
    public void setBtInvite()
    {
        btInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogGoiY dialogGoiY = new CustomDialogGoiY();
                dialogGoiY.setCancelable(false);
                dialogGoiY.show(getFragmentManager(), "abc");
                dialogGoiY.setGoiy("Vui lòng đăng nhập để sử dụng chức năng này");
                dialogGoiY.setTieude("Thông báo");
            }
        });
    }


}
