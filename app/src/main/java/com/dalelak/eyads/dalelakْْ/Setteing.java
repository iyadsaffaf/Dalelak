package com.dalelak.eyads.dalelakْْ;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class Setteing extends AppCompatActivity {
private LinearLayout profile;
private LinearLayout privacy;
private  LinearLayout Account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setteing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar9);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.vdsvsv);

        profile = (LinearLayout)findViewById(R.id.linear_profile);
        privacy = (LinearLayout)findViewById(R.id.linear_privacy);
        Account = (LinearLayout)findViewById(R.id.linear_account);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Setteing.this, SettingProfile.class);
                finish();
                startActivity(i);

            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Setteing.this, SettingPrivacy.class);
                finish();
                startActivity(i);
            }
        });

        Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Setteing.this, SettingAccount.class);
                finish();
                startActivity(i);
            }
        });









    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                Intent Is = new Intent(Setteing.this,MainActivity.class);
                finish();
                startActivity(Is);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent Is = new Intent(Setteing.this,MainActivity.class);
        finish();
        startActivity(Is);         }



}
