package com.example.cardiacrecorder;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class testActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    TextView txt1,txt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("records").child("s1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        txt1 = findViewById(R.id.textView2);
        txt2 = findViewById(R.id.textView3);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Record record = dataSnapshot.getValue(Record.class);
                txt1.setText(record.getDate());
                txt2.setText(record.getTime());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}