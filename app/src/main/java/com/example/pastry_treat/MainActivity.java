package com.example.pastry_treat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView buttonanim;
    ImageView title;
    public static final int DelayBeforeFadeIn = 500;
    public static final int FadeInDuration = 1500;
    public static final int StayDuration = 2000;
    public static final int FadeOutDuration = 1500;
    public static final int Timer = 1500;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        title = findViewById(R.id.title);
        title.setVisibility(View.INVISIBLE);

        // Delay before starting the fade-in animation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startFadeIn();
            }
        }, DelayBeforeFadeIn);
    }

    private void startFadeIn() {
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(FadeInDuration);
        fadeIn.setFillAfter(true);

        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setStartOffset(FadeInDuration + StayDuration);
        fadeOut.setDuration(FadeOutDuration);
        fadeOut.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(fadeIn);
        animationSet.addAnimation(fadeOut);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Start the Lottie animation after fade-out
                startLottieAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        title.startAnimation(animationSet);

    }

    private void startLottieAnimation() {
        buttonanim = findViewById(R.id.buttonanim);
        buttonanim.setVisibility(View.VISIBLE);
        buttonanim.playAnimation();

        // Delay the reset of the button
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resetButton();
            }
        }, Timer);
    }

    private void resetButton() {
        buttonanim.pauseAnimation();
        buttonanim.setVisibility(View.GONE);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}