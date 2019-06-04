package com.dalelak.eyads.dalelakْْ;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class market_expanded extends AppCompatActivity {
    private ImageView imageView;
    private ImageView imageView2;
    private ImageView imageView3;

    private TextView name;
    private TextView date;
    private TextView description;
    private TextView price;
    private TextView province;
    private TextView country;
    private TextView status;
    private TextView deliverty;
    private CircleImageView circleImageView;

    private TextView lovenumber;
    private TextView likenumber;

    private ImageView likearrow;
    private ImageView lovearrow;




    private FirebaseAuth mAuth;

    private FirebaseUser currentUser;
    private FirebaseDatabase database;
   private ArrayList<String> links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_expanded);
        mAuth = FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
         final ArrayList<String> d ;
        d = getIntent().getStringArrayListExtra("d");
        imageView=(ImageView)findViewById(R.id.tdd);
        imageView2=(ImageView)findViewById(R.id.tddd);
        imageView3=(ImageView)findViewById(R.id.tdddd);
        circleImageView =(CircleImageView) findViewById(R.id.dcircleimageexpande);
        likearrow=(ImageView)findViewById(R.id.marketEx_likarrow);

        lovearrow=(ImageView)findViewById(R.id.marketEx_lovearrow);

        Picasso.with(market_expanded.this)
                .load(Uri.parse(d.get(15))).placeholder(R.drawable.avatar)
                .into(circleImageView);

        database.getReference("Users").child(d.get(16)).child("imageUrlThumb").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                Picasso.with(market_expanded.this)
                        .load(s).placeholder(R.drawable.avatar)
                        .into(circleImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        name= (TextView)findViewById(R.id.market_name_textviewexpabded);
        date= (TextView)findViewById(R.id.market_date_textviewexpanded);
        description= (TextView)findViewById(R.id.descptionepanded);
        price= (TextView)findViewById(R.id.priceexpanded);
        province= (TextView)findViewById(R.id.provinceexpanded);
        country= (TextView)findViewById(R.id.countryepanded);
        status= (TextView)findViewById(R.id.statusexpanded);
        deliverty= (TextView)findViewById(R.id.deliveryepanded);
        lovenumber= (TextView)findViewById(R.id.textViewloveepanded);
        likenumber= (TextView)findViewById(R.id.textViewlikeexpanded);

        date.setText(d.get(4));
        description.setText(d.get(1));
        price.setText(d.get(3));
        province.setText(d.get(9));
        country.setText(d.get(0));
        status.setText(d.get(7));
        deliverty.setText(d.get(8));
        name.setText(d.get(2));


        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        imageView2.getLayoutParams().height = 0;
        imageView3.getLayoutParams().height = 0;

        links= new ArrayList<>();



        if(!d.get(10).equals("no uri")){
            Picasso.with(this)
                    .load(d.get(10)).placeholder(R.drawable.whiteimage)
                    .into(imageView);
       links.add(d.get(10));

        }
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(market_expanded.this, circleImageView);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popop, popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.ShowProfile) {
                            // Handle the camera action
                            Intent I = new Intent(market_expanded.this,Profile.class);
                            I.putExtra("userID",d.get(16));
                            startActivity(I);



                        }
                        if(id==R.id.Report){
                            Toast.makeText(market_expanded.this, R.string.reportText,Toast.LENGTH_SHORT).show();
                            database.getReference("Report").child("Market").child(d.get(0)).child(d.get(13)).setValue(d.get(16));



                        }

                        return true;}});

            }
        });









        if(!d.get(11).equals("no uri")){
            Picasso.with(this)
                    .load(d.get(11)).placeholder(R.drawable.whiteimage)
                    .into(imageView2);
            imageView2.setVisibility(View.VISIBLE);
            imageView2.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;
            imageView3.getLayoutParams().height = LinearLayout.LayoutParams.MATCH_PARENT;

            links.add(d.get(11));


        }

        if(!d.get(12).equals("no uri")){

            Picasso.with(this)
                    .load(d.get(12)).placeholder(R.drawable.whiteimage).into(imageView3)
            ;
            imageView3.setVisibility(View.VISIBLE);

            links.add(d.get(12));



        }










        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(market_expanded.this, com.dalelak.eyads.dalelakْْ.Slider.class);

                intent.putExtra("links",links);
                startActivity(intent);

            }
        });

     imageView2.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {


             Intent  intent = new Intent(market_expanded.this, Slider.class);
             intent.putExtra("links",links);
             intent.putExtra("position",1);

             startActivity(intent);
         }
     });

imageView3.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent  intent = new Intent(market_expanded.this,Slider.class);
        intent.putExtra("links",links);
        intent.putExtra("position",2);

        startActivity(intent);
    }

});


        database.getReference("content").child("Market").child(d.get(0)).child(d.get(13)).child("likes").addValueEventListener(new ValueEventListener() {
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
        database.getReference("content").child("Market").child(d.get(0)).child(d.get(13)).child("Lovers").addValueEventListener(new ValueEventListener() {
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



       lovearrow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent i = new Intent(market_expanded.this , likesActivity.class);
        i.putExtra("type","Market");
        i.putExtra("country",d.get(0));
        i.putExtra("postnumber",d.get(13));
        i.putExtra("whatsoort","Lovers");
        startActivity(i);

             }
             });


        likearrow.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View view) {

                                     Intent i = new Intent(market_expanded.this , likesActivity.class);
                                     i.putExtra("type","Market");
                                     i.putExtra("country",d.get(0));
                                     i.putExtra("postnumber",d.get(13));
                                     i.putExtra("whatsoort","likes");
                                     startActivity(i);

                                 }
                             }
                );





    }






}
