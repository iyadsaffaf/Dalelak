package com.dalelak.eyads.dalelakْْ;


import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
    private DataListUser dataListUser;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    private TextView firstname;
    private TextView secondname;
    private TextView Staus;
    private ImageView profileimage;
    private String ID;
    private ImageView imageButton;
    private LinearLayout linearLayout;
    private Button sendmessge;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar13);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.vsdvvd);


        ID= getIntent().getStringExtra("userID") ;
        if(getIntent().getStringExtra("upbutton")!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } ;


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        firstname =(TextView)  findViewById(R.id.profile_firsname);
        secondname =(TextView)  findViewById(R.id.profile_secondname);
        Staus =(TextView)  findViewById(R.id.profile_status);
        profileimage =(ImageView)findViewById(R.id.profileimage);
        imageButton =(ImageView) findViewById(R.id.buttonnface);
        linearLayout=(LinearLayout) findViewById(R.id.lin_pro);
        sendmessge=(Button) findViewById(R.id.profilesendmessage);
        progressBar=(ProgressBar) findViewById(R.id.progressBarprofilr);
if(currentUser.getUid().equals(ID)){

    sendmessge.setVisibility(View.GONE);
}

        sendmessge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this,ChatWindow.class);
                i.putExtra("userIDfromProfile",ID);
                startActivity(i);
            }
        });
        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.in);


        imageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          view.startAnimation(animation);

              Uri uri = Uri.parse("https://www.facebook.com/app_scoped_user_id/"+dataListUser.getFaceID()+"/");
        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + "https://www.facebook.com/app_scoped_user_id/"+dataListUser.getFaceID()+"/");
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        Intent r = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(r);


      }


  });



        database.getReference("Users").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataListUser = dataSnapshot.getValue(DataListUser.class);
if(dataListUser!=null){
                firstname.setText(dataListUser.getFirstName());
                secondname.setText(dataListUser.getLastName());
                Staus.setText(dataListUser.getStatus());
}
                progressBar.setVisibility(View.VISIBLE);
if(dataListUser!=null)
                Picasso.with(Profile.this).load(dataListUser.getImageUri()).placeholder(R.drawable.whiteimage).into(profileimage, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {

                    }
                });

if(dataListUser!=null){
                if(dataListUser.getShowMyprofile().equals("yes")){
                 imageButton.setVisibility(View.VISIBLE);

                }
                if(dataListUser.getShowLastname().equals("yes")){
                    linearLayout.setVisibility(View.VISIBLE);

                }}


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
