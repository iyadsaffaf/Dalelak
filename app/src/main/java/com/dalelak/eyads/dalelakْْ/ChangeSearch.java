package com.dalelak.eyads.dalelakْْ;


import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class ChangeSearch extends AppCompatActivity {
    //spiner1
    private Spinner mSpinner;
    private String[]  countries;
    private Integer[] images;

    //spiner2
    private Spinner mSpinnerProvinces;
    private String[]  provinces;

    private Button button;
    private String country;
    private   String province;
   private com.dalelak.eyads.dalelakْْ.SpinnerAdaptorCountries mAdaptor;
    private com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies mAdaptorprov;
    private String selectedstring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarey);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        selectedstring = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("province", "All");
        //spinner 1
        countries= new String[]{"Netherlands","Germany","Sweden","Hungary","Austria","Denmark","Belgium","France","Norway","Finland","Switzerland",
                "United Kingdom","Spain","Italy","Greece","Bulgaria"};

        images= new Integer[]{R.drawable.flag_netherlands,
                R.drawable.germanflag,R.drawable.swedenflag270x270,R.drawable.hungary,
                R.drawable.austria,R.drawable.denmark,
                R.drawable.belgium,R.drawable.france,R.drawable.norway,R.drawable.finland300x300,R.drawable.switherlands,
                R.drawable.unitedkingdom,R.drawable.spanish300x300,R.drawable.italy,R.drawable.greece,R.drawable.bulgaria};
        mSpinner= findViewById(R.id.spinnerchangecountry) ;
        mSpinnerProvinces = findViewById(R.id.spinnerchangeprovence);
        button= findViewById(R.id.changbutton);

        new ChangeSearch.ss().execute();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("MYLABEL", country).apply();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("province", province).apply();

                Intent I = new Intent(ChangeSearch.this,MainActivity.class);
                startActivity(I);
                finish();
            }
        });


    }

    public class ss extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {


              mAdaptor = new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorCountries(ChangeSearch.this, R.layout.spinner_ietm_countries, countries, images);
            mSpinner.setAdapter(mAdaptor);



            //spinner 2

            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 0:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Groningen", "North Holland", "Overijssel", "Drenthe","Flevoland","Friesland","Gelderland","Limburg"
                                    ,"North Brabant","South Holland","Utrecht","Zeeland"};
                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            province=mSpinnerProvinces.getSelectedItem().toString();
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
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

                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
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

                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            province=mSpinnerProvinces.getSelectedItem().toString();
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
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
                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            province=mSpinnerProvinces.getSelectedItem().toString();
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
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
                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            province=mSpinnerProvinces.getSelectedItem().toString();
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
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
                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
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
                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();

                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });                            break;
                        case 7:
                            country =mSpinner.getSelectedItem().toString();

                            provinces = new String[]{"All", "Auvergne-Rhône-Alpes", "Bourgogne-Franche-Comté", "Bretagne", "Centre-Val de Loire","Corse","Grand Est","Guadeloupe","Guyane"
                                    ,"Hauts-de-France","La Réunion","Martinique","Mayotte","Normandie","Nouvelle-Aquitaine","Occitanie","Pays de la Loire","Provence-Alpes-Côte d'Azur",
                                    "Île-de-France"};
                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();

                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });                            break;
                        case 8:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Northern Norway", "Trøndelag", "Western Norway", "Southern Norway","Eastern Norway"};
                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();

                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });                            break;
                        case 9:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Southern Finland", "Western Finland", "Eastern Finland", "Oulu","Lapland","Åland"};
                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();

                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });                            break;

                        case 10:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Aargau", "Appenzell Ausserrhoden", "Appenzell Innerrhoden", "Basel-Landschaft","Basel-Stadt","Bern","Fribourg","Geneva"
                                    ,"Glarus","Grisons","Jura","Luzern","Neuchâtel","Nidwalden","Obwalden","Schaffhausen","Schwyz","Solothurn","St. Gallen",
                                    "Thurgau","Ticino","Uri","Valais","Vaud","Zug","Zürich"};

                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();

                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });                            break;


                        case 11:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "East Midlands", "East of England", "England", "London","North East","North West","South East","South West"
                                    ,"West Midlands","Yorkshire and the Humber"};
                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();

                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });                            break;

                        case 12:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "A Coruña ", "Álava", "Alicante", "Almería","Asturias","Ávila","Badajoz","Balearic Islands"
                                    ,"Barcelona","Biscay","Burgos","Cáceres","Cádiz","Cantabria","Castellón","Ciudad Real","Córdoba","Cuenca","Gipuzkoa",
                                    "Girona","Granada","Guadalajara","Huelva","Huesca","Jaén","La Rioja","Las Palmas","León","Lleida ","Lugo","Madrid","Málaga","Murcia","Navarre","Ourense","Palencia",
                                    "Pontevedra","Salamanca","Santa Cruz de Tenerife","Segovia","Seville","Soria","Tarragona","Teruel","Toledo","Valencia","Valladolid","Zamora","Zaragoza"
                            };
                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();

                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });                            break;

                        case 13:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Nord-Ovest", "Nord-Est", "Centro", "Sud","Isole"};
                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();

                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });                            break;

                        case 14:
                            country =mSpinner.getSelectedItem().toString();
                            provinces = new String[]{"All", "Attica", "Central Greece", "Central Macedonia", "Crete",
                                    "East Macedonia and Thrace", "Epirus", "Ionian Islands", "North Aegean", "Peloponnese","South Aegean", "Thessaly", "West Greece", "West Macedonia", "Mount Athos"};
                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
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
                            mAdaptorprov=new com.dalelak.eyads.dalelakْْ.SpinnerAdaptorProvencies(ChangeSearch.this, R.layout.spinner_ietm_provencies, provinces);
                            mSpinnerProvinces.setAdapter(mAdaptorprov);
                            mSpinnerProvinces.setSelection(mAdaptorprov.getPosition(selectedstring));
                            mSpinnerProvinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    province=mSpinnerProvinces.getSelectedItem().toString();

                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });                            break;

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
    protected void onDestroy() {
        mAdaptor=null;
        mSpinner=null;
        mSpinnerProvinces=null;
        mAdaptorprov=null;
        images=null;
        countries=null;
        super.onDestroy();






    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent I = new Intent(ChangeSearch.this,MainActivity.class);
        startActivity(I);
        finish();
    }
}
