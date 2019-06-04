package com.dalelak.eyads.dalelakْْ;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import id.zelory.compressor.Compressor;

public class AddGhide extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText name;
    private EditText servec;
    private EditText phone;
    private EditText city;
    private EditText adress;
    private EditText website;
    private EditText descrption;
    private Button post;
    private Button upload;
    private ProgressBar br;
    private StorageReference mStorageRef;
    private Uri imageUri;

    private Userpost userpost;

   private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
   private ArrayList<ImageView> imageViewArrayList;
    private ArrayList<Uri> uriArrayList;



    private boolean b1;
    private boolean b2;
    private boolean b3;

    //spiner1
    private Spinner mSpinner;
    private String[] countries;
    private Integer[] images;

    //spiner2
    private Spinner mSpinnerProvinces;
    private SpinnerAdaptorProvencies spinnerAdaptorProvencies;
    private String[] provinces;
    //private string
    private String province;
    private String country;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ghide);
        imageViewArrayList= new ArrayList<>();
        uriArrayList= new ArrayList<>();
        new AddGhide.ss().execute();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();






        for(int i=0;i<7;i++){
            bitmapArray.add(i,null);
            uriArrayList.add(i,null);


        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar6);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.czv);


        name = (EditText) findViewById(R.id.ghide_name_editText);
        servec = (EditText) findViewById(R.id.ghide_servec_editText);
        city = (EditText) findViewById(R.id.ghide_city_edittext);
        adress = (EditText) findViewById(R.id.ghide_adress_edittext);
        website = (EditText) findViewById(R.id.ghude_website_edittext);
        phone = (EditText) findViewById(R.id.ghide_phone_edittext);
        descrption = (EditText) findViewById(R.id.ghide_description_edittext);
        post = (Button) findViewById(R.id.ghide_post_button);
        upload = (Button) findViewById(R.id.ghide_uploade_button);
        imageViewArrayList.add((ImageView) findViewById(R.id.ghide_imageview_1));
        imageViewArrayList.add((ImageView) findViewById(R.id.ghide_imageview_2));
        imageViewArrayList.add((ImageView) findViewById(R.id.ghide_imageview_3));
        imageViewArrayList.add((ImageView) findViewById(R.id.ghide_imageview_4));
        imageViewArrayList.add((ImageView) findViewById(R.id.ghide_imageview_5));
        imageViewArrayList.add((ImageView) findViewById(R.id.ghide_imageview_6));
        imageViewArrayList.add((ImageView) findViewById(R.id.ghide_imageview_7));
        linearLayout=(LinearLayout) findViewById(R.id.linearx);
        linearLayout.setVisibility(View.GONE);

        br = (ProgressBar) findViewById(R.id.ghide_progress_buttton);
        br.setVisibility(View.VISIBLE);






upload.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON).setMinCropWindowSize(500, 500)
                .setAspectRatio(1, 1)
                .start(AddGhide.this);
    }
});


