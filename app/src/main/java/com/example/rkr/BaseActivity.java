package com.example.rkr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.rkr.enums.UserTypes;
import com.example.rkr.models.UserModel;
import com.example.rkr.views.AboutActivity;
import com.example.rkr.views.HomeActivity;
import com.example.rkr.views.LicenseActivity;
import com.example.rkr.views.MainActivity;
import com.example.rkr.views.RegisterProductActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        super.setContentView(R.layout.activity_base);
        TextView license = findViewById(R.id.text_license);
        ImageButton imageButton = findViewById(R.id.imgButton1);
        TextView mainMenu = findViewById(R.id.main_menu);
        TextView register_product = findViewById(R.id.register_product);
        TextView logOut = findViewById(R.id.log_out);
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

        imageButton.setOnClickListener(v -> {
            String url = "https://madeinukraine.gov.ua/national-cashback";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        register_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, RegisterProductActivity.class);
                startActivity(intent);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("auth", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                startActivity(intent);
            }
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

    public void setHeader(String label) {
        LinearLayout header = findViewById(R.id.header);
        if (header != null) {
            TextView headerTitle = header.findViewById(R.id.header_title);
            if (headerTitle != null) {
                headerTitle.setText(label);
            }
        }
    }

    public void updateHeader(String userModel) {
        LinearLayout blockLayout = findViewById(R.id.block_layout);
        TextView register_product = findViewById(R.id.register_product);
        if(userModel != null) {
            blockLayout.setVisibility(View.VISIBLE);
            if(userModel.equals(UserTypes.COMPANY.getValue())){
                register_product.setVisibility(View.GONE);
            }
            else {
                register_product.setVisibility(View.VISIBLE);
            }
        }
        else blockLayout.setVisibility(View.GONE);

    }
}
