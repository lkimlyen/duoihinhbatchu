package com.example.yenyen.duoihinhbatchudemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    DatabaseReference mDatabase;
    Button btChoiThu;
    private CallbackManager mCallbackManager;
    LoginButton btDangNhap;
    String name;
    Integer score = 1;
    Integer money = 100;
    BigInteger id;
    private FirebaseAuth mAuth;
    private static final String TAG = "FacebookLogin";
    User user;
    private ProgressDialog mProgressDialog;
    ArrayList<String> dsId = new ArrayList<>();
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("json");
        btChoiThu = (Button) findViewById(R.id.btChoiThu);
        btDangNhap = (LoginButton) findViewById(R.id.btDangNhap);

        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        FirebaseUser mUser = mAuth.getCurrentUser();
        mDatabase.child("Users").addChildEventListener(originalListener);
        Log.e("demsoid", count + "");
        mp = MediaPlayer.create(MainActivity.this, R.raw.intro);
        mp.setLooping(true);
        mp.setVolume(100, 100);
        mp.start();

        if (mUser != null) {
            mp.stop();
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            //String uid = mAuth.getCurrentUser().getUid();
            //String image=mAuth.getCurrentUser().getPhotoUrl().toString();
            //intent.putExtra("user_id", uid);
            //if(image!=null || image!=""){
            //  intent.putExtra("profile_picture",image);
            //}
            startActivity(intent);
            finish();
//            for (UserInfo profile : mUser.getProviderData()) {
            // Id of the provider (ex: google.com)
            //  String providerId = profile.getProviderId();

            // UID specific to the provider
            //String uid = profile.getUid();

            // Name, email address, and profile photo Url
//                String name = profile.getDisplayName();
//                String email = profile.getEmail();
//                Uri photoUrl = profile.getPhotoUrl();
//                Log.d("profile", providerId+ "," + uid + "," +name + ","+ email);
//            };

            Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
        }


        Typeface typeface = Typeface.createFromAsset(MainActivity.this.getAssets(), "fonts/UTM_Cookies_0.ttf");
        btDangNhap.setTypeface(typeface);
        btDangNhap.setReadPermissions("email", "public_profile");
        btChoiThu.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });
        btDangNhap.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>()

        {
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
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });
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

    }

    public void result() {

        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON", response.getJSONObject().toString());
                try {
                    name = object.getString("name");
                    Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                    id = BigInteger.valueOf(object.getLong("id"));
                    Toast.makeText(MainActivity.this, id + "", Toast.LENGTH_SHORT).show();
                    setUpUser();
                    mDatabase.child("Users").push().setValue(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();

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
                            updateUI(user);
                            int count = dsId.size();
                            if (dsId.size() == 0) {
                                result();
                            } else {

                                for (int i = 0; i < dsId.size(); i++) {
                                    if (dsId.get(i).equals(String.valueOf(id))) {

                                    }
                                }
                            }
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

    public void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

        }
    }

    protected void setUpUser() {
        user = new User();
        user.setId(String.valueOf(id));
        user.setName(name);
        user.setScore(score);
        user.setMoney(money);
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

    ChildEventListener originalListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Log.e("Count ", "" + dataSnapshot.getChildrenCount());
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                String key = postSnapshot.getKey().toString();
                if (key.equals("id")) {
                    String value = postSnapshot.getValue().toString();
                    dsId.add(value);
                    Log.e("Get Data", key + value);
                }
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}