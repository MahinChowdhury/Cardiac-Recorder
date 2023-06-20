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


         insert_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 inputFormat();
             }
         });

    }

    private void inputFormat() {
        if(!TextUtils.isEmpty(time_txt.getText())) {
            if (!TextUtils.isEmpty(date_txt.getText())) {
                if ( (!TextUtils.isEmpty(systolic_txt.getText())) && (Integer.parseInt(systolic_txt.getText().toString()) >= 0) && (Integer.parseInt(systolic_txt.getText().toString()) <= 200) ) {
                    if ( (!TextUtils.isEmpty(diastolic_txt.getText())) && (Integer.parseInt(diastolic_txt.getText().toString()) >= 0) && (Integer.parseInt(diastolic_txt.getText().toString()) <= 150)) {
                        if ( (!TextUtils.isEmpty(heartRate_txt.getText())) && (Integer.parseInt(heartRate_txt.getText().toString()) >= 0) && (Integer.parseInt(heartRate_txt.getText().toString()) <= 150)) {

                            date = date_txt.getText().toString();
                            time = time_txt.getText().toString();
                            systolic = systolic_txt.getText().toString();
                            diastolic = diastolic_txt.getText().toString();
                            heartRate = heartRate_txt.getText().toString();
                            comment = comment_txt.getText().toString();

                            insertData(date,time,systolic,diastolic,comment,heartRate);

                            Toast.makeText(addUserRecords.this, "Inserted data successfully", Toast.LENGTH_SHORT).show();

                            Intent dashpage= new Intent(addUserRecords.this, userRecords.class);
                            startActivity(dashpage);
                            finish();

                        } else {
                            heartRate_txt.setError("Invalid data format added");

                            // Toast.makeText(DataEntry.this, "Invalid data format added", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        diastolic_txt.setError("Invalid data format added");
                        //Toast.makeText(DataEntry.this, "Invalid data format added", Toast.LENGTH_LONG).show();
                    }
                } else {
                    systolic_txt.setError("Invalid data format added");
                    //Toast.makeText(DataEntry.this, "Invalid data format added", Toast.LENGTH_LONG).show();
                }
            } else {
                time_txt.setError("The field must be required");
            }
        }
        else{
            date_txt.setError("The field must be required");
        }
    }

    public void insertData(String date,String time,String s,String d, String cmnt,String hr){

        String id = String.valueOf(child_cnt + 1);
        Record record = new Record(date,time,s,d,hr,cmnt,id);
        mDB.child("records").child(id).setValue(record);
    }
}