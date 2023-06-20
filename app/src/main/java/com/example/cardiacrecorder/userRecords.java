package com.example.cardiacrecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
=======
import android.util.Log;
import android.view.MenuItem;
>>>>>>> mahin
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class userRecords extends AppCompatActivity {
    public DatabaseReference mDB;

    RecyclerView recyclerView;
    ArrayList<Record> list;
    DatabaseReference reference;
    MyAdapter adapter;

<<<<<<< HEAD
=======
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    AddFragment addFragment = new AddFragment();
    LogoutFragment logoutFragment = new LogoutFragment();

    long maxCnt;


>>>>>>> mahin
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_records);

        //Insert Dummy Values to firebase for demo.

//        mDB = FirebaseDatabase.getInstance().getReference();
//        insertData("23-02-23","09:02","15","16","Good","68");

        //RecycleView Show
        recyclerView = findViewById(R.id.userRecords);
        reference = FirebaseDatabase.getInstance().getReference("records");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this,list);

        recyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
<<<<<<< HEAD
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
=======
                //maxCnt = snapshot.getChildrenCount();
                //Log.d("Count of list : " , String.valueOf(maxCnt));
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
>>>>>>> mahin
                    Record record = dataSnapshot.getValue(Record.class);
                    list.add(record);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

<<<<<<< HEAD
    public void insertData(String date,String time,String s,String d, String cmnt,String hr){
        Record record = new Record(date,time,s,d,hr,cmnt);
        String id = mDB.push().getKey();
        mDB.child("records").child(id).setValue(record);
=======
//    public void insertData(String date,String time,String s,String d, String cmnt,String hr){
//        Record record = new Record(date,time,s,d,hr,cmnt);
//        String id = mDB.push().getKey();
//        mDB.child("records").child(id).setValue(record);
//    }

    public void AddNewItem() {
        Intent newItem = new Intent(userRecords.this, addUserRecords.class);
        startActivity(newItem);
    }
    public void logoutUser() {

        auth = FirebaseAuth.getInstance();
        auth.signOut();

        Toast.makeText(userRecords.this, "You have logged out successfully.", Toast.LENGTH_SHORT).show();

        Intent home = new Intent(userRecords.this, MainActivity.class);
        startActivity(home);
        finish();
>>>>>>> mahin
    }
}