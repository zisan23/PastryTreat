package com.example.pastry_treat.More;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.pastry_treat.R;

import java.io.InputStream;
import java.util.Scanner;

public class AboutUs extends AppCompatActivity {
    private TextView description;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        description = findViewById(R.id.description);
        InputStream inputStream = getResources().openRawResource(R.raw.about);

        try {
            Scanner scanner = new Scanner(inputStream);
            StringBuilder text = new StringBuilder();
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine());
            }
            description.setText(text.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void onBackPressed() {
        super.onBackPressed();
    }
}