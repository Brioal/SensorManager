package com.brioal.sensormanager.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import com.brioal.baselib.base.BaseActivity;
import com.brioal.sensormanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class GyroActivity extends BaseActivity {


    @Bind(R.id.tv_gryo)
    TextView mTvResult;

    private SensorManager mManager;
    private Sensor mGryo;
    private SensorEventListener mListener;

    private float[] gryo = new float[3];

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.act_orientation);
        ButterKnife.bind(this);
        mManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mGryo = mManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                gryo[0] = event.values[0];
                gryo[1] = event.values[1];
                gryo[2] = event.values[2];
                mTvResult.setText(
                        "X:" + gryo[0] + "\n" +
                                "Y:" + gryo[1] + "\n" +
                                "Z:" + gryo[2]
                );

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mManager.registerListener(mListener, mGryo, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mManager != null) {
            mManager.unregisterListener(mListener);
        }
    }

    public static void startOrientation(Context context) {
        Intent intent = new Intent(context, GyroActivity.class);
        context.startActivity(intent);
    }
}
