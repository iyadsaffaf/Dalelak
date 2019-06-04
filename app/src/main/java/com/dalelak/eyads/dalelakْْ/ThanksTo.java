package com.dalelak.eyads.dalelakْْ;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dalelak.eyads.dalelakْْ.Adaptors.ThanksAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ThanksTo extends AppCompatActivity {
private ThanksAdapter thanksAdapter;
private RecyclerView recyclerView;
private List<DatalistThanks> datalistThanks;
private LinearLayoutManager mLayoutManager;
private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thanks_to);
        datalistThanks=new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar19);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.czvsv);
        recyclerView=(RecyclerView)findViewById(R.id.thankrecycle);
        mLayoutManager = new LinearLayoutManager(ThanksTo.this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager( mLayoutManager);
        thanksAdapter=new ThanksAdapter(ThanksTo.this,datalistThanks);
        recyclerView.setAdapter(thanksAdapter);
        database= FirebaseDatabase.getInstance();

       database.getReference("Thanks").addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               DatalistThanks df = dataSnapshot.getValue(DatalistThanks.class);
               datalistThanks.add(df);
               thanksAdapter.notifyDataSetChanged();
           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

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
onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

super.onBackPressed();

    }
}
