package com.example.fitbox;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import net.sqlcipher.database.SupportFactory;

import javax.crypto.SecretKey;

@Database(entities = {UserAccount.class, UserProfile.class, ActivityData.class, ActivityInformation.class, ActivityInsights.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract UserAccountDao userAccountDao();
    public abstract UserProfileDao userProfileDao();
    public abstract ActivityDataDao activityDataDao();
    public abstract ActivityInformationDao activityInformationDao();
    public abstract ActivityInsightsDao activityInsightsDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    // Generate or retrieve the key from the Keystore
                    SecretKey secretKey = KeyManager.generateRandomKeyAndStore(context);
                    byte[] passphraseBytes = secretKey.getEncoded();
                    SupportFactory factory = new SupportFactory(passphraseBytes);
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "meal")
                            //.openHelperFactory(factory) // Corrected placement of openHelperFactory
                            .fallbackToDestructiveMigration() // This is an example, choose migration strategy accordingly
                            .build();
                }
            }
        }
        return instance;
    }

}
