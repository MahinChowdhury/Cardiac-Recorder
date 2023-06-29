package com.example.cardiacrecorder;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTest {

    @Test
    public void AddTest(){
        Record record = new Record("12-12-12","12:00","120",
                "240","70","good","100");
        Record record1 = new Record("12-12-13","12:01","121",
                "241","71","good","101");
        UserTestRecord userTestRecord = new UserTestRecord();
        userTestRecord.add(record);
        assertEquals(1,userTestRecord.getRecords().size());
        userTestRecord.add(record1);
        assertEquals(2,userTestRecord.getRecords().size());
    }
    @Test
    public void DeleteTest(){
        Record record = new Record("12-12-12","12:00","120",
                "240","70","good","100");
        Record record1 = new Record("12-12-13","12:01","121",
                "241","71","good","101");
        UserTestRecord userTestRecord = new UserTestRecord();
        userTestRecord.add(record);
        userTestRecord.add(record1);
        assertEquals(2,userTestRecord.getRecords().size());
        userTestRecord.delete(record1);
        assertEquals(1,userTestRecord.getRecords().size());
        assertTrue(!userTestRecord.getRecords().contains(record1));
    }
    @Test
    public void TestAddException(){
        Record record = new Record("12-12-12","12:00","120",
                "240","70","good","100");
        Record record1 = new Record("12-12-13","12:01","121",
                "241","71","good","101");
        UserTestRecord userTestRecord = new UserTestRecord();
        userTestRecord.add(record);
        userTestRecord.add(record1);
        assertThrows(IllegalArgumentException.class,()->{
            userTestRecord.add(record1);
        });
    }
    @Test
    public void TestDeleteException(){
        Record record = new Record("12-12-12","12:00","120",
                "240","70","good","100");
        Record record1 = new Record("12-12-13","12:01","121",
                "241","71","good","101");
        UserTestRecord userTestRecord = new UserTestRecord();
        userTestRecord.add(record);
        userTestRecord.add(record1);
        userTestRecord.delete(record1);
        assertThrows(IllegalArgumentException.class,()->{
            userTestRecord.delete(record1);
        });
    }
    @Test
    public void EditTest(){
        Record record = new Record("12-12-12","12:00","120",
                "240","70","good","100");
        Record record1 = new Record("12-12-13","12:01","121",
                "241","71","good","101");
        UserTestRecord userTestRecord = new UserTestRecord();
        userTestRecord.add(record);
        userTestRecord.add(record1);

        userTestRecord.Edit(record1);
        String dummySystolic, dummyDiastolic,dummyHeartRate,dummyDate,dummyTime;
        dummyTime = "00:00";
        dummyDiastolic = "100";
        dummyDate = "12-12-13";
        dummyHeartRate = "80";
        dummySystolic = "200";
        assertEquals(dummyDate,record1.date);
        assertEquals(dummyTime,record1.time);
        assertEquals(dummyDiastolic,record1.diastolic);
        assertEquals(dummySystolic,record1.systolic);
        assertEquals(dummyHeartRate,record1.heart);
    }
}
