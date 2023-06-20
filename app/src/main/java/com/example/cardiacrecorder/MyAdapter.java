package com.example.cardiacrecorder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Record> list;

    public MyAdapter(Context context, ArrayList<Record> list) {
        this.context = context;
        this.list = list;
    }

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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView date,time,systolic,diastolic,heart,comment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.tvDate);
            time = itemView.findViewById(R.id.tvTime);
            heart = itemView.findViewById(R.id.tvHeartRate);
            systolic = itemView.findViewById(R.id.tvSystolic);
            diastolic = itemView.findViewById(R.id.tvDiastolic);
            comment = itemView.findViewById(R.id.tvComment);
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
