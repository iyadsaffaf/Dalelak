package com.dalelak.eyads.dalelakْْ.Adaptors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalelak.eyads.dalelakْْ.MessageChat;
import com.dalelak.eyads.dalelakْْ.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by eyads on 2/27/2018.
 */

public class ChatAdaptor extends RecyclerView.Adapter<ChatAdaptor.ViewHolder>  {
    private Context context;
    private List<MessageChat> messageChats;
    private FirebaseAuth mAuth;
    private FirebaseUser currentuser;
    private final int otheruser = 5;
    private FirebaseDatabase database;

    public ChatAdaptor(Context context,List<MessageChat> messageChats) {
        this.context = context;
        this.messageChats = messageChats;
        mAuth= FirebaseAuth.getInstance();
        currentuser=mAuth.getCurrentUser();
        database= FirebaseDatabase.getInstance();
    }

    @Override
    public int getItemViewType(int position) {
        if(currentuser.getUid().equals(messageChats.get(position).getFrom())){
            return otheruser;
        }
        return super.getItemViewType(position);
    }

    @Override
    public ChatAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==otheruser){
            LayoutInflater inflater = LayoutInflater.from(context);
            View r=     inflater.inflate(R.layout.chatitem2, parent, false);
            ChatAdaptor.ViewHolder viewHolder =new ChatAdaptor.ViewHolder(r);
            return viewHolder;
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View r=     inflater.inflate(R.layout.chatitem, parent, false);
        ChatAdaptor.ViewHolder viewHolder =new ChatAdaptor.ViewHolder(r);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(final ChatAdaptor.ViewHolder holder, int position) {
        if(currentuser.getUid().equals(messageChats.get(position).getFrom())) {

            holder.textView2.setText(messageChats.get(position).getMessage());
            holder.time.setText(messageChats.get(position).getTime());
            holder.time.setVisibility(View.GONE);

            holder.linearLayoutmine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.time.setVisibility(View.VISIBLE);
                }
            });
                database.getReference("Message").child(messageChats.get(position).getTo()).child(currentuser.getUid()
                ).child(messageChats.get(position).getKey())
                        .child("seen").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Boolean s = dataSnapshot.getValue(Boolean.class);
                        if(s!=null){
                        if(s) {
                            holder.imageViewseen.setVisibility(View.VISIBLE);
                        }
                        else{ holder.imageViewseen.setVisibility(View.INVISIBLE);}}
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        }
        if(!currentuser.getUid().equals(messageChats.get(position).getFrom())){

            holder.textView.setText(messageChats.get(position).getMessage());

            holder.time2.setText(messageChats.get(position).getTime());


        }

if(!currentuser.getUid().equals(messageChats.get(position).getFrom())) {
    database.getReference("Message").child(currentuser.getUid()).child(messageChats.get(position).getFrom()).child(messageChats.get(position).getKey())
            .child("seen").setValue(true);
    holder.time2.setVisibility(View.GONE);

    holder.linearLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            holder.time2.setVisibility(View.VISIBLE);
        }
    });
}
    }

    @Override
    public int getItemCount() {
        return messageChats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private LinearLayout linearLayoutmine;

        private TextView textView;
        private TextView textView2;
        private ImageView imageViewseen;
        private TextView time2;
        private TextView time;

        public ViewHolder(View itemView) {

            super(itemView);
            textView= itemView.findViewById(R.id.textView25);
            textView2 =(TextView)itemView.findViewById(R.id.textView26);
            imageViewseen =(ImageView) itemView.findViewById(R.id.imageviewseen);
            time =(TextView)itemView.findViewById(R.id.chattime1);
            time2 =(TextView)itemView.findViewById(R.id.chattime2);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linerotheruser);
            linearLayoutmine=(LinearLayout)itemView.findViewById(R.id.linearmy);




        }
    }
}
