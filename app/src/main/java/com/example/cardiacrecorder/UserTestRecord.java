package com.example.cardiacrecorder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
public class UserTestRecord {
    private List<Record> records = new ArrayList<>();
    public void add(Record record){
        if(records.contains(record)){
            throw new IllegalArgumentException();
        }
        records.add(record);
    }
    public List<Record>getRecords(){
        List<Record> recordList = records;
        return recordList;
    }
    public void  delete(Record record){
        if(!records.contains(record)){
            throw new IllegalArgumentException();
        }
        else{
            records.remove(record);
        }
    }
    public void Edit(Record record){
        if(records.contains(record)){
            record.time = "00:00";
            record.diastolic = "100";
            record.date = "12-12-13";
            record.heart = "80";
            record.systolic = "200";
        }
        else{
            throw new IllegalArgumentException();
        }

    }
    public int count(){
        return records.size();
    }
}
