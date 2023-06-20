package com.example.cardiacrecorder;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Record> list;

    public MyAdapter(Context context, ArrayList<Record> list) {
        this.context = context;
        this.list = list;
    }

    TextView date_txt,time_txt,systolic_txt,diastolic_txt,heartRate_txt,comment_txt;
    String keyID;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.singlerecord,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Record record = list.get(position);
        holder.date.setText(record.getDate());
        holder.time.setText(record.getTime());
        holder.diastolic.setText(record.getDiastolic());
        holder.systolic.setText(record.getSystolic());
        holder.heart.setText(record.getHeart());
        holder.comment.setText(record.getComment());

       holder.edit_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               View myView = LayoutInflater.from(context).inflate(R.layout.edit_user_record, null);
               final AlertDialog dialog = new AlertDialog.Builder(context).setView(myView).create();

               date_txt = myView.findViewById(R.id.editTextDate2);
               time_txt = myView.findViewById(R.id.editTextTime2);
               systolic_txt = myView.findViewById(R.id.editTextSystolic2);
               diastolic_txt = myView.findViewById(R.id.editTextDiastolic2);
               heartRate_txt = myView.findViewById(R.id.editTextHeart2);
               comment_txt = myView.findViewById(R.id.editTextComment2);

               Button update_btn = myView.findViewById(R.id.update_btn);

               date_txt.setText(record.getDate());
               time_txt.setText(record.getTime());
               systolic_txt.setText(record.getSystolic());
               diastolic_txt.setText(record.getDiastolic());
               heartRate_txt.setText(record.getHeart());
               comment_txt.setText(record.getComment());

               FirebaseAuth mAuth = FirebaseAuth.getInstance();
               DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("records");

               keyID = record.getRecordID();

               Log.d("KEY ID : " , keyID);

               dialog.show();

               update_btn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                        inputFormat();
                        dialog.dismiss();
                       Intent dashpage = new Intent(context, userRecords.class);
                       context.startActivity(dashpage);
                       ((Activity) context).finish();
                   }
               });

           }
       });
        holder.dlt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                keyID = record.getRecordID();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete this listing?");

// Set positive button action
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform the deletion operation here
                        deleteListing();
                        dialog.dismiss();
                        Intent dashpage = new Intent(context, userRecords.class);
                        context.startActivity(dashpage);
                        ((Activity) context).finish();
                    }
                });

// Set negative button action
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing or handle cancel operation
                    }
                });

// Create and show the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date,time,systolic,diastolic,heart,comment;
        Button edit_btn,dlt_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.tvDate);
            time = itemView.findViewById(R.id.tvTime);
            heart = itemView.findViewById(R.id.tvHeartRate);
            systolic = itemView.findViewById(R.id.tvSystolic);
            diastolic = itemView.findViewById(R.id.tvDiastolic);
            comment = itemView.findViewById(R.id.tvComment);
            edit_btn = itemView.findViewById(R.id.Edit_buttonId);
            dlt_btn = itemView.findViewById(R.id.DeleteBUttonId);
        }
    }

    private void inputFormat() {
        if(!TextUtils.isEmpty(time_txt.getText())) {
            if (!TextUtils.isEmpty(date_txt.getText())) {
                if ( (!TextUtils.isEmpty(systolic_txt.getText())) && (Integer.parseInt(systolic_txt.getText().toString()) >= 0) && (Integer.parseInt(systolic_txt.getText().toString()) <= 200) ) {
                    if ( (!TextUtils.isEmpty(diastolic_txt.getText())) && (Integer.parseInt(diastolic_txt.getText().toString()) >= 0) && (Integer.parseInt(diastolic_txt.getText().toString()) <= 150)) {
                        if ( (!TextUtils.isEmpty(heartRate_txt.getText())) && (Integer.parseInt(heartRate_txt.getText().toString()) >= 0) && (Integer.parseInt(heartRate_txt.getText().toString()) <= 150)) {


                            Record myrecord = new Record(date_txt.getText().toString(),time_txt.getText().toString(),systolic_txt.getText().toString(),diastolic_txt.getText().toString(),heartRate_txt.getText().toString(),comment_txt.getText().toString(),keyID);

                            DatabaseReference recordsRef = FirebaseDatabase.getInstance().getReference().child("records");
                            recordsRef.child(keyID).setValue(myrecord)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Data successfully inserted with the generated ID
                                            Log.d("FirebaseInsert", "Data inserted with ID: " + keyID);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle any error that occurred while inserting the data
                                            Log.e("FirebaseInsert", "Error inserting data: " + e.getMessage());
                                        }
                                    });

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

    private void deleteListing(){

        DatabaseReference recordsRef = FirebaseDatabase.getInstance().getReference().child("records");
        recordsRef.child(keyID).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data successfully deleted
                        Log.d("FirebaseDelete", "Data deleted with ID: " + keyID);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any error that occurred while deleting the data
                        Log.e("FirebaseDelete", "Error deleting data: " + e.getMessage());
                    }
                });
    }
}
