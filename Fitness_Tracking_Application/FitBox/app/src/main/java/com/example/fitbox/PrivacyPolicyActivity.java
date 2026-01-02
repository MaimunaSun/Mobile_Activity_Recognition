package com.example.fitbox;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        // Find the "I Agree" button and TextView in your layout
        Button buttonConsent = findViewById(R.id.buttonConsent);
        TextView textViewContent = findViewById(R.id.textViewContent);

        // Set the privacy policy text (you can replace this with your actual privacy policy)
        String privacyPolicyText = getString(R.string.privacy_policy);
        textViewContent.setText(Html.fromHtml(privacyPolicyText, Html.FROM_HTML_MODE_COMPACT));

        // Set an OnClickListener to handle the "I Agree" button click
        buttonConsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the User Registration Activity
                startActivity(new Intent(PrivacyPolicyActivity.this, UserRegistrationActivity.class));
                finish(); // Finish the PrivacyPolicyActivity
            }
        });
    }
}
