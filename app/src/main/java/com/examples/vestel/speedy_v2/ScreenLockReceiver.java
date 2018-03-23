package com.examples.vestel.speedy_v2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by vestel on 22.03.2018.
 */

public class ScreenLockReceiver extends BroadcastReceiver {
    private String st, dy;
    private String clock;

    private DatabaseReference databaseReference, dbRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String macadresi;


    @Override
    public void onReceive(Context context, Intent intent) {
        databaseReference = FirebaseDatabase.getInstance().getReference("ScreenUnLock");
        firebaseAuth      = FirebaseAuth.getInstance();
        firebaseUser      = firebaseAuth.getCurrentUser();



        macadresi = getUserMacAddr().toLowerCase().toString();

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.e("Lock", "OF");

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.e("Lock", "ON");

            SimpleDateFormat bicim2 = new SimpleDateFormat("hh:mm:ss");
            Date tarihSaat = new Date();
            st = bicim2.format(tarihSaat);//02:17:02

            Log.e("Time : ", st);

            SimpleDateFormat day = new SimpleDateFormat("dd-M-yyyy");
            Date date = new Date();
            dy = day.format(date);

            Bundle pudsBundle = intent.getExtras();

            AddUnLockTime(st, dy);
        }

    }
    public void AddUnLockTime(String screenontime, String cday){
        ScreenLockTime screenLockTime = new ScreenLockTime(screenontime);
        String ContactsIDFromServer = databaseReference.push().getKey();
        String userid = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child(userid).child(cday).setValue(screenLockTime);
        Log.e("  Screen On Time: ",screenontime);
    }
    public static String getUserMacAddr()
    {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "null-mac";
    }

}
