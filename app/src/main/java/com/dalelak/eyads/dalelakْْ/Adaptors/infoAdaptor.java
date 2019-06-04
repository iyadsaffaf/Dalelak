package com.dalelak.eyads.dalelakْْ.Adaptors;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dalelak.eyads.dalelakْْ.DataListInfo;
import com.dalelak.eyads.dalelakْْ.Profile;
import com.dalelak.eyads.dalelakْْ.R;
import com.dalelak.eyads.dalelakْْ.Userpost;
import com.dalelak.eyads.dalelakْْ.comment_market;
import com.dalelak.eyads.dalelakْْ.datalistlike;
import com.dalelak.eyads.dalelakْْ.likesActivity;
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
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by eyads on 2/17/2018.
 */

public class infoAdaptor extends RecyclerView.Adapter<infoAdaptor.ViewHolder > {
    private Context context;
   private List<DataListInfo> dataListInfo;
   private String country;
   private boolean isedited;

    private FirebaseDatabase database;
    private StorageReference mStorageRef;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
   private List<Userpost> userpost;
    private int viewfooter =3;
    private String islove;


    public infoAdaptor(Context context , List<DataListInfo> dataListInfo, String country) {
        this.context = context;
        this.dataListInfo= dataListInfo;
        this.country =country;
        isedited=false;

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

    }

    public infoAdaptor(Context context, List<DataListInfo> dataListInfo, String country, boolean isedited, List<Userpost> userpost) {
        this.context = context;
        this.dataListInfo = dataListInfo;
        this.country = country;
        this.isedited = isedited;
        this.userpost=userpost;

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
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
    public infoAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==viewfooter){

            LayoutInflater inflater = LayoutInflater.from(context);
            View r=     inflater.inflate(R.layout.ask_item, parent, false);
            infoAdaptor.ViewHolder viewHolder =new infoAdaptor.ViewHolder(r);
            float scale = context.getResources().getDisplayMetrics().density;

            int dpAsPixels = (int) (60*scale + 0.5f);
            r.setPadding(0,0,0,dpAsPixels);
            return viewHolder;

        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View r=     inflater.inflate(R.layout.ask_item, parent, false);
        infoAdaptor.ViewHolder viewHolder =new infoAdaptor.ViewHolder(r);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final infoAdaptor.ViewHolder holder, final int position) {

           holder.name.setText(dataListInfo.get(position).getQuestion());
        final Animation animation = AnimationUtils.loadAnimation(context,R.anim.in);


database.getReference("Users").child(dataListInfo.get(position).getUserID()).child("imageUrlThumb").addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
String s =dataSnapshot.getValue(String.class);
if(s != null)
        Picasso.with(context)
                .load(s).placeholder(R.drawable.avatar)
                .into(holder.circleImageView);


    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});



        holder.imageView.setOnClickListener(new View.OnClickListener() {


    @Override
    public void onClick(View view) {
        view.setAnimation(animation);
        Intent i = new Intent(context,comment_market.class);
        i.putExtra("type","info");
        i.putExtra("postname",dataListInfo.get(position).getQuestionnumber());
        context.startActivity(i);
    }
});

        if(isedited) {
            holder.delet.setVisibility(View.VISIBLE);

            holder.delet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context);
                    }
                    builder.setTitle(R.string.DeleteQuestion)
                            .setMessage(R.string.Areyousureyou)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    database.getReference("info").child(userpost.get(position).getCountry()).child(userpost.get(position).getPostnumber()).removeValue();
                                    database.getReference("UserX").child(user.getUid()).child("Info").child(userpost.get(position).getPostnumber()).removeValue();
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

        /// pop menue for profile en report
        holder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, holder.circleImageView);
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
                            I.putExtra("userID",dataListInfo.get(position).getUserID());
                            context.startActivity(I);



                        }if(id==R.id.Report){
                            Toast.makeText(context, R.string.reportText,Toast.LENGTH_SHORT).show();
                            database.getReference("Report").child("Info").child(dataListInfo.get(position).getCountry()).child(dataListInfo.get(position).getQuestionnumber()).setValue(dataListInfo.get(position).getUserID());



                        }


                        return true;}});



            }
        });

        holder.star.setOnLikeListener(new OnLikeListener() {
            @Override

            public void liked(LikeButton likeButton) {
                //getting date

                database.getReference("content").child("info").child(dataListInfo.get(position).getCountry()).child(dataListInfo.get(position).getQuestionnumber()).child("Interested").child(user.getUid()).setValue(user.getUid());
                datalistlike dd= new datalistlike(user.getUid(),"Interested",dataListInfo.get(position).getQuestionnumber(),dataListInfo.get(position).getCountry(),"info");
                database.getReference("UserX").child(user.getUid()).child("Interested").child(dataListInfo.get(position).getQuestionnumber()).setValue(dd);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                database.getReference("UserX").child(user.getUid()).child("Interested").child(dataListInfo.get(position).getQuestionnumber()).removeValue();

                database.getReference("content").child("info").child(dataListInfo.get(position).getCountry()).child(dataListInfo.get(position).getQuestionnumber()).child("Interested").child(user.getUid()).removeValue();


            }
        });
        database.getReference("content").child("info").child(dataListInfo.get(position).getCountry()).child(dataListInfo.get(position).getQuestionnumber()).child("Interested").child(user.getUid())
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
        database.getReference("content").child("info").child(dataListInfo.get(position).getCountry()).child(dataListInfo.get(position).getQuestionnumber()).child("Interested")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int x=0;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            x++;
                        }
                        holder.starnumber.setText(""+x);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        holder.starnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context , likesActivity.class);
                i.putExtra("type","info");
                i.putExtra("country",dataListInfo.get(position).getCountry());
                i.putExtra("postnumber",dataListInfo.get(position).getQuestionnumber());
                i.putExtra("whatsoort","Interested");
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return dataListInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView imageView;
        private CircleImageView circleImageView;
        private Button delet;
        private LikeButton star;
        private TextView starnumber;






        public ViewHolder(View itemView) {
            super(itemView);
              name= (TextView) itemView.findViewById(R.id.ask_info);
              imageView =(ImageView)itemView.findViewById(R.id.info_imageview);
            circleImageView =(CircleImageView) itemView.findViewById(R.id.info_circleimage);
            delet =(Button) itemView.findViewById(R.id.my_Info_delte);
            star =(LikeButton) itemView.findViewById(R.id.star_buttonX);
            starnumber= (TextView) itemView.findViewById(R.id.starnumber);







        }
    }
}