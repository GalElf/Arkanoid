package com.gale_matany.ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "channel_main";
    private static final CharSequence CHANNEL_NAME = "Main Channel";
    private NotificationManager notificationManager;
    private int notificationID;

    private MyReceiver batteryReceiver;
    private IntentFilter filter;

    private boolean runThreadGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //remove app title
        Objects.requireNonNull(getSupportActionBar()).hide();
        //remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        notificationsSetup();
        broadcastSetup();
    }

    private void broadcastSetup()
    {
        batteryReceiver = new MyReceiver(this, CHANNEL_ID, this.notificationID, this.notificationManager);
        filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
    }

    private void notificationsSetup()
    {
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationID = 1;
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        registerReceiver(batteryReceiver, filter);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        unregisterReceiver(batteryReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRunThreadGame(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        setRunThreadGame(false);
    }


    public boolean isRunThreadGame() {
        return runThreadGame;
    }

    public void setRunThreadGame(boolean runThreadGame) {
        this.runThreadGame = runThreadGame;
    }
}