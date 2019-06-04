package com.dalelak.eyads.dalelakْْ.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dalelak.eyads.dalelakْْ.DataListUser;
import com.dalelak.eyads.dalelakْْ.Profile;
import com.dalelak.eyads.dalelakْْ.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by eyads on 2/26/2018.
 */

public class LikesAdaptor extends RecyclerView.Adapter<LikesAdaptor.ViewHolder > {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private Context context;
    private List<DataListUser> users;

    public LikesAdaptor(Context context, List<DataListUser> users) {
        this.context = context;
        this.users = users;
        database= FirebaseDatabase.getInstance();
    }

    @Override
    public LikesAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View r=     inflater.inflate(R.layout.likesitem, parent, false);
        LikesAdaptor.ViewHolder viewHolder =new LikesAdaptor.ViewHolder(r);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(final LikesAdaptor.ViewHolder holder, final int position) {

        holder.name.setText(users.get(position).getFirstName() +" "+ users.get(position).getLastName());


                Picasso.with(context).load(users.get(position).getImageUrlThumb()).placeholder(R.drawable.avatar).into(holder.circleImageView);


holder.linearLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent I = new Intent(context,Profile.class);
        I.putExtra("userID",users.get(position).getId());
        context.startActivity(I);


    }
});
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView;
        private TextView name;
        private LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.circle_Likes);
            name = (TextView) itemView.findViewById(R.id.name_likes);
            linearLayout =(LinearLayout) itemView.findViewById(R.id.like_liner);

        }
    }
}
