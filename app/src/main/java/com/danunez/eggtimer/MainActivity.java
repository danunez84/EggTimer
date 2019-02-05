package com.danunez.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView displayCount;
    int time;
    boolean active;
    Button buttonGo;
    SeekBar mySeekBar;
    boolean stopSeekbar;
    CountDownTimer countDown;
    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayCount = findViewById(R.id.textViewCountDown);
        displayCount.setText("Initial");
        time = 5;
        active = true;
        buttonGo = findViewById(R.id.buttonGo);
        stopSeekbar = false;
        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);

        mySeekBar = findViewById(R.id.mySeekBar);
        mySeekBar.setMax(175);
        mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int minValue = 5;
                displayCount.setText(calculateTimeFormat(progress + minValue));
                time = progress + minValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public String calculateTimeFormat(int segs){
        int min = 0;
        int mySegs = segs;
        int seg = 0;

        String segundos="00";
        String minutos="0";

        min = mySegs / 60;
        if (mySegs < 60){
            seg = mySegs;
        }else {
            //seg = (int) ((segs % 60) * 0.6);
            while(mySegs > 59){
                mySegs = mySegs -60;
            }
            seg = mySegs;
        }

        if (seg < 10){
            segundos = "0"+seg;
        }else {
            segundos = ""+seg;
        }
        return minutos + min + " : " + segundos ;
    }

    public void startCountDown(View view) {

        //boolean stopSeekbar = false;
        if (active) {

            buttonGo.setText("STOP");
            mySeekBar.setEnabled(false);
            long timer = time * 1000;
            countDown = new CountDownTimer(timer, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (time >= 0 && !stopSeekbar) {
                        displayCount.setText(calculateTimeFormat(--time));
                        //time--;
                    }
                }

                @Override
                public void onFinish() {
                    mPlayer.start();
                }
            }.start();
            active = false;
        }else {
            buttonGo.setText("GO");
            mySeekBar.setEnabled(true);
            stopSeekbar = false;
            time = 31;
            active = true;
            countDown.cancel();
        }
    }
}
