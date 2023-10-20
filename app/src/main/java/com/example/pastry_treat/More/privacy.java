package com.example.pastry_treat.More;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.pastry_treat.R;

import java.io.InputStream;
import java.util.Scanner;

public class privacy extends AppCompatActivity {

    private TextView privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy);

        ActionBar actionBar = getSupportActionBar(); //actionbar = toolbar
        if (actionBar != null) {
            actionBar.hide();
        }

        privacy = findViewById(R.id.privacy);
        InputStream inputStream = getResources().openRawResource(R.raw.privacy);

        try {
            Scanner scanner = new Scanner(inputStream);
            StringBuilder text = new StringBuilder();
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine());
            }
            privacy.setText(text.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}