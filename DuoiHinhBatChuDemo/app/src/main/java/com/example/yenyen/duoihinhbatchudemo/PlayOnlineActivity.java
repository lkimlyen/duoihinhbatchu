package com.example.yenyen.duoihinhbatchudemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.facebook.share.widget.ShareDialog;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
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
import java.util.Comparator;

public class PlayOnlineActivity extends BaseActivity {
    ImageView ivAvatar, ivPictureBorder, ivAvatarKhung;
    String image, id, goiy, imagename;
    TextView tvCauHoi, tvTien;
    DatabaseReference mDatabase, getmDatabase;
    private FirebaseAuth mAuth;
    ArrayList<User> dsUser = new ArrayList<>();
    ArrayList<String> dsKey = new ArrayList<>();
    ArrayList<CauHoi> dsCauHoi = new ArrayList<>();
    LinearLayout layout, layout1, layout2, layout3;
    ImageView ivImage, imageView1, imageView2, ivTien;
    StorageReference storageRef;
    Button btHint, btLuotChoi, btInvite, btnMenu;
    String[] kyTu = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    ArrayList<String> dsItem = new ArrayList<>();
    ArrayList<TextView> dsODapAn = new ArrayList<>();
    ArrayList<ImageView> dsIVDapAn = new ArrayList<>();
    ArrayList<TextView> dsOChon = new ArrayList<>();
    StringBuilder chuoikq;
    TextView textview1, textview2, tvSai, tvTruDiem;
    int vitri = 0;
    int index = 0;
    FirebaseStorage storage;
    FirebaseUser mUser;
    Boolean aBoolean;
    private RewardedVideoAd mAd;
    long secondsout, secondsin;
    MediaPlayer player, pop, fail, mtrue;
    Boolean mBool;
    int luotchoi = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_online);

        mAd = MobileAds.getRewardedVideoAdInstance(this);

        anhXa();
        setImageView();
        getData();
        getUser(mUser);
        if (isOnline() == false) {
            Toast.makeText(this, "Kết nối mạng đã bị ngắt", Toast.LENGTH_LONG).show();
        }
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    dsKey.add(snapshot.getKey());
                    mDatabase.child(snapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {

                            User user = dataSnapshot2.getValue(User.class);
                            dsUser.add(user);
                            Log.e("CountdsUser", dsUser.size() + "");
                            if (dsUser.size() == dataSnapshot.getChildrenCount()) {
                                setBtMenu();
                                for (int i = 0; i < dsUser.size(); i++) {
                                    if (dsUser.get(i).id.toString().equals(id)) {
                                        tvCauHoi.setText(String.valueOf(dsUser.get(i).score));
                                        int cauhoi = dsUser.get(i).score - 1;
                                        tvTien.setText(dsUser.get(i).money + "$");
                                        setBtHint(i);
                                        mAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
                                            @Override
                                            public void onRewardedVideoAdLoaded() {
                                                if (mAd.isLoaded()) {
                                                    mAd.show();
                                                }
                                            }

                                            @Override
                                            public void onRewardedVideoAdOpened() {

                                            }

                                            @Override
                                            public void onRewardedVideoStarted() {

                                            }

                                            @Override
                                            public void onRewardedVideoAdClosed() {

                                            }

                                            @Override
                                            public void onRewarded(RewardItem rewardItem) {
                                                Toast.makeText(PlayOnlineActivity.this, "Bạn được tặng 10$", Toast.LENGTH_SHORT).show();

//
                                                // Reward the user.
                                            }

                                            @Override
                                            public void onRewardedVideoAdLeftApplication() {

                                            }

                                            @Override
                                            public void onRewardedVideoAdFailedToLoad(int i) {
                                                Toast.makeText(PlayOnlineActivity.this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        xemQuangCaoTangTien(i);
                                        getmDatabase.child(String.valueOf(cauhoi)).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                CauHoi cauHoi = dataSnapshot.getValue(CauHoi.class);
                                                dsCauHoi.add(cauHoi);
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
        setBtInvite();
        setMusic();
        setLuotChoi();
    }

    private void loadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-9912852468951137/6325122005", new AdRequest.Builder().addTestDevice("897C0897019A2718E2407F03124C44D7").build());
    }

    private void getData() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("json").child("Users");
        getmDatabase = FirebaseDatabase.getInstance().getReference().child("json");
        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    private void anhXa() {
        ivAvatar = (ImageView) findViewById(R.id.ivAvatar);
        tvCauHoi = (TextView) findViewById(R.id.tvCauHoi);
        tvTien = (TextView) findViewById(R.id.tvTien);
        layout = (LinearLayout) findViewById(R.id.frameLayout7);
        layout1 = (LinearLayout) findViewById(R.id.frameLayout4);
        layout2 = (LinearLayout) findViewById(R.id.frameLayout5);
        layout3 = (LinearLayout) findViewById(R.id.frameLayout6);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        ivTien = (ImageView) findViewById(R.id.ivTien);
        btHint = (Button) findViewById(R.id.btnhint);
        btLuotChoi = (Button) findViewById(R.id.btLuotChoi);
        tvSai = (TextView) findViewById(R.id.tvSai);
        tvSai.setVisibility(View.INVISIBLE);
        btInvite = (Button) findViewById(R.id.btInvite);
        ivAvatarKhung = (ImageView) findViewById(R.id.ivAvatarKhung);
        ivPictureBorder = (ImageView) findViewById(R.id.ivPictureBorder);
        tvTruDiem = (TextView) findViewById(R.id.tvTruDiem);
        tvTruDiem.setVisibility(View.INVISIBLE);
        btnMenu = (Button) findViewById(R.id.btnMenu);
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
                secondsout = System.currentTimeMillis() / 1000;
                btLuotChoi.setText(String.valueOf(luotchoi));
                SharedPreferences ghi = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = ghi.edit();
                editor.putInt("luotchoi", luotchoi);
                editor.putLong("secondsout", secondsout);
                editor.commit();
            }

        }
    }

    private void setImageView() {
        ivAvatarKhung.setImageResource(R.drawable.avataricon);
        ivPictureBorder.setImageResource(R.drawable.pictureborder);
    }

    private void getUser(FirebaseUser user) {

        if (user != null) {
            image = mAuth.getCurrentUser().getPhotoUrl().toString();
            id = Profile.getCurrentProfile().getId();
            Log.d("idprofile", id);
            new ImageLoadTask(image, ivAvatar).execute();


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
                    if (mBool == true) {

                        pop.setVolume(100, 100);
                        pop.start();

                    } else

                    {
                        pop.start();
                        pop.pause();
                    }
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
        Log.d("dapan", chuoikq.toString());
        if (s.equals(chuoikq.toString())) {
            if (mBool == true) {
                mtrue.setVolume(100, 100);
                mtrue.start();

            } else

            {
                mtrue.start();
                mtrue.pause();
            }
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
                    Intent intent = new Intent(PlayOnlineActivity.this, ResultPlayOnlineActivity.class);
                    intent.putExtra("kq", dsCauHoi.get(vitri).fullAnswer);
                    intent.putExtra("image", dsCauHoi.get(vitri).imagePath);
                    intent.putExtra("cauhoi", tvCauHoi.getText().toString());
                    intent.putExtra("statusmusic", mBool);
                    startActivity(intent);
                    finish();
                }
            };
            countDownTimer.start();

        } else

        {
            if (mBool == true) {
                fail.setVolume(100, 100);
                fail.start();

            } else

            {
                fail.start();
                fail.pause();
            }
            animate(layout);
            animate(layout3);
            for (int i = 0; i < dsOChon.size(); i++) {
                dsOChon.get(i).setClickable(false);
            }
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

    public void animate(LinearLayout ll) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        animation.setDuration(500);
        ll.setAnimation(animation);
        ll.animate();
        animation.start();

    }

    public void animateSlideup(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slideup);
        animation.setDuration(3000);
        view.setAnimation(animation);
        view.animate();
        animation.start();

    }

    public void animateTV(TextView tv) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        animation.setDuration(500);
        tv.setAnimation(animation);
        tv.animate();
        animation.setRepeatCount(3);
        animation.setRepeatMode(Animation.REVERSE);
        animation.start();

    }

    public void setBtInvite() {
        btInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogInvite(PlayOnlineActivity.this);
            }
        });
    }

    public static void openDialogInvite(final Activity activity) {

        String appLinkUrl;

        appLinkUrl = "https://www.facebook.com/yanyan0110";
        // previewImageUrl = "https://www.example.com/my_invite_image.jpg";

        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .build();

            AppInviteDialog appInviteDialog = new AppInviteDialog(activity);
            CallbackManager sCallbackManager = CallbackManager.Factory.create();
            appInviteDialog.registerCallback(sCallbackManager, new FacebookCallback<AppInviteDialog.Result>() {
                @Override
                public void onSuccess(AppInviteDialog.Result result) {

                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onError(FacebookException e) {
                }
            });

            appInviteDialog.show(content);
        }
    }


    public void setBtHint(final int i) {

        SharedPreferences lay = getPreferences(MODE_PRIVATE);
        aBoolean = lay.getBoolean("boolean", true);
        Log.d("getBoolean", String.valueOf(aBoolean));

        btHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dsUser.get(i).money < 20 && aBoolean == true) {
                    Toast.makeText(PlayOnlineActivity.this, "Bạn không đủ tiền để xem gợi ý", Toast.LENGTH_SHORT).show();
                } else {
                    if (aBoolean == true) {
                        tvTruDiem.setVisibility(View.VISIBLE);
                        animateSlideup(tvTruDiem);
                        CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                int money = dsUser.get(i).money - 20;
                                mDatabase.child(dsKey.get(i)).child("money").setValue(money);
                                tvTien.setText(String.valueOf(money) + "$");

                                Toast.makeText(PlayOnlineActivity.this, "Bạn bị trừ 20$", Toast.LENGTH_LONG).show();
                                tvTruDiem.setVisibility(View.INVISIBLE);
                            }
                        };
                        countDownTimer.start();
                        aBoolean = false;
                    }
                    boolean x = aBoolean;
                    SharedPreferences ghi = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = ghi.edit();
                    editor.putBoolean("boolean", x);
                    editor.commit();
                    CustomDialogGoiY dialogGoiY = new CustomDialogGoiY();
                    dialogGoiY.setCancelable(false);
                    dialogGoiY.show(getFragmentManager(), "cde");
                    dialogGoiY.setGoiy(goiy);
                    dialogGoiY.setTieude("Gợi ý");


                }
            }
        });

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void setMusic() {
        mBool = getIntent().getBooleanExtra("statusmusic", true);
        Log.d("statusmusic", mBool + "");
        player = MediaPlayer.create(PlayOnlineActivity.this, R.raw.themesong);
        pop = MediaPlayer.create(PlayOnlineActivity.this, R.raw.pop);
        fail = MediaPlayer.create(PlayOnlineActivity.this, R.raw.fail);
        mtrue = MediaPlayer.create(PlayOnlineActivity.this, R.raw.mtrue);
        if (mBool == true) {
            player.setLooping(true);
            player.setVolume(100, 100);
            player.start();

        } else

        {
            player.start();
            player.pause();
        }
    }

    public void xemQuangCaoTangTien(final int i) {
        SharedPreferences lay = getPreferences(MODE_PRIVATE);
        secondsout = lay.getLong("seconds", 0);
        secondsin = System.currentTimeMillis() / 1000;
        Log.d("secondsout", secondsout + "");
        Log.d("secondsin", secondsin + "");
        Log.d("secondsoutin", (secondsin - secondsout) + "");
        if ((secondsin - secondsout) >= 3600) {
            ivTien.setImageResource(R.drawable.coinicon);
            ivTien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long seconds = System.currentTimeMillis() / 1000;
                    SharedPreferences ghi = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = ghi.edit();
                    editor.putLong("seconds", seconds);
                    editor.apply();
                    Log.d("seconds", String.valueOf(seconds));
                    int money = dsUser.get(i).money + 10;
                    mDatabase.child(dsKey.get(i)).child("money").setValue(money);
                    Toast.makeText(PlayOnlineActivity.this, "Đang load quảng cáo...", Toast.LENGTH_SHORT).show();
                    loadRewardedVideoAd();
                }
            });
        } else {
            ivTien.setImageResource(R.drawable.coinicongray);
            ivTien.setOnClickListener(null);
        }
    }

    private void setBtMenu() {
        Collections.sort(dsUser, new Comparator<User>() {
            public int compare(User o1, User o2) {
                if (o1.score == o2.score)
                    return 0;
                return o1.score >

                        o2.score ? -1 : 1;
            }
        });
        for (int i = 0; i < dsUser.size(); i++) {
            if (dsUser.get(i).id.toString().equals(id)) {
                final int score = dsUser.get(i).score;
                final int money = dsUser.get(i).money;
                final String name = dsUser.get(i).name;
                final ShareDialog shareDialog = new ShareDialog(PlayOnlineActivity.this);
                final View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                btnMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialogMenu dialogMenu = new CustomDialogMenu();
                        dialogMenu.setCancelable(false);
                        dialogMenu.show(getFragmentManager(), "abc");
                        dialogMenu.setName(name);
                        dialogMenu.setImage(image);
                        dialogMenu.setMoney(money);
                        dialogMenu.setScore(score);
                        dialogMenu.setLuotchoi(Integer.parseInt(btLuotChoi.getText().toString()));
                        dialogMenu.setRootView(rootView);
                        dialogMenu.setContext(PlayOnlineActivity.this);
                        dialogMenu.setShareDialog(shareDialog);
                        dialogMenu.setUsers(dsUser);
                    }
                });
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        mAd.destroy(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        mAd.resume(this);
        super.onResume();
        if (mBool == true) {
            player.setLooping(true);
            player.setVolume(100, 100);
            player.start();

        } else

        {
            player.start();
            player.pause();
        }
    }

    @Override
    protected void onPause() {
        mAd.pause(this);
        super.onPause();
        player.pause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
