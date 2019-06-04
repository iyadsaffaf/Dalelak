package com.dalelak.eyads.dalelakْْ;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;

/**
 * Created by eyads on 1/29/2018.
 */

public class marketAdaptor extends RecyclerView.Adapter<marketAdaptor.ViewHolder >{
    private List<dataList> data;
    private Context c;

    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String islike;
    private String islove;
    private String country;
    private boolean isedited;
    private List<Userpost> userposts;
    private FirebaseStorage firebaseStorage;
    private int viewfooter =3;


    marketAdaptor(List<dataList> persons,Context context ,String country){
        this.data = persons;
        c=context;
        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        this.country =country;
        isedited=false;

    }

    public marketAdaptor(List<dataList> data, Context c, boolean isedited,List<Userpost> userposts) {
        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        this.data = data;
        this.c = c;
        this.isedited = isedited;
        this.userposts= userposts;
       firebaseStorage= FirebaseStorage.getInstance();

    }

    @Override
    public marketAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==viewfooter){

            LayoutInflater inflater = LayoutInflater.from(c);
            View r=     inflater.inflate(R.layout.market_ietem_layout, parent, false);
            ViewHolder viewHolder =new ViewHolder(r);
            float scale = c.getResources().getDisplayMetrics().density;

            int dpAsPixels = (int) (60*scale + 0.5f);
            r.setPadding(0,0,0,dpAsPixels);
            return viewHolder;

        }



        LayoutInflater inflater = LayoutInflater.from(c);
        View r=     inflater.inflate(R.layout.market_ietem_layout, parent, false);
        ViewHolder viewHolder =new ViewHolder(r);
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
    public void onBindViewHolder(final marketAdaptor.ViewHolder holder, final int position) {

        holder.name.setText(data.get(position).getName());
        holder.date.setText(data.get(position).getDate());
        holder.price.setText(data.get(position).getPrice());
        holder.description.setText(data.get(position).getDescription());

        if(data.get(position).getUri()!= null)

        Picasso.with(c)
                .load(Uri.parse(data.get(position).getUri())).placeholder(R.drawable.whiteimage)
                .into(holder.imagepost, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {

                    }
                });




       final ArrayList<String> d = new ArrayList<>();


        d.add(data.get(position).getCountry());
        d.add(data.get(position).getDescription());
        d.add(data.get(position).getName());
        d.add(data.get(position).getPrice());
        d.add(data.get(position).getDate());
        d.add(holder.likenumber.getText().toString() );
        d.add(holder.lovenumber.getText().toString() );
        d.add(data.get(position).getStatus());
        d.add(data.get(position).getDelivery());
        d.add(data.get(position).getProvince());
        d.add(data.get(position).getUri());
        d.add(data.get(position).getUri2());
        d.add(data.get(position).getUri3());
        d.add(data.get(position).getPostname());
        d.add(country);
        d.add(data.get(position).getImageUri());
        d.add(data.get(position).getUserID());
       if(data!=null && data.get(position).getUserID() !=null){
        database.getReference("Users").child(data.get(position).getUserID()).child("imageUrlThumb").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                if(s!=null){
                Picasso.with(c)
                        .load(Uri.parse(s)).placeholder(R.drawable.avatar)
                        .into(holder.circleImageView);
                d.set(15,s);}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}






        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c,market_expanded.class);
i.putStringArrayListExtra("d", d);

                c.startActivity(i);
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c,comment_market.class);
                i.putExtra("type","Market");
                i.putExtra("postname",data.get(position).getPostname());
                c.startActivity(i);}});

    if(data!=null && data.get(position).getCountry() !=null&&data.get(position).getPostname()!=null&& currentUser.getUid()!=null ) {
        database.getReference("content").child("Market").child(data.get(position).getCountry()).child(data.get(position).getPostname()).child("likes").child(currentUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        islike = dataSnapshot.getValue(String.class);
                        if (!(islike == null))
                            holder.like.setLiked(true);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
        //getting is have been loved
        if(data!=null && data.get(position).getCountry() !=null&&data.get(position).getPostname()!=null&& currentUser.getUid()!=null ) {

            database.getReference("content").child("Market").child(data.get(position).getCountry()).child(data.get(position).getPostname()).child("Lovers").child(currentUser.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            islove = dataSnapshot.getValue(String.class);
                            if (!(islove == null))
                                holder.love.setLiked(true);


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

        }







        holder.like.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                datalistlike dd= new datalistlike(currentUser.getUid(),"likes",data.get(position).getPostname(),data.get(position).getCountry(),"Market");
                database.getReference("UserX").child(currentUser.getUid()).child("Likes").child(data.get(position).getPostname()).setValue(dd);


                database.getReference("content").child("Market").child(data.get(position).getCountry()).child(data.get(position).getPostname()).child("likes").child(currentUser.getUid()).setValue(currentUser.getUid());


            }

            @Override
            public void unLiked(LikeButton likeButton) {
                database.getReference("content").child("Market").child(data.get(position).getCountry()).child(data.get(position).getPostname()).child("likes").child(currentUser.getUid()).removeValue();

                database.getReference("UserX").child(currentUser.getUid()).child("Likes").child(data.get(position).getPostname()).removeValue();



            }
        });

        holder.love.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                //getting date

                database.getReference("content").child("Market").child(data.get(position).getCountry()).child(data.get(position).getPostname()).child("Lovers").child(currentUser.getUid()).setValue(currentUser.getUid());
                datalistlike dd= new datalistlike(currentUser.getUid(),"Lovers",data.get(position).getPostname(),data.get(position).getCountry(),"Market");
                database.getReference("UserX").child(currentUser.getUid()).child("Lovers").child(data.get(position).getPostname()).setValue(dd);



            }

            @Override
            public void unLiked(LikeButton likeButton) {
                database.getReference("content").child("Market").child(data.get(position).getCountry()).child(data.get(position).getPostname()).child("Lovers").child(currentUser.getUid()).removeValue();
                database.getReference("UserX").child(currentUser.getUid()).child("Lovers").child(data.get(position).getPostname()).removeValue();


            }
        });
