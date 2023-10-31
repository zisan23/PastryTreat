package com.example.appadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgetPassword extends AppCompatActivity {


    private EditText EmailAddress;
    private Button reset;

    private String email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);


        auth = FirebaseAuth.getInstance();

        EmailAddress = findViewById(R.id.EmailAddress);
        reset = findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pd = new ProgressDialog(ForgetPassword.this);
                pd.setMessage("Please wait...");
                pd.show();
                validateData(pd);
            }
        });

    }
    private void validateData(ProgressDialog pd) {
        email = EmailAddress.getText().toString();
        if(email.isEmpty()){
            pd.dismiss();
            EmailAddress.setText("Email Required");
            Toast.makeText(this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
        }
        else{
            resetPassword(pd);
        }
    }


    private void resetPassword(ProgressDialog pd) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    pd.dismiss();
                    Toast.makeText(ForgetPassword.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgetPassword.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                else{
                    pd.dismiss();
                    Toast.makeText(ForgetPassword.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}