package com.example.airsim_rc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientService extends Service {

    public static int throtell = 0, yaw = 0, pitch = 0, roll = 0;
    public static double rate_of_sending = 1; // in sec
    public static boolean srv_running = false;

    Thread thread;

    public ClientService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.v("[SERVICE]", "Service is started");
                while(srv_running){
                    // send commands
                    long futuretime = System.currentTimeMillis() + (long)(1000 * ClientService.rate_of_sending);
                    while (System.currentTimeMillis() < futuretime){
                        synchronized (this){
                            try {
                                Client.send(
                                        "mv" + "@" +
                                                Integer.toString(ClientService.throtell) + "@" +
                                                Integer.toString(ClientService.yaw) + "@" +
                                                Integer.toString(ClientService.pitch) + "@" +
                                                Integer.toString(ClientService.roll)
                                );
                                Log.v("[SERVICE]", "Command Sent ..");
                                wait(futuretime - System.currentTimeMillis());
                            } catch (Exception e) {
                                Log.v("[SERVICE]", "Error in sending message to server");
                            }
                        }
                    }
                }
            }
        });
        thread.start();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.v("[SERVICE]", "Service is destroyed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
