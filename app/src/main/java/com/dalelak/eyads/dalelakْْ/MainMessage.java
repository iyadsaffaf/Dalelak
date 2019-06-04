package com.dalelak.eyads.dalelakْْ;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.dalelak.eyads.dalelakْْ.Adaptors.MainChatAdaptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainMessage extends AppCompatActivity {
private RecyclerView recyclerView;
private MainChatAdaptor mainChatAdaptor;

private LinearLayoutManager mLayoutManager;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private List<DataListUser> dataListUser;
    private ArrayList<String> users;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_message);
        users = new ArrayList<>();
        dataListUser = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar17);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.cvbfbs);
        recyclerView = (RecyclerView)findViewById(R.id.recycle_Mainchat);
        progressBar =(ProgressBar) findViewById(R.id.progressBarmainchat);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        mainChatAdaptor = new MainChatAdaptor(MainMessage.this,dataListUser);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager( mLayoutManager);

        recyclerView.setAdapter(mainChatAdaptor);
        progressBar.setVisibility(View.INVISIBLE);

        database.getReference("Chat").child(currentUser.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                progressBar.setVisibility(View.VISIBLE );


                final DataListMainChat m = dataSnapshot.getValue(DataListMainChat.class);
                database.getReference("Users").child(m.getUserid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataListUser d = dataSnapshot.getValue(DataListUser.class);
                        if(d!=null )
                            dataListUser.add(d);

                        else {DataListUser s = new DataListUser(m.getUserid(),"","NO user","","","","","","REMOVES","","");
                            dataListUser.add(s);

                        }
                        mainChatAdaptor.notifyDataSetChanged();
                        progressBar.setVisibility(View.INVISIBLE);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }








            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                refresh();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }



public  void refresh(){

            database.getReference("Chat").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataListUser=new ArrayList<>();

                    if(dataSnapshot.getChildrenCount()==0){
                        mainChatAdaptor = new MainChatAdaptor(MainMessage.this,dataListUser);

                        recyclerView.setAdapter(mainChatAdaptor);

                    }

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                       final DataListMainChat m = data.getValue(DataListMainChat.class);
                        database.getReference("Users").child(m.getUserid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                DataListUser d = dataSnapshot.getValue(DataListUser.class);
                                if(d!=null )
                                    dataListUser.add(d);

                                else {DataListUser s = new DataListUser(m.getUserid(),"","NO user","","","","","","REMOVES","","");
                                    dataListUser.add(s);

                                }

                                mainChatAdaptor.notifyDataSetChanged();
                                mainChatAdaptor = new MainChatAdaptor(MainMessage.this,dataListUser);

                                recyclerView.setAdapter(mainChatAdaptor);



                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });




}
    @Override
    protected void onStart() {
        super.onStart();

        database.getReference("Usersonline").child(currentUser.getUid()).child("timestamp").setValue(ServerValue.TIMESTAMP);
        database.getReference("Usersonline").child(currentUser.getUid()).child("online").removeValue();
        database.getReference("Usersonline").child(currentUser.getUid()).child("online").setValue(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.getReference("Usersonline").child(currentUser.getUid()).child("online").setValue(false);

    }

    @Override
    public void onBackPressed() {
super.onBackPressed();
           }
}
