package com.immortals.tw;

import android.app.Application;
import android.content.Context;
import com.global.sdk.NCGSDK;

import androidx.multidex.MultiDex;


/**
 * Created by LioN on 2018/11/27.
 * Func：
 */
public class OverSeaApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        NCGSDK.initApplication(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
