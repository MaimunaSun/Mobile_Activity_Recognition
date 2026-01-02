package com.example.fitbox;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ActivityDataRepository {
    private final ActivityDataDao activityDataDao;
    private final AppDatabase appDatabase;
    private final ActivityInformationDao activityInformationDao;
    private final UserProfileDao userProfileDao;

    private final Executor singleThreadExecutor = Executors.newSingleThreadExecutor();

    private final ScheduledExecutorService periodicUpdateExecutor = Executors.newScheduledThreadPool(1);

    public ActivityDataRepository(ActivityDataDao activityDataDao, AppDatabase appDatabase, UserProfileDao userProfileDao) {
        this.activityDataDao = activityDataDao;
        this.appDatabase = appDatabase;
        this.activityInformationDao = appDatabase.activityInformationDao();
        this.userProfileDao = userProfileDao;

        // Start the periodic update when the repository is created
        //startPeriodicActivityUpdate();
    }

    public void insertActivityData(long userId, String label) {
        singleThreadExecutor.execute(() -> {
            long activityId = getActivityIdByLabel(label);

            ActivityData activityData = new ActivityData(userId, activityId, label);
            activityDataDao.insertActivityData(activityData);
        });
    }

    public void updateUserProfile(int activeTime, int caloriesBurned,  double weight) {
        singleThreadExecutor.execute(() -> {
            long userID = 1;
            UserProfile userProfile = new UserProfile(userID, activeTime, caloriesBurned,  weight);
            userProfileDao.updateUserProfile(userProfile);
        });
    }

    private long getActivityIdByLabel(String label) {
        return activityInformationDao.getActivityIdByLabel(label);
    }

    public LiveData<List<ActivityData>> getAllActivityData() {
        return activityDataDao.getAllActivityData();
    }

    public void deleteAllActivityData() {
        singleThreadExecutor.execute(activityDataDao::deleteAllActivityData);
    }

    /*private void updateActivityDurationsInBackground(List<ActivityData> activityDataList) {
        singleThreadExecutor.execute(() -> {
        // Initialize durations for each label to zero
        int joggingDuration = 0;
        int walkingDuration = 0;
        int sittingDuration = 0;
        int standingDuration = 0;
        int stairsDuration = 0;

        // Calculate durations for each label
        for (ActivityData activityData : activityDataList) {
            switch (activityData.getActivity()) {
                case "Jogging":
                    joggingDuration += 2.5; // Assuming 2.5 seconds for each entry
                    break;
                case "Walking":
                    walkingDuration += 2.5;
                    break;
                case "Sitting":
                    sittingDuration += 2.5;
                    break;
                case "Standing":
                    standingDuration += 2.5;
                    break;
                case "Stairs":
                    stairsDuration += 2.5;
                    break;
            }
        }

        // Update the ActivityInformation table with the calculated durations
        activityInformationDao.updateActivityDuration("Jogging", joggingDuration);
        activityInformationDao.updateActivityDuration("Walking", walkingDuration);
        activityInformationDao.updateActivityDuration("Sitting", sittingDuration);
        activityInformationDao.updateActivityDuration("Standing", standingDuration);
        activityInformationDao.updateActivityDuration("Stairs", stairsDuration);

        // Update the ActivityInsights using ViewModel
        updateActivityInsights();
        });
    }

    private void startPeriodicActivityUpdate() {
        periodicUpdateExecutor.scheduleAtFixedRate(() -> {
            long twoMinutesAgo = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(2);
            List<ActivityData> activityDataList = activityDataDao.getRecentActivityData(twoMinutesAgo);

            // Calculate and update durations
            updateActivityDurationsInBackground(activityDataList);
        }, 0, 2, TimeUnit.MINUTES); // Run every 2 minutes
    }*/

    /*public void updateActivityDurationsInBackground() {
        singleThreadExecutor.execute(() -> {
            // Fetch recent activity data from the database
            long twoMinutesAgo = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(2);
            List<ActivityData> activityDataList = activityDataDao.getRecentActivityData(twoMinutesAgo);

            // Initialize durations for each label to zero
            int joggingDuration = 0;
            int walkingDuration = 0;
            int sittingDuration = 0;
            int standingDuration = 0;
            int stairsDuration = 0;

            // Calculate durations for each label
            for (ActivityData activityData : activityDataList) {
                switch (activityData.getActivity()) {
                    case "Jogging":
                        joggingDuration += 2.5; // Assuming 2.5 seconds for each entry
                        break;
                    case "Walking":
                        walkingDuration += 2.5;
                        break;
                    case "Sitting":
                        sittingDuration += 2.5;
                        break;
                    case "Standing":
                        standingDuration += 2.5;
                        break;
                    case "Stairs":
                        stairsDuration += 2.5;
                        break;
                }
            }

            // Update the ActivityInformation table with the calculated durations
            activityInformationDao.updateActivityDuration("Jogging", joggingDuration);
            activityInformationDao.updateActivityDuration("Walking", walkingDuration);
            activityInformationDao.updateActivityDuration("Sitting", sittingDuration);
            activityInformationDao.updateActivityDuration("Standing", standingDuration);
            activityInformationDao.updateActivityDuration("Stairs", stairsDuration);

            // Update the ActivityInsights using ViewModel
            //updateActivityInsights();
        });
    }*/

    private int calculateDuration(List<ActivityData> activityDataList, String label) {
        int duration = 0;

        for (ActivityData activityData : activityDataList) {
            if (label.equals(activityData.getActivity())) {
                // Each entry represents a duration of 2.5 seconds
                duration += 2.5;
            }
        }

        return duration;
    }



    // ... other methods
}
