package com.brioal.sensormanager;

import android.content.Intent;
import android.os.Bundle;

import com.brioal.baselib.base.BaseActivity;
import com.brioal.baselib.util.BrioalUtil;
import com.brioal.sensormanager.activity.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LauncherActivity extends BaseActivity {


    @Override
    public void initData() {
        setTheme(R.style.AppTheme_NoActionBar);
        super.initData();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.act_launcher);
        BrioalUtil.init(mContext);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

}
