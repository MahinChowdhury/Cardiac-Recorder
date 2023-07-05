package com.example.cardiacrecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addUserRecords extends AppCompatActivity {

    public DatabaseReference mDB;

    EditText date_txt,time_txt,systolic_txt,diastolic_txt,heartRate_txt,comment_txt;
    String date,time,systolic,diastolic,heartRate,comment;
    long child_cnt = 0;
    boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_records);

        mDB = FirebaseDatabase.getInstance().getReference();

        date_txt = findViewById(R.id.editTextDate);
        time_txt = findViewById(R.id.editTextTime);
        systolic_txt = findViewById(R.id.editTextSystolic);
        diastolic_txt = findViewById(R.id.editTextDiastolic);
        heartRate_txt = findViewById(R.id.editTextHeart);
        comment_txt = findViewById(R.id.editTextComment);

        Button insert_btn = findViewById(R.id.insert_btn);

        mDB.child("records").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                child_cnt = snapshot.getChildrenCount();
                //Log.d("Count of list : " , String.valueOf(child_cnt));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //inserting Records

         insert_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 inputFormat();

                 if(isValid){
                     Intent dashpage= new Intent(addUserRecords.this, userRecords.class);
                     startActivity(dashpage);
                     finish();
                 }
             }
         });

    }

    //Check input formats

    private void inputFormat() {

        isValid = true;

        String timeInput = time_txt.getText().toString();
        String dateInput = date_txt.getText().toString();
        String systolicInput = systolic_txt.getText().toString();
        String diastolicInput = diastolic_txt.getText().toString();
        String heartRateInput = heartRate_txt.getText().toString();

        if (TextUtils.isEmpty(timeInput)) {
            time_txt.setError("The field must be required");
            isValid = false;
        }

        if (TextUtils.isEmpty(dateInput)) {
            date_txt.setError("The field must be required");
            isValid = false;
        }

        if (TextUtils.isEmpty(systolicInput) || !isValidRange(systolicInput, 90, 140)) {
            systolic_txt.setError("Invalid data format added");
            isValid = false;
        }

        if (TextUtils.isEmpty(diastolicInput) || !isValidRange(diastolicInput, 60, 90)) {
            diastolic_txt.setError("Invalid data format added");
            isValid = false;
        }

        if (TextUtils.isEmpty(heartRateInput) || !isValidRange(heartRateInput, 60, 100)) {
            heartRate_txt.setError("Invalid data format added");
            isValid = false;
        }

        if (isValid) {
            date = dateInput;
            time = timeInput;
            systolic = systolicInput;
            diastolic = diastolicInput;
            heartRate = heartRateInput;
            comment = comment_txt.getText().toString();

            insertData(date, time, systolic, diastolic, comment, heartRate);

            Toast.makeText(addUserRecords.this, "Inserted data successfully", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isValidRange(String value, int min, int max) {
        try {
            int intValue = Integer.parseInt(value);
            return intValue >= min && intValue <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void insertData(String date,String time,String s,String d, String cmnt,String hr){

        String id = String.valueOf(child_cnt + 1);
        Record record = new Record(date,time,s,d,hr,cmnt,id);
        mDB.child("records").child(id).setValue(record);
    }
}