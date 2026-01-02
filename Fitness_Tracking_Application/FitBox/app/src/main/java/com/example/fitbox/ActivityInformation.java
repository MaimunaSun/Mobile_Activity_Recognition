package com.example.fitbox;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "activity_information")
public class ActivityInformation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "activityId")
    private long activityId;

    @ColumnInfo(name = "activity_name")
    private String activityName;

    @ColumnInfo(name = "met")
    private double met;

    @ColumnInfo(name = "activity_duration")
    private int activityDuration;

    // Constructors

    public ActivityInformation(String activityName, double met, int activityDuration) {
        this.activityName = activityName;
        this.met = met;
        this.activityDuration = activityDuration;
    }

    // Getters and Setters

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public double getMet() {
        return met;
    }

    public void setMet(double met) {
        this.met = met;
    }

    public int getActivityDuration() {
        return activityDuration;
    }

    public void setActivityDuration(int activityDuration) {
        this.activityDuration = activityDuration;
    }
}
