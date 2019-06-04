package com.dalelak.eyads.dalelakْْ.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dalelak.eyads.dalelakْْ.DataListComment;
import com.dalelak.eyads.dalelakْْ.DataListUser;
import com.dalelak.eyads.dalelakْْ.Profile;
import com.dalelak.eyads.dalelakْْ.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by eyads on 2/7/2018.
 */

public class comment_adaptor extends RecyclerView.Adapter<comment_adaptor.ViewHolder > {
   private Context c;
   private List<DataListComment> dadaListComments;
   private FirebaseUser currentuser;
   private FirebaseDatabase database;
   private DataListUser dataListUsers;

    public comment_adaptor( Context context,List<DataListComment >dataListComment) {
        currentuser= FirebaseAuth.getInstance().getCurrentUser();
        database= FirebaseDatabase.getInstance();

        c = context;

        this.dadaListComments= dataListComment;
    }

    @Override
    public comment_adaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View r = inflater.inflate(R.layout.comment, parent, false);
        comment_adaptor.ViewHolder viewHolder = new comment_adaptor.ViewHolder(r);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final comment_adaptor.ViewHolder holder, final int position) {
        database.getReference("Users").child(dadaListComments.get(position).getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataListUsers =  dataSnapshot.getValue(DataListUser.class);
                if(dataListUsers!=null){

                    Picasso.with(c).load(Uri.parse(dataListUsers.getImageUrlThumb())).placeholder(R.drawable.avatar).into(holder.circleImageView);
                holder.name.setText(dataListUsers.getFirstName()+" "+ dataListUsers.getLastName());}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.t1.setText(dadaListComments.get(position).getComment());

        holder.date.setText(dadaListComments.get(position).getDate());


holder.dots.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        PopupMenu popup = new PopupMenu(c, holder.dots);
        //Inflating the Popup using xml file
        if(currentuser.getUid().equals(dadaListComments.get(position).getId())){
            popup.getMenuInflater()
                    .inflate(R.menu.delet, popup.getMenu());
        }
        if(!currentuser.getUid().equals(dadaListComments.get(position).getId())){
            popup.getMenuInflater()
                    .inflate(R.menu.popop, popup.getMenu());
        }

        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.ShowProfile) {
                    // Handle the camera action
                    Intent I = new Intent(c,Profile.class);
                    I.putExtra("userID",dadaListComments.get(position).getId());
                    c.startActivity(I);



                }
                if(id==R.id.Report){
                    Toast.makeText(c, R.string.reportText,Toast.LENGTH_SHORT).show();
                    database.getReference("Report").child("comment").child(dadaListComments.get(position).getCountry()).child(dadaListComments.get(position).getPostnumber()).setValue(dadaListComments.get(position).getId());



                }


                if (id == R.id.deletem) {
                    // Handle the camera action
                   // database.getReference("content").

                    database.getReference("content").child(dadaListComments.get(position).getType()).child(dadaListComments.get(position).getCountry()).child(dadaListComments.get(position).getPostnumber()).child("comments").child(dadaListComments.get(position).getNumber()).
                            removeValue();

                    database.getReference("UserX").child(currentuser.getUid()).child("comments").child(dadaListComments.get(position).getNumber()).removeValue();

                }



                return true;}});

    }
});
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
                    I.putExtra("userID",dadaListComments.get(position).getId());
                    c.startActivity(I);



                }
                if(id==R.id.Report){
                    Toast.makeText(c, R.string.reportText,Toast.LENGTH_SHORT).show();
                    database.getReference("Report").child("comment").child(dadaListComments.get(position).getCountry()).child(dadaListComments.get(position).getPostnumber()).setValue(dadaListComments.get(position).getId());



                }


                return true;}});
    }
});
    }

    @Override
    public int getItemCount() {
        return dadaListComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView t1;
        private CircleImageView circleImageView;
        private TextView date;
        private TextView name;
        private ImageButton dots;



        public ViewHolder(View itemView) {
            super(itemView);
            t1 = (TextView) itemView.findViewById(R.id.textView10);
            date = (TextView) itemView.findViewById(R.id.comment_date);
            name = (TextView) itemView.findViewById(R.id.comment_name);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.circleImageViewcomment);
            dots=(ImageButton) itemView.findViewById(R.id.imageButton3dots);


        }
    }

    {
    }
}