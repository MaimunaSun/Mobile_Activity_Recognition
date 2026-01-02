package com.example.fitbox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import android.widget.TextView;

public class ActivityDataAdapter extends RecyclerView.Adapter<ActivityDataAdapter.ActivityDataViewHolder> {

    private List<ActivityData> activityDataList = new ArrayList<>();

    public void setActivityDataList(List<ActivityData> activityDataList) {
        this.activityDataList = activityDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActivityDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_data, parent, false);
        return new ActivityDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityDataViewHolder holder, int position) {
        ActivityData activityData = activityDataList.get(position);
        holder.textActivity.setText(activityData.getActivity());
        holder.textTimestamp.setText(activityData.getTimeStamp().toString());
    }

    @Override
    public int getItemCount() {
        return activityDataList.size();
    }

    // ViewHolder class
    public static class ActivityDataViewHolder extends RecyclerView.ViewHolder {
        TextView textActivity;
        TextView textTimestamp;

        public ActivityDataViewHolder(@NonNull View itemView) {
            super(itemView);
            textActivity = itemView.findViewById(R.id.textActivity); // Replace with the actual ID
            textTimestamp = itemView.findViewById(R.id.textTimestamp); // Replace with the actual ID
        }
    }
}
