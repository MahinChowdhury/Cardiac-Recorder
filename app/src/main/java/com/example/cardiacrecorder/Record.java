package com.example.cardiacrecorder;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Record {

    public String date,time,systolic,diastolic,heart,comment,recordID;

    public Record() {
    }
    public Record(String date, String time, String systolic, String diastolic, String heart, String comment,String recordID) {
        this.date = date;
        this.time = time;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.heart = heart;
        this.comment = comment;
        this.recordID = recordID;
    }
    public String getDate() {
        return date;
    }

    public String getRecordID() {
        return recordID;
    }

    public String getComment() {
        return comment;
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
