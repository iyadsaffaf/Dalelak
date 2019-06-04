package com.dalelak.eyads.dalelakْْ;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class SettingProfile extends AppCompatActivity {
    private FirebaseDatabase database;
    private DataListUser dataListUser;
    private StorageReference mStorageRef;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private EditText lastname;
    private EditText status;
    private ImageView profileimage;
    private Button  Chlastname;
    private Bitmap bitmap;
    private Bitmap bitmapthumb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar12);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.profile);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();
        lastname = (EditText) findViewById(R.id.Set_Pro_lastname);
        status = (EditText) findViewById(R.id.Set_Pro_status);
        profileimage = (ImageView) findViewById(R.id.Set_Pro_image);

        Chlastname = (Button) findViewById(R.id.Set_Pro_saveimage);

        database.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(DataListUser.class) == null) {
                    return;
                }

                dataListUser = dataSnapshot.getValue(DataListUser.class);

                lastname.setText(dataListUser.getLastName());
                status.setText(dataListUser.getStatus());
                Picasso.with(SettingProfile.this).load(dataListUser.getImageUri()).placeholder(R.drawable.emptys).into(profileimage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON).setMinCropWindowSize(500, 500)
                        .setAspectRatio(1, 1)
                        .start(SettingProfile.this);
            }
        });

Chlastname.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Chlastname.setVisibility(View.GONE);
        ///////////
        if(bitmap!=null){


        ByteArrayOutputStream baos;
        baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] datax = baos.toByteArray();

        StorageReference mountainsRef = mStorageRef.child("Users/" + currentUser.getUid() + "/profile.jpg");
        UploadTask uploadTask = mountainsRef.putBytes(datax);

        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                database.getReference("Users").child(currentUser.getUid()).child("imageUri").setValue(task.getResult().getDownloadUrl().toString());






            }
        });

        baos = new ByteArrayOutputStream();

        bitmapthumb.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        datax = baos.toByteArray();

        mountainsRef = mStorageRef.child("Users/" + currentUser.getUid() + "/thumb.jpg");
        uploadTask = mountainsRef.putBytes(datax);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                database.getReference("Users").child(currentUser.getUid()).child("imageUrlThumb").setValue(task.getResult().getDownloadUrl().toString());


            }
        });

        }
        /////


        database.getReference("Users").child(currentUser.getUid()).child("status").setValue(status.getText().toString().trim());


        ////

        database.getReference("Users").child(currentUser.getUid()).child("lastName").setValue(lastname.getText().toString().trim());
        Toast.makeText(SettingProfile.this, R.string.fsfsb,Toast.LENGTH_LONG).show();
        Chlastname.setVisibility(View.VISIBLE);
        Intent d = new Intent(SettingProfile.this,MainActivity.class);
        finish();
        startActivity(d);



    }
});


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                Intent Is = new Intent(SettingProfile.this,Setteing.class);
                finish();
                startActivity(Is);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent Is = new Intent(SettingProfile.this,Setteing.class);
        finish();
        startActivity(Is);         }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri imageUri = result.getUri();
                File thumbPath = new File(imageUri.getPath());
                try {  bitmap= new Compressor(this)
                                .setMaxWidth(300)
                                .setMaxHeight(300)
                                .setQuality(70)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                .compressToBitmap(thumbPath);
                    profileimage.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                try {  bitmapthumb= new Compressor(this)
                        .setMaxWidth(50)
                        .setMaxHeight(50)
                        .setQuality(30)
                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .compressToBitmap(thumbPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
