package com.example.fitbox;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyPeriodicWorker extends Worker {

    private final ActivityDataDao activityDataDao;
    private final ActivityInformationDao activityInformationDao;

    public MyPeriodicWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params,
            AppDatabase appDatabase
    ) {
        super(context, params);
        // Assuming you have references to your DAO instances
        this.activityDataDao = appDatabase.activityDataDao();
        this.activityInformationDao = appDatabase.activityInformationDao();
    }

    @NonNull
    @Override
    public Result doWork() {
        // Perform your background task here
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
        updateActivityDurationsInBackground(
                joggingDuration, walkingDuration, sittingDuration, standingDuration, stairsDuration);

        // Update the ActivityInsights using ViewModel
        // updateActivityInsights();

        return Result.success();
    }

    private void updateActivityDurationsInBackground(
            int joggingDuration, int walkingDuration, int sittingDuration, int standingDuration, int stairsDuration) {
        // Update the ActivityInformation table with the calculated durations
        activityInformationDao.updateActivityDuration("Jogging", joggingDuration);
        activityInformationDao.updateActivityDuration("Walking", walkingDuration);
        activityInformationDao.updateActivityDuration("Sitting", sittingDuration);
        activityInformationDao.updateActivityDuration("Standing", standingDuration);
        activityInformationDao.updateActivityDuration("Stairs", stairsDuration);
    }

    /* private void updateActivityInsights() {
        // Assuming you have a constructor in ActivityInsightsViewModel that takes ActivityInsightsRepository
        ActivityInsightsViewModel activityInsightsViewModel = new ActivityInsightsViewModel(
                new ActivityInsightsRepository(activityInformationDao, userProfileDao)
        );
        activityInsightsViewModel.updateActivityInsights();
    } */
}
