package com.examples.vestel.speedy_v2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by vestel on 23.03.2018.
 */

public class ControlService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static Timer timer = new Timer();
    private Context context;

    public void onCreate(){
        super.onCreate();
        context = this;
        startService();
    }
    private void startService(){
        timer.scheduleAtFixedRate(new mainTask(),0, 1000);
    }
    private class mainTask extends TimerTask{
        @Override
        public void run() {
            toastHandler.sendEmptyMessage(0);
        }
    }
    public void onDestroy(){
        super.onDestroy();
        Log.e("SERVICE STOPPPPP","OOOOPS");
    }

    private final Handler toastHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.e("Success","Succeesssss");
        }
    };
}
