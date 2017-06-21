package com.example.yenyen.duoihinhbatchudemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends BaseActivity {
    MediaPlayer mp;
    DatabaseReference mDatabase;
    Button btChoiThu;
    private CallbackManager mCallbackManager;
    LoginButton btDangNhap;
    private FirebaseAuth mAuth;
    private static final String TAG = "FacebookLogin";
    private ProgressDialog mProgressDialog;
    int count = 0;
    FirebaseUser mUser;
    ImageView ivNotiTangQua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("json");
        btChoiThu = (Button) findViewById(R.id.btChoiThu);
        btDangNhap = (LoginButton) findViewById(R.id.btDangNhap);
        ivNotiTangQua = (ImageView) findViewById(R.id.ivNotiTangQua);
        ivNotiTangQua.setImageResource(R.drawable.textnotitangqua);
        animate(ivNotiTangQua);

        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        mUser = mAuth.getCurrentUser();
        Log.e("demsoid", count + "");
       // setMusic();
        chekUser();
        setBtDangNhap();
        setBtChoiThu();
    }

    private void setBtChoiThu() {
        btChoiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setBtDangNhap() {

        btDangNhap.setReadPermissions("email", "public_profile");
        btDangNhap.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());


            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...

            }

            @Override
            public void onError(FacebookException error) {
                CustomDialogGoiY dialogGoiY = new CustomDialogGoiY();
                dialogGoiY.setCancelable(false);
                dialogGoiY.show(getFragmentManager(), "cde");
                dialogGoiY.setGoiy("Bạn vui lòng kết nối mạng để tiếp tục");
                dialogGoiY.setTieude("Thông báo");

            }

        });

    }

    private void setMusic() {

        mp = MediaPlayer.create(MainActivity.this, R.raw.intro);
        mp.setLooping(true);
        mp.setVolume(100, 100);
        mp.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mp.stop();
    }

    private void chekUser() {
        if (mUser != null) {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            String image = mAuth.getCurrentUser().getPhotoUrl().toString();
            if (image != null || image != "") {
                intent.putExtra("profile_picture", image);
            }
            String name = mAuth.getCurrentUser().getDisplayName().toString();
            intent.putExtra("name", name);
            startActivity(intent);
            finish();
            Log.d("profile_picture", image);
            Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
        }

    }
    public void animate(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.zoomout);
        animation.setDuration(500);
        view.setAnimation(animation);
        view.animate();
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        animation.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }


    @Override

    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        setMusic();

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        showProgressDialog();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Authentication Success",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);

                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);

                        }
                        hideProgressDialog();
                    }

                });
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            mp.stop();
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            String image = mAuth.getCurrentUser().getPhotoUrl().toString();
            if (image != null || image != "") {
                intent.putExtra("profile_picture", image);
            }
            String name = mAuth.getCurrentUser().getDisplayName().toString();
            String id = Profile.getCurrentProfile().getId();
            intent.putExtra("name", name);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
            Log.d("profile_picture", image);
            Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);

        }

        mProgressDialog.show();

    }


    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

    }


}