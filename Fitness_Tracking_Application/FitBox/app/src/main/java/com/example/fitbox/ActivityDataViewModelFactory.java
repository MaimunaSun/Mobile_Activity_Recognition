package com.example.fitbox;
import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

public class ActivityDataViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final ActivityDataRepository activityDataRepository;

    public ActivityDataViewModelFactory(Application application, ActivityDataRepository activityDataRepository) {
        this.application = application;
        this.activityDataRepository = activityDataRepository;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ActivityDataViewModel.class)) {
            return (T) new ActivityDataViewModel(application, activityDataRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
