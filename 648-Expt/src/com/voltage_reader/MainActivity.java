package com.voltage_reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {
    
    private TextView mVoltage;
    private TextView mTimeLeft;
    private int timeLeft;
    
    File f = new File("/sys/class/power_supply/battery/voltage_now");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mVoltage = (TextView) this.findViewById(R.id.Voltage);
        mTimeLeft = (TextView) this.findViewById(R.id.TimeLeft);
        timeLeft = 0;
        
        Runnable r = new Runnable() {
            @Override
            public void run() {
                mTimeLeft.setText("" + timeLeft);
                
                if (timeLeft == 0) {
                    try {
                        Scanner sc = new Scanner(f);
                        String voltage = sc.nextLine();
                        mVoltage.setText("voltage is "+voltage+"mV");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    timeLeft = 30;
                } else {
                    timeLeft--;
                }
                
                mTimeLeft.postDelayed(this, 1000);
            }
        };
        
        mTimeLeft.post(r);
        
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
