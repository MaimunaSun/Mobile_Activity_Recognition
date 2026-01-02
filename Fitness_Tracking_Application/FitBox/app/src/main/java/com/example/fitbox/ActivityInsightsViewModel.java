package com.example.fitbox;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ActivityInsightsViewModel extends AndroidViewModel {
    private final ActivityInsightsRepository activityInsightsRepository;

    public ActivityInsightsViewModel(Application application, ActivityInsightsRepository activityInsightsRepository) {
        super(application);
        this.activityInsightsRepository = activityInsightsRepository;
    }

    public void updateActivityInsights() {
        activityInsightsRepository.updateActivityInsights();
    }

    public void updateActiveTimeUser(int ActiveTime) {
        activityInsightsRepository.updateActiveTimeUser(ActiveTime);
    }

    public void updateWeightUser(double Weight) {
        activityInsightsRepository.updateWeightUser(Weight);
    }

    public void updateCaloriesBurnedUser(int CaloriesBurned) {
        activityInsightsRepository.updateCaloriesBurnedUser(CaloriesBurned);
    }



    public LiveData<Integer> getActiveTimeLiveData() {
        return activityInsightsRepository.getActiveTimeLiveData();
    }

    public LiveData<Integer> getCaloriesGoalLiveData() {
        return  activityInsightsRepository.getCaloriesGoalLiveData();
    }

    public LiveData<Double> getWeightLiveData() {
        return activityInsightsRepository.getWeightLiveData();
    }

    public LiveData<List<ActivityInsights>> getAllActivityInsightsData() {
        return activityInsightsRepository.getAllActivityInsightsData();
    }

    public LiveData<List<UserProfile>> getAllUserProfileData() {
        return activityInsightsRepository.getAllUserProfileData();
    }
}


