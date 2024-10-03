package com.example.alarmclockapp;

public class AlarmModel {
    private int id;
    private String time;
    private String tone;
    public boolean active;

    public AlarmModel(int id, String time, String tone, boolean active) {
        this.id = id;
        this.time = time;
        this.tone = tone;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getTone() {
        return tone;
    }

    public boolean isActive() {
        return active;
    }
}

