package com.example.fitbox;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserProfileActivity extends AppCompatActivity {

    private long userId; // To store the USER_ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Retrieve the USER_ID from the Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("USER_ID")) {
            userId = intent.getLongExtra("USER_ID", -1); // -1 is the default value if USER_ID is not found
        }

        // Now, you can use the userId as needed in the UserProfileActivity
        // For example, you can display user-specific information based on the userId.

        // Example: Insert user profile data
        insertUserProfileData();
        // Navigate to LogInActivity
        navigateToLogInActivity();
    }

    private void insertUserProfileData() {
        AsyncTask.execute(() -> {
            AppDatabase appDatabase = AppDatabase.getInstance(this);
            UserProfileDao userProfileDao = appDatabase.userProfileDao();

            // Retrieve values from UI elements
            EditText editTextActive = findViewById(R.id.editTextActive);
            EditText editTextCalories = findViewById(R.id.editTextCalories);
            EditText editTextTextWeight = findViewById(R.id.editTextTextWeight);
            EditText editTextTextHeight = findViewById(R.id.editTextTextHeight);
            EditText editTextTextDate = findViewById(R.id.editTextTextDate);

            RadioGroup radioGroupGender = findViewById(R.id.radioGroup); // Fix the ID
            int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
            RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
            String gender = selectedGenderRadioButton.getText().toString();

            String activityGoals = editTextActive.getText().toString();
            String birthDateStr = editTextTextDate.getText().toString();
            double weight = Double.parseDouble(editTextTextWeight.getText().toString());
            double height = Double.parseDouble(editTextTextHeight.getText().toString());

            // Parse the birthDate string to Date object (you may need to handle this according to your date format)
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date parsedDate;
            try {
                parsedDate = dateFormat.parse(birthDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
                return; // Handle the parsing error
            }

           //// UserProfile userProfile = new UserProfile(userId, activeTime, caloriesBurned, weight);
            ///userProfileDao.insertUserProfile(userProfile);
        });
    }

    private void navigateToLogInActivity() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);

        // Finish the current activity to prevent the user from going back to UserProfileActivity
        finish();
    }
}
