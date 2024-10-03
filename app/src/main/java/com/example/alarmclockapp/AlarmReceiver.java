package com.example.alarmclockapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String alarmTone = intent.getStringExtra("ALARM_TONE");
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Uri.parse(alarmTone));
        mediaPlayer.start();

        // Start AlarmRingActivity to show snooze/dismiss options
        Intent alarmIntent = new Intent(context, AlarmRingActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.putExtra("ALARM_TONE", alarmTone);
        context.startActivity(alarmIntent);
    }
}


