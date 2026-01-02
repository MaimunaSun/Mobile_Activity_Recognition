package com.example.fitbox;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogIn = findViewById(R.id.LogIn);

        buttonLogIn.setOnClickListener(v -> {
            // Retrieve entered email and password
            String enteredEmail = editTextEmail.getText().toString().trim();
            String enteredPassword = editTextPassword.getText().toString().trim();

            // Validate the email and password by checking the database
            validateCredentials(enteredEmail, enteredPassword);
        });
    }

    private void validateCredentials(String enteredEmail, String enteredPassword) {
        // Perform the database check in an AsyncTask
        AsyncTask.execute(() -> {
            // Assuming you have a UserDao in your AppDatabase
            AppDatabase appDatabase = AppDatabase.getInstance(LogInActivity.this);
            UserAccountDao userAccountDao = appDatabase.userAccountDao();

            // Check if the entered credentials match any user in the database
            UserAccount userAccount = userAccountDao.getUserByEmailAndPassword(enteredEmail, enteredPassword);

            // If user is not null, credentials are valid
            if (userAccount != null) {
                // If credentials are valid, proceed to the Main Activity
                runOnUiThread(this::navigateToMainActivity);
            } else {
                // If credentials are not valid, show a toast message
                runOnUiThread(() -> Toast.makeText(LogInActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

        // Finish the current activity to prevent the user from going back to LogInActivity
        finish();
    }
}
