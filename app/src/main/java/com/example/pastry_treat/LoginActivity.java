package com.example.pastry_treat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {


   private Button signUpBtnSelector;

   private Button LoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUpBtnSelector = findViewById(R.id.signupSelectorBtn);
        LoginBtn = findViewById(R.id.loginButton);

        Transition transition = new AutoTransition();

        signUpBtnSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}