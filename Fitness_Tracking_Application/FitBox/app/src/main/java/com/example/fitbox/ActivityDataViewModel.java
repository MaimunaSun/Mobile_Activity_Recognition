package com.example.fitbox;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ActivityDataViewModel extends AndroidViewModel {
    private final ActivityDataRepository activityDataRepository;

    public ActivityDataViewModel(Application application, ActivityDataRepository activityDataRepository) {
        super(application);
        this.activityDataRepository = activityDataRepository;
    }

    public LiveData<List<ActivityData>> getAllActivityData() {
        return activityDataRepository.getAllActivityData();
    }

    public void insertActivityData(long userId, String label) {
        activityDataRepository.insertActivityData(userId, label);
    }

    public void updateUserProfile(int activeTime, int caloriesBurned,  double weight) {
        activityDataRepository.updateUserProfile(activeTime, caloriesBurned, weight);
    }

   /* public void updateActivityDataDuration() {
        activityDataRepository.updateActivityDurationsInBackground();
    }*/

    public void deleteAllActivityData() {
        activityDataRepository.deleteAllActivityData();
    }
}
