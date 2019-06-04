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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import id.zelory.compressor.Compressor;


public class AddMarket extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG = 109;
    private FirebaseAuth mAuth;
    private EditText price;
    private EditText status;
    private EditText delivery;
    private EditText descrption;
    private Button post;
    private Button upload;
    private Button upload2;
    private Button upload3;
    private ImageView image;
    private ImageView image2;
    private ImageView image3;
    private ProgressBar br;
    private StorageReference mStorageRef;
    private Uri downloadUrl;
    private Uri downloadUrl2;
    private Uri downloadUrl3;
    private Uri imageUri;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Bitmap pickedBitmap;
    private Bitmap pickedBitmap2;
    private Bitmap pickedBitmap3;
    private boolean b1;
    private boolean b2;
    private boolean b3;
    //spiner1
    private Spinner mSpinner;
    private String[] countries;
    private Integer[] images;
    //spiner2
    private Spinner mSpinnerProvinces;
    private String[] provinces;
    SpinnerAdaptorProvencies spinnerAdaptorProvencies;
    //private string
    private String province;
    private String country;
    private DataListUser dataListUser;
    private  String currency;
    private Userpost userpost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__market);
        new ss().execute();
        currency="";
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        pickedBitmap = null;
        pickedBitmap2 = null;
        pickedBitmap3 = null;
        b1 = false;
        b2 = false;
        b3 = false;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.vsbsc);
        price = (EditText) findViewById(R.id.market_price_editText);
        status = (EditText) findViewById(R.id.market_status_editText);
        delivery = (EditText) findViewById(R.id.markrt_delivery_edittext);
        descrption = (EditText) findViewById(R.id.market_description_edittext);

        post = (Button) findViewById(R.id.button_market_post);
        upload = (Button) findViewById(R.id.button_marker_upload);
        image = (ImageView) findViewById(R.id.imageView_market_upload);

        upload2 = (Button) findViewById(R.id.button_marker_upload2);
        image2 = (ImageView) findViewById(R.id.imageView_market_upload2);

        upload3 = (Button) findViewById(R.id.button_marker_upload3);
        image3 = (ImageView) findViewById(R.id.imageView_market_upload3);

        br = (ProgressBar) findViewById(R.id.zxzx);
        br.setVisibility(View.VISIBLE);


        upload2.setVisibility(View.INVISIBLE);
        image2.setVisibility(View.INVISIBLE);
        upload3.setVisibility(View.INVISIBLE);
        image3.setVisibility(View.INVISIBLE);

        upload2.getLayoutParams().height = 0;
        image2.getLayoutParams().height = 0;
        upload3.getLayoutParams().height = 0;
        image3.getLayoutParams().height = 0;

        database.getReference("Users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataListUser = dataSnapshot.getValue(DataListUser.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (pickedBitmap == null) {
                    Toast.makeText(AddMarket.this, R.string.davadv, Toast.LENGTH_LONG).show();
                    return;
                }
                if (price.getText().toString().trim().equals("")) {
                    Toast.makeText(AddMarket.this, R.string.vadadva, Toast.LENGTH_LONG).show();
                    price.requestFocus();

                    return;
                }

                if (descrption.getText().toString().trim().equals("")) {
                    Toast.makeText(AddMarket.this, R.string.dbdfbd, Toast.LENGTH_LONG).show();
                    descrption.requestFocus();
                    return;
                }
                if (status.getText().toString().trim().equals("")) {
                    Toast.makeText(AddMarket.this, R.string.fbfbz, Toast.LENGTH_LONG).show();
                    status.requestFocus();
                    return;
                }
                if (delivery.getText().toString().trim().equals("")) {
                    Toast.makeText(AddMarket.this, R.string.dnbdzbz, Toast.LENGTH_LONG).show();
                    delivery.requestFocus();
                    return;
                }




                post.setVisibility(View.INVISIBLE);

                //upload image
               Random r = new Random();
                int f=r.nextInt(54545245);
                ByteArrayOutputStream baos;

                //upload imagee 3

                if (pickedBitmap3 != null) {
                    baos = new ByteArrayOutputStream();
                    pickedBitmap3.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] datax = baos.toByteArray();
                    StorageReference mountainsRef = mStorageRef.child("Market").child(currentUser.getUid()).child(f  + ".jpg");
                    UploadTask uploadTask = mountainsRef.putBytes(datax);


                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            downloadUrl3 = taskSnapshot.getDownloadUrl();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(AddMarket.this, "Trt Again", Toast.LENGTH_LONG).show();
                        }
                    });

                }

                //upload image 3
                if (pickedBitmap2 != null) {
                     f=r.nextInt(54545245);

                    baos = new ByteArrayOutputStream();
                    pickedBitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] datax = baos.toByteArray();
                    StorageReference mountainsRef = mStorageRef.child("Market").child(currentUser.getUid()).child(f  + ".jpg");
                    UploadTask uploadTask = mountainsRef.putBytes(datax);


                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            downloadUrl2 = taskSnapshot.getDownloadUrl();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(AddMarket.this, "Trt Again", Toast.LENGTH_LONG).show();
                        }
                    });

                }


                // upload image one
                f=r.nextInt(54545245);

                baos = new ByteArrayOutputStream();
                pickedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datax = baos.toByteArray();
                StorageReference mountainsRef = mStorageRef.child("Market").child(currentUser.getUid()).child(f  + ".jpg");
                UploadTask uploadTask = mountainsRef.putBytes(datax);


                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(AddMarket.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        downloadUrl = taskSnapshot.getDownloadUrl();
                        dataList ds;
                        String key = database.getReference("UserX").child(currentUser.getUid()).child("Market").push().getKey();
                        if (pickedBitmap2 == null)
                            downloadUrl2 = Uri.parse("no uri");
                                if (pickedBitmap3 == null)
                                    downloadUrl3 = Uri.parse("no uri");


                                //getting date
                                Date c = Calendar.getInstance().getTime();
                                userpost = new Userpost(key,country);
                                database.getReference("UserX").child(currentUser.getUid()).child("Market").child(key).setValue(userpost);


                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd' At 'HH:mm", Locale.ENGLISH);
                                String formattedDate = df.format(c);

                                ds = new com.dalelak.eyads.dalelakْْ.dataList(currentUser.getUid(),downloadUrl3.toString(), downloadUrl2.toString(), downloadUrl.toString(),
                                        descrption.getText().toString().trim(), dataListUser.getFirstName(), currency+" "+price.getText().toString().trim(),
                                        formattedDate, status.getText().toString().trim(),
                                        delivery.getText().toString().trim(),
                                        country, province, key + "",dataListUser.getImageUrlThumb());
                                DatabaseReference dd = database.getReference("Market").child(country);

                                dd.child(key).setValue(ds);

                                Intent I = new Intent(AddMarket.this, MainActivity.class);
                                startActivity(I);
                                finish();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(AddMarket.this, "Try Again", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

image.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        b1 = true;
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON).setMinCropWindowSize(500, 500)
                .setAspectRatio(1, 1)
                .start(AddMarket.this);
    }
});

