package com.example.alarmclockapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import missing.namespace.R;

public class MainActivity extends AppCompatActivity {

    private TextView currentTime;
    private Button setAlarmButton;
    private ListView alarmListView;
    private AlarmAdapter alarmAdapter;
    private ArrayList<AlarmModel> alarmList;
    private AlarmDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentTime = findViewById(R.id.current_time);
        setAlarmButton = findViewById(R.id.set_alarm_button);
        alarmListView = findViewById(R.id.alarm_list);

        dbHelper = new AlarmDatabaseHelper(this);
        alarmList = dbHelper.getAllAlarms();
        alarmAdapter = new AlarmAdapter(this, alarmList, dbHelper);
        alarmListView.setAdapter(alarmAdapter);

        // Update current time every second
        updateTime();

        // Set Alarm button
        setAlarmButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SetAlarmActivity.class);
            startActivity(intent);
        });
    }

    private void updateTime() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
                currentTime.setText(sdf.format(new Date()));
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the alarm list when returning from SetAlarmActivity
        alarmList.clear();
        alarmList.addAll(dbHelper.getAllAlarms());
        alarmAdapter.notifyDataSetChanged();
    }
}

