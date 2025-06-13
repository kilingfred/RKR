package com.example.rkr.views;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rkr.BaseActivity;
import com.example.rkr.R;

public class LicenseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        updateHeader(null);
        setContentView(R.layout.activity_license);
        setHeader("Ліцензія");
    }
}