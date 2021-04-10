package com.gale_matany.ex2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "channel_main";
    private static final CharSequence CHANNEL_NAME = "Main Channel";
    private NotificationManager notificationManager;
    private int notificationID;

    private BroadcastReceiver batteryReceiver;
    private IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //remove app title
        Objects.requireNonNull(getSupportActionBar()).hide();
        //remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void notificationsSetup()
    {
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID, 	// Constant for Channel ID
                    CHANNEL_NAME, 	// Constant for Channel NAME
                    NotificationManager.IMPORTANCE_HIGH);  // for popup use: IMPORTANCE_HIGH

            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationID = 1;
    }

    public void notify(View view)
    {
//        String title = ((EditText)findViewById(R.id.txvTitleID)).getText().toString();
//        String text = ((EditText)findViewById(R.id.txvTextID)).getText().toString();
        String title = "battery low";
        String text = "battery low2";

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        // 3. Create & show the Notification. on Build.VERSION < OREO notification avoid CHANEL_ID
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.bell_notify)
                .setContentTitle(title + " ("+ notificationID +")")
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(notificationID++, notification);
    }

//    private void broadcastSetup()
//    {
//        // 1. Create a new Class that extends Broadcast Receiver
//
//        // 2. Create BatteryReceiver object
//        batteryReceiver = new MyReceiver();
//
//        // 3. Create IntentFilter for BATTERY_CHANGED & AIRPLANE_MODE_CHANGED broadcast
//        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//    }

//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//
//        // 4. Register the receiver to start listening for battery change messages
//        registerReceiver(batteryReceiver, filter);
//    }
//
//    @Override
//    protected void onStop()
//    {
//        super.onStop();
//
//        // 5. Un-Register the receiver to stop listening for battery change messages
//        unregisterReceiver(batteryReceiver);
//    }
}