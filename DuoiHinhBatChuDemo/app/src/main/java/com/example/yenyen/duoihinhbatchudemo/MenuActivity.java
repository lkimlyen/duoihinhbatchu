package com.example.yenyen.duoihinhbatchudemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MenuActivity extends BaseActivity {
    ToggleButton btmusic;
    MediaPlayer player;
    boolean mBool = true;
    String name, image, id, userId;
    Button btProfile, btChoiNgay, btBXH, btLogout;
    DatabaseReference mDatabase;
    ArrayList<User> dsUser = new ArrayList<>();
    ArrayList<String> dsId = new ArrayList<>();
    Boolean aBoolean = true;
    private FirebaseAuth mAuth;
    int score, money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("json").child("Users");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        getUser(mUser);

        btmusic = (ToggleButton) findViewById(R.id.btmusic);
        btProfile = (Button) findViewById(R.id.btProfile);
        btChoiNgay = (Button) findViewById(R.id.btChoiNgay);
        btBXH = (Button) findViewById(R.id.btBXH);
        btLogout = (Button) findViewById(R.id.btLogout);

        getValueFromMainActivity();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    mDatabase.child(snapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            User user = dataSnapshot2.getValue(User.class);
                            dsUser.add(user);
                            for (DataSnapshot postSnapshot : dataSnapshot2.getChildren()) {
                                String key = postSnapshot.getKey().toString();
                                if (key.equals("id")) {
                                    String value = postSnapshot.getValue().toString();
                                    dsId.add(value);
                                }
                            }
                            if (dsId.size() == dataSnapshot.getChildrenCount() && aBoolean == true) {
                                insertUser();

                            }
                            if (dsUser.size() == dataSnapshot.getChildrenCount()) {
                                goiDiaglogUser();
                                goiDialogBXH();

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


        setMusic();
        btChoiNgay();
        showProgressDialog();
        setBtLogout();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();

    }

    public void getValueFromMainActivity() {
        name = getIntent().getStringExtra("name");
        image = getIntent().getStringExtra("profile_picture");
        id = getIntent().getStringExtra("id");
    }

    public void setBtLogout() {
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                getUser(null);
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    public void insertUser() {
        int count = dsId.size();
        int dem = 0;

        if (id != null) {
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    if (dsId.get(i).toString().equals(id)) {
                        dem++;
                    }
                }
                if (dem > 0) {
                    Log.d("PushServer", "Push không thành công");

                } else {
                    User user = new User(id, name, image, 1, 100);
                    mDatabase.push().setValue(user);
                    aBoolean = false;
                }
            } else {
                User user = new User(id, name, image, 1, 100);
                mDatabase.push().setValue(user);
                aBoolean = false;

            }
        }
    }

    public void goiDiaglogUser() {
        for (int i = 0; i < dsUser.size(); i++) {
            if (dsUser.get(i).id.toString().equals(userId)) {
                score = dsUser.get(i).score;
                money = dsUser.get(i).money;
                btProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CustomDialogUser dialogUser = new CustomDialogUser();
                        dialogUser.setCancelable(false);
                        dialogUser.show(getFragmentManager(), "abc");
                        dialogUser.setName(name);
                        dialogUser.setImage(image);
                        dialogUser.setMoney(money);
                        dialogUser.setScore(score);
                    }
                });
                break;
            }
        }
    }

    public void btChoiNgay() {
        btChoiNgay.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PlayOnlineActivity.class);
                intent.putExtra("image", image);
                startActivity(intent);
            }
        });
    }

    private void getUser(FirebaseUser user) {
        if (user != null) {
            userId = Profile.getCurrentProfile().getId();
        }
    }

    public void goiDialogBXH() {
        Collections.sort(dsUser, new Comparator<User>() {
            public int compare(User o1, User o2) {
                if (o1.score == o2.score)
                    return 0;
                return o1.score >

                        o2.score ? -1 : 1;
            }
        });
        Log.d("dsUser.count", dsUser.size() + "");
        btBXH.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                CustomDialogBXH dialogBXH = new CustomDialogBXH();
                dialogBXH.setCancelable(false);
                dialogBXH.show(getFragmentManager(), "abc");
                dialogBXH.setUsers(dsUser);
                dialogBXH.setContext(MenuActivity.this);
            }
        });
    }

    public void setMusic() {
        SharedPreferences lay = getPreferences(MODE_PRIVATE);
        mBool = lay.getBoolean("boolean", true);
        btmusic.setChecked(mBool);

        player = MediaPlayer.create(MenuActivity.this, R.raw.intro);
        if (btmusic.isChecked())

        {
            player.setLooping(true);
            player.setVolume(100, 100);
            player.start();

        } else

        {
            player.start();
            player.pause();
        }

        btmusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("boolean", String.valueOf(isChecked));
                if (isChecked) {
                    mBool = true;
                    player.setLooping(true);
                    player.setVolume(100, 100);
                    player.start();
                    boolean x = mBool;
                    SharedPreferences ghi = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = ghi.edit();
                    editor.putBoolean("boolean", x);
                    editor.commit();
                } else {
                    mBool = false;
                    player.start();
                    player.pause();
                    boolean x = mBool;
                    SharedPreferences ghi = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = ghi.edit();
                    editor.putBoolean("boolean", x);
                    editor.commit();
                }
            }
        });

    }

    private void showProgressDialog() {
        final ProgressDialog pd = new ProgressDialog(MenuActivity.this);
        pd.setMessage("Đang tải dữ liệu...");
        pd.show();
        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                pd.dismiss();
            }
        };
        countDownTimer.start();
    }
}
