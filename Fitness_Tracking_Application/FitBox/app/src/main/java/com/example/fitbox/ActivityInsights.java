package com.example.fitbox;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "activity_insights",
        foreignKeys = @ForeignKey(entity = UserProfile.class,
                parentColumns = "userProfileId",
                childColumns = "userProfileId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("userProfileId")})
public class ActivityInsights {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "activityInsightsId")
    private long activityInsightsId;

    @ColumnInfo(name = "userProfileId")
    private long userProfileId;  // Foreign key referencing UserProfile

    @ColumnInfo(name = "active_time")
    private int activeTime;

    @ColumnInfo(name = "inactive_time")
    private int inactiveTime;
    @ColumnInfo(name = "calories_burned")
    private int caloriesBurned;

    @ColumnInfo(name = "standing_duration")
    private int standingDuration;

    @ColumnInfo(name = "sitting_duration")
    private int sittingDuration;

    @ColumnInfo(name = "walking_duration")
    private int walkingDuration;

    @ColumnInfo(name = "jogging_duration")
    private int joggingDuration;

    @ColumnInfo(name = "stairs_duration")
    private int stairsDuration;

    @ColumnInfo(name = "calories_burned_progress")
    private double caloriesBurnedProgress;

    @ColumnInfo(name = "active_time_progress")
    private double activeTimeProgress;

    // Constructors, Getters, and Setters

    // Constructors
    public ActivityInsights(long userProfileId, int activeTime, int caloriesBurned, int inactiveTime, int standingDuration,
                            int sittingDuration, int walkingDuration, int joggingDuration,
                            int stairsDuration, double caloriesBurnedProgress, double activeTimeProgress) {
        this.userProfileId = userProfileId;
        this.activeTime = activeTime;
        this.caloriesBurned = caloriesBurned;
        this.inactiveTime = inactiveTime;
        this.standingDuration = standingDuration;
        this.sittingDuration = sittingDuration;
        this.walkingDuration = walkingDuration;
        this.joggingDuration = joggingDuration;
        this.stairsDuration = stairsDuration;
        this.caloriesBurnedProgress = caloriesBurnedProgress;
        this.activeTimeProgress = activeTimeProgress;
    }

    // Getters and Setters

    public long getActivityInsightsId() {
        return activityInsightsId;
    }

    public void setActivityInsightsId(long activityInsightsId) {
        this.activityInsightsId = activityInsightsId;
    }

    public long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(long userProfileId) {
        this.userProfileId = userProfileId;
    }

    public int getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(int activeTime) {
        this.activeTime = activeTime;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public int getInactiveTime() {
        return inactiveTime;
    }

    public void setInactiveTime(int inactiveTime) {
        this.inactiveTime = inactiveTime;
    }

    public int getStandingDuration() {
        return standingDuration;
    }

    public void setStandingDuration(int standingDuration) {
        this.standingDuration = standingDuration;
    }

    public int getSittingDuration() {
        return sittingDuration;
    }

    public void setSittingDuration(int sittingDuration) {
        this.sittingDuration = sittingDuration;
    }

    public int getWalkingDuration() {
        return walkingDuration;
    }

    public void setWalkingDuration(int walkingDuration) {
        this.walkingDuration = walkingDuration;
    }

    public int getJoggingDuration() {
        return joggingDuration;
    }

    public void setJoggingDuration(int joggingDuration) {
        this.joggingDuration = joggingDuration;
    }

    public int getStairsDuration() {
        return stairsDuration;
    }

    public void setStairsDuration(int stairsDuration) {
        this.stairsDuration = stairsDuration;
    }

    public double getCaloriesBurnedProgress() {
        return caloriesBurnedProgress;
    }

    public void setCaloriesBurnedProgress(double caloriesBurnedProgress) {
        this.caloriesBurnedProgress = caloriesBurnedProgress;
    }
    public double getActiveTimeProgress() {
        return activeTimeProgress;
    }

    public void setActiveTimeProgress(double activeTimeProgress) {
        this.activeTimeProgress = activeTimeProgress;
    }
}
