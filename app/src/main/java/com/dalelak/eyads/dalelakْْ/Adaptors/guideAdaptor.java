package com.dalelak.eyads.dalelakْْ.Adaptors;

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

import com.dalelak.eyads.dalelakْْ.Expandedghide;
import com.dalelak.eyads.dalelakْْ.Profile;
import com.dalelak.eyads.dalelakْْ.R;
import com.dalelak.eyads.dalelakْْ.Userpost;
import com.dalelak.eyads.dalelakْْ.comment_market;
import com.dalelak.eyads.dalelakْْ.datalistG;
import com.dalelak.eyads.dalelakْْ.datalistlike;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
 * Created by eyads on 1/29/2018.
 */

public class guideAdaptor extends RecyclerView.Adapter<guideAdaptor.ViewHolder >{
    private List<datalistG> contract;
    private Context c;
    private String counry;
    private FirebaseAuth mAuth;
    private String islike;
    private String islove;
    private StorageReference mStorageRef;
    private FirebaseStorage firebaseStorage;

    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private int viewfooter =3;
    private boolean isedited;
    private List<Userpost> userposts;

    public guideAdaptor(List<datalistG> persons, Context context, String country) {
        this.contract = persons;
        c=context;
        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        this.counry =country;
    }

    public guideAdaptor(List<datalistG> datalistGS, Context context, boolean b, List<Userpost> userposts) {
        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        isedited=b;
        this.userposts=userposts;
        c=context;
        firebaseStorage= FirebaseStorage.getInstance();
        contract =datalistGS;

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
    public guideAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==viewfooter){

            LayoutInflater inflater = LayoutInflater.from(c);
            View r=     inflater.inflate(R.layout.guide_card_item, parent, false);
            guideAdaptor.ViewHolder viewHolder =new guideAdaptor.ViewHolder(r);
            float scale = c.getResources().getDisplayMetrics().density;

            int dpAsPixels = (int) (60*scale + 0.5f);
            r.setPadding(0,0,0,dpAsPixels);
            return viewHolder;

        }

        LayoutInflater inflater = LayoutInflater.from(c);
        View r=     inflater.inflate(R.layout.guide_card_item, parent, false);
        guideAdaptor.ViewHolder viewHolder =new guideAdaptor.ViewHolder(r);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final guideAdaptor.ViewHolder holder, final int position) {
        holder.name.setText(contract.get(position).getName());
        holder.servec.setText(contract.get(position).getServes());
        holder.adress.setText(contract.get(position).getAdress());
        holder.city.setText(contract.get(position).getCity());
        holder.province.setText(contract.get(position).getProvince());
        holder.likenumber.setText(contract.get(position).getNumbersOfLikes()+"");
        holder.lovenumber.setText(contract.get(position).getNumberOfLoves()+"");


        final ArrayList<String> d = new ArrayList<>();


        d.add(contract.get(position).getPostname());
        d.add(contract.get(position).getName());
        d.add(contract.get(position).getServes());
        d.add(contract.get(position).getAdress());
        d.add(contract.get(position).getCity());
        d.add(contract.get(position).getWebsite());
        d.add(contract.get(position).getPhone());
        d.add(contract.get(position).getDescription());
        d.add(contract.get(position).getDate());
        d.add(holder.likenumber.getText().toString() );
        d.add(holder.lovenumber.getText().toString() );
        d.add(contract.get(position).getProvince());
        d.add(contract.get(position).getCountry());
        d.add(contract.get(position).getUri());
        d.add(contract.get(position).getUri2());
        d.add(contract.get(position).getUri3());
        d.add(contract.get(position).getUri4());
        d.add(contract.get(position).getUri5());
        d.add(contract.get(position).getUri6());
        d.add(contract.get(position).getUri7());
        d.add(counry);
        d.add(contract.get(position).getUserID());

        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, Expandedghide.class);
                i.putStringArrayListExtra("sd", d);

                c.startActivity(i);
            }
        });
        if(contract.get(position).getUri()!= null)
