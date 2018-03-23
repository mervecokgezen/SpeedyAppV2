package com.examples.vestel.speedy_v2;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by vestel on 22.03.2018.
 */

public class ScreenLockService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public  int onStartCommand(Intent intent, int flags, int startId){
        final IntentFilter filter= new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        final BroadcastReceiver broadcastReceiver = new ScreenLockReceiver();
        registerReceiver(broadcastReceiver,filter);
        MainActivity main = new MainActivity();
        return START_STICKY; //Uygulama kapatılsa bile servisin arka planda çalışmaya devam etmesini sağlıyor!
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        ScreenLockService getService(){
            return ScreenLockService.this;
        }
    }
}
