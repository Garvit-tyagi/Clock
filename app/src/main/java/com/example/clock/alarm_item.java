package com.example.clock;

public class alarm_item {
private String id;
private String time;
private String alarm_atatus;

    public alarm_item(String id, String time, String alarm_atatus) {
        this.id = id;
        this.time = time;
        this.alarm_atatus = alarm_atatus;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String  getAlarm_atatus() {
        return alarm_atatus;
    }
}
