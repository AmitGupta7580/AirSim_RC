package com.example.airsim_rc;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static Socket socket = null;
    private static DataOutputStream out = null;
    public static boolean connected = false;

    // constructor to put ip address and port
    public Client(final String host, final int port){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(host, port);
                    out = new DataOutputStream(socket.getOutputStream());
                    Client.connected = true;
                } catch (UnknownHostException e) {
                    Log.v("[CLIENT]", "HOST NOT REACHABLE");
                } catch (IOException e) {
                    Log.v("[CLIENT]", "IOException in socket");
                }
            }
        });

        thread.start();
    }

    public static void send(final String data){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    out.writeUTF(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void close() throws IOException{
        socket.close();
        out.close();
    }
}
