package com.example.pastry_treat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;



import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    LottieAnimationView buttonanim;
    Button startbutton;

    public static final int Timer = 1000;



    protected void onResume() {
        super.onResume();
        // Make the startbutton visible when returning to the activity
        startbutton.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar(); //actionbar = toolbarif (actionBar != null) {
        actionBar.hide();
        startbutton = findViewById(R.id.startbutton);

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonanim = findViewById(R.id.buttonanim);
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
}