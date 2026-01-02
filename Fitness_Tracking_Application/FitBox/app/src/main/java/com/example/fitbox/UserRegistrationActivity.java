package com.example.fitbox;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UserRegistrationActivity extends AppCompatActivity {

    private EditText editTextName, editTextSurname, editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        // Initialize UI elements
        //editTextName = findViewById(R.id.editTextTextPersonName);
       // editTextSurname = findViewById(R.id.editTextTextPersonNameSurname);
       // editTextEmail = findViewById(R.id.editTextTextPersonNameEmail);
        //editTextPassword = findViewById(R.id.editTextTextPersonNamePassword);

        Button buttonRegister = findViewById(R.id.buttonAccount);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve user input
                final String name = editTextName.getText().toString();
                final String surname = editTextSurname.getText().toString();
                final String email = editTextEmail.getText().toString();
                final String password = editTextPassword.getText().toString();

                // Create a UserAccount object
                final UserAccount newUserAccount = new UserAccount(name, surname, password, email);

                // Insert the user account into the database
                insertUserAccountData(newUserAccount, new OnUserAccountInsertedListener() {
                    @Override
                    public void onUserAccountInserted(long userId) {
                        // The user account is inserted, and userId is available
                        newUserAccount.setUserId(userId);

                        // Start the UserProfileActivity
                        startUserProfileActivity(newUserAccount.getUserId());

                        // Finish the activity
                        finish();
                    }
                });
            }
        });
    }

    private interface OnUserAccountInsertedListener {
        void onUserAccountInserted(long userId);
    }

    private void insertUserAccountData(final UserAccount userAccount, final OnUserAccountInsertedListener listener) {
        AsyncTask.execute(() -> {
            // Get the DAOs using the appDatabase instance
            AppDatabase appDatabase = AppDatabase.getInstance(UserRegistrationActivity.this);
            UserAccountDao userAccountDao = appDatabase.userAccountDao();
            // Insert the user account into the database
            long userId = userAccountDao.insertUserAccount(userAccount);

            // Notify the listener with the generated userId
            if (listener != null) {
                listener.onUserAccountInserted(userId);
            }
        });
    }

    private void startUserProfileActivity(long userId) {
        // Start the UserProfileActivity
        // Pass the USER_ID to UserProfileActivity if needed
        // You can add an Intent to include USER_ID in UserProfileActivity
        Intent intent = new Intent(UserRegistrationActivity.this, UserProfileActivity.class);
        intent.putExtra("USER_ID", userId);
        startActivity(intent);
    }
}
