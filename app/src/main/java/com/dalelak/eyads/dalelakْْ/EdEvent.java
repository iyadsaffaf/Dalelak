package com.dalelak.eyads.dalelakْْ;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dalelak.eyads.dalelakْْ.Adaptors.eventAdaptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class EdEvent extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Userpost> userposts;
    private FirebaseDatabase database;
    private DataListUser dataListUser;
    private StorageReference mStorageRef;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private List<DataEvent > dataLists;
    private LinearLayoutManager mLayoutManager;
    private eventAdaptor eventAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ed_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar14);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.bsfb);



        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        user = mAuth.getCurrentUser();

        recyclerView =(RecyclerView)findViewById(R.id.edeventrecycleview);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager( mLayoutManager);
        dataLists = new ArrayList<>();
        database.getReference("UserX").child(user.getUid()).child("Event").addValueEventListener(new ValueEventListener() {
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
        dataLists= new ArrayList<>();
        for(int x=0 ;x<userposts.size();x++) {

            database.getReference("event").child(userposts.get(x).getCountry()).child(userposts.get(x).getPostnumber()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    try {

                        if (dataSnapshot.getValue(DataEvent.class) != null) {
                            DataEvent d = dataSnapshot.getValue(DataEvent.class);

                            dataLists.add(d);

                        }}catch (DatabaseException e){


                    }


                     eventAdaptor = new eventAdaptor(dataLists, EdEvent.this, true,userposts);

                    recyclerView.setAdapter(eventAdaptor);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                Intent Is = new Intent(EdEvent.this,MyPosts.class);
                finish();
                startActivity(Is);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent Is = new Intent(EdEvent.this,MyPosts.class);
        finish();
        startActivity(Is);         }
}