// for edit activity
        if(isedited){
          holder.delete.setVisibility(View.VISIBLE);
                mStorageRef = FirebaseStorage.getInstance().getReference();
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(c, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(c);
                        }
                        builder.setTitle(R.string.dvvs)
                                .setMessage(R.string.vsdvsa)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        database.getReference("Market").child(userposts.get(position).getCountry()).child(userposts.get(position).getPostnumber()).removeValue();
                                        database.getReference("UserX").child(currentUser.getUid()).child("Market").child(userposts.get(position).getPostnumber()).removeValue();
                                        database.getReference("Marketcomment").child(userposts.get(position).getCountry()).child(userposts.get(position).getPostnumber()).child("comments").removeValue();
                                        StorageReference photoRef = firebaseStorage.getReferenceFromUrl(data.get(position).getUri());
                                        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {}}).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {}});

                                        if(!data.get(position).getUri2().equals("no uri")){
                                            StorageReference photoRef2 = firebaseStorage.getReferenceFromUrl(data.get(position).getUri2());
                                            photoRef2.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {}}).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {}});}

                                        if(!data.get(position).getUri3().equals("no uri")){
                                            StorageReference photoRef3 = firebaseStorage.getReferenceFromUrl(data.get(position).getUri3());
                                            photoRef3.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {}}).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception exception) {}});}



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
holder.circleImageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        PopupMenu popup = new PopupMenu(c, holder.circleImageView);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.popop, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.ShowProfile) {
                    // Handle the camera action
                    Intent I = new Intent(c,Profile.class);
                    I.putExtra("userID",data.get(position).getUserID());
                    c.startActivity(I);



                    }

                if(id==R.id.Report){
                    Toast.makeText(c, R.string.reportText,Toast.LENGTH_SHORT).show();
                    database.getReference("Report").child("Market").child(data.get(position).getCountry()).child(data.get(position).getPostname()).setValue(data.get(position).getUserID());



                }
                return true;}});

    }
});


 holder.likenumber.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         Intent i = new Intent(c , likesActivity.class);
         i.putExtra("type","Market");
         i.putExtra("country",data.get(position).getCountry());
         i.putExtra("postnumber",data.get(position).getPostname());
         i.putExtra("whatsoort","likes");
         c.startActivity(i);

     }
 });

        holder.lovenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c , likesActivity.class);
                i.putExtra("type","Market");
                i.putExtra("country",data.get(position).getCountry());
                i.putExtra("postnumber",data.get(position).getPostname());
                i.putExtra("whatsoort","Lovers");
                c.startActivity(i);

            }
        });
holder.report.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        PopupMenu popup = new PopupMenu(c, holder.report);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.popop, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.ShowProfile) {
                    // Handle the camera action
                    Intent I = new Intent(c,Profile.class);
                    I.putExtra("userID",data.get(position).getUserID());
                    c.startActivity(I);



                }
                if(id==R.id.Report){
                    Toast.makeText(c, R.string.reportText,Toast.LENGTH_SHORT).show();
                    database.getReference("Report").child("Market").child(data.get(position).getCountry()).child(data.get(position).getPostname()).setValue(data.get(position).getUserID());



                }


                return true;}});
    }
});
        database.getReference("content").child("Market").child(data.get(position).getCountry()).child(data.get(position).getPostname()).child("likes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int xx=0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                   xx++;

                }
                holder.likenumber.setText(xx+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database.getReference("content").child("Market").child(data.get(position).getCountry()).child(data.get(position).getPostname()).child("Lovers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int xx=0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    xx++;
                }
                holder.lovenumber.setText(xx+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {




        return  data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private ImageView imagepost;
        private TextView date;
        private TextView description;
        private TextView price;
        private Button review;
        private Button comment;
        private  LikeButton like ;
        private  LikeButton love ;
        private TextView lovenumber;
        private TextView likenumber;
        private ProgressBar progressBar;
        private CircleImageView circleImageView;
        private Button delete;
        private ImageView report;






        public ViewHolder(View itemView) {
            super(itemView);
            name= (TextView)itemView.findViewById(R.id.market_name_textviewexpabded);
            date= (TextView)itemView.findViewById(R.id.market_date_textview);
            imagepost= (ImageView) itemView.findViewById(R.id.marker_postimage_imageview);
            description= (TextView)itemView.findViewById(R.id.market_descrption_textview);
            price= (TextView)itemView.findViewById(R.id.market_price_textview);
            review=(Button) itemView.findViewById(R.id.event_detail_button);
            comment=(Button) itemView.findViewById(R.id.market_comment);
            like =(LikeButton)itemView.findViewById(R.id.star_button2);
            love =(LikeButton)itemView.findViewById(R.id.star_button);
            lovenumber= (TextView)itemView.findViewById(R.id.textViewlove);
            likenumber= (TextView)itemView.findViewById(R.id.textViewlike);
            progressBar= (ProgressBar) itemView.findViewById(R.id.homeprogress);
            circleImageView= (CircleImageView) itemView.findViewById(R.id.CircleImageView_market);
            delete=(Button) itemView.findViewById(R.id.Ed_Delete);
            report= (ImageView) itemView.findViewById(R.id.markey_report);












        }

    }

}
