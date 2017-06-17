package com.example.yenyen.duoihinhbatchudemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;

public class PlayOnlineActivity extends AppCompatActivity {
    ImageView ivAvatar;
    String image, id;
    TextView tvCauHoi, tvTien;
    DatabaseReference mDatabase, getmDatabase;
    private FirebaseAuth mAuth;
    ArrayList<User> dsUser = new ArrayList<>();
    ArrayList<String> dsKey = new ArrayList<>();
    ArrayList<CauHoi> dsCauHoi = new ArrayList<>();
    LinearLayout layout, layout1, layout2, layout3;
    ImageView ivImage, imageView1, imageView2;
    StorageReference storageRef;
    Button btHint, btLuotChoi;
    String[] kyTu = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    ArrayList<String> dsItem = new ArrayList<>();
    ArrayList<TextView> dsODapAn = new ArrayList<>();
    ArrayList<ImageView> dsIVDapAn = new ArrayList<>();
    StringBuilder chuoikq;
    String goiy;
    TextView textview1, textview2, tvSai;
    int vitri = 0;
    int index = 0;
    FirebaseStorage storage;
    String imagename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_online);

        ivAvatar = (ImageView) findViewById(R.id.ivAvatar);
        tvCauHoi = (TextView) findViewById(R.id.tvCauHoi);
        tvTien = (TextView) findViewById(R.id.tvTien);
        layout = (LinearLayout) findViewById(R.id.frameLayout7);
        layout1 = (LinearLayout) findViewById(R.id.frameLayout4);
        layout2 = (LinearLayout) findViewById(R.id.frameLayout5);
        layout3 = (LinearLayout) findViewById(R.id.frameLayout6);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        btHint = (Button) findViewById(R.id.btnhint);
        btLuotChoi = (Button) findViewById(R.id.btLuotChoi);
        tvSai = (TextView) findViewById(R.id.tvSai);
        tvSai.setVisibility(View.INVISIBLE);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("json").child("Users");
        getmDatabase = FirebaseDatabase.getInstance().getReference().child("json");
        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        getUser(mUser);
        new ImageLoadTask(image, ivAvatar).execute();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mDatabase.child(snapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            String key = dataSnapshot2.getKey().toString();
                            User user = dataSnapshot2.getValue(User.class);
                            dsKey.add(key);
                            dsUser.add(user);
                            Log.e("CountdsUser", dsUser.size() + "");
                            if (dsUser.size() == dataSnapshot.getChildrenCount()) {
                                for (int i = 0; i < dsUser.size(); i++) {
                                    if (dsUser.get(i).id.toString().equals(id)) {
                                        tvCauHoi.setText(String.valueOf(dsUser.get(i).score));
                                        int cauhoi = dsUser.get(i).score - 1;
                                        tvTien.setText(String.valueOf(dsUser.get(i).money));
                                        getmDatabase.child(String.valueOf(cauhoi)).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                CauHoi cauHoi = dataSnapshot.getValue(CauHoi.class);
                                                dsCauHoi.add(cauHoi);
                                                Log.d("countDSCH", dsCauHoi.get(0).fullAnswer + "");
                                                hienthi();
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getUser(FirebaseUser user) {

        if (user != null) {
            image = mAuth.getCurrentUser().getPhotoUrl().toString();
            id = Profile.getCurrentProfile().getId();
            Log.d("idprofile", id);

        }
    }

    public void hienthi() {
        LayoutInflater inf = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        chuoikq = new StringBuilder();
        //cauHoi = dsCauHoi.get(vitri);
        goiy = dsCauHoi.get(vitri).description;
        imagename = dsCauHoi.get(vitri).imagePath;
        storageRef = storage.getReferenceFromUrl("gs://duoihinhbatchu-9cee7.appspot.com/" + imagename);
        Glide.with(PlayOnlineActivity.this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(ivImage);
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
                            tvSai.setText("Bạn đã chọn đáp án đúng");
                            tvSai.setVisibility(View.VISIBLE);
                            for (int j = 0; j < dsODapAn.size(); j++) {
                                dsIVDapAn.get(j).setImageResource(R.drawable.tiletrue);
                            }

                            Intent intent = new Intent(PlayOnlineActivity.this, ResultPlayOnlineActivity.class);
                            intent.putExtra("kq", dsCauHoi.get(vitri).fullAnswer);

                            intent.putExtra("image", dsCauHoi.get(vitri).imagePath);
                            intent.putExtra("tien",tvTien.getText().toString());
                            startActivity(intent);
                            finish();
                        } else {

                            //luotchoi--;
                            //btLuotChoi.setText(String.valueOf(luotchoi));
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
                            tvSai.setText("Bạn đã chọn đáp án đúng");
                            tvSai.setVisibility(View.VISIBLE);
                            for (int j = 0; j < dsODapAn.size(); j++) {
                                dsIVDapAn.get(j).setImageResource(R.drawable.tiletrue);
                            }
                            Intent intent = new Intent(PlayOnlineActivity.this, ResultPlayOnlineActivity.class);
                            intent.putExtra("kq", dsCauHoi.get(vitri).fullAnswer);
                            intent.putExtra("image", dsCauHoi.get(vitri).imagePath);
                            intent.putExtra("cauhoi", tvCauHoi.getText().toString());
                            intent.putExtra("tien",tvTien.getText().toString());
                            startActivity(intent);
                            finish();

                        } else {
                            v.setClickable(false);
                            // luotchoi--;
                            // btLuotChoi.setText(String.valueOf(luotchoi));
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
}
