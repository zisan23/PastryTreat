package com.example.appadmin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;

import com.example.appadmin.Models.RestaurentModel;
import com.example.appadmin.databinding.ActivityRestaurentInfoBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class RestaurentInfoActivity extends AppCompatActivity {

    ActivityRestaurentInfoBinding binding;
    private Uri imageUri;
    FirebaseFirestore firestore;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurentInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        binding.restInfoBtnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });

        binding.restInfoBtnCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final StorageReference storageReference = firebaseStorage.getReference().child("RestaurentImages")
                        .child(System.currentTimeMillis() + "");

                storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                RestaurentModel restaurentModel = new RestaurentModel();

                                restaurentModel.setProfile_image(uri.toString());
                                restaurentModel.setRestaurentName(binding.restInfoEtRestName.getText().toString().trim());
                                restaurentModel.setLocation(binding.restInfoEtRestLocation.getText().toString().trim());

                                // Get the current user's ID
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    String userId = user.getUid();
                                    restaurentModel.setOwnerId(userId);
                                }



                                // Save data to Firestore
                                firestore.collection("restaurants")
                                        .add(restaurentModel)
                                        .addOnSuccessListener(new OnSuccessListener() {
                                            @Override
                                            public void onSuccess(Object documentReference) {
                                                Toast.makeText(RestaurentInfoActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(RestaurentInfoActivity.this, RestaurentActivity.class);

                                                startActivity(intent);

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(RestaurentInfoActivity.this, "Upload Error!!!!", Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        });
                    }
                });
            }
        });
    }

    private void UploadImage() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_MEDIA_IMAGES)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(RestaurentInfoActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {
            assert data != null;
            imageUri = data.getData();
            binding.restInfoIvProfileImg.setImageURI(imageUri);
        }
    }
}
