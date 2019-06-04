package com.dalelak.eyads.dalelakْْ;



import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import  com.dalelak.eyads.dalelakْْ.Adaptors.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.facebook.FacebookSdk.getApplicationContext;


public class guide extends Fragment {
   private List<datalistG> contracts;

    private RecyclerView rvvv;
    private FirebaseDatabase database;
    private ProgressBar pb;
    private String country;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private guideAdaptor mGhuidAdaptor;
    private TextView popuptext;
    private String selectedstring;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View r=     inflater.inflate(R.layout.fragment_guide, container, false);
        country= PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("MYLABEL", "Netherlands");

        database = FirebaseDatabase.getInstance();

        contracts = new ArrayList<>();

         rvvv = (RecyclerView) r.findViewById(R.id.guide_recycleview);

         popuptext=(TextView) r.findViewById(R.id.pouptext);
         selectedstring = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("province", "All");

        popuptext.setText(selectedstring);
        popuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ChangeSearch.class);
                startActivity(i);
                getActivity().finish();

            }
        });

         mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rvvv.setLayoutManager( mLayoutManager);
         mGhuidAdaptor = new guideAdaptor(contracts,getActivity(),country);
        rvvv.setAdapter(mGhuidAdaptor);
        new download().execute(contracts);
        mSwipeRefreshLayout = (SwipeRefreshLayout) r.findViewById(R.id.ghide_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new download().execute(contracts);

            }
        });


        return r;
    }
    public  class download extends AsyncTask<List<datalistG>,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mSwipeRefreshLayout.setRefreshing(false);



        }

        @Override
        protected Void doInBackground(List<datalistG>[] lists) {
            database.getReference("Guide").child(country).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    contracts = new ArrayList<>();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        try {
                        if (data.getValue(datalistG.class) != null) {

                            datalistG d = data.getValue(datalistG.class);
                            if(d.getProvince().equals(selectedstring)||selectedstring.equals("All")){
                            contracts.add(d);}

                        }  }catch (com.google.firebase.database.DatabaseException e){
                            continue;

                        }

                    }



                     mGhuidAdaptor = new guideAdaptor(contracts,getActivity(),country);

                    rvvv.setAdapter(mGhuidAdaptor);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





            return null;
        }
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
