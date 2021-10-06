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
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WirelessConnectionActivity extends AppCompatActivity {

    EditText inp_host, inp_port;
    Button btn_con;

    Client client = null;

    private static final String IPV4_REGEX =
                    "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    private static final Pattern IPv4_PATTERN = Pattern.compile(IPV4_REGEX);

    public static boolean isValidIP(String ip){
        if (ip == null || ip.isEmpty()){
            return false;
        }
        Matcher matcher = IPv4_PATTERN.matcher(ip);
        return matcher.matches();
    }

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
        setContentView(R.layout.activity_wireless_connection);

        inp_host = (EditText)findViewById(R.id.inp_ip);
        inp_port = (EditText)findViewById(R.id.inp_port);
        btn_con = (Button)findViewById(R.id.btn_con);

        btn_con.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        connect();
                    }
                }
        );
    }

    public void connect(){
        Log.v("[APP]", "Connect Button Pressed");
        String HOST = inp_host.getText().toString();
        boolean valid = isValidIP(HOST);
        int PORT = 2222;
        if(valid && !inp_port.getText().toString().isEmpty()){
            try{
                PORT = Integer.parseInt(inp_port.getText().toString());
            } catch (Exception e){
                valid = false;
            }
        } else {
            valid = false;
        }
        if(valid){
            client = new Client(HOST, PORT);

            // check in 200ms for 2s
            long futuretime = System.currentTimeMillis() + 5000;
            while(System.currentTimeMillis() < futuretime){
                synchronized (this){
                    if(Client.connected){
                        Toast.makeText(this, "Server Connected", Toast.LENGTH_LONG).show();
                        Intent rc_com_intent = new Intent(this, RcCommunicationActivity.class);
                        startActivity(rc_com_intent);
                    } else {
                        try {
                            wait(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            Toast.makeText(this, "Server Not Reachable", Toast.LENGTH_LONG).show();

        } else {
//            Log.v("[APP]", "Please enter Ip and PORT");
            Toast.makeText(this, "Enter Valid IP and PORT", Toast.LENGTH_LONG).show();
        }
    }
}