package com.dalelak.eyads.dalelakْْ;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dalelak.eyads.dalelakْْ.Adaptors.FacebookLoginActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class SettingAccount extends AppCompatActivity {
    private Button deleteacoount;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase database;
    private StorageReference mStorageRef;
    private FirebaseStorage firebaseStorage;
    private List<Userpost > market;
    private List<Userpost> guide;
    private List<Userpost> info;
    private List<Userpost> event;
    private List<datalistlike> likes;
    private List<DataListComment> comments;
    private List<String> uris;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseStorage= FirebaseStorage.getInstance();
        mStorageRef= firebaseStorage.getReference();
        setContentView(R.layout.activity_setting_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar11);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.vdvd);
        deleteacoount= findViewById(R.id.buttonaccountdelete);
        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        market= new ArrayList<>();
        guide = new ArrayList<>();
        info= new ArrayList<>();
        event=new ArrayList<>();
        likes=new ArrayList<>();
        comments=new ArrayList<>();
        uris =new ArrayList<>();



        database.getReference("UserX").child(user.getUid()).child("comments").addChildEventListener(new ChildEventListener() {
                                                                                                     @Override
                                                                                                     public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                                                                         DataListComment f = dataSnapshot.getValue(DataListComment.class);
                                                                                                         comments.add(f);
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
                                                                                                 }
        );


        database.getReference("UserX").child(user.getUid()).child("Likes").addChildEventListener(new ChildEventListener() {
                                                                                                      @Override
                                                                                                      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                                                                          datalistlike f = dataSnapshot.getValue(datalistlike.class);
                                                                                                          likes.add(f);
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
                                                                                                  }
        );



        database.getReference("UserX").child(user.getUid()).child("Interested").addChildEventListener(new ChildEventListener() {
                                                                                                      @Override
                                                                                                      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                                                                          datalistlike f = dataSnapshot.getValue(datalistlike.class);
                                                                                                          likes.add(f);
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
                                                                                                  }
        );



        database.getReference("UserX").child(user.getUid()).child("Lovers").addChildEventListener(new ChildEventListener() {
                                                                                                      @Override
                                                                                                      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                                                                          datalistlike f = dataSnapshot.getValue(datalistlike.class);
                                                                                                          likes.add(f);
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
                                                                                                  }
        );




        database.getReference("UserX").child(user.getUid()).child("going").addChildEventListener(new ChildEventListener() {
                                                                                                      @Override
                                                                                                      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                                                                          datalistlike f = dataSnapshot.getValue(datalistlike.class);
                                                                                                          likes.add(f);
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
                                                                                                  }
        );


        database.getReference("UserX").child(user.getUid()).child("Market").addChildEventListener(new ChildEventListener() {
                                                                                                      @Override
                                                                                                      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                                                                          Userpost f = dataSnapshot.getValue(Userpost.class);
                                                                                                          market.add(f);

                                                                                                          database.getReference("Market").child(f.getCountry()).
                                                                                                                          child(f.getPostnumber()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                                                                  @Override


                                                                                                                                                                                                  public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                                                                                                                      dataList d=  dataSnapshot.getValue(dataList.class);
                                                                                                                                                                                                      if(d!=null){
                                                                                                                                                                                                          uris.add(d.getUri());
                                                                                                                                                                                                          uris.add(d.getUri2());
                                                                                                                                                                                                          uris.add(d.getUri3());}

                                                                                                                                                                                                  }

                                                                                                                                                                                                  @Override
                                                                                                                                                                                                  public void onCancelled(DatabaseError databaseError) {

                                                                                                                                                                                                  }
                                                                                                                                                                                              }
                                                                                                                  );






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
                                                                                                  }
        );
        database.getReference("UserX").child(user.getUid()).child("Event").addChildEventListener(new ChildEventListener() {
                                                                                                     @Override
                                                                                                     public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                                                                         Userpost f = dataSnapshot.getValue(Userpost.class);
                                                                                                         event.add(f);
                                                                                                         database.getReference("event").child(f.getCountry()).
                                                                                                                 child(f.getPostnumber()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                                             @Override


                                                                                                                                                                             public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                                                                                                 DataEvent d=  dataSnapshot.getValue(DataEvent.class);
                                                                                                                                                                                 if(d!=null) {
                                                                                                                                                                                     uris.add(d.getCoverIMage());
                                                                                                                                                                                 }

                                                                                                                                                                             }

                                                                                                                                                                             @Override
                                                                                                                                                                             public void onCancelled(DatabaseError databaseError) {

                                                                                                                                                                             }
                                                                                                                                                                         }
                                                                                                         );

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
                                                                                                 }
        );
        database.getReference("UserX").child(user.getUid()).child("Guide").addChildEventListener(new ChildEventListener() {
                                                                                                     @Override
                                                                                                     public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                                                                         Userpost f = dataSnapshot.getValue(Userpost.class);
                                                                                                         guide.add(f);

                                                                                                         database.getReference("Guide").child(f.getCountry()).
                                                                                                                 child(f.getPostnumber()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                                             @Override


                                                                                                                                                                             public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                                                                                                 datalistG d=  dataSnapshot.getValue(datalistG.class);
                                                                                                                                                                                 if(d!=null) {
                                                                                                                                                                                     uris.add(d.getUri());
                                                                                                                                                                                     uris.add(d.getUri2());
                                                                                                                                                                                     uris.add(d.getUri3());
                                                                                                                                                                                     uris.add(d.getUri4());
                                                                                                                                                                                     uris.add(d.getUri5());
                                                                                                                                                                                     uris.add(d.getUri6());
                                                                                                                                                                                     uris.add(d.getUri7());
                                                                                                                                                                                 }

                                                                                                                                                                             }

                                                                                                                                                                             @Override
                                                                                                                                                                             public void onCancelled(DatabaseError databaseError) {

                                                                                                                                                                             }
                                                                                                                                                                         }
                                                                                                         );

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
                                                                                                 }
        );
        database.getReference("UserX").child(user.getUid()).child("Info").addChildEventListener(new ChildEventListener() {
                                                                                                    @Override
                                                                                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                                                                                        Userpost f = dataSnapshot.getValue(Userpost.class);
                                                                                                        info.add(f);
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
                                                                                                }
        );








        deleteacoount.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 AlertDialog.Builder builder;
                                                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                     builder = new AlertDialog.Builder(SettingAccount.this, android.R.style.Theme_Material_Dialog_Alert);
                                                 } else {
                                                     builder = new AlertDialog.Builder(SettingAccount.this);
                                                 }
                                                 builder.setTitle(R.string.deletewar)
                                                         .setMessage(R.string.deletacoount)
                                                         .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                             public void onClick(DialogInterface dialog, int which) {


                                                                 database.getReference("Chat").child(user.getUid()).removeValue();
                                                                 database.getReference("Users").child(user.getUid()).removeValue();
                                                                 database.getReference("UserX").child(user.getUid()).removeValue();
                                                                 database.getReference("Message").child(user.getUid()).removeValue();
                                                                 database.getReference("Usersonline").child(user.getUid()).removeValue();


                                                                 if(market.size()>=0&& market!=null)
                                                                     for(int x=0;x<market.size();x++){
                                                                         database.getReference("Market").child(market.get(x).getCountry()).child(market.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("Market").child(market.get(x).getCountry()).child(market.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("Guide").child(market.get(x).getCountry()).child(market.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("event").child(market.get(x).getCountry()).child(market.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("info").child(market.get(x).getCountry()).child(market.get(x).getPostnumber()).removeValue();

                                                                     }
                                                                 if(info.size()>=0&& info!=null)
                                                                     for(int x=0;x<info.size();x++){
                                                                         database.getReference("info").child(info.get(x).getCountry()).child(info.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("Market").child(info.get(x).getCountry()).child(info.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("Guide").child(info.get(x).getCountry()).child(info.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("event").child(info.get(x).getCountry()).child(info.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("info").child(info.get(x).getCountry()).child(info.get(x).getPostnumber()).removeValue();

                                                                     }

                                                                 if(guide.size()>=0&& guide!=null)
                                                                     for(int x=0;x<guide.size();x++){
                                                                         database.getReference("Guide").child(guide.get(x).getCountry()).child(guide.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("Market").child(guide.get(x).getCountry()).child(guide.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("Guide").child(guide.get(x).getCountry()).child(guide.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("event").child(guide.get(x).getCountry()).child(guide.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("info").child(guide.get(x).getCountry()).child(guide.get(x).getPostnumber()).removeValue();

                                                                     }

                                                                 if(event.size()>=0&& event!=null)
                                                                     for(int x=0;x<event.size();x++){
                                                                         database.getReference("event").child(event.get(x).getCountry()).child(event.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("Market").child(event.get(x).getCountry()).child(event.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("Guide").child(event.get(x).getCountry()).child(event.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("event").child(event.get(x).getCountry()).child(event.get(x).getPostnumber()).removeValue();
                                                                         database.getReference("content").child("info").child(event.get(x).getCountry()).child(event.get(x).getPostnumber()).removeValue();

                                                                     }

                                                                 if(likes.size()>=0&& likes!=null)
                                                                     for(int x=0;x<likes.size();x++){
                                                                         database.getReference("content").child(likes.get(x).getType()).child(likes.get(x).getCountry()).child(likes.get(x).getKey()).
                                                                                 child(likes.get(x).getTypeoflike()).child(likes.get(x).getUserid()).removeValue();

                                                                     }



                                                                 if(comments.size()>=0&& comments!=null)
                                                                     for(int x=0;x<comments.size();x++){
                                                                        try {
                                                                            database.getReference("content").child(comments.get(x).getType()).child(comments.get(x).getCountry()).child(comments.get(x).getPostnumber()).child("comments").child(comments.get(x).getNumber()).
                                                                                    removeValue();
                                                                        }catch (IllegalArgumentException s){


                                                                        }


                                                                     }




                                                                  for(int f=0 ;f<uris.size();f++){
                                                                         try {
                                                                             firebaseStorage.getReferenceFromUrl(uris.get(f)).delete();


                                                                         }catch (Exception o ){}

                                                                     }
                                                              mStorageRef.child("Users/"+user.getUid()+"/thumb.jpg").delete();
                                                                 mStorageRef.child("Users/" + user.getUid() + "/profile.jpg").delete();





                                                                 user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                         @Override
                                                                                                         public void onComplete(@android.support.annotation.NonNull Task<Void> task) {
                                                                                                             Intent intent = new Intent(getApplicationContext(), FacebookLoginActivity.class);
                                                                                                             LoginManager.getInstance().logOut();
                                                                                                             finishAffinity();
                                                                                                             finish();
                                                                                                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                                                             startActivity(intent);
                                                                                                         }
                                                                                                     }
                                                                 );




                                                             }
                                                         })
                                                         .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                                             public void onClick(DialogInterface dialog, int which) {
                                                                 // do nothing
                                                             }
                                                         })
                                                         .setIcon(android.R.drawable.ic_dialog_alert)
                                                         .show();




                                             }







                                             }

        );



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                Intent Is = new Intent(SettingAccount.this,Setteing.class);
                finish();
                startActivity(Is);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent Is = new Intent(SettingAccount.this,Setteing.class);
        finish();
        startActivity(Is);         }
}
