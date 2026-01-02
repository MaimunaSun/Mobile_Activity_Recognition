package com.example.fitbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText editTextUpdateActiveTime;
    private EditText editTextUpdateCaloriesBurned;
    private EditText editTextUpdateWeight;
    //private ActivityDataViewModel activityDataViewModel;

    private final ActivityResultLauncher<Intent> updateProfileLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            // Handle the result, for example, refresh the UI or perform any other action
                            // This is where you can update the UI after the profile is updated
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        editTextUpdateActiveTime = findViewById(R.id.editTextUpdateActiveTime);
        editTextUpdateCaloriesBurned = findViewById(R.id.editTextUpdateCaloriesBurned);
        editTextUpdateWeight = findViewById(R.id.editTextUpdateWeight);

        AppDatabase appDatabase = AppDatabase.getInstance(this);
        //ActivityDataDao activityDataDao = appDatabase.activityDataDao();
        UserProfileDao userProfileDao = appDatabase.userProfileDao();
        // Provide both instances to the repository
        ActivityInsightsDao activityInsightsDao = appDatabase.activityInsightsDao();
        ActivityInformationDao activityInformationDao = appDatabase.activityInformationDao();

        Button buttonUpdateProfile = findViewById(R.id.buttonUpdateProfile);
        buttonUpdateProfile.setOnClickListener(v -> {
            // Get values from EditText fields
            int activeTime = Integer.parseInt(editTextUpdateActiveTime.getText().toString());
            int caloriesBurned = Integer.parseInt(editTextUpdateCaloriesBurned.getText().toString());
            double weight = Double.parseDouble(editTextUpdateWeight.getText().toString());
            // Use a single-thread executor to perform database operations off the main thread
            /*ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {

            long userProfileId = 1; // Change this to the actual user profile ID
            userProfileDao.updateActiveTime(userProfileId, activeTime);
            userProfileDao.updateCaloriesBurned(userProfileId, caloriesBurned);
            userProfileDao.updateWeight(userProfileId, weight);

            });*/
            // Set the result to indicate success


            // Provide both instances to the repository

            ActivityInsightsRepository activityInsightsRepository = new ActivityInsightsRepository(activityInformationDao, activityInsightsDao, userProfileDao);
            ActivityInsightsViewModelFactory factoryI = new ActivityInsightsViewModelFactory(getApplication(), activityInsightsRepository);
            ActivityInsightsViewModel insightsViewModel = new ViewModelProvider(this,factoryI).get(ActivityInsightsViewModel.class);

            // Delete all activity data
            insightsViewModel.updateActiveTimeUser(activeTime);
            insightsViewModel.updateCaloriesBurnedUser(caloriesBurned);
            insightsViewModel.updateWeightUser(weight);

            setResult(RESULT_OK);

            // Finish the activity after updating the profile
            finish();
        });
    }
}
