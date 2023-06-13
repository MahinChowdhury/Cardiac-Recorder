package com.example.cardiacrecorder;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Record {

    public String date,time,systolic,diastolic,heart,comment;

    public String getComment() {
        return comment;
    }

    public Record() {
    }

    public Record(String date, String time, String systolic, String diastolic, String heart, String comment) {
        this.date = date;
        this.time = time;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.heart = heart;
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getSystolic() {
        return systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public String getHeart() {
        return heart;
    }
}
