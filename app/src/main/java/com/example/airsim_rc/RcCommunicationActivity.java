package com.example.airsim_rc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

public class RcCommunicationActivity extends AppCompatActivity {

    RelativeLayout layout_joystick_left, layout_joystick_right;
    TextView text_throttle, text_yaw, text_pitch, text_roll;
    Button btn_start;
    Switch switch_start_srv, switch_lock_hgt;

//    int throtell = 0, yaw = 0, pitch = 0, roll = 0;

    JoyStickClass js_right, js_left;

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
        setContentView(R.layout.activity_rc_communication);

        text_throttle = (TextView)findViewById(R.id.text_throttle);
        text_yaw = (TextView)findViewById(R.id.text_yaw);
        text_pitch = (TextView)findViewById(R.id.text_pitch);
        text_roll = (TextView)findViewById(R.id.text_roll);

        switch_start_srv = (Switch)findViewById(R.id.switch_start_srv);
        switch_lock_hgt = (Switch)findViewById(R.id.switch_lock_hgt);

        btn_start = (Button)findViewById(R.id.btn_takeoff);

        layout_joystick_left = (RelativeLayout)findViewById(R.id.layout_joystick_left);
        layout_joystick_right = (RelativeLayout)findViewById(R.id.layout_joystick_right);

        final Intent service = new Intent(this, ClientService.class);

        js_right = new JoyStickClass(getApplicationContext(), layout_joystick_right, R.drawable.image_button);
        js_right.setStickSize(120, 120);
        js_right.setLayoutSize(650, 650);
        js_right.setLayoutAlpha(150);
        js_right.setStickAlpha(100);
        js_right.setOffset(90);
        js_right.setMinimumDistance(0);

        js_left = new JoyStickClass(getApplicationContext(), layout_joystick_left, R.drawable.image_button);
        js_left.setStickSize(120, 120);
        js_left.setLayoutSize(650, 650);
        js_left.setLayoutAlpha(150);
        js_left.setStickAlpha(100);
        js_left.setOffset(90);
        js_left.setMinimumDistance(0);

        switch_start_srv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
//                     start the sending service
                    ClientService.srv_running = true;
                    startService(service);
                } else {
                    ClientService.srv_running = false;
                }
            }
        });

        switch_lock_hgt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ClientService.hgt_locked = isChecked;
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client.send("tf");
            }
        });

        layout_joystick_right.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js_right.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN || arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    ClientService.pitch = js_right.getY();
                    ClientService.roll = js_right.getX();

                    text_pitch.setText("Pitch : " + String.valueOf(js_right.getY()));
                    text_roll.setText("Roll : " + String.valueOf(js_right.getX()));

                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {

                    ClientService.pitch = js_right.getY();
                    ClientService.roll = js_right.getX();

                    text_pitch.setText("Pitch : " + String.valueOf(js_right.getY()));
                    text_roll.setText("Roll : " + String.valueOf(js_right.getX()));
                }
                return true;
            }
        });

        layout_joystick_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent arg1) {
                js_left.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN || arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    ClientService.throtell = js_left.getY();
                    ClientService.yaw = js_left.getX();

                    text_throttle.setText("Throttle : " + String.valueOf(js_left.getY()));
                    text_yaw.setText("Yaw : " + String.valueOf(js_left.getX()));

                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {

                    ClientService.throtell = js_left.getY();
                    ClientService.yaw = js_left.getX();

                    text_throttle.setText("Throttle : " + String.valueOf(js_left.getY()));
                    text_yaw.setText("Yaw : " + String.valueOf(js_left.getX()));
                }
                return true;
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}