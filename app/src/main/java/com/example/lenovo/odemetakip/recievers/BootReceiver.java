package com.example.lenovo.odemetakip.recievers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.lenovo.odemetakip.services.BildirimServisi;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        //telefon kapanıp açıldıgında burası tetiklenecek.

        //bildirim alarmı için.
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent bildirimIntent2=new Intent(context, BildirimServisi.class);
        PendingIntent pendingIntent=PendingIntent.getService(context,100, bildirimIntent2,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,10000,3600000,pendingIntent);
        ///


    }
}
