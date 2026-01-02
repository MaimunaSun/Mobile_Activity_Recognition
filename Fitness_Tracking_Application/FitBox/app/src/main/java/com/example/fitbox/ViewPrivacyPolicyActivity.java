package com.example.fitbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewPrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_privacy_policy);

        TextView textViewContent = findViewById(R.id.textViewContentView);

        // Set the privacy policy text (you can replace this with your actual privacy policy)
        String privacyPolicyText = getString(R.string.privacy_policy);
        textViewContent.setText(Html.fromHtml(privacyPolicyText, Html.FROM_HTML_MODE_COMPACT));

        // Set OnClickListener for the "Done" button
        Button doneButton = findViewById(R.id.buttonDone);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and go back to the previous activity
                finish();
            }
        });
    }
}