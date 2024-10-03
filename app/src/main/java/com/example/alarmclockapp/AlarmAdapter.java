package com.example.alarmclockapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class AlarmAdapter extends ArrayAdapter<AlarmModel> {

    private Context context;
    private ArrayList<AlarmModel> alarms;
    private AlarmDatabaseHelper dbHelper;

    public AlarmAdapter(Context context, ArrayList<AlarmModel> alarms, AlarmDatabaseHelper dbHelper) {
        super(context, R.layout.alarm_item, alarms);
        this.context = context;
        this.alarms = alarms;
        this.dbHelper = dbHelper;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.alarm_item, parent, false);
        }

        TextView alarmTime = convertView.findViewById(R.id.alarm_time);
        Switch alarmSwitch = convertView.findViewById(R.id.alarm_switch);

        AlarmModel alarm = alarms.get(position);
        alarmTime.setText(alarm.getTime());
        alarmSwitch.setChecked(alarm.isActive());

        alarmSwitch.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            alarm.active = isChecked;
            dbHelper.updateAlarmStatus(alarm.getId(), isChecked);
        });

        return convertView;
    }
}

