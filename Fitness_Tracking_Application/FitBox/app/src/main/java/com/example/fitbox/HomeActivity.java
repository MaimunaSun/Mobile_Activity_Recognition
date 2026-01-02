package com.example.fitbox;
import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;


public class HomeActivity extends AppCompatActivity implements SensorEventListener {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Executor singleThreadExecutor = Executors.newSingleThreadExecutor();
    private static final int TARGET_RATE_HZ = 20;
    //private final List<float[]> accelerometerData = new ArrayList<>();
    private final double [][] accelerometerData = new double [100][3];
    private int sampleCount = 0;
    private int sampleCountUI = 0;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long USER_ID;
    private long USER_PROFILE_ID;
    boolean userDataInserted = false;
    private int sensorDelay = 1000000/ TARGET_RATE_HZ;
    OrtEnvironment ortEnvironment;
    OrtSession ortSession;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //WorkManager.initialize(this, new Configuration.Builder().build());

        // Initialize the Room database
        AppDatabase appDatabase = AppDatabase.getInstance(this);
        new InsertDataAsyncTask(appDatabase).execute();
        //Retrieve the userId from the database using the UserAccount object
        UserAccount newUserAccount = new UserAccount("rose", "wilson", "penguins", "busiemczeel@gmail.com");
        insertUserAccountData(newUserAccount);
        insertUserProfileData(50, 10000, 75.0);
        //insertInitialActivityInsights(USER_PROFILE_ID);


        // Initialize and set up OrtEnvironment as needed
        ortEnvironment = OrtEnvironment.getEnvironment();
        // Initialize and set up OrtEnvironment as needed
        ortSession = createORTSession(ortEnvironment);


        ActivityDataDao activityDataDao = appDatabase.activityDataDao();
        UserProfileDao userProfileDao = appDatabase.userProfileDao();
        ActivityDataRepository activityDataRepository = new ActivityDataRepository(activityDataDao, appDatabase, userProfileDao);
        ActivityDataViewModelFactory factory = new ActivityDataViewModelFactory(getApplication(), activityDataRepository);
        ActivityDataViewModel activityDataViewModel = new ViewModelProvider(this, factory).get(ActivityDataViewModel.class);


        ActivityInsightsDao activityInsightsDao = appDatabase.activityInsightsDao();
        ActivityInformationDao activityInformationDao = appDatabase.activityInformationDao();

        ActivityInsightsRepository activityInsightsRepository = new ActivityInsightsRepository(activityInformationDao, activityInsightsDao, userProfileDao);
        ActivityInsightsViewModelFactory factoryI = new ActivityInsightsViewModelFactory(getApplication(), activityInsightsRepository);
        ActivityInsightsViewModel insightsViewModel = new ViewModelProvider(this,factoryI).get(ActivityInsightsViewModel.class);

        initializeSensorDataCollection();
        // Load the default fragment
        loadFragment(new HomeFragment());

