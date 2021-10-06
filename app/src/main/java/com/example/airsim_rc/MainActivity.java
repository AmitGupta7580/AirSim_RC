package com.example.airsim_rc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button btn_wired;
    Button btn_wireless;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            Log.v("[APP]", "There is not Action Bar previously...");
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        btn_wired = (Button)findViewById(R.id.btn_wired);
        btn_wireless = (Button)findViewById(R.id.btn_wireless);

        btn_wireless.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connect_wireless();
                    }
                }
        );

        btn_wired.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connect_wired();
                    }
                }
        );
    }

    public void connect_wireless(){
        Intent wireless_con_intent = new Intent(this, WirelessConnectionActivity.class);
        startActivity(wireless_con_intent);
    }

    public void connect_wired(){
//        Intent wireless_con_intent = new Intent(this, WirelessConnectionActivity.class);
//        startActivity(wireless_con_intent);
        Log.v("[APP]", "To be implemented");
    }
}