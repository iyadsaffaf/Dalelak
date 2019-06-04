package com.dalelak.eyads.dalelakْْ;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EdMarket extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Userpost> userposts;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private List<dataList > dataLists;
    private LinearLayoutManager mLayoutManager;
    private marketAdaptor marketAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ed_market);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar8);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.vsfbds);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();

        recyclerView =(RecyclerView)findViewById(R.id.recycleview_mymarket);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager( mLayoutManager);
        dataLists = new ArrayList<>();

        database.getReference("UserX").child(user.getUid()).child("Market").addValueEventListener(new ValueEventListener() {
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

            database.getReference("Market").child(userposts.get(x).getCountry()).child(userposts.get(x).getPostnumber()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                      try {

                    if (dataSnapshot.getValue(DataListInfo.class) != null) {
                        dataList d = dataSnapshot.getValue(dataList.class);

                        dataLists.add(d);

                    }}catch (DatabaseException e){


                              }


                    marketAdaptor marketAdaptor = new marketAdaptor(dataLists, EdMarket.this, true,userposts);

                    recyclerView.setAdapter(marketAdaptor);
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

                Intent Is = new Intent(EdMarket.this,MyPosts.class);
                finish();
                startActivity(Is);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent Is = new Intent(EdMarket.this,MyPosts.class);
        finish();
        startActivity(Is);         }
}
