package com.dalelak.eyads.dalelakْْ.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dalelak.eyads.dalelakْْ.ChatWindow;
import com.dalelak.eyads.dalelakْْ.DataListUser;
import com.dalelak.eyads.dalelakْْ.MessageChat;
import com.dalelak.eyads.dalelakْْ.Profile;
import com.dalelak.eyads.dalelakْْ.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by eyads on 2/27/2018.
 */

public class MainChatAdaptor extends RecyclerView.Adapter<MainChatAdaptor.ViewHolder> {
    private Context context;
    private List<DataListUser> dataListUser;
    private FirebaseDatabase database;
    private FirebaseUser currentuser;
  private int d=0;
    public MainChatAdaptor(Context context,List<DataListUser> dataListUser) {
        this.context = context;
        this.dataListUser= dataListUser;
        database = FirebaseDatabase.getInstance();
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public MainChatAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View r=     inflater.inflate(R.layout.chatmainitem, parent, false);
        MainChatAdaptor.ViewHolder viewHolder =new MainChatAdaptor.ViewHolder(r);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final MainChatAdaptor.ViewHolder holder, final int position) {
  holder.linearLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {


          Intent i = new Intent(context , ChatWindow.class);
          if(dataListUser.get(position)!=null)
          i.putExtra("userIDfromProfile",dataListUser.get(position).getId());
          if(dataListUser.get(position).getFirstName().equals("NO user")  &&dataListUser.get(position).getImageUri().equals("") ){
              i.putExtra("removed","true");
          }
          context.startActivity(i);

      }
  });

  holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View view) {



          PopupMenu popup = new PopupMenu(context,  holder.mainlinerlayput);
          //Inflating the Popup using xml file
          popup.getMenuInflater()
                  .inflate(R.menu.delet, popup.getMenu());
          popup.show();

          popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
              public boolean onMenuItemClick(MenuItem item) {
                  int id = item.getItemId();

                  if (id == R.id.deletem) {
                      database.getReference("Message").child(currentuser.getUid()).child(dataListUser.get(position).getId()).removeValue();
                      database.getReference("Chat").child(currentuser.getUid()).child(dataListUser.get(position).getId()).removeValue();
                      // Handle the camera action




                  }


                  return true;}});

          return true;
      }
  });
 if(dataListUser!=null&&dataListUser.get(position)!=null){
  holder.name.setText(dataListUser.get(position).getFirstName()+" "+dataListUser.get(position).getLastName());
if(!dataListUser.get(position).getImageUrlThumb().equals(""))
        Picasso.with(context).load(dataListUser.get(position).getImageUrlThumb()).placeholder(R.drawable.avatar).into(holder.circleImageView);

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
                            if(dataListUser.get(position).getFirstName().equals("NO user")  &&dataListUser.get(position).getImageUri().equals("") ){
                                Toast.makeText(context, R.string.theaccountdelted,Toast.LENGTH_SHORT)   .show();                         }
                            else {
                            Intent I = new Intent(context,Profile.class);
                            I.putExtra("userID",dataListUser.get(position).getId());
                            context.startActivity(I);}



                        }

                        if (id == R.id.Report) {
                            // Handle the camera action
                           database.getReference("Report").child("users").push().setValue(dataListUser.get(position).getId());
                            Toast.makeText(context, R.string.reportText,Toast.LENGTH_SHORT)   .show();                         }






                        return true;}});
            }
        });
//
        Query q = database.getReference("Message").child(currentuser.getUid()).child(dataListUser.get(position).getId()).limitToLast(30);
       q.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               d=0;

               for (DataSnapshot data : dataSnapshot.getChildren()) {

                   MessageChat m = data.getValue(MessageChat.class);
                   holder.lastmessage.setText(m.getMessage());
                   holder.lasttime.setText(m.getTime());

                   if(!m.isSeen()){
                       d++;
                       holder.lastmessage.setTextColor(Color.BLACK);
                       Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                       holder.lastmessage.setTypeface(boldTypeface);
                       holder.imageView.setVisibility(View.VISIBLE);
                       holder.Nmessage.setVisibility(View.VISIBLE);
                       holder.Nmessage.setText(d+"");

                   }else {
                       holder.imageView.setVisibility(View.INVISIBLE);
                       holder.lastmessage.setTextColor(Color.GRAY);
                       Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.NORMAL);
                       holder.lastmessage.setTypeface(boldTypeface);
                       holder.Nmessage.setVisibility(View.INVISIBLE);


                   }



               }
               }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });}
       else {



     holder.name.setText("NO User");
 }
       holder.mainlinerlayput.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View view) {
               holder.mainlinerlayput.setBackgroundColor(android.R.drawable.screen_background_dark_transparent);


               PopupMenu popup = new PopupMenu(context,  holder.mainlinerlayput);
               //Inflating the Popup using xml file
               popup.getMenuInflater()
                       .inflate(R.menu.delet, popup.getMenu());
               popup.show();

               popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   public boolean onMenuItemClick(MenuItem item) {
                       int id = item.getItemId();

                       if (id == R.id.deletem) {
                           // Handle the camera action

database.getReference("Message").child(currentuser.getUid()).child(dataListUser.get(position).getId()).removeValue();
database.getReference("Chat").child(currentuser.getUid()).child(dataListUser.get(position).getId()).removeValue();



                       }


                       return true;}});

               return true;
           }
       });

    }

    @Override
    public int getItemCount() {
        return dataListUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private RelativeLayout linearLayout;
        private CircleImageView circleImageView;
        private TextView lastmessage;
        private TextView lasttime;
        private TextView Nmessage;
        private ImageView imageView;
        private LinearLayout mainlinerlayput;
        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.chatmainlinearlayout);
            name = (TextView)itemView.findViewById(R.id.chatMain_name);
            circleImageView = (CircleImageView)itemView.findViewById(R.id.mainchatcircle);
            lastmessage=(TextView)itemView.findViewById(R.id.lastmessage);
            lasttime=(TextView)itemView.findViewById(R.id.mainchattime);
            Nmessage=(TextView)itemView.findViewById(R.id.textViewmainchat);
            imageView=(ImageView) itemView.findViewById(R.id.imageViewmainchat);

                    mainlinerlayput=(LinearLayout) itemView.findViewById(R.id.mainchatLinerlayout);








        }
    }
}
