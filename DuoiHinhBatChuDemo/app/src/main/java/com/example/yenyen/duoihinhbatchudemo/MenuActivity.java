package com.example.yenyen.duoihinhbatchudemo;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MenuActivity extends AppCompatActivity {
    ToggleButton btmusic;
    MediaPlayer player;
    boolean mBool = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btmusic = (ToggleButton) findViewById(R.id.btmusic);

        SharedPreferences lay = getPreferences(MODE_PRIVATE);
        mBool = lay.getBoolean("boolean",true);

        btmusic.setChecked(mBool);

        player = MediaPlayer.create(MenuActivity.this, R.raw.intro);
        if (btmusic.isChecked()) {
            player.setLooping(true);
            player.setVolume(100, 100);
            player.start();

        } else {
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
                } else {
                    mBool = false;
                    player.start();
                    player.pause();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();
        boolean x = mBool;
        SharedPreferences ghi = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = ghi.edit();
        editor.putBoolean("boolean", x);
        editor.commit();
    }
}
