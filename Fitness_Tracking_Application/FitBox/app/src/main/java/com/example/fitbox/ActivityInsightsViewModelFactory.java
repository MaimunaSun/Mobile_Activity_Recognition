package com.example.fitbox;


import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

public class ActivityInsightsViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final ActivityInsightsRepository activityInsightsRepository;

    public ActivityInsightsViewModelFactory(Application application, ActivityInsightsRepository activityInsightsRepository) {
        this.application = application;
        this.activityInsightsRepository = activityInsightsRepository;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ActivityInsightsViewModel.class)) {
            return (T) new ActivityInsightsViewModel(application,activityInsightsRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
