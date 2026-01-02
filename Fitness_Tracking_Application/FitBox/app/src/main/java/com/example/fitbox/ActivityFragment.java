
package com.example.fitbox;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ai.onnxruntime.OrtEnvironment;

public class ActivityFragment extends Fragment {

    private TextView textView;
    private ActivityDataAdapter adapter;
    private ActivityDataViewModel activityDataViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);

        // Initialize UI components
         textView = view.findViewById(R.id.textView);
         RecyclerView recyclerView = view.findViewById(R.id.recyclerViewNew);

        // Set up RecyclerView
        adapter = new ActivityDataAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        AppDatabase appDatabase = AppDatabase.getInstance(requireContext());
        ActivityDataDao activityDataDao = appDatabase.activityDataDao();
        UserProfileDao userProfileDao = appDatabase.userProfileDao();
        // Provide both instances to the repository

        ActivityDataRepository activityDataRepository = new ActivityDataRepository(activityDataDao, appDatabase,userProfileDao);
        ActivityDataViewModelFactory factory = new ActivityDataViewModelFactory(requireActivity().getApplication(), activityDataRepository);
        activityDataViewModel = new ViewModelProvider(this, factory).get(ActivityDataViewModel.class);

        // Observe the LiveData and update the RecyclerView adapter
        observeActivityData();

        return view;
    }

    private void observeActivityData() {
        activityDataViewModel.getAllActivityData().observe(getViewLifecycleOwner(), activityDataList -> {
            // Add the latest activity at the top of the list
            if (activityDataList != null && !activityDataList.isEmpty()) {
                ActivityData latestActivity = activityDataList.get(0);
                activityDataList.add(0, latestActivity);
                adapter.setActivityDataList(activityDataList);
            }

            // Update the heading based on the data availability
            if (activityDataList != null && !activityDataList.isEmpty()) {
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.GONE);
            }
        });
    }


}
