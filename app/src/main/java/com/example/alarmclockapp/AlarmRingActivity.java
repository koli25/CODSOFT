package com.example.alarmclockapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AlarmRingActivity extends AppCompatActivity {

    private Button snoozeButton, dismissButton;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ring);

        snoozeButton = findViewById(R.id.snooze_button);
        dismissButton = findViewById(R.id.dismiss_button);

        String alarmTone = getIntent().getStringExtra("ALARM_TONE");
        mediaPlayer = MediaPlayer.create(this, Uri.parse(alarmTone));
        mediaPlayer.start();

        snoozeButton.setOnClickListener(v -> {
            snoozeAlarm();
            finish();
        });

        dismissButton.setOnClickListener(v -> {
            mediaPlayer.stop();
            mediaPlayer.release();
            finish();
        });
    }

    private void snoozeAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("ALARM_TONE", getIntent().getStringExtra("ALARM_TONE"));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5 * 60 * 1000, pendingIntent);
    }
}
