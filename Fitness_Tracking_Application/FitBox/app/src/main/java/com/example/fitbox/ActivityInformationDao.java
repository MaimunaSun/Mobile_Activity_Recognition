package com.example.fitbox;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public interface ActivityInformationDao {
    @Insert
    void insert(ActivityInformation activityInformation);

    @Query("SELECT activityId FROM activity_information WHERE activity_name = :label")
    long getActivityIdByLabel(String label);

    @Query("SELECT * FROM activity_information WHERE activityId = :activityId AND activity_name = :label")
    ActivityInformation getActivityInformation(long activityId, String label);

    @Query("UPDATE activity_information SET activity_duration = :duration WHERE activity_name = :label")
    void updateActivityDuration(String label, long duration);

    @Query("SELECT * FROM activity_information WHERE activity_name = :activityName LIMIT 1")
    ActivityInformation getActivityInformationByName(String activityName);

    @Query("SELECT activity_duration FROM activity_information WHERE activity_name = :activityType")
    int getActivityDuration(String activityType);




}
