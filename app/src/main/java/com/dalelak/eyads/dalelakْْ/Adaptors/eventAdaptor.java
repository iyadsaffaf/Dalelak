package com.dalelak.eyads.dalelakْْ.Adaptors;

import android.support.v7.widget.RecyclerView;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.dalelak.eyads.dalelakْْ.DataEvent;
import com.dalelak.eyads.dalelakْْ.Profile;
import com.dalelak.eyads.dalelakْْ.R;
import com.dalelak.eyads.dalelakْْ.Userpost;
import com.dalelak.eyads.dalelakْْ.comment_market;
import com.dalelak.eyads.dalelakْْ.datalistlike;
import com.dalelak.eyads.dalelakْْ.expandedEvent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * Created by eyads on 2/18/2018.
 */

public class eventAdaptor extends RecyclerView.Adapter<eventAdaptor.ViewHolder > {

    private Context context;
    private List<DataEvent> dataEvents;
    private String country;
    private boolean presse;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseStorage firebaseStorage;
    private int viewfooter =3;
    private boolean isedited;
    private List<Userpost> userposts;

    private String islove;

    public eventAdaptor(Context context, List<DataEvent> dataEvents, String country) {
        this.context = context;
        this.dataEvents = dataEvents;
        this.country = country;
        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        firebaseStorage= FirebaseStorage.getInstance();

    }
    public eventAdaptor(List<DataEvent> datalistGS, Context context, boolean b, List<Userpost> userposts) {
        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        isedited=b;
        this.userposts=userposts;
        this.context = context;
        firebaseStorage= FirebaseStorage.getInstance();
        this.dataEvents = datalistGS;


    }

