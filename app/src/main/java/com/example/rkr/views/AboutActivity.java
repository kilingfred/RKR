package com.example.rkr.views;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;

import com.example.rkr.BaseActivity;
import com.example.rkr.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHeader("Про нас");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);
    }
}