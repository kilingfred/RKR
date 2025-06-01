package com.example.rkr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rkr.views.AboutActivity;
import com.example.rkr.views.LicenseActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        super.setContentView(R.layout.activity_base);
        TextView license = findViewById(R.id.text_license);
        TextView about = findViewById(R.id.text_about);
        ImageButton cashbackBtn = findViewById(R.id.imgButton2);

        license.setOnClickListener(v -> {
            Intent intent = new Intent(this, LicenseActivity.class);
            startActivity(intent);
        });

        about.setOnClickListener(v -> {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        });

        cashbackBtn.setOnClickListener(v -> {
            String url = "https://madeinukraine.gov.ua/national-cashback";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        // Inflate the layout into the content_frame
        FrameLayout contentFrame = findViewById(R.id.content_frame);
        if (contentFrame != null) {
            getLayoutInflater().inflate(layoutResID, contentFrame, true);
        } else {
            // fallback
            super.setContentView(layoutResID);
        }
    }


}
