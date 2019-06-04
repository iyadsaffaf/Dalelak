package com.dalelak.eyads.dalelakْْ;

import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.dalelak.eyads.dalelakْْ.Adaptors.FacebookLoginActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //firebase
    private FirebaseAuth mAuth;
    private TextView nav_user;
    //view pager

    private TabLayout mTabLayout;
    private Menu menu;
    private  CircleImageView circleImageView;
    private DataListUser dataListUser;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   if(!isNetworkAvailable()){
       Toast.makeText(MainActivity.this, R.string.internet_connection,Toast.LENGTH_LONG).show();
   }

        // firebase
        mAuth = FirebaseAuth.getInstance();
         currentUser = mAuth.getCurrentUser();
         database = FirebaseDatabase.getInstance();

        if(currentUser==null){
            Intent I = new Intent(MainActivity.this, FacebookLoginActivity.class);
            finish();
            startActivity(I);


        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarey);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
         nav_user = (TextView)hView.findViewById(R.id.nav_name);
         circleImageView= (CircleImageView) hView.findViewById(R.id.nav_image);
        if(currentUser!=null) {
              database.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            dataListUser = dataSnapshot.getValue(DataListUser.class);
            if (dataListUser != null) {
                Picasso.with(MainActivity.this).load(dataListUser.getImageUrlThumb()).placeholder(R.drawable.whiteimage).into(circleImageView);
                nav_user.setText(dataListUser.getFirstName() + " " + dataListUser.getLastName());
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

}


        // page viewer
        mTabLayout =(TabLayout)findViewById(R.id.tabPage);

        ViewPager mViewpager =(ViewPager)findViewById(R.id.viewPagerMain);
       pageAdaptor mPageAdaptor= new pageAdaptor(getSupportFragmentManager(),MainActivity.this);
//       if( Locale.getDefault().getDisplayLanguage().equals("ar")){
//           mViewpager.setRotationY(180);
//
//       }else {
//
//       }

        mViewpager.setAdapter(mPageAdaptor);
        mTabLayout.setupWithViewPager(mViewpager);

         AdView mAdView;

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        String s =PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("MYLABEL", "Netherlands");

        MenuItem f =menu.findItem(R.id.refresh);
        switch(s){
            case  "Netherlands":
                f.setIcon(R.drawable.flag_netherlands);
                break;
            case "Germany":
                    f.setIcon(R.drawable.germanflag);
                    break;
            case  "Sweden":
                f.setIcon(R.drawable.swedenflag270x270);
                break;
            case  "Hungary":
                f.setIcon(R.drawable.hungary);
                break;
            case  "Austria":
                f.setIcon(R.drawable.austria);
                break;
            case  "Denmark":
                f.setIcon(R.drawable.denmark);
                break;
            case  "Belgium":
                f.setIcon(R.drawable.belgium);
                break;
            case  "France":
                f.setIcon(R.drawable.france);
                break;
            case  "Norway":
                f.setIcon(R.drawable.norway);
                break;
            case  "Finland":
                f.setIcon(R.drawable.finland300x300);
                break;
            case  "Switzerland":
                f.setIcon(R.drawable.switherlands);
                break;
            case  "United Kingdom":
                f.setIcon(R.drawable.unitedkingdom);
                break;
            case  "Spain":
                f.setIcon(R.drawable.spanish300x300);
                break;
            case  "Italy":
                f.setIcon(R.drawable.italy);
                break;
            case  "Greece":
                f.setIcon(R.drawable.greece);
                break;
            case  "Bulgaria":
                f.setIcon(R.drawable.bulgaria);
                break;

             default:
                 f.setIcon(R.drawable.italy);


        }



        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                Intent i = new Intent(this, com.dalelak.eyads.dalelakْْ.ChangeSearch.class);
                startActivity(i);
                finish();

            default:
                break;
        }

        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()){
            case R.id.nav_myprofile:
                Intent I = new Intent(MainActivity.this,Profile.class);
                I.putExtra("userID",currentUser.getUid());
                I.putExtra("upbutton",currentUser.getUid());

                startActivity(I);


                break;

            case R.id.nav_add_List:
                Intent Is = new Intent(MainActivity.this, com.dalelak.eyads.dalelakْْ.ADD_Main.class);
                finish();
                startActivity(Is);

                break;

            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent asd = new Intent(MainActivity.this, FacebookLoginActivity.class);
                startActivity(asd);
                finish();
                break;
            case     R.id.nav_slideshow:
                Intent Iq = new Intent(MainActivity.this, MyPosts.class);
                startActivity(Iq);
                finish();

                break;

            case R.id.nav_setting:
                Intent Ir = new Intent(MainActivity.this, Setteing.class);
                startActivity(Ir);
                finish();
                break;
            case R.id.invite:
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Dalelak");
                    String strShareMessage = "\nLet me recommend you Dalelak the best app for Arabic people in Europe \n\n";
                    strShareMessage = strShareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName();
                    i.putExtra(Intent.EXTRA_TEXT, strShareMessage);
                    startActivity(Intent.createChooser(i, "Share via"));

                } catch(Exception e) {
                    //e.toString();
                }
                break;

            case R.id.mymessage:
                Intent sd = new Intent(MainActivity.this, MainMessage.class);
                startActivity(sd);
                break;

            case R.id.supportus:
                Intent sds = new Intent(MainActivity.this, SupportUs.class);

                startActivity(sds);
                break;
            case R.id.thankto:
                Intent sdss = new Intent(MainActivity.this, ThanksTo.class);

                startActivity(sdss);
                break;






        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null){
            Intent I = new Intent(MainActivity.this, com.dalelak.eyads.dalelakْْ.Adaptors.FacebookLoginActivity.class);
            startActivity(I);
            finish();


        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        try {
            trimCache(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(database!=null&&currentUser!=null){
        database.getReference("Usersonline").child(currentUser.getUid()).child("timestamp").setValue(ServerValue.TIMESTAMP);
        database.getReference("Usersonline").child(currentUser.getUid()).child("online").setValue(false);}

        try {
            //trimCache(this);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

