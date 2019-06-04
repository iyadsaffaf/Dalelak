package com.dalelak.eyads.dalelakْْ;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Add_Info extends AppCompatActivity {
    private EditText editText;
    private Button ask;
    private FirebaseAuth mAuth;
    private Spinner mSpinner;
    private String[] countries;
    private Integer[] images;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    //private string
    private String country;
    private SpinnerAdaptorCountries mAdaptor;
    private DataListUser dataListUser;
    private Userpost userpost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__info);
        editText=(EditText)findViewById(R.id.ask_info);
        mSpinner=(Spinner)findViewById(R.id.spinner_info);
        ask=(Button)findViewById(R.id.button_info);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.svds);
        countries = new String[]{"Netherlands", "Germany", "Sweden", "Hungary", "Austria", "Denmark", "Belgium", "France", "Norway", "Finland", "Switzerland",
                "United Kingdom", "Spain", "Italy", "Greece", "Bulgaria"};

        images = new Integer[]{R.drawable.flag_netherlands,
                R.drawable.germanflag, R.drawable.swedenflag270x270, R.drawable.hungary,
                R.drawable.austria, R.drawable.denmark,
                R.drawable.belgium, R.drawable.france, R.drawable.norway, R.drawable.finland300x300, R.drawable.switherlands,
                R.drawable.unitedkingdom, R.drawable.spanish300x300, R.drawable.italy, R.drawable.greece, R.drawable.bulgaria};
         mAdaptor = new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorCountries(Add_Info.this, R.layout.spinner_ietm_countries, countries, images);
        mSpinner.setAdapter(mAdaptor);
        String s = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("MYLABEL", "Netherlands");

        mSpinner.setSelection(mAdaptor.getPosition(s));
        database = FirebaseDatabase.getInstance();

        database.getReference("Users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataListUser = dataSnapshot.getValue(DataListUser.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country = mSpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



ask.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(editText.getText().toString().equals("")){
            Toast.makeText(Add_Info.this, R.string.asa,Toast.LENGTH_LONG).show();
            return;
        }
        if(editText.getText().toString().length()<=6){
            Toast.makeText(Add_Info.this, R.string.qeeq,Toast.LENGTH_LONG).show();
            return;
        }
        if(editText.getText().toString().contains(" كس ")||editText.getText().toString().contains(" ذب ")||editText.getText().toString().contains(" اير ")
                ||editText.getText().toString().contains(" نيك ")||editText.getText().toString().contains(" طيز ")||editText.getText().toString().contains("fuck ")
                ||editText.getText().toString().contains("كس امك")||editText.getText().toString().contains("كس اختك")||editText.getText().toString().contains(" احبة ")){
            Toast.makeText(Add_Info.this, R.string.afaf,Toast.LENGTH_LONG).show();
            return;
        }


        ask.setEnabled(false);


                          String key = database.getReference("info").child(country).push().getKey();

                                Date c = Calendar.getInstance().getTime();
                                //send the informatiion to user x to retrive later
                                userpost = new Userpost(key,country);
                                database.getReference("UserX").child(currentUser.getUid()).child("Info").child(key).setValue(userpost);


                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd' At 'HH:mm", Locale.ENGLISH);
                                String formattedDate = df.format(c);
                                DataListInfo dataListInfo = new DataListInfo(editText.getText().toString().trim(),key,formattedDate,currentUser.getUid(),country,dataListUser.getImageUrlThumb());
                                database.getReference("info").child(country).child(key).setValue(dataListInfo);
                                Intent i = new Intent(Add_Info.this,MainActivity.class);
                                startActivity(i);
                                finish();

                            }







});

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country = mSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                Intent Is = new Intent(Add_Info.this,ADD_Main.class);
                finish();
                startActivity(Is);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent Is = new Intent(Add_Info.this,ADD_Main.class);
        finish();
        startActivity(Is);         }


}


