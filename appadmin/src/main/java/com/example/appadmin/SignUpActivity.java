package com.example.appadmin;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appadmin.Models.OwnerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    private Button loginBtnSelector;

    private Button SignUpButton;

    EditText username, email, password, confirm_password;

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    ProgressBar progressBar;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        /*
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), RestaurentInfoActivity.class);
            startActivity(intent);
            finish();
        }*/
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        loginBtnSelector = (Button) findViewById(R.id.loginSelectorBtn);
        SignUpButton = (Button) findViewById(R.id.SignUpButton);


        loginBtnSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        username = findViewById(R.id.username);

        email = findViewById(R.id.email);

        password = findViewById(R.id.password);

        confirm_password = findViewById(R.id.confirm_password);

        progressBar = findViewById(R.id.progressBar);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Zisan", "starting signup");

                String sUsername, sEmail, sPassword, sConfirm_Password;
                progressBar.setVisibility(View.VISIBLE);

                sUsername = String.valueOf( username.getText());
                sEmail = String.valueOf(email.getText());
                sPassword = String.valueOf(password.getText());
                sConfirm_Password = String.valueOf(confirm_password.getText());


                if(TextUtils.isEmpty(sUsername)){
                    Toast.makeText(SignUpActivity.this, "Enter user name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(sEmail)){
                    Toast.makeText(SignUpActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(sPassword)){
                    Toast.makeText(SignUpActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(sConfirm_Password)){
                    Toast.makeText(SignUpActivity.this, "Enter confirmation password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(sEmail, sPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    // Sign in success, update UI with the signed-in user's information

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(SignUpActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();

                                    firestore.collection("Owner").document(FirebaseAuth.getInstance().getUid())
                                            .set(new OwnerModel(sUsername, sEmail, sPassword));
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(sUsername)
                                            .build();

                                    user.updateProfile(profileUpdates);


                                    Intent intent = new Intent(SignUpActivity.this, RestaurentInfoActivity.class);
                                    intent.putExtra("email",sEmail);
                                    startActivity(intent);
                                    //finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(SignUpActivity.this, "Authentication failed."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    switch (task.getException().toString()) {
                                        case "auth/email-already-in-use":
                                            //EmailWarning.setText("Email already in use");
                                            Toast.makeText(SignUpActivity.this, "Email already in use", Toast.LENGTH_SHORT).show();
                                        case "auth/invalid-email":
                                            //EmailWarning.setText("Invalid Email");
                                            Toast.makeText(SignUpActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                                        case "auth/weak-password":
                                            //PasswordWarning.setText("Password should be 8 characters or longer");
                                            Toast.makeText(SignUpActivity.this, "Password should be 8 characters or longer", Toast.LENGTH_SHORT).show();

                                        default:
                                            //PasswordWarning.setText("Error during sign up");
                                            Toast.makeText(SignUpActivity.this, "Error during sign up", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        });


            }
        });


    }
}