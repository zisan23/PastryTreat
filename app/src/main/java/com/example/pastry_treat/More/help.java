package com.example.pastry_treat.More;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pastry_treat.R;

public class help extends AppCompatActivity {

    private ImageButton call1, call2, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        call1 = findViewById(R.id.call1);
        call2 = findViewById(R.id.call2);
        mail = findViewById(R.id.mail);


        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "+880 1793704188";
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);
            }
        });

        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "+880 1534211977";
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = "ssadman8877@gmail.com";
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, "Send email using:"));
            }
        });


//        settings_msg_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String number = "01793704188";
//                Intent messageIntent = new Intent(Intent.ACTION_SENDTO);
//                messageIntent.setData(Uri.parse("smsto:" + number));
//                startActivity(messageIntent);
//            }
//        });

    }
    public void onBackPressed() {
        super.onBackPressed();
    }

}