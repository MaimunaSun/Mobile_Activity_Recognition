package com.example.fitbox;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface UserProfileDao {
    @Query("SELECT * FROM user_profile")
    LiveData<List<UserProfile>> getAllUserProfileData();

    @Query("UPDATE user_profile SET active_time_goals = :activeTime WHERE userProfileId = :userProfileId")
    void updateActiveTimeUser(long userProfileId, int activeTime);

    @Query("UPDATE user_profile SET calories_burned_goals = :caloriesBurned WHERE userProfileId = :userProfileId")
    void updateCaloriesBurnedUser(long userProfileId, int caloriesBurned);

    @Query("UPDATE user_profile SET weight = :weight WHERE userProfileId = :userProfileId")
    void updateWeightUser(long userProfileId, double weight);

    @Query("SELECT active_time_goals FROM user_profile WHERE userProfileId = :userProfileId")
    LiveData<Integer> getActiveTimeLiveData(long userProfileId);

    @Query("SELECT calories_burned_goals FROM user_profile WHERE userProfileId = :userProfileId")
    LiveData<Integer> getCaloriesGoalLiveData(long userProfileId);

    @Query("SELECT weight FROM user_profile WHERE userProfileId = :userProfileId")
    LiveData<Double> getWeightLiveData(long userProfileId);

    @Query("SELECT active_time_goals FROM user_profile WHERE userProfileId = :userProfileId")
    int getActiveTime(long userProfileId);

    @Query("SELECT calories_burned_goals FROM user_profile WHERE userProfileId = :userProfileId")
    int getCaloriesGoal(long userProfileId);

    @Query("SELECT weight FROM user_profile WHERE userProfileId = :userProfileId")
    double getWeight(long userProfileId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUserProfile(UserProfile userProfile);

    @Query("SELECT * FROM user_profile WHERE userId = :userId LIMIT 1")
    LiveData<UserProfile> getUserProfileById(long userId);

    @Update
    void updateUserProfile(UserProfile userProfile);

    @Query("SELECT * FROM user_profile WHERE userId = :userId")
    UserProfile getUserProfileByUserId(long userId);

    @Query("SELECT userProfileId FROM user_profile LIMIT 1")
    long getUserProfileId(); // Adjust the query based on your actual structure

    @Query("SELECT weight FROM user_profile WHERE userProfileId = :userProfileId LIMIT 1")
    double getUserWeight(long userProfileId);

    @Query("SELECT active_time_goals FROM user_profile WHERE userProfileId = :userId")
    double getActiveTimeGoal(long userId);

    @Query("SELECT calories_burned_goals FROM user_profile WHERE userProfileId = :userId")
    double getCaloriesBurnedGoal(long userId);


}
