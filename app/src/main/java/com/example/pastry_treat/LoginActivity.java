package com.example.pastry_treat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


   private Button signUpBtnSelector;

   private Button LoginBtn;
   private ProgressBar progress_bar;
   private TextView fp;

   private EditText email, password;
   private Boolean passwordVisible = false ;
   FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUpBtnSelector = findViewById(R.id.signupSelectorBtn);
        LoginBtn = findViewById(R.id.loginButton);
        progress_bar = findViewById(R.id.progress_bar);
        fp = findViewById(R.id.fp);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intent);
            }
        });
        signUpBtnSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() > password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        togglePasswordVisibility();
                        return true; // Consume the touch event
                    }
                }

                return false; // Let the system handle typing input
            }
        });


        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Lemail, Lpassword;
                Lemail = String.valueOf(email.getText());
                Lpassword = String.valueOf(password.getText());

                if(TextUtils.isEmpty(Lemail)){
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(Lpassword)){
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progress_bar.setVisibility(View.VISIBLE);


                mAuth.signInWithEmailAndPassword(Lemail, Lpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // This code will be executed after the delay
                                    progress_bar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 1000);


                        } else {
                            // If sign in fails, display a message to the user.
                            progress_bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });
    }

    private void togglePasswordVisibility() {
        int selection = password.getSelectionEnd();
        if (passwordVisible) {
            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_24, 0);
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            passwordVisible = false;
        } else {
            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_24, 0);
            password.setTransformationMethod(null); // Clear the transformation method
            passwordVisible = true;
        }
        password.setSelection(selection);
    }
}