package com.dalelak.eyads.dalelakْْ;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class expandedEvent extends AppCompatActivity {

    private ArrayList<String> d ;
    private TextView title;
    private TextView publisher;
    private TextView NOfintersted;
    private TextView NOfGoing;
    private TextView city;
    private TextView dateofevent;
    private TextView StartAT;
    private TextView endAt;
    private TextView price;
    private TextView forwho;
    private TextView Detail;
    private TextView date;
    private TextView country;
    private TextView adress;
    private ImageView imageView;
    private ImageView arrowinteresed;
    private ImageView arrowgoing;
    private FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expanded_event);
        database = FirebaseDatabase.getInstance();
        d = getIntent().getStringArrayListExtra("eventd");

        title= findViewById(R.id.eventex_title_Textview);
        publisher= findViewById(R.id.eventex_publisher_Textview);
        NOfintersted= findViewById(R.id.eventex_intersted_Textview);
        NOfGoing= findViewById(R.id.eventex_going_Textview);
        city= findViewById(R.id.eventex_city_Textview);
        dateofevent= findViewById(R.id.eventex_dateofevent_TExtview);
        StartAT= findViewById(R.id.eventex_startAt_Textview);
        endAt= findViewById(R.id.eventex_endAT_Textview);
        price= findViewById(R.id.eventex_price_Textview);
        forwho= findViewById(R.id.eventex_invitation_Textview);
        Detail= findViewById(R.id.eventex_detail_Textview);
        date= findViewById(R.id.eventex_date_Textview);
        country= findViewById(R.id.eventex_country_Textview);
        adress= findViewById(R.id.eventex_adress_Textview);
        imageView= findViewById(R.id.expevente_cover);
        arrowinteresed= findViewById(R.id.exevent_interstedarrow);
        arrowgoing= findViewById(R.id.exevent_goingarrow);


        title.setText(d.get(0));
        publisher.setText(d.get(1));
        date.setText(d.get(4));
        dateofevent.setText(d.get(5));
        price.setText(d.get(6));
        country.setText(d.get(7));
        city.setText(d.get(8));
       adress.setText(d.get(9));
        StartAT.setText(d.get(10));
        endAt.setText(d.get(11));
        forwho.setText(d.get(12));
        Detail.setText(d.get(13));
        Picasso.with(expandedEvent.this).load(d.get(14)).placeholder(R.drawable.whiteimage).into(imageView);

        publisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(expandedEvent.this, publisher);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popop, popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.ShowProfile) {
                            // Handle the camera action
                            Intent I = new Intent(expandedEvent.this,Profile.class);
                            I.putExtra("userID",d.get(2));
                            startActivity(I);



                        }
                        if(id==R.id.Report){
                            Toast.makeText(expandedEvent.this, R.string.reportText,Toast.LENGTH_SHORT).show();
                            database.getReference("Report").child("Event").child(d.get(7)).child(d.get(3)).setValue(d.get(2));



                        }



                        return true;}});
            }
        });







     arrowinteresed.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent i = new Intent(expandedEvent.this , likesActivity.class);
             i.putExtra("type","event");
             i.putExtra("country",d.get(7));
             i.putExtra("postnumber",d.get(3));
             i.putExtra("whatsoort","Interested");
             startActivity(i);

         }
     });
        arrowgoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(expandedEvent.this , likesActivity.class);
                i.putExtra("type","event");
                i.putExtra("country",d.get(7));
                i.putExtra("postnumber",d.get(3));
                i.putExtra("whatsoort","going");
                startActivity(i);

            }
        });



        database.getReference("content").child("event").child(d.get(7)).child(d.get(3)).child("going").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int xx=0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    xx++;

                }
                NOfGoing.setText(xx+" are going");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        database.getReference("content").child("event").child(d.get(7)).child(d.get(3)).child("Interested").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int xx=0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    xx++;
                }
                NOfintersted.setText(xx+" are interested");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
