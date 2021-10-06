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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class RcCommunicationActivity extends AppCompatActivity {

    RelativeLayout layout_joystick;
    TextView text_throtell, text_yaw, text_pitch, text_roll;
    Button btn_start;
    SeekBar seek_throtell, seek_yaw;
    Switch switch_start_srv;

//    int throtell = 0, yaw = 0, pitch = 0, roll = 0;

    JoyStickClass js;

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

        text_throtell = (TextView)findViewById(R.id.text_throtell);
        text_yaw = (TextView)findViewById(R.id.text_yaw);
        text_pitch = (TextView)findViewById(R.id.text_pitch);
        text_roll = (TextView)findViewById(R.id.text_roll);

        seek_throtell = (SeekBar)findViewById(R.id.seekbar_throtell);
        seek_yaw = (SeekBar)findViewById(R.id.seekbar_yaw);

        switch_start_srv = (Switch)findViewById(R.id.switch_start_srv);

        btn_start = (Button)findViewById(R.id.btn_takeoff);

        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);

        final Intent service = new Intent(this, ClientService.class);

        js = new JoyStickClass(getApplicationContext(), layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);

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

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client.send("tf");
            }
        });

        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if(arg1.getAction() == MotionEvent.ACTION_DOWN || arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    ClientService.pitch = js.getX();
                    ClientService.roll = js.getY();

                    text_pitch.setText("Pitch : " + String.valueOf(js.getX()));
                    text_roll.setText("Roll : " + String.valueOf(js.getY()));

                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {

                    ClientService.pitch = js.getX();
                    ClientService.roll = js.getY();

                    text_pitch.setText("Pitch : " + String.valueOf(js.getX()));
                    text_roll.setText("Roll : " + String.valueOf(js.getY()));
                }
                return true;
            }
        });

        seek_throtell.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ClientService.throtell = progress;
                text_throtell.setText("Throtell : " + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek_yaw.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ClientService.yaw = progress;
                text_yaw.setText("Yaw : " + String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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