package com.example.pastry_treat.Profile_Edit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pastry_treat.LoginActivity;
import com.example.pastry_treat.ProfileActivity;
import com.example.pastry_treat.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class profile_edit_activity extends AppCompatActivity {

    ImageView imageView;
    Button save_button_update;
    EditText profile_name, password, email;

    FirebaseAuth auth;
    FirebaseUser user;
    String uid;
    FirebaseFirestore db;
    DocumentReference docRef;
    StorageReference storageReference;


    int galaryrequestCode = 1000;
    String user_name, user_email, user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent data = getIntent();
        user_name = data.getStringExtra("name");
        user_email = data.getStringExtra("email");
        user_password = data.getStringExtra("password");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileref = storageReference.child("User/"+ auth.getCurrentUser().getUid()+"/Profile.png");
        profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageView);
            }
        });


        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            imageView = findViewById(R.id.imageView);
            save_button_update = findViewById(R.id.save_button_update);
            profile_name = findViewById(R.id.profile_name);
            password = findViewById(R.id.password);
            email = findViewById(R.id.email);

            profile_name.setText(user_name);
            email.setText(user_email);
            password.setText(user_password);

            uid = user.getUid();
            docRef = db.collection("User").document(uid);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openGallary = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(openGallary, galaryrequestCode);
                }
            });

            save_button_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(profile_name.getText().toString().isEmpty() || password.getText().toString().isEmpty() || email.getText().toString().isEmpty()){
                        Toast.makeText(profile_edit_activity.this, "Can not update empty fields", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String Email = email.getText().toString();
                    user.updateEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Map<String, Object> edited = new HashMap<>();
                            edited.put("email", Email);
                            edited.put("name", profile_name.getText().toString());
                            edited.put("password", password.getText().toString());
                            docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(profile_edit_activity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                    finish();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(profile_edit_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == galaryrequestCode){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                uploadImagetoFirebase(imageUri);
            }
        }
    }

    private void uploadImagetoFirebase(Uri imageUri) {
        StorageReference fileRef = storageReference.child("User/"+ auth.getCurrentUser().getUid()+"/Profile.png");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(profile_edit_activity.this, "Image Updated", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imageView);
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(profile_edit_activity.this, "Update failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
}