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

import com.dalelak.eyads.dalelakْْ.Adaptors.eventAdaptor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class events extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private ProgressBar pb;
    private String country;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private eventAdaptor mEventAdaptor;
    private List<DataEvent> mDataEvents;


    public events() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View r=     inflater.inflate(R.layout.fragment_events, container, false);
        setCountry(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("MYLABEL", "Netherlands"));

        setDatabase(FirebaseDatabase.getInstance());

        setRecyclerView((RecyclerView) r.findViewById(R.id.event_recycleview));
        mDataEvents= new ArrayList<>();
        setmLayoutManager(new LinearLayoutManager(getActivity()));
        getmLayoutManager().setReverseLayout(true);
        getmLayoutManager().setStackFromEnd(true);
        getRecyclerView().setLayoutManager(getmLayoutManager());
        setmEventAdaptor(new eventAdaptor(getContext(), getmDataEvents(), getCountry()));
        getRecyclerView().setAdapter(getmEventAdaptor());
        new events.download().execute();

        setmSwipeRefreshLayout((SwipeRefreshLayout) r.findViewById(R.id.event_refresh_layout));
        getmSwipeRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new events.download().execute();
            }
        });

return r;


    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void setDatabase(FirebaseDatabase database) {
        this.database = database;
    }

    public ProgressBar getPb() {
        return pb;
    }

    public void setPb(ProgressBar pb) {
        this.pb = pb;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public SwipeRefreshLayout getmSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void setmSwipeRefreshLayout(SwipeRefreshLayout mSwipeRefreshLayout) {
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }

    public LinearLayoutManager getmLayoutManager() {
        return mLayoutManager;
    }

    public void setmLayoutManager(LinearLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
    }

    public eventAdaptor getmEventAdaptor() {
        return mEventAdaptor;
    }

    public void setmEventAdaptor(eventAdaptor mEventAdaptor) {
        this.mEventAdaptor = mEventAdaptor;
    }

    public List<DataEvent> getmDataEvents() {
        return mDataEvents;
    }

    public void setmDataEvents(List<DataEvent> mDataEvents) {
        this.mDataEvents = mDataEvents;
    }

    public  class download extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            getDatabase().getReference("event").child(getCountry()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mDataEvents = new ArrayList<>();

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        try{
                        if (data.getValue(DataEvent.class) != null) {
                            DataEvent d = data.getValue(DataEvent.class);
                            getmDataEvents().add(d);

                        }}catch (com.google.firebase.database.DatabaseException e){
                            continue;

                        }
                    }



                    setmEventAdaptor(new eventAdaptor(getContext(), getmDataEvents(), getCountry()));

                    getRecyclerView().setAdapter(getmEventAdaptor());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });





            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            getmSwipeRefreshLayout().setRefreshing(false);



        }


    }
}
