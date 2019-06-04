package com.dalelak.eyads.dalelakْْ;


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

import com.dalelak.eyads.dalelakْْ.Adaptors.infoAdaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Info extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private ProgressBar pb;
    private String country;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private infoAdaptor mInfoAdaptor;


    private List<DataListInfo>  dataListInfos;

    public Info() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View r=     inflater.inflate(R.layout.fragment_work, container, false);
        country= PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("MYLABEL", "Netherlands");

        database = FirebaseDatabase.getInstance();

        dataListInfos= new ArrayList<>();
        recyclerView = (RecyclerView) r.findViewById(R.id.info_recycleview);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager( mLayoutManager);

        new Info.download().execute();

        mSwipeRefreshLayout = (SwipeRefreshLayout) r.findViewById(R.id.info_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
new Info.download().execute();
            }
        });


        return r;



    }
    public  class download extends AsyncTask<List<DataListInfo>,Void,Void> {

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
        protected Void doInBackground(List<DataListInfo>[] lists) {
            dataListInfos= new ArrayList<>();
            database.getReference("info").child(country).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        try {
                        if (data.getValue(DataListInfo.class) != null) {
                            DataListInfo d = data.getValue(DataListInfo.class);
                            dataListInfos.add(d);

                        }}
                        catch (com.google.firebase.database.DatabaseException e){
                            continue;

                        }


                    }



                    mInfoAdaptor = new infoAdaptor(getActivity(),dataListInfos ,country);

                    recyclerView.setAdapter(mInfoAdaptor);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





            return null;
        }
    }
}
