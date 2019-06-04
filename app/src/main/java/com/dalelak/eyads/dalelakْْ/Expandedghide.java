package com.dalelak.eyads.dalelakْْ;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Expandedghide extends AppCompatActivity {

    private TextView name;
    private TextView servec;
    private TextView adress;
    private TextView city;
    private TextView countryT;
    private TextView province;
    private TextView website;
    private TextView descption;
    private TextView phone;

    private TextView date;
    private TextView nameX;
    private TextView lovenumber;
    private TextView likenumber;
    private ImageView likearrow,lovearrow,downarrow;


    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ArrayList<String> links;
    private ArrayList<ImageView> bitmapArrayList ;
   private ArrayList<String> d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandedghide);
        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        d = getIntent().getStringArrayListExtra("sd");
        bitmapArrayList= new ArrayList<>();
        links =new ArrayList<>();

        bitmapArrayList.add((ImageView)findViewById(R.id.expanded_g_imageview));
        bitmapArrayList.add((ImageView)findViewById(R.id.expanded_g_imageview2));
        bitmapArrayList.add((ImageView)findViewById(R.id.expanded_g_imageview3));
        bitmapArrayList.add((ImageView)findViewById(R.id.expanded_g_imageview4));
        bitmapArrayList.add((ImageView)findViewById(R.id.expanded_g_imageview5));
        bitmapArrayList.add((ImageView)findViewById(R.id.expanded_g_imageview6));
        bitmapArrayList.add((ImageView)findViewById(R.id.expanded_g_imageview7));

        name= (TextView)findViewById(R.id.expanded_g_textview_name);
        servec= (TextView)findViewById(R.id.expanded_g_textview_servece);
        adress= (TextView)findViewById(R.id.expanded_g_textview_adress);
        city= (TextView)findViewById(R.id.expanded_g_textview_city);
        countryT= (TextView)findViewById(R.id.expanded_g_textview_country);
        province= (TextView)findViewById(R.id.expanded_g_textview_provence);
        website= (TextView)findViewById(R.id.expanded_g_textview_website);
        phone= (TextView)findViewById(R.id.expanded_g_textview_phone);

        descption= (TextView)findViewById(R.id.expanded_g_textview_descrption);
        date= (TextView)findViewById(R.id.expanded_g_textview_data);
        nameX= (TextView)findViewById(R.id.expandeextview_namepost);
        lovenumber= (TextView)findViewById(R.id.expanded_g_textview_loves);
        likenumber= (TextView)findViewById(R.id.expanded_g_textview_likes);

        downarrow= (ImageView)findViewById(R.id.arrowguideex);
        lovearrow= (ImageView)findViewById(R.id.ex_love_arrow);
        likearrow= (ImageView)findViewById(R.id.ex_like_arrow);

        downarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(Expandedghide.this, downarrow);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popop, popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.ShowProfile) {
                            // Handle the camera action
                            Intent I = new Intent(Expandedghide.this,Profile.class);
                            I.putExtra("userID",d.get(21));
                            startActivity(I);



                        }
                        if(id==R.id.Report){
                            Toast.makeText(Expandedghide.this, R.string.reportText,Toast.LENGTH_SHORT).show();
                            database.getReference("Report").child("Guide").child(d.get(12)).child(d.get(0)).setValue(d.get(21));



                        }



                        return true;}});
            }
        });


       likearrow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(Expandedghide.this , likesActivity.class);
               i.putExtra("type","Guide");
               i.putExtra("country",d.get(12));
               i.putExtra("postnumber",d.get(0));
               i.putExtra("whatsoort","Likes");
               startActivity(i);

           }
       });
       lovearrow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(Expandedghide.this , likesActivity.class);
               i.putExtra("type","Guide");
               i.putExtra("country",d.get(12));
               i.putExtra("postnumber",d.get(0));
               i.putExtra("whatsoort","Lovers");
               startActivity(i);
           }
       });
       nameX.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               PopupMenu popup = new PopupMenu(Expandedghide.this, nameX);
               //Inflating the Popup using xml file
               popup.getMenuInflater()
                       .inflate(R.menu.popop, popup.getMenu());
               popup.show();

               popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   public boolean onMenuItemClick(MenuItem item) {
                       int id = item.getItemId();

                       if (id == R.id.ShowProfile) {
                           // Handle the camera action
                           Intent I = new Intent(Expandedghide.this,Profile.class);
                           I.putExtra("userID",d.get(21));
                           startActivity(I);



                       }
                       if(id==R.id.Report){
                           Toast.makeText(Expandedghide.this, R.string.reportText,Toast.LENGTH_SHORT).show();
                           database.getReference("Report").child("Guide").child(d.get(12)).child(d.get(0)).setValue(d.get(21));



                       }

                       return true;}});
           }
       });

        name.setText(d.get(1));
        servec.setText(d.get(2));
        adress.setText(d.get(3));
        city.setText(d.get(4));
        website.setText(d.get(5));
        phone.setText(d.get(6));
        descption.setText(d.get(7));
        date.setText(d.get(8));
        likenumber.setText(d.get(9));
        lovenumber.setText(d.get(10));
        province.setText(d.get(11));
        countryT.setText(d.get(12));

        database.getReference("Users").child(d.get(21)).child("firstName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameX.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        int x=0;
        for(int dd=0;dd<bitmapArrayList.size();dd++){
            bitmapArrayList.get(dd).setVisibility(View.GONE);
        }


        for(int i =13 ;i<d.size()-2;i++){
            if(!d.get(i).equals("Empty")){
                bitmapArrayList.get(x).setVisibility(View.VISIBLE);
            Picasso.with(this)
                    .load(Uri.parse(d.get(i))).placeholder(R.drawable.whiteimage)
                    .into(bitmapArrayList.get(x));
            links.add(d.get(i));

            x++;}

        }
        for(int er=0;er<links.size();er++) {
           final int f= er;
            bitmapArrayList.get(er).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Expandedghide.this, com.dalelak.eyads.dalelakْْ.Slider.class);
                    intent.putExtra("links", links);
                    intent.putExtra("position", f);
                    startActivity(intent);

                }
            });
        }
        database.getReference("content").child("Guide").child(d.get(12)).child(d.get(0)).child("Likes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int xx=0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    xx++;

                }
                likenumber.setText(xx+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database.getReference("content").child("Guide").child(d.get(12)).child(d.get(0)).child("Lovers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int xx=0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    xx++;
                }
                lovenumber.setText(xx+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
