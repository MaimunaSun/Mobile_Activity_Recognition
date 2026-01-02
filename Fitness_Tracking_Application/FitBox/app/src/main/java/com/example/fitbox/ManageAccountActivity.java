package com.example.fitbox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ManageAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);

        // Set OnClickListener for the "View Privacy Policy" button
        Button privacyPolicyButton = findViewById(R.id.buttonPrivacyPolicy);
        privacyPolicyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ViewPrivacyPolicyActivity
                Intent intent = new Intent(ManageAccountActivity.this, ViewPrivacyPolicyActivity.class);
                startActivity(intent);
            }
        });
    }

    // Called when the "Go Back" button is clicked
    /*public void onGoBackButtonClick(View view) {
        // Close the current activity
        finish();
    }*/

    // Handle the button click
    public void onDeleteButtonClick(View view) {
        // Show a confirmation dialog before deleting
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete all activity data?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User confirmed, proceed with deletion
                        deleteActivityData();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    // Method to delete activity data
    private void deleteActivityData() {
        // Get the ViewModel using a ViewModelFactory
        AppDatabase appDatabase = AppDatabase.getInstance(this);
        ActivityDataDao activityDataDao = appDatabase.activityDataDao();
        UserProfileDao userProfileDao = appDatabase.userProfileDao();
        // Provide both instances to the repository

        ActivityDataRepository activityDataRepository = new ActivityDataRepository(activityDataDao, appDatabase,userProfileDao);
        ActivityDataViewModelFactory factory = new ActivityDataViewModelFactory(getApplication(), activityDataRepository);
        ActivityDataViewModel activityDataViewModel = new ViewModelProvider(this, factory).get(ActivityDataViewModel.class);


        // Delete all activity data
        activityDataViewModel.deleteAllActivityData();

        // Show a message after completion
        Toast.makeText(this, "Activity data deleted", Toast.LENGTH_SHORT).show();
    }
}