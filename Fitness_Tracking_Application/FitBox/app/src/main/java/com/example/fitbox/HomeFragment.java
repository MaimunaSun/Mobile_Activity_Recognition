package com.example.fitbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class HomeFragment extends Fragment {

    private ActivityInsightsViewModel activityInsightsViewModel;
    private BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barChart = view.findViewById(R.id.barChart);
        AppDatabase appDatabase = AppDatabase.getInstance(requireContext());

        UserProfileDao userProfileDao = appDatabase.userProfileDao();
        ActivityInsightsDao activityInsightsDao = appDatabase.activityInsightsDao();
        ActivityInformationDao activityInformationDao = appDatabase.activityInformationDao();

        ActivityInsightsRepository activityInsightsRepository = new ActivityInsightsRepository(activityInformationDao, activityInsightsDao, userProfileDao);
        ActivityInsightsViewModelFactory factory = new ActivityInsightsViewModelFactory(requireActivity().getApplication(), activityInsightsRepository);
        activityInsightsViewModel = new ViewModelProvider(this, factory).get(ActivityInsightsViewModel.class);

        // Observe changes in UserProfile data
        activityInsightsViewModel.getAllUserProfileData().observe(getViewLifecycleOwner(), userProfiles -> {
            if (userProfiles != null && userProfiles.size() > 0) {
                UserProfile userProfile = userProfiles.get(0);

                // Observe changes in ActivityInsights data
                activityInsightsViewModel.getAllActivityInsightsData().observe(getViewLifecycleOwner(), activityInsightsList -> {
                    if (activityInsightsList != null && activityInsightsList.size() > 0) {
                        ActivityInsights activityInsights = activityInsightsList.get(0);

                        // Update UI with UserProfile and ActivityInsights data
                        updateUIWithUserProfileAndActivityInsights(userProfile, activityInsights);

                        // Create and populate the bar chart
                        createBarChart(activityInsights);
                    }
                });
            }
        });
    }


    private void createBarChart(ActivityInsights activityInsights) {
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, activityInsights.getStandingDuration()));
        entries.add(new BarEntry(1, activityInsights.getWalkingDuration()));
        entries.add(new BarEntry(2, activityInsights.getSittingDuration()));
        entries.add(new BarEntry(3, activityInsights.getJoggingDuration()));
        entries.add(new BarEntry(4, activityInsights.getStairsDuration()));

        BarDataSet dataSet = new BarDataSet(entries, "Activity Durations");
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        // Customize X-axis labels
        String[] activities = {"Standing", "Walking", "Sitting", "Jogging", "Stairs"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(activities));
        xAxis.setGranularity(1f); // Ensure the X-axis labels are not repeated

        // Customize Y-axis
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

        // Customize chart
        barChart.setTouchEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.animateY(1000);

        // Refresh chart
        barChart.invalidate();
    }

    // ValueFormatter for custom X-axis labels
    private static class MyXAxisValueFormatter extends ValueFormatter {
        private final String[] values;

        public MyXAxisValueFormatter(String[] values) {
            this.values = values;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            int index = (int) value;
            if (index >= 0 && index < values.length) {
                return values[index];
            }
            return "";
        }
    }

    // Method to update UI with UserProfile and ActivityInsights data
    private void updateUIWithUserProfileAndActivityInsights(UserProfile userProfile, ActivityInsights activityInsights) {
        if (getView() != null) {
            TextView textViewActive = getView().findViewById(R.id.textViewActive);
            ProgressBar progressBarActive = getView().findViewById(R.id.progressBarActive);

            TextView textViewCalories = getView().findViewById(R.id.textViewCalories);
            ProgressBar progressBarCalories = getView().findViewById(R.id.progressBarCalories);

            TextView textViewInactive = getView().findViewById(R.id.textViewInActive); // Updated TextView for Inactive Time

            // Update UI elements based on UserProfile data
            textViewActive.setText("Active Time: " + activityInsights.getActiveTime() + "/" + userProfile.getActiveTime() + " minutes");

            // Update UI elements based on ActivityInsights data
            textViewCalories.setText("Calories Burned: " + activityInsights.getCaloriesBurned() + "/" + userProfile.getCaloriesBurned() + " Calories");

            // Update UI elements for Inactive Time
            textViewInactive.setText("Inactive Time: " + activityInsights.getInactiveTime() + " minutes for the Day");

            // Update progress bars
            //updateProgressBar(progressBarActive, activityInsights.getActiveTime(), userProfile.getActiveTime());

            // Update progress bars
            //updateProgressBar(progressBarActive, activityInsights.getCaloriesBurned(), userProfile.getCaloriesBurned());

        }
    }

    // Method to update progress bar
    private void updateProgressBar(ProgressBar progressBar, int currentValue, int goalValue) {
        if (goalValue > 0) {
            int progress = (int) ((float) (currentValue / goalValue) * 100);
            progressBar.setProgress(progress);
        }
    }
}
