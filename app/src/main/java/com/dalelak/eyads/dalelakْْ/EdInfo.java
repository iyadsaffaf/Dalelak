package com.dalelak.eyads.dalelakْْ;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dalelak.eyads.dalelakْْ.Adaptors.infoAdaptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class EdInfo extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Userpost> userposts;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private List<DataListInfo > dataListInfo;
    private LinearLayoutManager mLayoutManager;
    private infoAdaptor mInfoAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ed_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar7);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.svbb);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();

        recyclerView =(RecyclerView)findViewById(R.id.recycleview_my);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager( mLayoutManager);
        dataListInfo = new ArrayList<>();

        database.getReference("UserX").child(user.getUid()).child("Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
         userposts = new ArrayList<>();

                if (!dataSnapshot.hasChildren()) {

                    recyclerView.setAdapter(null);



                }
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.getValue(Userpost.class) != null) {
                        Userpost d = data.getValue(Userpost.class);
                        userposts.add(d);


                    }

                }
               getdata();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
public void getdata(){
        dataListInfo= new ArrayList<>();
for(int x=0 ;x<userposts.size();x++){

    database.getReference("info").child(userposts.get(x).getCountry()).child(userposts.get(x).getPostnumber()).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue(DataListInfo.class) != null) {
                    DataListInfo d = dataSnapshot.getValue(DataListInfo.class);

                    dataListInfo.add(d);

                }

            mInfoAdaptor = new infoAdaptor(EdInfo.this,dataListInfo ,userposts.get(0).getCountry(),true,userposts);

            recyclerView.setAdapter(mInfoAdaptor);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

}



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                Intent Is = new Intent(EdInfo.this,MyPosts.class);
                finish();
                startActivity(Is);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent Is = new Intent(EdInfo.this,MyPosts.class);
        finish();
        startActivity(Is);         }
}