        // Set up BottomNavigationView item selected listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    loadFragment(new HomeFragment());
                    return true;
                case R.id.activity:
                    loadFragment(new ActivityFragment());
                    return true;
                case R.id.profile:
                    loadFragment(new ProfileFragment());
                    return true;
            }
            return false;
        });
        // Start periodic work
        //startPeriodicWork();
    }

    // Helper method to load fragments
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void initializeSensorDataCollection() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (accelerometer != null) {
                sensorManager.registerListener(this, accelerometer, sensorDelay);
            } else {
                Toast.makeText(this, "Accelerometer sensor is not detected", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Sensor service is not detected", Toast.LENGTH_SHORT).show();
        }
    }

    /*@Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (userDataInserted) {
                accelerometerData[sampleCount][0] = event.values[0];
                accelerometerData[sampleCount][1] = event.values[1];
                accelerometerData[sampleCount][2] = event.values[2];
                sampleCount++;

                if (sampleCount == 100) {
                    sensorManager.unregisterListener(this);
                    FeatureExtractor.processAccelerometerData(accelerometerData);
                    FeatureExtractor.extractFeatures();
                    double[] features = FeatureExtractor.Features;
                    float[] features_Float = convertDoubleToFloat(features);

                    assert ortSession != null;
                    String label = Recognition.runPrediction(features_Float, ortSession, ortEnvironment);
                    //
                    //insertActivityLabel(label);
                    ActivityDataViewModel activityDataViewModel = new ViewModelProvider(this).get(ActivityDataViewModel.class);
                    activityDataViewModel.insertActivityData(USER_ID, label);
                    System.arraycopy(accelerometerData, 50, accelerometerData, 0, 50);


                    sensorManager.registerListener(this, accelerometer, sensorDelay);
                }
            } else{
                initializeSensorDataCollection();

            }
        }
    }*/
  @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (userDataInserted) {
                // Wait for the user data insertion to complete
                //float[] values = new float[]{event.values[0], event.values[1], event.values[2]};
                //accelerometerData[sampleCount] = values;
                sampleCountUI++;
                if (sampleCountUI == 60000) {
                    updateActivityDuration();
                    ActivityInsightsViewModel activityInsightsViewModel = new ViewModelProvider(this).get(ActivityInsightsViewModel.class);
                    activityInsightsViewModel.updateActivityInsights();
                // Reset the counter
                sampleCountUI = 0;
                 }
                else{
                accelerometerData[sampleCount][0] = event.values[0];
                accelerometerData[sampleCount][1] = event.values[1];
                accelerometerData[sampleCount][2] = event.values[2];
                sampleCount++;

                if (sampleCount == 100) {
                    sensorManager.unregisterListener(this);
                    FeatureExtractor.processAccelerometerData(accelerometerData);
                    FeatureExtractor.extractFeatures();
                    double[] features = FeatureExtractor.Features;
                    float[] features_Float = convertDoubleToFloat(features);
                    // Initialize and set up OrtEnvironment as needed
                    //OrtEnvironment ortEnvironment = OrtEnvironment.getEnvironment();
                    // Initialize and set up OrtEnvironment as needed
                    //OrtSession ortSession = createORTSession(ortEnvironment);
                    assert ortSession != null;
                    String label = Recognition.runPrediction(features_Float, ortSession, ortEnvironment);
                    //
                    //insertActivityLabel(label);
                    ActivityDataViewModel activityDataViewModel = new ViewModelProvider(this).get(ActivityDataViewModel.class);
                    activityDataViewModel.insertActivityData(USER_ID, label);

                    // Overlapping window: Process the next 60 samples with a 30-sample overlap
                    System.arraycopy(accelerometerData, 50, accelerometerData, 0, 50);
                    sampleCount = 50;
                    sensorManager.registerListener(this, accelerometer, sensorDelay);
                }
                else {
                    initializeSensorDataCollection();
                }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public float[] convertDoubleToFloat(double[] doubleArray) {
        float[] floatArray = new float[doubleArray.length];
        for (int i = 0; i < doubleArray.length; i++) {
            floatArray[i] = (float) doubleArray[i];
        }
        return floatArray;
    }
    private OrtSession createORTSession(OrtEnvironment ortEnvironment) {

        try {
            InputStream modelInputStream = getResources().openRawResource(R.raw.genesis);
            byte[] modelBytes = IOUtils.toByteArray(modelInputStream);
            modelInputStream.close();

            return ortEnvironment.createSession(modelBytes);
        } catch (IOException | OrtException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void startPeriodicWork() {

        // Create a Constraints object to ensure that the work only runs when the device is charging and connected to the network
        Constraints constraints = new Constraints.Builder()
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                MyPeriodicWorker.class,
                2, // Repeat interval in minutes
                TimeUnit.MINUTES
        ).setConstraints(constraints).build();



        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
    }

    private void insertUserAccountData(final UserAccount userAccount) {
        AsyncTask.execute(() -> {
            // Get the DAOs using the appDatabase instance
            AppDatabase appDatabase = AppDatabase.getInstance(this);
            UserAccountDao userAccountDao = appDatabase.userAccountDao();
            // Insert the user account into the database
            USER_ID = userAccountDao.insertUserAccount(userAccount);
            //set automatically generated UserId
            //UserAccount user = new UserAccount()
        });
    }

    private void insertInitialActivityInsights(long userProfileId) {
        AsyncTask.execute(() -> {
            // Get the DAO using the appDatabase instance
            AppDatabase appDatabase = AppDatabase.getInstance(this);
            ActivityInsightsDao activityInsightsDao = appDatabase.activityInsightsDao();

            // Create an ActivityInsights entity with all values set to 0 and the provided userProfileId
            ActivityInsights initialInsights = new ActivityInsights(userProfileId, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

            // Insert the entity into the table
            activityInsightsDao.insert(initialInsights);
        });
    }

    public void updateActivityDuration() {
        singleThreadExecutor.execute(() -> {
            // Initialize the Room database
            AppDatabase appDatabase = AppDatabase.getInstance(this);
            ActivityDataDao activityDataDao = appDatabase.activityDataDao();
            ActivityInformationDao activityInformationDao = appDatabase.activityInformationDao();
            // Fetch recent activity data from the database
            long twoMinutesAgo = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(5);
            List<ActivityData> activityDataList = activityDataDao.getRecentActivityData(twoMinutesAgo);

            // Initialize durations for each label to zero
            int joggingDuration = 0;
            int walkingDuration = 0;
            int sittingDuration = 0;
            int standingDuration = 0;
            int stairsDuration = 0;

            // Calculate durations for each label
            for (ActivityData activityData : activityDataList) {
                switch (activityData.getActivity()) {
                    case "Jogging":
                        joggingDuration += 2.5; // Assuming 2.5 seconds for each entry
                        break;
                    case "Walking":
                        walkingDuration += 2.5;
                        break;
                    case "Sitting":
                        sittingDuration += 2.5;
                        break;
                    case "Standing":
                        standingDuration += 2.5;
                        break;
                    case "Stairs":
                        stairsDuration += 2.5;
                        break;
                }
            }

            // Update the ActivityInformation table with the calculated durations
            activityInformationDao.updateActivityDuration("Jogging", joggingDuration);
            activityInformationDao.updateActivityDuration("Walking", walkingDuration);
            activityInformationDao.updateActivityDuration("Sitting", sittingDuration);
            activityInformationDao.updateActivityDuration("Standing", standingDuration);
            activityInformationDao.updateActivityDuration("Stairs", stairsDuration);

        });
    }

    private void insertUserProfileData(int activeTime, int caloriesBurned, double weight) {
        AsyncTask.execute(() -> {
            AppDatabase appDatabase = AppDatabase.getInstance(this);
            UserProfileDao userProfileDao = appDatabase.userProfileDao();
            UserProfile userProfile = new UserProfile(USER_ID, activeTime, caloriesBurned, weight);
            //userProfileDao.insertUserProfile(userProfile);
            USER_PROFILE_ID = userProfileDao.insertUserProfile(userProfile);
            if (USER_PROFILE_ID != 0) {
                insertInitialActivityInsights(USER_PROFILE_ID);
            }
            userDataInserted = true;

        });}

    private static class InsertDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private final AppDatabase appDatabase;

        InsertDataAsyncTask(AppDatabase appDatabase) {
            this.appDatabase = appDatabase;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Insert data into the database
            insertActivityInformation(appDatabase, "Jogging", 7, 0);
            insertActivityInformation(appDatabase, "Walking", 2.9, 0);
            insertActivityInformation(appDatabase, "Sitting", 1.3, 0);
            insertActivityInformation(appDatabase, "Standing", 2.3, 0);
            insertActivityInformation(appDatabase, "Stairs", 5.75, 0);

            return null;
        }

        private void insertActivityInformation(AppDatabase appDatabase, String activityName, double met, int activityDuration) {
            ActivityInformation activityInformation = new ActivityInformation(activityName, met, activityDuration);
            appDatabase.activityInformationDao().insert(activityInformation);
        }


    }

}

