package com.example.fitbox;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the registration layout
        setContentView(R.layout.activity_registration);

        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle registration logic here

                // Launch the PrivacyPolicyActivity
                startActivity(new Intent(RegistrationActivity.this, PrivacyPolicyActivity.class));
                finish(); // Finish the registration activity
            }
        });
    }
}
