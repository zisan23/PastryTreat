package com.example.pastry_treat;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

public class SignUpActivity extends AppCompatActivity {

    EditText username;
    EditText email;
    EditText newpassword;
    EditText confirmpassword;
    CountryCodePicker country_code;
    EditText mobilenumber;
    Button signupbutton;
    TextView logintext;
    MediaPlayer mediaPlayer;
    public void playsound() {
        if(mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        newpassword = findViewById(R.id.newpassword);
        confirmpassword = findViewById(R.id.confirmpassword);
        country_code = findViewById(R.id.country_code);
        mobilenumber = findViewById(R.id.mobilenumber);
        signupbutton = findViewById(R.id.signupbutton);
        logintext = findViewById(R.id.logintext);

        mediaPlayer = MediaPlayer.create(this, R.raw.signupbuttonsound);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playsound();
                Toast.makeText(SignUpActivity.this, "Still On Construction", Toast.LENGTH_SHORT).show();
            }
        });

        logintext.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
