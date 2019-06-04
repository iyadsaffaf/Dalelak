package com.dalelak.eyads.dalelakْْ;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import id.zelory.compressor.Compressor;

public class Addevents extends AppCompatActivity {

    private EditText title;
    private EditText date;
    private EditText startAT;
    private EditText endAt;
    private EditText adress;
    private EditText city;
    private EditText price;
    private EditText invitation;
    private EditText detail;
    private ImageView cover;
    private Button share;
    private Button upload;
    private ProgressBar br;
    private List<com.dalelak.eyads.dalelakْْ.datalistG> mdataList;
    private StorageReference mStorageRef;
    private Uri downloadUrl;
    private Uri imageUri;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    //spiner1
    private Spinner mSpinner;
    private String[] countries;
    private Integer[] images;
    private String country;
    private Bitmap bitmap;
private Uri downloadUrl3;
    private com.dalelak.eyads.dalelakْْ.SpinnerAdaptorCountries mAdaptor;
    private DataListUser dataListUser;
    private Userpost userpost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevents);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mSpinner=(Spinner)findViewById(R.id.spiner_event);

        title = (EditText) findViewById(R.id.event_title_editText);
        date = (EditText) findViewById(R.id.event_date_editText);
        startAT = (EditText) findViewById(R.id.event_startat_editText);
        endAt = (EditText) findViewById(R.id.event_endat_editText);
        adress = (EditText) findViewById(R.id.event_adress_edittext);
        city  = (EditText) findViewById(R.id.event_city_edittext);
        price = (EditText) findViewById(R.id.event_price_editText);
        invitation = (EditText) findViewById(R.id.event_invitation_edittext);
        detail = (EditText) findViewById(R.id.event_detail_editText);

        share = (Button) findViewById(R.id.event_share_button);
        upload = (Button) findViewById(R.id.event_upload_button);
        cover =(ImageView) findViewById(R.id.event_cover_imageview);

        br = (ProgressBar) findViewById(R.id.event_progress_buttton);
        br.setVisibility(View.VISIBLE);
        database = FirebaseDatabase.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.sfbf);


        database.getReference("Users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataListUser = dataSnapshot.getValue(DataListUser.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        countries = new String[]{"Netherlands", "Germany", "Sweden", "Hungary", "Austria", "Denmark", "Belgium", "France", "Norway", "Finland", "Switzerland",
                "United Kingdom", "Spain", "Italy", "Greece", "Bulgaria"};

        images = new Integer[]{R.drawable.flag_netherlands,
                R.drawable.germanflag, R.drawable.swedenflag270x270, R.drawable.hungary,
                R.drawable.austria, R.drawable.denmark,
                R.drawable.belgium, R.drawable.france, R.drawable.norway, R.drawable.finland300x300, R.drawable.switherlands,
                R.drawable.unitedkingdom, R.drawable.spanish300x300, R.drawable.italy, R.drawable.greece, R.drawable.bulgaria};
        mAdaptor = new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorCountries(Addevents.this, R.layout.spinner_ietm_countries, countries, images);
        mSpinner.setAdapter(mAdaptor);
        String s = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("MYLABEL", "Netherlands");

        mSpinner.setSelection(mAdaptor.getPosition(s));



        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country = mSpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });







        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON).setMinCropWindowSize(500, 500)
                        .setAspectRatio(1, 1)
                        .start(Addevents.this);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (bitmap == null) {
                    Toast.makeText(Addevents.this, R.string.fsadfaf, Toast.LENGTH_LONG).show();
                    return;
                }
                if (price.getText().toString().trim().equals("")) {
                    Toast.makeText(Addevents.this, R.string.dvsdvZ, Toast.LENGTH_LONG).show();
                    price.requestFocus();

                    return;
                }

                if (detail.getText().toString().trim().equals("")) {
                    Toast.makeText(Addevents.this, R.string.dsvsdzv, Toast.LENGTH_LONG).show();
                    detail.requestFocus();
                    return;
                }
                if (title.getText().toString().trim().equals("")) {
                    Toast.makeText(Addevents.this, R.string.bzbzxbx, Toast.LENGTH_LONG).show();
                    title.requestFocus();
                    return;
                }
                if (date.getText().toString().trim().equals("")) {
                    Toast.makeText(Addevents.this, R.string.bfzbbxzbz, Toast.LENGTH_LONG).show();
                    date.requestFocus();
                    return;
                }
                if (startAT.getText().toString().trim().equals("")) {
                    Toast.makeText(Addevents.this, R.string.bxzcbxzb, Toast.LENGTH_LONG).show();
                    startAT.requestFocus();
                    return;
                }
                if (endAt.getText().toString().trim().equals("")) {
                    Toast.makeText(Addevents.this, R.string.sbzsbxzbz, Toast.LENGTH_LONG).show();
                    endAt.requestFocus();
                    return;
                }
                if (city.getText().toString().trim().equals("")) {
                    Toast.makeText(Addevents.this, R.string.bzxbcxbzxc, Toast.LENGTH_LONG).show();
                    city.requestFocus();
                    return;
                }
                if (invitation.getText().toString().trim().equals("")) {
                    Toast.makeText(Addevents.this, R.string.bxczbzx, Toast.LENGTH_LONG).show();
                    invitation.requestFocus();
                    return;
                }
                if (adress.getText().toString().trim().equals("")) {
                    Toast.makeText(Addevents.this, R.string.zvvZVzcxbzsfn, Toast.LENGTH_LONG).show();
                    adress.requestFocus();
                    return;
                }









                share.setVisibility(View.INVISIBLE);
                Random v = new Random();
                int f = v.nextInt(3200000);

                ByteArrayOutputStream baos;
                    baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] datax = baos.toByteArray();
                    StorageReference mountainsRef = mStorageRef.child("event").child(currentUser.getUid()).child(f + ".jpg");
                    UploadTask uploadTask = mountainsRef.putBytes(datax);



                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                             downloadUrl3 = taskSnapshot.getDownloadUrl();
                            uploaddata();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(Addevents.this, "Trt Again", Toast.LENGTH_LONG).show();
                        }
                    });





            }
        });




    }
    public void uploaddata() {


        //upload datat

        database = FirebaseDatabase.getInstance();

        DatabaseReference dd = database.getReference("event").child(country);
        String key =database.getReference("event").child(country).push().getKey();


                DataEvent ds;


                //getting date
                Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd' At 'HH:mm", Locale.ENGLISH);
                String formattedDate = df.format(c);
                userpost = new Userpost(key,country);
                database.getReference("UserX").child(currentUser.getUid()).child("Event").child(key).setValue(userpost);

                ds = new com.dalelak.eyads.dalelakْْ.DataEvent(title.getText().toString().trim(),dataListUser.getFirstName(),currentUser.getUid(),
                        key,formattedDate,date.getText().toString().trim(),price.getText().toString().trim()
                        ,country,city.getText().toString().trim(),adress.getText().toString().trim(),startAT.getText().toString().trim(),
                        endAt.getText().toString().trim(),invitation.getText().toString().trim(),detail.getText().toString().trim(),downloadUrl3.toString());

                dd.child("" + (key)).setValue(ds);

                Intent I = new Intent(Addevents.this, MainActivity.class);
                startActivity(I);
                finish();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();

                File thumbPath = new File(imageUri.getPath());
                try {
                    bitmap= new Compressor(this)
                            .setMaxWidth(300)
                            .setMaxHeight(300)
                            .setQuality(70)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToBitmap(thumbPath);
                    cover.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                Intent Is = new Intent(Addevents.this,ADD_Main.class);
                finish();
                startActivity(Is);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent Is = new Intent(Addevents.this,ADD_Main.class);
        finish();
        startActivity(Is);         }
}
