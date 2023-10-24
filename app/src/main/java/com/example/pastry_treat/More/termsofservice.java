package com.example.pastry_treat.More;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.pastry_treat.R;

import java.io.InputStream;
import java.util.Scanner;

public class termsofservice extends AppCompatActivity {

    private TextView t_o_s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.terns_of_service);

        ActionBar actionBar = getSupportActionBar(); //actionbar = toolbar
        if (actionBar != null) {
            actionBar.hide();
        }

        t_o_s = findViewById(R.id.t_o_s);
        InputStream inputStream = getResources().openRawResource(R.raw.tos);

        try {
            Scanner scanner = new Scanner(inputStream);
            StringBuilder text = new StringBuilder();
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine());
            }
            t_o_s.setText(text.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onBackPressed() {
        super.onBackPressed();
    }
}