package com.example.cardiacrecorder;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Rule;
import org.junit.Test;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

@RunWith(AndroidJUnit4.class)

public class UiTest {

    //UI Testing for Insert Data
    @Test
    public void testInsertData() {

        ActivityScenarioRule<addUserRecords> activityScenarioRule = new ActivityScenarioRule<>(addUserRecords.class);
        ActivityScenario.launch(addUserRecords.class).onActivity(activity ->
                activity.getWindow().setWindowAnimations(0)
        );

        Espresso.onView(ViewMatchers.withId(R.id.editTextDate))
                .perform(ViewActions.replaceText("02-07-23"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.editTextTime))
                .perform(ViewActions.replaceText("10:30"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.editTextSystolic))
                .perform(ViewActions.replaceText("120"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.editTextDiastolic))
                .perform(ViewActions.replaceText("80"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.editTextHeart))
                .perform(ViewActions.replaceText("70"), ViewActions.closeSoftKeyboard());

//        Espresso.onView(ViewMatchers.withId(R.id.editTextComment))
//                .perform(ViewActions.typeText("Some comment"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.insert_btn)).perform(ViewActions.click());


        try {
            Thread.sleep(2000); // Wait for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.recycleViewId)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("records");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    boolean dataInserted = false;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Record record = dataSnapshot.getValue(Record.class);
                        if (record != null && record.getDate().equals("02-07-23") && record.getTime().equals("10:30")) {
                            dataInserted = true;
                            break;
                        }
                    }
                    Assert.assertTrue(dataInserted);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //UI Testing for Editing Data

    @Rule
    public ActivityTestRule<userRecords> activityTestRule = new ActivityTestRule<>(userRecords.class);
    @Test
    public void testEditRecord() {

        // Wait for the activity to be fully launched

        try {
            Thread.sleep(3000); // Wait for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        activityTestRule.getActivity();

        // Perform actions to edit the record
        Espresso.onView(ViewMatchers.withId(R.id.recycleViewId))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new ClickEditButtonAction()));

        // Perform actions to modify the record
        Espresso.onView(ViewMatchers.withId(R.id.editTextDate2))
                .perform(ViewActions.clearText(), ViewActions.replaceText("01-01-20"));
        // Add more actions to modify other fields as needed

        // Close the soft keyboard
        Espresso.closeSoftKeyboard();

        // Click the update button
        Espresso.onView(ViewMatchers.withId(R.id.update_btn))
                .perform(ViewActions.click());

        // Wait for the record to be updated in the database
        try {
            Thread.sleep(2000); // Wait for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify the changes in the record
        DatabaseReference recordRef = FirebaseDatabase.getInstance().getReference().child("records").child("2");
        recordRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Record updatedRecord = dataSnapshot.getValue(Record.class);
                // Assert the updated fields of the record match the changes made in the UI
                Assert.assertEquals("01-01-20", updatedRecord.getDate());
                // Add more assertions for other fields as needed
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle onCancelled event
            }
        });
    }


    public static class ClickEditButtonAction implements ViewAction {
        @Override
        public Matcher<View> getConstraints() {
            return Matchers.allOf(ViewMatchers.isAssignableFrom(View.class), ViewMatchers.isDisplayed());
        }

        @Override
        public String getDescription() {
            return "click Edit button in first item of RecyclerView";
        }

        @Override
        public void perform(UiController uiController, View view) {
            // Perform actions to click the edit button
            Button editButton = view.findViewById(R.id.Edit_buttonId);
            editButton.performClick();
        }
    }
    
    
    
    //UI test for deleting

    @Test
    public void testDeleteRecord() {

        // Wait for the activity to be fully launched
        try {
            Thread.sleep(3000); // Wait for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        activityTestRule.getActivity();

        // Perform actions to delete the record
        Espresso.onView(ViewMatchers.withId(R.id.recycleViewId))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, new ClickDeleteButtonAction()));

        // Wait for the dialog to appear
        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click the delete button in the dialog
        Espresso.onView(ViewMatchers.withText("Delete"))
                .inRoot(RootMatchers.isDialog())
                .perform(ViewActions.click());

        // Wait for the record to be deleted in the database
        try {
            Thread.sleep(3000); // Wait for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the record is deleted in the database
        DatabaseReference recordRef = FirebaseDatabase.getInstance().getReference().child("records").child("4");
        recordRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Assert that the record is deleted (dataSnapshot should be null)
                Assert.assertNull(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle onCancelled event
            }
        });
    }


    public static class ClickDeleteButtonAction implements ViewAction {
        @Override
        public Matcher<View> getConstraints() {
            return Matchers.allOf(ViewMatchers.isAssignableFrom(View.class), ViewMatchers.isDisplayed());
        }

        @Override
        public String getDescription() {
            return "click Delete button in first item of RecyclerView";
        }

        @Override
        public void perform(UiController uiController, View view) {
            // Perform actions to click the delete button
            Button deleteButton = view.findViewById(R.id.DeleteBUttonId);
            deleteButton.performClick();
        }
    }


}