post.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        if (bitmapArray.get(0) == null) {
            Toast.makeText(AddGhide.this, R.string.dsdbgsda, Toast.LENGTH_LONG).show();
            return;
        }
        if (name.getText().toString().trim().equals("")) {
            Toast.makeText(AddGhide.this, R.string.dbnnndf, Toast.LENGTH_LONG).show();
            name.requestFocus();

            return;
        }

        if (servec.getText().toString().trim().equals("")) {
            Toast.makeText(AddGhide.this, R.string.zfbbfdnb, Toast.LENGTH_LONG).show();
            servec.requestFocus();
            return;
        }
        if (city.getText().toString().trim().equals("")) {
            Toast.makeText(AddGhide.this, R.string.fbbfssnz, Toast.LENGTH_LONG).show();
            city.requestFocus();
            return;
        }
        if (descrption.getText().toString().trim().equals("")) {
            Toast.makeText(AddGhide.this, R.string.fsbzsznbfs, Toast.LENGTH_LONG).show();
            descrption.requestFocus();
            return;
        }
        if (adress.getText().toString().trim().equals("")) {
            Toast.makeText(AddGhide.this, R.string.dbbdfhndf, Toast.LENGTH_LONG).show();
            adress.requestFocus();
            return;
        }









        post.setVisibility(View.INVISIBLE);


        //upload images and getting uri
        for( int i=0 ;i<bitmapArray.size();i++){

            if (bitmapArray.get(i) != null) {
             final int s=i;
                //upload image
                Random v = new Random();
                int f = v.nextInt(32800000);
                ByteArrayOutputStream baos;
                baos = new ByteArrayOutputStream();
                bitmapArray.get(i) .compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datax = baos.toByteArray();
                StorageReference mountainsRef = mStorageRef.child("Guide").child(currentUser.getUid()).child(f  + ".jpg");
                    final UploadTask uploadTask = mountainsRef.putBytes(datax);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            uriArrayList.set(s,taskSnapshot.getDownloadUrl());
                            if(s==bitmapArray.size()-1)
                                uploaddata();
                            if(s!=bitmapArray.size()-1){
                                if(bitmapArray.get(s+1)==null)
                                uploaddata();
                            }

                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(AddGhide.this, "Trt Again", Toast.LENGTH_LONG).show();
                        }
                    });



            }


        }




    }
});



    }
    public void uploaddata() {


        //upload datat

        database = FirebaseDatabase.getInstance();

                   datalistG ds;

        DatabaseReference dd = database.getReference("Guide").child(country);
        String key =database.getReference("Guide").child(country).push().getKey();

                //getting date
                Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd' At 'HH:mm", Locale.ENGLISH);
                String formattedDate = df.format(c);
                for(int i =0;i<uriArrayList.size();i++){
                    if(uriArrayList.get(i)==null){
                        uriArrayList.set(i, Uri.parse("Empty"));

                    }

                }

                userpost = new Userpost(key,country);
                database.getReference("UserX").child(currentUser.getUid()).child("Guide").child(key).setValue(userpost);

                ds = new datalistG(currentUser.getUid(),key,name.getText().toString().trim(),servec.getText().toString().trim(),adress.getText().toString().trim(),
                        city.getText().toString().trim(),website.getText().toString().trim(),phone.getText().toString().trim(),descrption.getText().toString().trim(),formattedDate,
                        0,0,province,country,uriArrayList.get(0).toString(),uriArrayList.get(1).toString(),uriArrayList.get(2).toString()
                        ,uriArrayList.get(3).toString(),uriArrayList.get(4).toString(),uriArrayList.get(5).toString(),uriArrayList.get(6).toString());

                dd.child(key).setValue(ds);



                Intent I = new Intent(AddGhide.this, MainActivity.class);
                startActivity(I);
                finish();
            }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();

                File thumbPath = new File(imageUri.getPath());
         for(int i=0 ;i<bitmapArray.size();i++){
             if(bitmapArray.get(i)==null){
                 if(i==4){
                     linearLayout.setVisibility(View.VISIBLE);

                 }
                 if(i==bitmapArray.size()-1){
                     upload.setEnabled(false);
                 }

                 try { Bitmap c= new Compressor(this)
                             .setMaxWidth(300)
                             .setMaxHeight(300)
                             .setQuality(70)
                             .setCompressFormat(Bitmap.CompressFormat.WEBP)
                             .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                     Environment.DIRECTORY_PICTURES).getAbsolutePath())
                             .compressToBitmap(thumbPath);
                     bitmapArray.set(i,c);
                     imageViewArrayList.get(i).setImageBitmap(c);
                     upload.setText("Upload More");
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 break;
             }

         }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public int dpToPx(int dp) {
        float density = AddGhide.this.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public class ss extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            //spinner 1
            countries = new String[]{"Netherlands", "Germany", "Sweden", "Hungary", "Austria", "Denmark", "Belgium", "France", "Norway", "Finland", "Switzerland",
                    "United Kingdom", "Spain", "Italy", "Greece", "Bulgaria"};

            images = new Integer[]{R.drawable.flag_netherlands,
                    R.drawable.germanflag, R.drawable.swedenflag270x270, R.drawable.hungary,
                    R.drawable.austria, R.drawable.denmark,
                    R.drawable.belgium, R.drawable.france, R.drawable.norway, R.drawable.finland300x300, R.drawable.switherlands,
                    R.drawable.unitedkingdom, R.drawable.spanish300x300, R.drawable.italy, R.drawable.greece, R.drawable.bulgaria};
            mSpinner = findViewById(R.id.spinner_ghide_country);
            final SpinnerAdaptorCountries mAdaptor = new SpinnerAdaptorCountries(AddGhide.this, R.layout.spinner_ietm_countries, countries, images);
            mSpinner.setAdapter(mAdaptor);
            mSpinner.post(new Runnable() {
                @Override
                public void run() {
                    String s = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("MYLABEL", "Netherlands");

                    mSpinner.setSelection(mAdaptor.getPosition(s));
                }
            });


            //spinner 2

            mSpinnerProvinces = findViewById(R.id.spinner_ghide_province);
            provinces = new String[]{"All", "Hamburg", "Brlen ", "Bremen", "oldenburg"};
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Groningen", "North Holland", "Overijssel", "Drenthe","Flevoland","Friesland","Gelderland","Limburg"
                                    ,"North Brabant","South Holland","Utrecht","Zeeland"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            break;
                        case 1:
                            country =mSpinner.getSelectedItem().toString();

                            provinces = new String[]{"All", "Baden-Württemberg", "Bavaria", "Berlin", "Brandenburg","Bremen","Hamburg","Hesse","Lower Saxony"
                                    ,"Mecklenburg Vorpommern","North Rhine Westphalia","Rhineland Palatinate","Saarland","Saxony","Saxony-Anhalt","Schleswig-Holstein","Thuringia"};

                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            break;
                        case 2:
                            country =mSpinner.getSelectedItem().toString();

                            provinces = new String[]{"All", "Ångermanland", "Blekinge", "Bohuslän", "Dalarna","Dalsland","Gotland","Gästrikland","Halland"
                                    ,"Hälsingland","Härjedalen","Jämtland","Lappland","Medelpad","Norrbotten","Närke","Öland","Östergötland","Skåne","Småland",
                                    "Södermanland","Uppland","Värmland","Västmanland","Västerbotten","Västergötland"};

                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });

                            break;
                        case 3:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Baranya", "Borsod-Abaúj-Zemplén", "Budapest", "Bács-Kiskun","Békés","Csongrád","Fejér","Győr-Moson-Sopron"
                                    ,"Hajdú-Bihar","Heves","Hungary","Jász-Nagykun-Szolnok","Komárom-Esztergom","Nógrád","Pest","Somogy","Szabolcs-Szatmár-Bereg","Tolna","Vas",
                                    "Veszprém","Zala"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            break;
                        case 4:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Burgenland", "Carinthia", "Lower Austria", "Salzburg","Styria","Tyrol","Upper Austria","Vienna"
                                    ,"Vorarlberg"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            break;

                        case 5:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "København By", "Københavns omegn", "Nordsjælland", "Bornholm","Østsjælland","Vest- og Sydsjælland","Fyn","Sydjylland"
                                    ,"Vestjylland","Østjylland","Nordjylland"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            break;
                        case 6:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Antwerpen", "Brussel"," Limburg", "Oost-Vlaanderen", "Vlaams-Brabant","West-Vlaanderen","Henegouwen","Luik","Luxemburg"
                                    ,"Namen","Waals-Brabant "};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            break;
                        case 7:
                            country =mSpinner.getSelectedItem().toString();

                            provinces = new String[]{"All", "Auvergne-Rhône-Alpes", "Bourgogne-Franche-Comté", "Bretagne", "Centre-Val de Loire","Corse","Grand Est","Guadeloupe","Guyane"
                                    ,"Hauts-de-France","La Réunion","Martinique","Mayotte","Normandie","Nouvelle-Aquitaine","Occitanie","Pays de la Loire","Provence-Alpes-Côte d'Azur",
                                    "Île-de-France"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            break;
                        case 8:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Northern Norway", "Trøndelag", "Western Norway", "Southern Norway","Eastern Norway"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            break;
                        case 9:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Southern Finland", "Western Finland", "Eastern Finland", "Oulu","Lapland","Åland"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            break;

                        case 10:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Aargau", "Appenzell Ausserrhoden", "Appenzell Innerrhoden", "Basel-Landschaft","Basel-Stadt","Bern","Fribourg","Geneva"
                                    ,"Glarus","Grisons","Jura","Luzern","Neuchâtel","Nidwalden","Obwalden","Schaffhausen","Schwyz","Solothurn","St. Gallen",
                                    "Thurgau","Ticino","Uri","Valais","Vaud","Zug","Zürich"};

                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            break;


                        case 11:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "East Midlands", "East of England", "England", "London","North East","North West","South East","South West"
                                    ,"West Midlands","Yorkshire and the Humber"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            break;

                        case 12:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "A Coruña ", "Álava", "Alicante", "Almería","Asturias","Ávila","Badajoz","Balearic Islands"
                                    ,"Barcelona","Biscay","Burgos","Cáceres","Cádiz","Cantabria","Castellón","Ciudad Real","Córdoba","Cuenca","Gipuzkoa",
                                    "Girona","Granada","Guadalajara","Huelva","Huesca","Jaén","La Rioja","Las Palmas","León","Lleida ","Lugo","Madrid","Málaga","Murcia","Navarre","Ourense","Palencia",
                                    "Pontevedra","Salamanca","Santa Cruz de Tenerife","Segovia","Seville","Soria","Tarragona","Teruel","Toledo","Valencia","Valladolid","Zamora","Zaragoza"
                            };
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            break;

                        case 13:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Nord-Ovest", "Nord-Est", "Centro", "Sud","Isole"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            break;

                        case 14:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Attica", "Central Greece", "Central Macedonia", "Crete",
                                    "East Macedonia and Thrace", "Epirus", "Ionian Islands", "North Aegean", "Peloponnese","South Aegean", "Thessaly", "West Greece", "West Macedonia", "Mount Athos"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                            break;

                        case 15:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Blagoevgrad", "Burgas", "Dobrich", "Gabrovo","Haskovo","Kardzhali","Kyustendil","Lovech"
                                    ,"Montana","Pazardzhik","Pernik","Pleven","Plovdiv","Razgrad","Ruse","Shumen","Silistra","Sliven","Smolyan",
                                    "Sofia","Sofia City","Stara Zagora","Targovishte","Varna","Veliko Tarnovo","Vidin","Vratsa","Yambol"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddGhide.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(spinnerAdaptorProvencies);
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });

                            break;

                        default:
                            break;

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            String s = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("MYLABEL", "Netherlands");

            mSpinner.setSelection(mAdaptor.getPosition(s));
            return null;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                Intent Is = new Intent(AddGhide.this,ADD_Main.class);
                finish();
                startActivity(Is);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent Is = new Intent(AddGhide.this,ADD_Main.class);
        finish();
        startActivity(Is);         }
}
