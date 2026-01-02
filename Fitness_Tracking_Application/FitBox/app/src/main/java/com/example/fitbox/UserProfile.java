package com.example.fitbox;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(
        tableName = "user_profile",
        foreignKeys = @ForeignKey(
                entity = UserAccount.class,
                parentColumns = "userId",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("userId")}
)

@TypeConverters(UserProfileDateConverter.class)
public class UserProfile {

    @PrimaryKey(autoGenerate = true)
    private long userProfileId;

    @ColumnInfo(name = "userId")
    private long userId;  // Foreign Key

    @ColumnInfo(name = "active_time_goals")
    private int activeTime;

    @ColumnInfo(name = "calories_burned_goals")
    private int caloriesBurned;

    @ColumnInfo(name = "weight")
    private double weight;

    // Constructor without age
    public UserProfile(long userId, int activeTime, int caloriesBurned,  double weight) {
        this.userId = userId;
        this.activeTime = activeTime;
        this.caloriesBurned = caloriesBurned;
        this.weight = weight;
    }

   /* // Empty constructor
    public UserProfile() {
        // Default constructor
    }*/

    public void setUserProfileId(long userProfileId) {
        this.userProfileId = userProfileId;
    }

    // Getter for userProfileId
    public long getUserProfileId() {
        return userProfileId;
    }

    // Getter for userId
    public long getUserId() {
        return userId;
    }

    // Getter for activityGoals
    public int getActiveTime() {
        return activeTime;
    }

    // Getter for gender
    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    // Getter for birthDate

    public double getWeight() {
        return weight;
    }




}
