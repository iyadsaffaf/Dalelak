package com.dalelak.eyads.dalelakْْ;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingPrivacy extends AppCompatActivity {
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private CheckBox facebook,lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_privacy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar10);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.dvav);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();


         facebook = ( CheckBox ) findViewById( R.id.checkBox2 );
         lastname = ( CheckBox ) findViewById( R.id.checkBox3 );




        lastname.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                    database.getReference("Users").child(currentUser.getUid()).child("showLastname").setValue("yes");
                    return;
                }

                database.getReference("Users").child(currentUser.getUid()).child("showLastname").setValue("no");

            }
        });
        facebook.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                    database.getReference("Users").child(currentUser.getUid()).child("showMyprofile").setValue("yes");
                    return;
                }

                    database.getReference("Users").child(currentUser.getUid()).child("showMyprofile").setValue("no");

            }
        });



/////////////////////////

        database.getReference("Users").child(currentUser.getUid()).child("showMyprofile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              String s = dataSnapshot.getValue(String.class);
              if(s.equals("yes")){
                 facebook.setChecked(true);

              }
                if(s.equals("no")){
                    facebook.setChecked(false);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        database.getReference("Users").child(currentUser.getUid()).child("showLastname").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                if(s.equals("yes")){
                    lastname.setChecked(true);

                }
                if(s.equals("no")){
                    lastname.setChecked(false);

                }
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

                Intent Is = new Intent(SettingPrivacy.this,Setteing.class);
                finish();
                startActivity(Is);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent Is = new Intent(SettingPrivacy.this,Setteing.class);
        finish();
        startActivity(Is);         }
}
