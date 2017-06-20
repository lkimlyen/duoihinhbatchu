package com.example.yenyen.duoihinhbatchudemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
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

public class ResultPlayOnlineActivity extends BaseActivity {
    TextView textView, tvCauHoi, tvTien;
    ImageView imageView, ivAvatar, ivAvatarKhung, ivCoinIcon, ivPictureBorder, ivDolaIcon;
    ShareDialog shareDialog;
    Button btChoiTiep, btShare;
    String imageName;
    StorageReference storageRef;
    FirebaseStorage storage;
    private FirebaseAuth mAuth;
    String image, userId;
    DatabaseReference mDatabase;
    ArrayList<User> dsUser = new ArrayList<>();
    ArrayList<String> dskey = new ArrayList<>();
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_play_online);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        getUser(mUser);
        shareDialog = new ShareDialog(ResultPlayOnlineActivity.this);
        rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("json").child("Users");

        anhxa();

        setImageView();

        textView.setText(getIntent().getStringExtra("kq"));
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    dskey.add(snapshot.getKey());
                    mDatabase.child(snapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            User user = dataSnapshot2.getValue(User.class);
                            dsUser.add(user);
                            if (dsUser.size() == dataSnapshot.getChildrenCount()) {
                                for (int i = 0; i < dsUser.size(); i++) {
                                    if (dsUser.get(i).id.toString().equals(userId)) {
                                        tvTien.setText(String.valueOf(dsUser.get(i).money) + "$");
                                        tvCauHoi.setText(String.valueOf(dsUser.get(i).score));
                                        int score = dsUser.get(i).score + 1;
                                        int money = dsUser.get(i).money + 5;
                                        Log.d("score", String.valueOf(score));
                                        Log.d("money", String.valueOf(money));
                                        mDatabase.child(dskey.get(i)).child("score").setValue(score);
                                        mDatabase.child(dskey.get(i)).child("money").setValue(money);

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

        setBtChoiTiep();
        setBtShare();
    }

    private void setBtChoiTiep() {
        btChoiTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean x = true;
                SharedPreferences ghi = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = ghi.edit();
                editor.putBoolean("boolean", x);
                editor.commit();
                Intent intent = new Intent(ResultPlayOnlineActivity.this, PlayOnlineActivity.class);
                boolean mBool = getIntent().getBooleanExtra("statusmusic", true);
                intent.putExtra("statusmusic", mBool);
                startActivity(intent);
                finish();

            }
        });
    }

    private void setImageView() {
        new ImageLoadTask(image, ivAvatar).execute();
        imageName = getIntent().getStringExtra("image");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://duoihinhbatchu-9cee7.appspot.com/" + imageName);
        Glide.with(ResultPlayOnlineActivity.this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(imageView);
        ivPictureBorder.setImageResource(R.drawable.pictureborder);
        ivCoinIcon.setImageResource(R.drawable.coinicon);
        ivAvatarKhung.setImageResource(R.drawable.avataricon);
        ivDolaIcon.setImageResource(R.drawable.dolaicon);
    }

    private void anhxa() {
        textView = (TextView) findViewById(R.id.tvKetQua);
        tvCauHoi = (TextView) findViewById(R.id.tvCauHoi);
        tvTien = (TextView) findViewById(R.id.tvTien);
        imageView = (ImageView) findViewById(R.id.ivImageCH);
        ivAvatar = (ImageView) findViewById(R.id.ivAvatar);
        btChoiTiep = (Button) findViewById(R.id.btChoiTiep);
        ivAvatarKhung = (ImageView) findViewById(R.id.ivAvatarKhung);
        ivDolaIcon = (ImageView) findViewById(R.id.ivDolaIcon);
        ivPictureBorder = (ImageView) findViewById(R.id.ivPictureBorder);
        ivCoinIcon = (ImageView) findViewById(R.id.ivCoinIcon);
        btShare = (Button) findViewById(R.id.btShare);
    }

    private void setBtShare() {
        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = takeScreenshot();
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bitmap)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content);
            }
        });
    }

    private void getUser(FirebaseUser user) {
        if (user != null) {
            image = mAuth.getCurrentUser().getPhotoUrl().toString();
            userId = Profile.getCurrentProfile().getId();
        }
    }

    private Bitmap takeScreenshot() {
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

}
