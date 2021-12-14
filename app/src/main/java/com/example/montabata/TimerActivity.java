package com.example.montabata;

import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {
    private TextView showTimer;
    private Button startTimer;
    private Button cancelTimer;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        showTimer = (TextView) findViewById(R.id.showTimer);
        startTimer = (Button) findViewById(R.id.startButton);
        cancelTimer = (Button) findViewById(R.id.cancelButton);
    }

    public void StartTimer(View v){
        countDownTimer = new CountDownTimer(5 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                showTimer.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                showTimer.setText("0");

            }
        };
        countDownTimer.start();
    }

    public void CancelTimer(View v){
        if (countDownTimer != null);
        countDownTimer.cancel();
        countDownTimer = null;
    }
}