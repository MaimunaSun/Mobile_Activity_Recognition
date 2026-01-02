package com.example.fitbox;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ActivityInsightsRepository {
    private static final String TAG = "ActivityInsights";
    private final ActivityInformationDao activityInformationDao;
    private final ActivityInsightsDao activityInsightsDao;
    private final UserProfileDao userProfileDao; // Add this Dao for getting user weight
    private long ID;
    private final Executor singleThreadExecutor = Executors.newSingleThreadExecutor();


    public ActivityInsightsRepository(ActivityInformationDao activityInformationDao, ActivityInsightsDao activityInsightsDao, UserProfileDao userProfileDao) {
        this.activityInformationDao = activityInformationDao;
        this.activityInsightsDao = activityInsightsDao;
        this.userProfileDao = userProfileDao;

    }

    public LiveData<Integer> getActiveTimeLiveData() {
        return userProfileDao.getActiveTimeLiveData(ID);
    }

    public LiveData<Integer> getCaloriesGoalLiveData() {
        return userProfileDao.getCaloriesGoalLiveData(ID);
    }

    public LiveData<Double> getWeightLiveData() {
        return userProfileDao.getWeightLiveData(ID);
    }

    public void updateActivityInsights() {
        singleThreadExecutor.execute(() -> {
        int totalCaloriesBurned = 0;
        //ID = userProfileDao.getUserProfileId();

        // Retrieve durations for each activity type and convert to minutes
        int joggingDuration = convertToMinutes(activityInformationDao.getActivityDuration("Jogging"));
        int walkingDuration = convertToMinutes(activityInformationDao.getActivityDuration("Walking"));
        int sittingDuration = convertToMinutes(activityInformationDao.getActivityDuration("Sitting"));
        int standingDuration = convertToMinutes(activityInformationDao.getActivityDuration("Standing"));
        int stairsDuration = convertToMinutes(activityInformationDao.getActivityDuration("Stairs"));

        // Calculate calories burned for each activity type
        totalCaloriesBurned += calculateCaloriesBurnedForActivity("Jogging", joggingDuration);
        totalCaloriesBurned += calculateCaloriesBurnedForActivity("Walking", walkingDuration);
        totalCaloriesBurned += calculateCaloriesBurnedForActivity("Sitting", sittingDuration);
        totalCaloriesBurned += calculateCaloriesBurnedForActivity("Standing", standingDuration);
        totalCaloriesBurned += calculateCaloriesBurnedForActivity("Stairs", stairsDuration);

        // Calculate active time (jogging + walking + stairs)
        int totalActiveTime = joggingDuration + walkingDuration + stairsDuration;

        // Calculate inactive time (standing + sitting)
        int totalInactiveTime = standingDuration + sittingDuration;

        // Update the total calories burned, active time, and inactive time in ActivityInsights table
        activityInsightsDao.updateTotalCaloriesBurned(totalCaloriesBurned, 1);
        activityInsightsDao.updateActiveTime(totalActiveTime, 1);
        activityInsightsDao.updateInactiveTime(totalInactiveTime, 1);

        // Update activity durations in ActivityInsights table
        activityInsightsDao.updateJoggingDuration(joggingDuration, 1);
        activityInsightsDao.updateWalkingDuration(walkingDuration, 1);
        activityInsightsDao.updateSittingDuration(sittingDuration, 1);
        activityInsightsDao.updateStandingDuration(standingDuration, 1);
        activityInsightsDao.updateStairsDuration(stairsDuration, 1);

        // Update progress values
        double activeTimeGoal = userProfileDao.getActiveTimeGoal(1);
        double caloriesBurnedGoal = userProfileDao.getCaloriesBurnedGoal(1);
        double caloriesBurnedProgress = 0;
        double activeTimeProgress = 0;

        if (activeTimeGoal > 0) {
            activeTimeProgress = (totalActiveTime / activeTimeGoal)*100;
        }

        if (caloriesBurnedGoal > 0) {
            caloriesBurnedProgress = (totalCaloriesBurned / caloriesBurnedGoal)*100;
        }

        // Update progress values in ActivityInsights table
        activityInsightsDao.updateActiveTimeProgress(activeTimeProgress, 1);
        activityInsightsDao.updateCaloriesBurnedProgress(caloriesBurnedProgress, 1);
        });

    }

    public LiveData<List<ActivityInsights>> getAllActivityInsightsData() {
        return activityInsightsDao.getAllActivityInsightsData();
    }

    public void updateActiveTimeUser(int activeTime) {
        singleThreadExecutor.execute(() -> {
            long userID = 1;
            userProfileDao.updateActiveTimeUser(userID, activeTime);
        });
    }

    public void updateWeightUser(double Weight) {
        singleThreadExecutor.execute(() -> {
            long userID = 1;
            userProfileDao.updateWeightUser(userID, Weight);
        });
    }

    public void updateCaloriesBurnedUser(int CaloriesBurned) {
        singleThreadExecutor.execute(() -> {
            long userID = 1;
            userProfileDao.updateCaloriesBurnedUser(userID, CaloriesBurned);
        });
    }

    public LiveData<List<UserProfile>> getAllUserProfileData() {
        return userProfileDao.getAllUserProfileData();
    }


    private int convertToMinutes(int durationInSeconds) {
        return durationInSeconds / 60; // Convert duration to minutes
    }

    private double calculateCaloriesBurnedForActivity(String activityType, int duration) {
        ActivityInformation activityInformation = activityInformationDao.getActivityInformationByName(activityType);

        if (activityInformation != null) {
            int caloriesBurnedPerMinute = calculateCaloriesBurned(activityInformation.getMet(),  userProfileDao.getUserWeight(1));
            return caloriesBurnedPerMinute*0.2*duration;
        } else {
            // Handle the case where activity information is not found (e.g., log an error)
            // You can throw an exception, log a message, or return a default value
            Log.e(TAG, "Activity information not found for: " + activityType);
            return 0.0; // Default value if activity information is not found
        }
    }

    private int calculateCaloriesBurned(double met, double userWeight) {
        return (int) Math.round(met * 3.5 * userWeight / 200);
    }


   /* private int calculateCaloriesBurned(double met, double userWeight) {
        return met * 3.5 * userWeight / 200;
    }*/

}

