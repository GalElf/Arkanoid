package com.gale_matany.ex2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.view.View;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {

    private int batteryAmount;
    private boolean isCharging;
    private final Context context;
    private final String channelId;
    private final int notificationId;
    private final NotificationManager notificationManager;
    private boolean sendNotification;

    public MyReceiver(Context context, String channelId, int notificationId, NotificationManager notificationManager){
        this.context = context;
        this.channelId = channelId;
        this.notificationId = notificationId;
        this.notificationManager = notificationManager;
        this.sendNotification = true;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();

        if(action.equals(Intent.ACTION_BATTERY_CHANGED)){
            this.batteryAmount = intent.getIntExtra("level", 0);
            int getStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            this.isCharging = BatteryManager.BATTERY_STATUS_CHARGING == getStatus;

            sendNotify();
        }
    }

    public void sendNotify()
    {
        if(this.batteryAmount <= 10 && !this.isCharging && this.sendNotification) {
            Intent intent = new Intent(this.context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this.context, 0, intent, 0);

            Notification notification = new NotificationCompat.Builder(this.context, this.channelId)
                    .setSmallIcon(R.drawable.bell_notify)
                    .setContentTitle("Low Battery")
                    .setContentText("Battery will run out soon.")
                    .setContentIntent(pendingIntent)
                    .build();
            this.notificationManager.notify(this.notificationId, notification);
            this.sendNotification = false;
        }
        if(this.batteryAmount >10 && !this.sendNotification){
            this.sendNotification = true;
        }
    }



}
