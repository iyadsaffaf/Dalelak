package com.dalelak.eyads.dalelakْْ;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class ADD_Main extends AppCompatActivity {
    private Button marekt ;
    private Button info ;
    private Button ghide ;
    private Button events ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__main);
        marekt =(Button)findViewById(R.id.button_add_market);
        info =(Button)findViewById(R.id.buttoninfo);
        ghide =(Button)findViewById(R.id.buttonghide);
        events =(Button)findViewById(R.id.buttonevents);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.sfvbs);


        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.in);

        marekt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                Intent Is = new Intent(ADD_Main.this, com.dalelak.eyads.dalelakْْ.AddMarket.class);
                startActivity(Is);
                finish();

            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);

                Intent Is = new Intent(ADD_Main.this,Add_Info.class);
                finish();
                startActivity(Is);
            }
        });

       ghide.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               view.startAnimation(animation);

               Intent Is = new Intent(ADD_Main.this, com.dalelak.eyads.dalelakْْ.AddGhide.class);
               startActivity(Is);
               finish();

           }
       });

       events.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               view.startAnimation(animation);

               Intent Is = new Intent(ADD_Main.this, com.dalelak.eyads.dalelakْْ.Addevents.class);
               startActivity(Is);
               finish();

           }
       });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                Intent Is = new Intent(ADD_Main.this,MainActivity.class);
                finish();
                startActivity(Is);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent Is = new Intent(ADD_Main.this,MainActivity.class);
        finish();
        startActivity(Is);         }
}