holder.progressBar.setVisibility(View.VISIBLE);
            Picasso.with(c)
                    .load(Uri.parse(contract.get(position).getUri())).placeholder(R.drawable.whiteimage)
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);

                        }

                        @Override
                        public void onError() {

                        }
                    });


        holder.love.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                //getting date
                database.getReference("content").child("Guide").child(contract.get(position).getCountry()).child(contract.get(position).getPostname()).child("Lovers").child(currentUser.getUid()).setValue(currentUser.getUid());
                datalistlike dd= new datalistlike(currentUser.getUid(),"Lovers",contract.get(position).getPostname(),contract.get(position).getCountry(),"Guide");
                database.getReference("UserX").child(currentUser.getUid()).child("Lovers").child(contract.get(position).getPostname()).setValue(dd);


            }

            @Override
            public void unLiked(LikeButton likeButton) {
                database.getReference("content").child("Guide").child(contract.get(position).getCountry()).child(contract.get(position).getPostname()).child("Lovers").child(currentUser.getUid()).removeValue();
                database.getReference("UserX").child(currentUser.getUid()).child("Lovers").child(contract.get(position).getPostname()).removeValue();



            }
        });

        holder.like.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                //getting date

                database.getReference("content").child("Guide").child(contract.get(position).getCountry()).child(contract.get(position).getPostname()).child("Likes").child(currentUser.getUid()).setValue(currentUser.getUid());
                datalistlike dd= new datalistlike(currentUser.getUid(),"Likes",contract.get(position).getPostname(),contract.get(position).getCountry(),"Guide");
                database.getReference("UserX").child(currentUser.getUid()).child("Likes").child(contract.get(position).getPostname()).setValue(dd);


            }

            @Override
            public void unLiked(LikeButton likeButton) {
                database.getReference("content").child("Guide").child(contract.get(position).getCountry()).child(contract.get(position).getPostname()).child("Likes").child(currentUser.getUid()).removeValue();
                database.getReference("UserX").child(currentUser.getUid()).child("Likes").child(contract.get(position).getPostname()).removeValue();


            }
        });
        //getting is have been loved
        if(contract!=null && contract.get(position).getCountry() !=null&&contract.get(position).getPostname()!=null&& currentUser.getUid()!=null ) {

            database.getReference("content").child("Guide").child(contract.get(position).getCountry()).child(contract.get(position).getPostname()).child("Lovers").child(currentUser.getUid())
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
        if(contract!=null && contract.get(position).getCountry() !=null&&contract.get(position).getPostname()!=null&& currentUser.getUid()!=null ) {
            database.getReference("content").child("Guide").child(contract.get(position).getCountry()).child(contract.get(position).getPostname()).child("Likes").child(currentUser.getUid())
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

        database.getReference("content").child("Guide").child(contract.get(position).getCountry()).child(contract.get(position).getPostname()).child("Likes").addValueEventListener(new ValueEventListener() {
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
        database.getReference("content").child("Guide").child(contract.get(position).getCountry()).child(contract.get(position).getPostname()).child("Lovers").addValueEventListener(new ValueEventListener() {
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

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c,comment_market.class);
                i.putExtra("type","Guide");
                i.putExtra("postname",contract.get(position).getPostname());
                c.startActivity(i);}});
        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(c, holder.arrow);
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
                            I.putExtra("userID",contract.get(position).getUserID());
                            c.startActivity(I);



                        }
                        if(id==R.id.Report){
                            Toast.makeText(c, R.string.reportText,Toast.LENGTH_SHORT).show();
                            database.getReference("Report").child("Guide").child(contract.get(position).getCountry()).child(contract.get(position).getPostname()).setValue(contract.get(position).getUserID());



                        }


                        return true;}});

            }
        });
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
                    builder.setTitle(R.string.Delete_your_post)
                            .setMessage(R.string.areyousuroyouamlkaf)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    database.getReference("Guide").child(userposts.get(position).getCountry()).child(userposts.get(position).getPostnumber()).removeValue();
                                    database.getReference("UserX").child(currentUser.getUid()).child("Guide").child(userposts.get(position).getPostnumber()).removeValue();
                                    database.getReference("content").child("Guide").child(userposts.get(position).getCountry()).child(userposts.get(position).getPostnumber()).removeValue();
                                    StorageReference photoRef = firebaseStorage.getReferenceFromUrl(contract.get(position).getUri());
                                    photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {}}).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {}});

                                    if(!contract.get(position).getUri2().equals("Empty")){
                                        StorageReference photoRef2 = firebaseStorage.getReferenceFromUrl(contract.get(position).getUri2());
                                        photoRef2.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {}}).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {}});}

                                    if(!contract.get(position).getUri3().equals("Empty")){
                                        StorageReference photoRef3 = firebaseStorage.getReferenceFromUrl(contract.get(position).getUri3());
                                        photoRef3.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {}}).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {}});}
                                    if(!contract.get(position).getUri4().equals("Empty")){
                                        StorageReference photoRef3 = firebaseStorage.getReferenceFromUrl(contract.get(position).getUri4());
                                        photoRef3.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {}}).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {}});}
                                    if(!contract.get(position).getUri5().equals("Empty")){
                                        StorageReference photoRef3 = firebaseStorage.getReferenceFromUrl(contract.get(position).getUri5());
                                        photoRef3.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {}}).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {}});}
                                    if(!contract.get(position).getUri6().equals("Empty")){
                                        StorageReference photoRef3 = firebaseStorage.getReferenceFromUrl(contract.get(position).getUri6());
                                        photoRef3.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {}}).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {}});}
                                    if(!contract.get(position).getUri7().equals("Empty")){
                                        StorageReference photoRef3 = firebaseStorage.getReferenceFromUrl(contract.get(position).getUri7());
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
    }

    @Override
    public int getItemCount() {
        return contract.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView servec;
        private TextView adress;
        private TextView city;
        private TextView province;
        private Button comment,review;
        private ImageView imageView;
        private LikeButton like ;
        private  LikeButton love ;
        private TextView lovenumber;
        private TextView likenumber;
        private ProgressBar progressBar;
        private ImageView arrow;
        private Button delete;



        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.guide_text_workname);
            servec = (TextView) itemView.findViewById(R.id.guide_text_servece);
            adress = (TextView) itemView.findViewById(R.id.guide_text_Adress);
            city = (TextView) itemView.findViewById(R.id.guide_text_city);
            province = (TextView) itemView.findViewById(R.id.guide_text_province);
            comment = (Button) itemView.findViewById(R.id.ghide_comment);
            review = (Button) itemView.findViewById(R.id.event_detail_button);
            imageView = (ImageView) itemView.findViewById(R.id.image_ghide);

            like =(LikeButton)itemView.findViewById(R.id.star_button_ghide);
            love =(LikeButton)itemView.findViewById(R.id.star_button_ghidelove);
            lovenumber= (TextView)itemView.findViewById(R.id.ghide_lovenumber3);
            likenumber= (TextView)itemView.findViewById(R.id.ghide_likenumberx);
            progressBar= (ProgressBar) itemView.findViewById(R.id.progressBarghide);
            arrow = (ImageView) itemView.findViewById(R.id.downarrow_guide);
            delete = (Button) itemView.findViewById(R.id.guidedelete);








        }
    }
}