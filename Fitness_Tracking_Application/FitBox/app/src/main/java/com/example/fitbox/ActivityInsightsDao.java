package com.example.fitbox;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ActivityInsightsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ActivityInsights activityInsights);

    @Query("UPDATE activity_insights SET calories_burned = calories_burned + :caloriesBurned WHERE userProfileId = :userProfileId")
    void updateTotalCaloriesBurned(int caloriesBurned, long userProfileId);

    @Update
    void update(ActivityInsights activityInsights);

    @Query("SELECT * FROM activity_insights")
    LiveData<List<ActivityInsights>> getAllActivityInsightsData();

    @Query("UPDATE activity_insights SET active_time = :activeTime WHERE userProfileId = :userProfileId")
    void updateActiveTime(int activeTime, long userProfileId);

    @Query("UPDATE activity_insights SET inactive_time = :inactiveTime WHERE userProfileId = :userProfileId")
    void updateInactiveTime(int inactiveTime, long userProfileId);


    @Query("UPDATE activity_insights SET jogging_duration = :joggingDuration WHERE userProfileId = :userProfileId")
    void updateJoggingDuration(int joggingDuration, long userProfileId);

    @Query("UPDATE activity_insights SET walking_duration = :walkingDuration WHERE userProfileId = :userProfileId")
    void updateWalkingDuration(int walkingDuration, long userProfileId);

    @Query("UPDATE activity_insights SET sitting_duration = :sittingDuration WHERE userProfileId = :userProfileId")
    void updateSittingDuration(int sittingDuration, long userProfileId);

    @Query("UPDATE activity_insights SET standing_duration = :standingDuration WHERE userProfileId = :userProfileId")
    void updateStandingDuration(int standingDuration, long userProfileId);

    @Query("UPDATE activity_insights SET stairs_duration = :stairsDuration WHERE userProfileId = :userProfileId")
    void updateStairsDuration(int stairsDuration, long userProfileId);

    @Query("UPDATE activity_insights SET active_time_progress = :activeTimeProgress WHERE userProfileId = :userId")
    void updateActiveTimeProgress(double activeTimeProgress, long userId);

    @Query("UPDATE activity_insights SET calories_burned_progress = :caloriesBurnedProgress WHERE userProfileId = :userId")
    void updateCaloriesBurnedProgress(double caloriesBurnedProgress, long userId);
}



