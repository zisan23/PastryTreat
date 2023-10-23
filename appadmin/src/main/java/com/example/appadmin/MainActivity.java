package com.example.appadmin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    LottieAnimationView buttonanim;
    Button startbutton;

    public static final int Timer = 1000;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar(); //actionbar = toolbarif (actionBar != null) {

        if(actionBar!= null){
            actionBar.hide();
        }

        startbutton = findViewById(R.id.startbutton);
        buttonanim = findViewById(R.id.buttonanim);

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonanim.setVisibility(View.VISIBLE);
                buttonanim.playAnimation();
                startbutton.setVisibility(View.GONE);
                new Handler().postDelayed(this::resetbutton, Timer);
            }

            private void resetbutton(){
                buttonanim.pauseAnimation();;
                buttonanim.setVisibility(View.GONE);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    protected void onResume() {
        super.onResume();
        // Make the startbutton visible when returning to the activity
        startbutton.setVisibility(View.VISIBLE);
    }
}