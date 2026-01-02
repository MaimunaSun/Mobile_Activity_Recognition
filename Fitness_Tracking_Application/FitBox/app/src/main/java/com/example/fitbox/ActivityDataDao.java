package com.example.fitbox;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Delete;
import java.util.List;
import androidx.lifecycle.LiveData;


@Dao
public interface ActivityDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertActivityData(ActivityData activityData);


    @Query("SELECT * FROM activity_data ORDER BY time_stamp DESC")
    LiveData<List<ActivityData>> getAllActivityData();

    @Query("SELECT * FROM activity_data WHERE time_stamp BETWEEN :startTimeMillis AND :endTimeMillis")
    List<ActivityData> getActivitiesInTimeRange(long startTimeMillis, long endTimeMillis);

    // Inside ActivityDataDao
    @Query("DELETE FROM activity_data")
    void deleteAllActivityData();

    @Query("SELECT * FROM activity_data WHERE time_stamp >= :timestampThreshold")
    List<ActivityData> getRecentActivityData(long timestampThreshold);
}



