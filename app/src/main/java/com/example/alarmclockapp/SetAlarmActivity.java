package com.example.alarmclockapp;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class SetAlarmActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button chooseToneButton, saveAlarmButton;
    private Uri alarmTone;

    private AlarmDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        timePicker = findViewById(R.id.timePicker);
        chooseToneButton = findViewById(R.id.choose_tone_button);
        saveAlarmButton = findViewById(R.id.save_alarm_button);

        dbHelper = new AlarmDatabaseHelper(this);

        // Default alarm tone
        alarmTone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        chooseToneButton.setOnClickListener(v -> {
            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Alarm Tone");
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, alarmTone);
            startActivityForResult(intent, 1);
        });

        saveAlarmButton.setOnClickListener(v -> {
            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();
            String time = String.format("%02d:%02d", hour, minute);
            AlarmModel alarm = new AlarmModel(0, time, alarmTone.toString(), true);
            dbHelper.addAlarm(alarm);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            alarmTone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
        }
    }
}