    @Override
    public eventAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
 if(viewType==viewfooter){
     LayoutInflater inflater = LayoutInflater.from(context);
     View r=     inflater.inflate(R.layout.event_item, parent, false);
     eventAdaptor.ViewHolder viewHolder =new eventAdaptor.ViewHolder(r);
     float scale = context.getResources().getDisplayMetrics().density;

     int dpAsPixels = (int) (60*scale + 0.5f);
     r.setPadding(0,0,0,dpAsPixels);
     return viewHolder;

 }
        LayoutInflater inflater = LayoutInflater.from(context);
        View r=     inflater.inflate(R.layout.event_item, parent, false);
        eventAdaptor.ViewHolder viewHolder =new eventAdaptor.ViewHolder(r);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            // This is where we'll add footer.
            return viewfooter;
        }

        return super.getItemViewType(position);
    }


    @Override
    public void onBindViewHolder(final eventAdaptor.ViewHolder holder, final int position) {
        holder.name.setText(dataEvents.get(position).getTitle());
        holder.date.setText(dataEvents.get(position).getDateOfEvent());
        holder.country.setText(dataEvents.get(position).getCountry());
        holder.publisher.setText(dataEvents.get(position).getPublisher());
        holder.city.setText(dataEvents.get(position).getCity());

         final ArrayList<String> d = new ArrayList<>();

        d.add(dataEvents.get(position).getTitle());
        d.add(dataEvents.get(position).getPublisher());
        d.add(dataEvents.get(position).getPublisherID());
        d.add(dataEvents.get(position).getEventNumber());
        d.add(dataEvents.get(position).getDate());
        d.add(dataEvents.get(position).getDateOfEvent());
        d.add(dataEvents.get(position).getPrice());
        d.add(dataEvents.get(position).getCountry());
        d.add(dataEvents.get(position).getCity());
        d.add(dataEvents.get(position).getAdress());
        d.add(dataEvents.get(position).getSatrtAt());
        d.add(dataEvents.get(position).getEndAt());
        d.add(dataEvents.get(position).getForWho());
        d.add(dataEvents.get(position).getDetail());
        d.add(dataEvents.get(position).getCoverIMage());
        d.add(country);

        holder.progressBar.setVisibility(View.VISIBLE);
        Picasso.with(context).load(dataEvents.get(position).getCoverIMage()).placeholder(R.drawable.whiteimage).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onError() {

            }
        });


        final Animation animation = AnimationUtils.loadAnimation(context,R.anim.in);
        presse =false;
        final MediaPlayer mp = MediaPlayer.create(context, R.raw.gosound);



        holder.gobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.start();
                if(!presse){
                    holder.gobutton.setImageResource(R.drawable.gopressed);
                    view.startAnimation(animation);
                    presse=true;


                    database.getReference("content").child("event").child(dataEvents.get(position).getCountry()).child(dataEvents.get(position).getEventNumber()).child("going").child(currentUser.getUid()).setValue(currentUser.getUid());
                    datalistlike dd= new datalistlike(currentUser.getUid(),"going",dataEvents.get(position).getEventNumber(),dataEvents.get(position).getCountry(),"event");
                    database.getReference("UserX").child(currentUser.getUid()).child("going").child(dataEvents.get(position).getEventNumber()).setValue(dd);

                    return;
                }
                if(presse){
                    holder.gobutton.setImageResource(R.drawable.gounpressed);
                    database.getReference("content").child("event").child(dataEvents.get(position).getCountry()).child(dataEvents.get(position).getEventNumber()).child("going").child(currentUser.getUid()).removeValue();
                    database.getReference("UserX").child(currentUser.getUid()).child("going").child(dataEvents.get(position).getEventNumber()).removeValue();


                    view.startAnimation(animation);
                    presse=false;


                    return;}


            }
        });

     holder.detail.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent s = new Intent(context, expandedEvent.class);
             s.putStringArrayListExtra("eventd", d);

             context.startActivity(s);
         }
     });
     holder.publisher.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             PopupMenu popup = new PopupMenu(context, holder.publisher);
             //Inflating the Popup using xml file
             popup.getMenuInflater()
                     .inflate(R.menu.popop, popup.getMenu());
             popup.show();

             popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                 public boolean onMenuItemClick(MenuItem item) {
                     int id = item.getItemId();

                     if (id == R.id.ShowProfile) {
                         // Handle the camera action
                         Intent I = new Intent(context,Profile.class);
                         I.putExtra("userID",dataEvents.get(position).getPublisherID());
                         context.startActivity(I);



                     }if(id==R.id.Report){
                         Toast.makeText(context, R.string.reportText,Toast.LENGTH_SHORT).show();
                         database.getReference("Report").child("Event").child(dataEvents.get(position).getCountry()).child(dataEvents.get(position).getEventNumber()).setValue(dataEvents.get(position).getPublisherID());



                     }


                     return true;}});
         }
     });
        database.getReference("content").child("event").child(dataEvents.get(position).getCountry()).child(dataEvents.get(position).getEventNumber()).child("Interested").child(currentUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        islove = dataSnapshot.getValue(String.class);
                        if(!(islove==null))
                            holder.star.setLiked(true);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        holder.star.setOnLikeListener(new OnLikeListener() {

            @Override
            public void liked(LikeButton likeButton) {

                database.getReference("content").child("event").child(dataEvents.get(position).getCountry()).child(dataEvents.get(position).getEventNumber()).child("Interested").child(currentUser.getUid()).setValue(currentUser.getUid());
                datalistlike dd= new datalistlike(currentUser.getUid(),"Interested",dataEvents.get(position).getEventNumber(),dataEvents.get(position).getCountry(),"event");
                database.getReference("UserX").child(currentUser.getUid()).child("Interested").child(dataEvents.get(position).getEventNumber()).setValue(dd);


            }

            @Override
            public void unLiked(LikeButton likeButton) {

                database.getReference("content").child("event").child(dataEvents.get(position).getCountry()).child(dataEvents.get(position).getEventNumber()).child("Interested").child(currentUser.getUid()).removeValue();
                database.getReference("UserX").child(currentUser.getUid()).child("Interested").child(dataEvents.get(position).getEventNumber()).removeValue();




            }
        });

        database.getReference("content").child("event").child(dataEvents.get(position).getCountry()).child(dataEvents.get(position).getEventNumber()).child("going").child(currentUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue(String.class)!=null) {
                            holder.gobutton.setImageResource(R.drawable.gopressed);
                            presse=true;

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        database.getReference("content").child("event").child(dataEvents.get(position).getCountry()).child(dataEvents.get(position).getEventNumber()).child("going")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int x=0;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                           x++;
                        }
                       holder.goingnumber.setText(""+x);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        database.getReference("content").child("event").child(dataEvents.get(position).getCountry()).child(dataEvents.get(position).getEventNumber()).child("Interested")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int x=0;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            x++;
                        }
                        holder.interstednumber.setText(""+x);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,comment_market.class);
                i.putExtra("type","event");
                i.putExtra("postname",dataEvents.get(position).getEventNumber());
                context.startActivity(i);}});

        if(isedited){
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context);
                    }
                    builder.setTitle(R.string.DeleteyourEvent)
                            .setMessage(R.string.Areyou)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    database.getReference("event").child(userposts.get(position).getCountry()).child(userposts.get(position).getPostnumber()).removeValue();
                                    database.getReference("UserX").child(currentUser.getUid()).child("Event").child(userposts.get(position).getPostnumber()).removeValue();
                                    database.getReference("content").child("event").child(userposts.get(position).getCountry()).child(userposts.get(position).getPostnumber()).removeValue();
                                    StorageReference photoRef = firebaseStorage.getReferenceFromUrl(dataEvents.get(position).getCoverIMage());
                                    photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {}}).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {}});



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
            });





        }

    }

    @Override
    public int getItemCount() {
        return dataEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView date;
        private TextView country;
        private TextView city;

        private TextView publisher;
        private TextView interstednumber;
        private TextView goingnumber;
        private ImageView imageView;
        private ImageButton gobutton;
        private Button detail;
        private Button comments;
        private LikeButton star ;
        private ProgressBar progressBar;
        private Button delete;





        public ViewHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.eventex_title_Textview);
            date= (TextView) itemView.findViewById(R.id.eventex_dateofevent_TExtview);
            country= (TextView) itemView.findViewById(R.id.event_country_Textview);
            city= (TextView) itemView.findViewById(R.id.event_city_Textview);
            publisher= (TextView) itemView.findViewById(R.id.event_publisher_Textview);
            interstednumber= (TextView) itemView.findViewById(R.id.interest_number);
            goingnumber= (TextView) itemView.findViewById(R.id.going_number);
            imageView= (ImageView) itemView.findViewById(R.id.cover_event);
            star =(LikeButton)itemView.findViewById(R.id.star_button_event);
            gobutton = (ImageButton) itemView.findViewById(R.id.Event_gobutton);
            detail = (Button) itemView.findViewById(R.id.event_detail_button);
            country = (TextView) itemView.findViewById(R.id.event_country_Textview);
            comments = (Button) itemView.findViewById(R.id.event_comment_d);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar4x);
            delete=(Button) itemView.findViewById(R.id.delteedevent);









        }
    }
}
