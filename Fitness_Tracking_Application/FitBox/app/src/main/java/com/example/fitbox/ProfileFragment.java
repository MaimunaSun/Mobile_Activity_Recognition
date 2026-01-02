package com.example.fitbox;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileFragment extends Fragment {

    private TextView activeTimeTextView;
    private TextView caloriesTextView;
    private TextView weightTextView;
    private UserProfileDao userProfileDao;
    private ActivityResultLauncher<Intent> updateProfileLauncher;

    private static final int UPDATE_PROFILE_REQUEST = 1; // Arbitrary request code

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        activeTimeTextView = view.findViewById(R.id.activeTimeTextView);
        caloriesTextView = view.findViewById(R.id.caloriesTextView);
        weightTextView = view.findViewById(R.id.weightTextView);
        Button updateProfileButton = view.findViewById(R.id.updateProfileButton);

        // Inside onCreateView method of ProfileFragment

        Button manageAccountButton = view.findViewById(R.id.manageAccountButton);

        manageAccountButton.setOnClickListener(v -> {
            // When the "Manage Account" button is pressed, navigate to the ManageAccountActivity
            Intent intent = new Intent(requireContext(), ManageAccountActivity.class);
            startActivity(intent);
        });


        // Initialize userProfileDao
        userProfileDao = AppDatabase.getInstance(requireContext()).userProfileDao();

        // Use a single-thread executor to perform database operations off the main thread
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            long userProfileId = 1;

            if (userProfileId != 0) {
                int activeTime = userProfileDao.getActiveTime(userProfileId);
                int caloriesGoal = userProfileDao.getCaloriesGoal(userProfileId);
                double weight = userProfileDao.getWeight(userProfileId);

                // Update the UI on the main thread
                requireActivity().runOnUiThread(() -> {
                    activeTimeTextView.setText("Active Time Goals: " + activeTime + " minutes");
                    caloriesTextView.setText("Calories Goal: " + caloriesGoal + " Calories");
                    weightTextView.setText("Weight: " + weight + " kg");
                });
            }
        });

        updateProfileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Handle the result, for example, refresh the UI or perform any other action
                        // This is where you can update the UI after the profile is updated
                        executorService.execute(() -> {
                            long userProfileId = 1;

                            if (userProfileId != 0) {
                                int activeTime = userProfileDao.getActiveTime(userProfileId);
                                int caloriesGoal = userProfileDao.getCaloriesGoal(userProfileId);
                                double weight = userProfileDao.getWeight(userProfileId);

                                // Update the UI on the main thread
                                requireActivity().runOnUiThread(() -> {
                                    activeTimeTextView.setText("Active Time Goals: " + activeTime + " minutes");
                                    caloriesTextView.setText("Calories Goal: " + caloriesGoal + " Calories");
                                    weightTextView.setText("Weight: " + weight + " kg");
                                });
                            }
                        });
                    }
                }
        );

        updateProfileButton.setOnClickListener(v -> {
            // When the "Update Profile" button is pressed, navigate to the UpdateProfileActivity
            Intent intent = new Intent(requireContext(), UpdateProfileActivity.class);
            updateProfileLauncher.launch(intent);
        });

        return view;
    }
}
