package com.dalelak.eyads.dalelakْْ;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dalelak.eyads.dalelakْْ.Adaptors.ChatAdaptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatWindow extends AppCompatActivity {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private ChatAdaptor mChatAdaptor;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private ImageButton imageButton;
    private List<DataListMainChat> dataListMainChatss;
    private String userID;
    private DataListMainChat dataListMainChat;
    private EditText editText;
    private boolean conented;
    private MessageChat messageChat;
    private MessageChat messageChat2;
    private List<MessageChat> messageChats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_window);
       dataListMainChatss = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        imageButton = (ImageButton) findViewById(R.id.imageButton2d);
        editText = (EditText) findViewById(R.id.editTextmessage);
        messageChats = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar18);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("online");

        userID = getIntent().getStringExtra("userIDfromProfile");
        String removed="falsek";
        try {
            removed = getIntent().getStringExtra("removed");
       }
       catch (NullPointerException d){
        removed="falsel";

            }

   if(removed !=null){
       editText.setVisibility(View.GONE);
       imageButton.setVisibility(View.GONE);
       getSupportActionBar().setSubtitle(getString(R.string.deleted));



   }
        // if(userID.equals("NO USER")){}

        database.getReference("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataListUser d = dataSnapshot.getValue(DataListUser.class);
                if(d!=null)
                getSupportActionBar().setTitle(d.getFirstName()+" "+d.getLastName());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database.getReference("Usersonline").child(userID).child("online").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean I = dataSnapshot.getValue(Boolean.class);
                if(I !=null){
               if(I){
                getSupportActionBar().setSubtitle("online");

               }
                else {
                   database.getReference("Usersonline").child(userID).child("timestamp").addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {
                           Long l =dataSnapshot.getValue(Long.class);

                            if(l!=null)
                           getSupportActionBar().setSubtitle(getTimeAgo(l,ChatWindow.this));

                       }

                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });


               }}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });











        final DatabaseReference user2 = database.getReference("chatuser").child(currentUser.getUid()).push();

        database.getReference("Chat").child(currentUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(userID)){
                    conented=true;
                    return;
                }
                else {
                    conented=false;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database.getReference("Chat").child(  userID).child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(userID)){
                    conented=true;
                    return;
                }
                else {
                    conented=false;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



       imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equals("")){
                    return;
                }

                if(!conented) {


                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd' At 'HH:mm", Locale.ENGLISH);
                    String formattedDate = df.format(c);

                    DataListMainChat d = new DataListMainChat(userID,formattedDate);
                    database.getReference("Chat").child(currentUser.getUid()).child(userID).setValue(d);
                    d = new DataListMainChat(currentUser.getUid(),formattedDate);
                    database.getReference("Chat").child(userID).child(currentUser.getUid()).setValue(d);


                    conented=true;

                }
                Date c = Calendar.getInstance().getTime();



                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd' At 'HH:mm", Locale.ENGLISH);
                String formattedDate = df.format(c);
                String key = database.getReference("Message").child(currentUser.getUid()).child(userID).push().getKey();
                messageChat = new MessageChat(editText.getText().toString().trim(),"text",currentUser.getUid(),key,userID,true,formattedDate);
                database.getReference("Message").child(currentUser.getUid()).child(userID).child(key).setValue(messageChat);
                messageChat = new MessageChat(editText.getText().toString().trim(),"text",currentUser.getUid(),key,userID,false,formattedDate);

                database.getReference("Message").child(userID).child(currentUser.getUid()).child(key).setValue(messageChat);
                editText.setText("");

            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclechat);
        mChatAdaptor = new ChatAdaptor( ChatWindow.this,messageChats);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager( mLayoutManager);

        recyclerView.setAdapter(mChatAdaptor);
        new ChatWindow.download().execute();

        database.getReference("Message").child(currentUser.getUid()).child(userID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageChat m = dataSnapshot.getValue(MessageChat.class);
                messageChats.add(m);
                mChatAdaptor.notifyDataSetChanged();
                recyclerView.scrollToPosition(mChatAdaptor.getItemCount()-1);
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
        });}
    public  class download extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            messageChats= new ArrayList<>();
            database.getReference("Message").child(currentUser.getUid()).child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    messageChats = new ArrayList<>();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        MessageChat m = data.getValue(MessageChat.class);

                        messageChats.add(m);

                    }


                    mChatAdaptor = new ChatAdaptor( ChatWindow.this,messageChats);

                   recyclerView.scrollToPosition(mChatAdaptor.getItemCount()-1);
                   recyclerView.setAdapter(mChatAdaptor);


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

    public static String getTimeAgo(long time, Context ctx) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
           return null;
       }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
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
    public void onBackPressed() {
        super.onBackPressed();
    }

}
