package com.dalelak.eyads.dalelakْْ;

import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalelak.eyads.dalelakْْ.Adaptors.comment_adaptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class comment_market extends AppCompatActivity {
    private RecyclerView recyclerView;
    private comment_adaptor adapt;
    private EditText editText;
    private ImageView images;
    private List<com.dalelak.eyads.dalelakْْ.DataListComment> dataListComments;
   private FirebaseDatabase database;
   private FirebaseUser currentuser;

    private String country;
    private  String postnumber;
    private String type;
    private int x;
    private List<DataListUser> dataListUser;
    private FirebaseAuth mAuth;;
    private TextView numberofcomments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_market);
        mAuth = FirebaseAuth.getInstance();
        currentuser=mAuth.getCurrentUser();
       country=  PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("MYLABEL", "Netherlands");
       type = getIntent().getStringExtra("type");
        postnumber = getIntent().getStringExtra("postname");
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_comment);
        database = FirebaseDatabase.getInstance();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        dataListComments = new ArrayList<>();

        recyclerView.setLayoutManager(mLayoutManager);
        adapt = new comment_adaptor( comment_market.this,dataListComments);

      //  new download().execute();

        recyclerView.setAdapter(adapt);
        editText =(EditText)findViewById(R.id.commenttext);
        numberofcomments=(TextView) findViewById(R.id.like_box);
        images =(ImageView)findViewById(R.id.send);
       dataListUser = new ArrayList<>();



        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String z= editText.getText().toString().trim();
                if(z.equals("")){
                    return;
                }
                Date c = Calendar.getInstance().getTime();


                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd' At 'HH:mm", Locale.ENGLISH);
                String formattedDate = df.format(c);
                String push=database.getReference("content").child(type).child(country).child(postnumber).child("comments").child(x+"").push().getKey();
                DataListComment d = new DataListComment(currentuser.getUid(),push,formattedDate,z,country,type,postnumber);

                database.getReference("content").child(type).child(country).child(postnumber).child("comments").child(push).setValue(d);
                database.getReference("UserX").child(currentuser.getUid()).child("comments").child(push).setValue(d);


                editText.setText("");


            }
        });

        database.getReference("content").child(type).child(country).child(postnumber)
                .child("comments").addChildEventListener(new ChildEventListener() {
                                                             @Override
                                                             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                                 DataListComment d = dataSnapshot.getValue(DataListComment.class);
                                                                 dataListComments.add(d);

                                                                 adapt = new comment_adaptor( comment_market.this,dataListComments);
                                                                 recyclerView.setAdapter(adapt);

                                                                 recyclerView.scrollToPosition(adapt.getItemCount()-1);                                                                 x++;
                                                                 numberofcomments.setText(getString(R.string.commentnumber)+" "+x);



                                                             }

                                                             @Override
                                                             public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                                             }

                                                             @Override
                                                             public void onChildRemoved(DataSnapshot dataSnapshot) {
                                                                 DataListComment d = dataSnapshot.getValue(DataListComment.class);
                                                                 if(x==1)
                                                                     x=0;
                                                               new download().execute();


                                                                 numberofcomments.setText(getString(R.string.commentnumber)+" "+x);
                                                                 x--;


                                                             }

                                                             @Override
                                                             public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                                             }

                                                             @Override
                                                             public void onCancelled(DatabaseError databaseError) {

                                                             }
                                                         }
        );

    }
    public  class download extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            dataListComments = new ArrayList<>();
            database.getReference("content").child(type).child(country).child(postnumber).child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataListComments = new ArrayList<>();

                    x=0;
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        x++;
                        numberofcomments.setText(getString(R.string.commentnumber)+" "+x);
                        if (data.getValue(com.dalelak.eyads.dalelakْْ.DataListComment.class) == null) {
                            x=0;
                            numberofcomments.setText(getString(R.string.commentnumber)+" "+x);



                        }
                        if (data.getValue(com.dalelak.eyads.dalelakْْ.DataListComment.class) != null) {
                           DataListComment d = data.getValue(DataListComment.class);
                            dataListComments.add(d);



                        }

                    }

                    adapt = new comment_adaptor( comment_market.this,dataListComments);
                    recyclerView.setAdapter(adapt);

                    recyclerView.scrollToPosition(adapt.getItemCount()-1);



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);




        }

    }


}
