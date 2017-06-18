package com.example.yenyen.duoihinhbatchudemo;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by yenyen on 6/18/2017.
 */

public class CalligraphyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getResources().getString(R.string.font_name))
                .setFontAttrId(R.attr.fontPath)
                .build());
    }
}
