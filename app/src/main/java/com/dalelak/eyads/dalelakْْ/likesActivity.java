package com.dalelak.eyads.dalelakْْ;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.dalelak.eyads.dalelakْْ.Adaptors.LikesAdaptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class likesActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private String type;
    private String country;
    private String postnumber;
    private String whatsoort;
    private List<String> users;
    private List<DataListUser> dataListUsers;

    private RecyclerView recyclerView;

    private LikesAdaptor likesAdaptor;
private int dd=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database= FirebaseDatabase.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.likes_recycleview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar16);
        setSupportActionBar(toolbar);
        type = getIntent().getStringExtra("type");
        country = getIntent().getStringExtra("country");
        postnumber = getIntent().getStringExtra("postnumber");
        whatsoort = getIntent().getStringExtra("whatsoort");
        getSupportActionBar().setTitle(whatsoort);



        database.getReference("content").child(type).child(country).child(postnumber).child(whatsoort).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    try {
                        if (data.getValue(String.class) != null) {
                            String d = data.getValue(String.class);

                            users.add(d);

                        }
                    }catch (com.google.firebase.database.DatabaseException e){
                        continue;

                    }

                }
                    getdata();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    private void getdata() {
        dataListUsers = new ArrayList<>();
        for(int x=0 ; x<users.size();x++) {
            dd=x;
            database.getReference("Users").child(users.get(x)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    DataListUser d = dataSnapshot.getValue(DataListUser.class);
                    dataListUsers.add(d);

                if(dd==users.size()-1){
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(likesActivity.this);
                    mLayoutManager.setReverseLayout(true);
                    mLayoutManager.setStackFromEnd(true);

                    recyclerView.setLayoutManager(mLayoutManager);
                    likesAdaptor = new LikesAdaptor(likesActivity.this, dataListUsers);

                    recyclerView.setAdapter(likesAdaptor);}

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }




}
