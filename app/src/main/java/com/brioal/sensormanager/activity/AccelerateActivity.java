package com.brioal.sensormanager.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.brioal.baselib.base.BaseActivity;
import com.brioal.sensormanager.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class AccelerateActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_acce)
    TextView mTvAcce;
    @Bind(R.id.tv_gray)
    TextView mTvGray;
    @Bind(R.id.btn_begin)
    Button mBtnBegin;
    @Bind(R.id.btn_clear)
    Button mBtnClear;

    private SensorManager mManager;
    private Sensor mAcceSensor;
    private Sensor mGyroSensor;

    private float[] values = new float[6];
    private boolean isSaveing = false;
    String mFilePath = null;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.act_accelerate);
        ButterKnife.bind(this);
        mBtnBegin.setOnClickListener(this);
        mBtnClear.setOnClickListener(this);
        mManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mAcceSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGyroSensor = mManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                values[0] = sensorEvent.values[0];
                values[1] = sensorEvent.values[1];
                values[2] = sensorEvent.values[2];
                mTvAcce.setText(
                        "线性\n" +
                                "X:" + values[0] + "\n" +
                                "Y:" + values[1] + "\n" +
                                "Z:" + values[2] + "\n"
                );
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        }, mAcceSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                values[3] = sensorEvent.values[0];
                values[4] = sensorEvent.values[1];
                values[5] = sensorEvent.values[2];
                mTvGray.setText(
                        "角速度\n" +
                                "X:" + values[3] + "\n" +
                                "Y:" + values[4] + "\n" +
                                "Z:" + values[5] + "\n"
                );
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        }, mGyroSensor, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //开始记录数据
    public void startSaveLog() {
        final int[] index = {0};
        final long startTime = System.currentTimeMillis();
        final BufferedWriter br;
        File file = Environment.getExternalStorageDirectory();
        mFilePath = file.getAbsolutePath() + "/SensorLog.csv";
        try {
            br = new BufferedWriter(new FileWriter(mFilePath));
            Observable.interval(100, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    try {
                        if (isSaveing) {
                            br.write(
                                    (++index[0]) + ","
                                            + (System.currentTimeMillis() - startTime) + ","
                                            + values[0] + ","
                                            + values[1] + ","
                                            + values[2] + ","
                                            + values[3] + ","
                                            + values[4] + ","
                                            + values[5] + "\n"
                            );
                        } else {
                            br.flush();
                            br.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //停止记录
    public void stopSave() {
        isSaveing = false;
    }


    public static void startAccelerate(Context context) {
        Intent intent = new Intent(context, AccelerateActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_begin:
                if (isSaveing) {
                    isSaveing = false;

                    stopSave();
                    mBtnBegin.setText("Start");
                } else {
                    startSaveLog();
                    isSaveing = true;
                    mBtnBegin.setText("Stop");
                }
                break;
            case R.id.btn_clear:
                File file = new File(mFilePath);
                if (file.exists()) {
                    file.delete();
                }
                break;

        }
    }
}
