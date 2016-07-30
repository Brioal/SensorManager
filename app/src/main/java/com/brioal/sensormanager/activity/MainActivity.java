package com.brioal.sensormanager.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.brioal.baselib.base.BaseActivity;
import com.brioal.sensormanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.main_btn_light)
    Button mBtnLight;
    @Bind(R.id.main_btn_gyro)
    Button mBtnAccelerate;
    @Bind(R.id.main_btn_orientation)
    Button mBtnOrientation;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mBtnLight.setOnClickListener(this);
        mBtnAccelerate.setOnClickListener(this);
        mBtnOrientation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_light:
                LightSensorActivity.startLight(mContext);
                break;
            case R.id.main_btn_gyro:
                AccelerateActivity.startAccelerate(mContext);
                break;
            case R.id.main_btn_orientation:
                GyroActivity.startOrientation(mContext);
                break;
        }
    }

}
