package com.example.yenyen.duoihinhbatchudemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    ImageView ivImage, imageView1, imageView2, ivAvatar, ivAvatarKhung, ivDolaIcon, ivPictureBorder;
    Button btHint, btLuotChoi, btInvite;
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
    MediaPlayer player, pop, fail, mtrue;

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
        dsCauHoi = new ArrayList<CauHoi>();
        dsCauHoi = helper.getQuestion();
        anhXa();
        tvTien.setText("0$");
        hienthi();
        setBtHint();
        setBtInvite();
        setImageView();
        setMusic();
    }

    private void setImageView() {
        // ivAvatar.setImageResource(R.drawable.avatar);
        ivPictureBorder.setImageResource(R.drawable.pictureborder);
        ivDolaIcon.setImageResource(R.drawable.coinicon);
        ivAvatarKhung.setImageResource(R.drawable.avataricon);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.avatar);
        Bitmap iconcrop = getCroppedBitmap(icon);
        ivAvatar.setImageBitmap(iconcrop);

    }

    private void setBtHint() {
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
    }

    private void anhXa() {

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
        ivAvatar = (ImageView) findViewById(R.id.ivAvatar);
        ivAvatarKhung = (ImageView) findViewById(R.id.ivAvatarKhung);
        ivDolaIcon = (ImageView) findViewById(R.id.ivDolaIcon);
        ivPictureBorder = (ImageView) findViewById(R.id.ivPictureBorder);
        setLuotChoi();
    }

    private void setLuotChoi() {
        luotchoi = Integer.parseInt(btLuotChoi.getText().toString());
        SharedPreferences lay = getPreferences(MODE_PRIVATE);
        long secondsout = lay.getLong("secondsout", 0);
        long secondsin = System.currentTimeMillis() / 1000;
        luotchoi = lay.getInt("luotchoi", 5);
        btLuotChoi.setText(String.valueOf(luotchoi));
        if (luotchoi < 5) {
            if ((secondsin - secondsout) > 900) {
                luotchoi++;
                btLuotChoi.setText(String.valueOf(luotchoi));
                SharedPreferences ghi = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = ghi.edit();
                editor.putInt("luotchoi", luotchoi);
                editor.commit();

            }
        }
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
        final String s = shortAnswer.replace(",", "");
        if (getShortAnswer.length > 7) {

            for (int i = 0; i < 7; i++) {

                setLayout3(inf, s);

            }
            for (int i = 7; i < getShortAnswer.length; i++) {

                setLayout(inf, s);

            }
        } else {
            for (int i = 0; i < getShortAnswer.length; i++) {
                setLayout3(inf, s);

            }
        }
        ////chỗ này show ra các để chọn
        for (int i = 0; i < 8; i++) {
            setLayout1(inf, s, i);
        }

        for (int j = 8; j < 16; j++) {
            setLayout2(inf, s, j);
        }
    }

    private void setLayout2(LayoutInflater inf, final String s, int j) {
        View rowview = inf.inflate(R.layout.layout_item_choose1, null);
        textview2 = (TextView) rowview.findViewById(R.id.tvKyTu);
        dsOChon.add(textview2);
        imageView2 = (ImageView) rowview.findViewById(R.id.ivTileHover);
        imageView2.setImageResource(R.drawable.tilehover);
        textview2.setTag(imageView2);
        textview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTextViewOTraLoi(v, s);
            }
        });
        textview2.setText(dsItem.get(j));
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        param.gravity = Gravity.CENTER;
        rowview.setLayoutParams(param);
        layout2.addView(rowview);
    }

    public void clickTextViewOTraLoi(View v, final String s) {
        if (luotchoi == 0) {
            CustomDialogGoiY dialogGoiY = new CustomDialogGoiY();
            dialogGoiY.setCancelable(false);
            dialogGoiY.show(getFragmentManager(), "abc");
            dialogGoiY.setGoiy("Bạn đã hết lượt chơi, vui lòng quay lại sau");
            dialogGoiY.setTieude("Thông báo");
        } else {

            String chuoi = ((TextView) v).getText().toString();
            int dem = 0;
            for (int i = 0; i < index; i++) {
                if (dsODapAn.get(i).getText().toString().equals("")) {
                    dsODapAn.get(i).setText(chuoi);
                    dsODapAn.get(i).setTag(v);
                    dsIVDapAn.get(i).setImageResource(R.drawable.tilehover);
                    ((TextView) v).setText("");
                    v.setClickable(false);
                    ((ImageView) v.getTag()).setVisibility(View.INVISIBLE);
                    dem++;
                    break;
                }

            }
            if (dem == 0) {
                if (index < dsODapAn.size()) {

                    pop.setVolume(100, 100);
                    pop.start();
                    dsODapAn.get(index).setText(chuoi);
                    dsODapAn.get(index).setTag(v);
                    dsIVDapAn.get(index).setImageResource(R.drawable.tilehover);
                    index++;
                    ((TextView) v).setText("");
                    v.setClickable(false);
                    ((ImageView) v.getTag()).setVisibility(View.INVISIBLE);

                }
                if (index == dsODapAn.size()) {
                    soSanhKetQua(s);
                }
            }
        }
    }

    private void setLayout1(LayoutInflater inf, final String s, int i) {
        View rowview = inf.inflate(R.layout.layout_item_choose1, null);
        textview1 = (TextView) rowview.findViewById(R.id.tvKyTu);
        dsOChon.add(textview1);
        imageView1 = (ImageView) rowview.findViewById(R.id.ivTileHover);
        imageView1.setImageResource(R.drawable.tilehover);
        textview1.setTag(imageView1);
        textview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTextViewOTraLoi(v, s);
            }
        });
        textview1.setText(dsItem.get(i));
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        param.gravity = Gravity.CENTER;
        rowview.setLayoutParams(param);
        layout1.addView(rowview);
    }

    private void setLayout3(LayoutInflater inf, final String s) {

        View rowview = inf.inflate(R.layout.layout_item_choose, null);
        TextView textview = (TextView) rowview.findViewById(R.id.tvKyTu2);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTextViewDaChon(v, s);
            }
        });

        ImageView imageview = (ImageView) rowview.findViewById(R.id.ivTileEmpty);
        imageview.setImageResource(R.drawable.tileempty);
        imageview.setTag(textview);
        dsODapAn.add(textview);
        dsIVDapAn.add(imageview);
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams((int) px, LinearLayout.LayoutParams.WRAP_CONTENT);
        param.gravity = Gravity.CENTER;
        rowview.setLayoutParams(param);
        layout3.addView(rowview);
    }

    private void clickTextViewDaChon(View v, final String s) {
        if (!((TextView) v).getText().toString().equals("")) {
            ((ImageView) ((TextView) v.getTag()).getTag()).setVisibility(View.VISIBLE);
            ((TextView) v.getTag()).setText(((TextView) v).getText().toString());
            ((TextView) v.getTag()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTextViewOTraLoi(v, s);
                }
            });
            ((ImageView) ((TextView) v.getTag()).getTag()).setImageResource(R.drawable.tilehover);
            for (int i = 0; i < dsIVDapAn.size(); i++) {
                if (dsIVDapAn.get(i).getTag().equals(v)) {
                    dsIVDapAn.get(i).setImageResource(R.drawable.tileempty);
                    dsODapAn.get(i).setText("");
                    break;
                }
            }
            v.setTag(null);
            ((TextView) v).setText("");
        }

    }

    public void setLayout(LayoutInflater inf, final String s) {
        View rowview = inf.inflate(R.layout.layout_item_choose, null);
        TextView textview = (TextView) rowview.findViewById(R.id.tvKyTu2);
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTextViewDaChon(v, s);
            }
        });
        ImageView imageview = (ImageView) rowview.findViewById(R.id.ivTileEmpty);
        imageview.setImageResource(R.drawable.tileempty);
        imageview.setTag(textview);
        dsODapAn.add(textview);
        dsIVDapAn.add(imageview);
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, r.getDisplayMetrics());
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams((int) px, LinearLayout.LayoutParams.WRAP_CONTENT);
        param.gravity = Gravity.CENTER;
        rowview.setLayoutParams(param);
        layout.addView(rowview);

    }


    private void soSanhKetQua(String s) {
        for (int i = 0; i < dsODapAn.size(); i++) {
            chuoikq.append(dsODapAn.get(i).getText().toString());
        }
        if (s.equals(chuoikq.toString())) {
            mtrue.setVolume(100, 100);
            mtrue.start();

            boolean x = true;
            luotchoi = 5;
            SharedPreferences ghi = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = ghi.edit();
            editor.putBoolean("boolean", x);
            editor.putInt("luotchoi", luotchoi);
            editor.commit();
            animate(layout);
            animate(layout3);
            animateTV(tvSai);
            tvSai.setText("Bạn đã chọn đáp án đúng");
            tvSai.setVisibility(View.VISIBLE);
            for (int j = 0; j < dsODapAn.size(); j++) {
                dsIVDapAn.get(j).setImageResource(R.drawable.tiletrue);
            }
            CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Intent intent = new Intent(PlayActivity.this, ResultActivity.class);
                    intent.putExtra("kq", dsCauHoi.get(vitri).fullAnswer);
                    intent.putExtra("image", dsCauHoi.get(vitri).imagePath);
                    intent.putExtra("cauhoi", tvCauHoi.getText().toString());
                    startActivity(intent);
                    finish();
                }
            };
            countDownTimer.start();

        } else {
            if (luotchoi > 0) {
                luotchoi--;

                long seconds = System.currentTimeMillis() / 1000;
                SharedPreferences ghi = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = ghi.edit();
                editor.putLong("secondsout", seconds);
                editor.putInt("luotchoi", luotchoi);
                editor.apply();

            }

            btLuotChoi.setText(String.valueOf(luotchoi));
            fail.setVolume(100, 100);
            fail.start();
            animate(layout);
            animate(layout3);
            for (int i = 0; i < dsOChon.size(); i++) {
                dsOChon.get(i).setClickable(false);
            }
            tvSai.setText("Bạn đã chọn đáp án sai");
            tvSai.setVisibility(View.VISIBLE);
            for (int j = 0; j < dsODapAn.size(); j++) {
                dsIVDapAn.get(j).setImageResource(R.drawable.tilefalse);
            }

            CountDownTimer timer = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    chuoikq.setLength(0);
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

    public void setBtInvite() {
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

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    public void setMusic() {

        player = MediaPlayer.create(PlayActivity.this, R.raw.themesong);
        pop = MediaPlayer.create(PlayActivity.this, R.raw.pop);
        fail = MediaPlayer.create(PlayActivity.this, R.raw.fail);
        mtrue = MediaPlayer.create(PlayActivity.this, R.raw.mtrue);
        player.setLooping(true);
        player.setVolume(100, 100);
        player.start();
    }

    public void animate(LinearLayout ll) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        animation.setDuration(500);
        ll.setAnimation(animation);
        ll.animate();
        animation.start();

    }

    public void animateTV(TextView tv) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        animation.setDuration(500);
        tv.setAnimation(animation);
        tv.animate();
        animation.setRepeatCount(3);
        animation.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.start();
    }


}
