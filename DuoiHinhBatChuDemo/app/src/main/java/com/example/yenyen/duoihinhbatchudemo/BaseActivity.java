package com.example.yenyen.duoihinhbatchudemo;

import android.app.Activity;
import android.content.Context;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by yenyen on 6/18/2017.
 */

public class BaseActivity extends Activity{
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
