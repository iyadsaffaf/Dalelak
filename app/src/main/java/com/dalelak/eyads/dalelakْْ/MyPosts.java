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

public class MyPosts extends AppCompatActivity {
    private Button marekt ;
    private Button info ;
    private Button ghide ;
    private Button events ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2_my);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.vzcvbbad);



        marekt =(Button)findViewById(R.id.button_add_market_my);
        info =(Button)findViewById(R.id.buttoninfo_my);
        ghide =(Button)findViewById(R.id.buttonghide_my);
        events =(Button)findViewById(R.id.buttonevents_my);

        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.in);


        marekt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                Intent Is = new Intent(MyPosts.this, EdMarket.class);
                startActivity(Is);
                finish();

            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);

                Intent Is = new Intent(MyPosts.this,EdInfo.class);
                finish();
                startActivity(Is);
            }
        });

        ghide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);

                Intent Is = new Intent(MyPosts.this, EdGuide.class);
                startActivity(Is);
                finish();

            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);

                Intent Is = new Intent(MyPosts.this, EdEvent.class);
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

                Intent Is = new Intent(MyPosts.this,MainActivity.class);
                finish();
                startActivity(Is);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent Is = new Intent(MyPosts.this,MainActivity.class);
        finish();
        startActivity(Is);         }
}