image2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        b2 = true;

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON).setMinCropWindowSize(500, 500)
                .setAspectRatio(1, 1)
                .start(AddMarket.this);

    }
});

image3.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                  b3 = true;
                                  CropImage.activity()
                                          .setGuidelines(CropImageView.Guidelines.ON).setMinCropWindowSize(500, 500)
                                          .setAspectRatio(1, 1)
                                          .start(AddMarket.this);
                              }
                          }
);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1 = true;
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON).setMinCropWindowSize(500, 500)
                        .setAspectRatio(1, 1)
                        .start(AddMarket.this);


            }
        });

        upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b2 = true;
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON).setMinCropWindowSize(500, 500)
                        .setAspectRatio(1, 1)
                        .start(AddMarket.this);


            }


        });

        upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b3 = true;
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON).setMinCropWindowSize(500, 500)
                        .setAspectRatio(1, 1)
                        .start(AddMarket.this);


            }


        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();

                File thumbPath = new File(imageUri.getPath());

                label:
                try {

                    if (b1) {

                        pickedBitmap = new Compressor(this)
                                .setMaxWidth(300)
                                .setMaxHeight(300)
                                .setQuality(70)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                .compressToBitmap(thumbPath);
                        break label;
                    }
                    if (b2) {

                        pickedBitmap2 = new Compressor(this)
                                .setMaxWidth(300)
                                .setMaxHeight(300)
                                .setQuality(70)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                .compressToBitmap(thumbPath);
                        break label;
                    }

                    if (b3) {

                        pickedBitmap3 = new Compressor(this)
                                .setMaxWidth(300)
                                .setMaxHeight(300)
                                .setQuality(70)
                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                .compressToBitmap(thumbPath);
                        break label;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (pickedBitmap != null) {
                    image.setImageBitmap(pickedBitmap);
                    upload2.setVisibility(View.VISIBLE);
                    image2.setVisibility(View.VISIBLE);

                    upload2.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    image2.getLayoutParams().height = dpToPx(183);
                }

                if (pickedBitmap2 != null) {
                    image2.setImageBitmap(pickedBitmap2);
                    upload3.setVisibility(View.VISIBLE);
                    image3.setVisibility(View.VISIBLE);
                    upload3.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    image3.getLayoutParams().height = dpToPx(183);
                }

                if (pickedBitmap3 != null) {
                    image3.setImageBitmap(pickedBitmap3);
                }
                b1 = false;
                b2 = false;
                b3 = false;


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public int dpToPx(int dp) {
        float density = AddMarket.this.getResources()
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
            mSpinner = findViewById(R.id.spinner_market_country);
            final com.dalelak.eyads.dalelakْْ.SpinnerAdaptorCountries mAdaptor = new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorCountries(AddMarket.this, R.layout.spinner_ietm_countries, countries, images);
            mSpinner.setAdapter(mAdaptor);


            //spinner 2

            mSpinnerProvinces = findViewById(R.id.spinner_market_province);
            provinces = new String[]{"All", "Hamburg", "Brlen ", "Bremen", "oldenburg"};

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            currency="€";
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Groningen", "North Holland", "Overijssel", "Drenthe","Flevoland","Friesland","Gelderland","Limburg"
                                    ,"North Brabant","South Holland","Utrecht","Zeeland"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="€";
                            country =mSpinner.getSelectedItem().toString();

                            provinces = new String[]{"All", "Baden-Württemberg", "Bavaria", "Berlin", "Brandenburg","Bremen","Hamburg","Hesse","Lower Saxony"
                                    ,"Mecklenburg Vorpommern","North Rhine Westphalia","Rhineland Palatinate","Saarland","Saxony","Saxony-Anhalt","Schleswig-Holstein","Thuringia"};

                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="";

                            country =mSpinner.getSelectedItem().toString();

                            provinces = new String[]{"All", "Ångermanland", "Blekinge", "Bohuslän", "Dalarna","Dalsland","Gotland","Gästrikland","Halland"
                                    ,"Hälsingland","Härjedalen","Jämtland","Lappland","Medelpad","Norrbotten","Närke","Öland","Östergötland","Skåne","Småland",
                                    "Södermanland","Uppland","Värmland","Västmanland","Västerbotten","Västergötland"};

                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="";

                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Baranya", "Borsod-Abaúj-Zemplén", "Budapest", "Bács-Kiskun","Békés","Csongrád","Fejér","Győr-Moson-Sopron"
                                    ,"Hajdú-Bihar","Heves","Hungary","Jász-Nagykun-Szolnok","Komárom-Esztergom","Nógrád","Pest","Somogy","Szabolcs-Szatmár-Bereg","Tolna","Vas",
                                    "Veszprém","Zala"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="€";
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Burgenland", "Carinthia", "Lower Austria", "Salzburg","Styria","Tyrol","Upper Austria","Vienna"
                                    ,"Vorarlberg"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="";

                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "København By", "Københavns omegn", "Nordsjælland", "Bornholm","Østsjælland","Vest- og Sydsjælland","Fyn","Sydjylland"
                                    ,"Vestjylland","Østjylland","Nordjylland"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="€";
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Antwerpen", "Brussel"," Limburg", "Oost-Vlaanderen", "Vlaams-Brabant","West-Vlaanderen","Henegouwen","Luik","Luxemburg"
                                    ,"Namen","Waals-Brabant "};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="€";
                            country =mSpinner.getSelectedItem().toString();

                            provinces = new String[]{"All", "Auvergne-Rhône-Alpes", "Bourgogne-Franche-Comté", "Bretagne", "Centre-Val de Loire","Corse","Grand Est","Guadeloupe","Guyane"
                                    ,"Hauts-de-France","La Réunion","Martinique","Mayotte","Normandie","Nouvelle-Aquitaine","Occitanie","Pays de la Loire","Provence-Alpes-Côte d'Azur",
                                    "Île-de-France"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="";

                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Northern Norway", "Trøndelag", "Western Norway", "Southern Norway","Eastern Norway"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="";

                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Southern Finland", "Western Finland", "Eastern Finland", "Oulu","Lapland","Åland"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="";

                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Aargau", "Appenzell Ausserrhoden", "Appenzell Innerrhoden", "Basel-Landschaft","Basel-Stadt","Bern","Fribourg","Geneva"
                                    ,"Glarus","Grisons","Jura","Luzern","Neuchâtel","Nidwalden","Obwalden","Schaffhausen","Schwyz","Solothurn","St. Gallen",
                                    "Thurgau","Ticino","Uri","Valais","Vaud","Zug","Zürich"};

                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="";

                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "East Midlands", "East of England", "England", "London","North East","North West","South East","South West"
                                    ,"West Midlands","Yorkshire and the Humber"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="€";
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "A Coruña ", "Álava", "Alicante", "Almería","Asturias","Ávila","Badajoz","Balearic Islands"
                                    ,"Barcelona","Biscay","Burgos","Cáceres","Cádiz","Cantabria","Castellón","Ciudad Real","Córdoba","Cuenca","Gipuzkoa",
                                    "Girona","Granada","Guadalajara","Huelva","Huesca","Jaén","La Rioja","Las Palmas","León","Lleida ","Lugo","Madrid","Málaga","Murcia","Navarre","Ourense","Palencia",
                                    "Pontevedra","Salamanca","Santa Cruz de Tenerife","Segovia","Seville","Soria","Tarragona","Teruel","Toledo","Valencia","Valladolid","Zamora","Zaragoza"
                            };
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="€";
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Nord-Ovest", "Nord-Est", "Centro", "Sud","Isole"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="€";
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Attica", "Central Greece", "Central Macedonia", "Crete",
                                    "East Macedonia and Thrace", "Epirus", "Ionian Islands", "North Aegean", "Peloponnese","South Aegean", "Thessaly", "West Greece", "West Macedonia", "Mount Athos"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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
                            currency="";
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Blagoevgrad", "Burgas", "Dobrich", "Gabrovo","Haskovo","Kardzhali","Kyustendil","Lovech"
                                    ,"Montana","Pazardzhik","Pernik","Pleven","Plovdiv","Razgrad","Ruse","Shumen","Silistra","Sliven","Smolyan",
                                    "Sofia","Sofia City","Stara Zagora","Targovishte","Varna","Veliko Tarnovo","Vidin","Vratsa","Yambol"};
                            spinnerAdaptorProvencies=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(AddMarket.this, R.layout.spinner_ietm_provencies, provinces);
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

                Intent Is = new Intent(AddMarket.this,ADD_Main.class);
                finish();
                startActivity(Is);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent Is = new Intent(AddMarket.this,ADD_Main.class);
        finish();
        startActivity(Is);         }
}