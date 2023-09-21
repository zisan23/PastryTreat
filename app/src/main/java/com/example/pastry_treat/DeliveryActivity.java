package com.example.pastry_treat;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DeliveryActivity extends AppCompatActivity {

    private TextView restaurantsText;
    private EditText searchEditText;
    private Button sendDeliveryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery);

        //  UI elements
        restaurantsText = findViewById(R.id.restaurantsText);
        searchEditText = findViewById(R.id.searchEditText);
        sendDeliveryButton = findViewById(R.id.sendDeliveryButton);

        // I will Set an OnClickListener for the "Send My Delivery" button
        sendDeliveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // I wil Add handle the button click event
                String searchText = searchEditText.getText().toString();
                // And later Perform actions based on the search input
            }
        });
    }
}

