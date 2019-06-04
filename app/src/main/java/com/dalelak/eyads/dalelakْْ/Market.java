package com.dalelak.eyads.dalelakْْ;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


public class Market extends Fragment {
    private FirebaseAuth mAuth;
    private List<dataList> contracts;
    private marketAdaptor marketAdaptor;
    private RecyclerView rvvv;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private ProgressBar pb;
    private String country;
    private   SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;



    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        country= PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("MYLABEL", "Netherlands");


         View r = inflater.inflate(R.layout.fragment_market, container, false);
        contracts = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        rvvv = (RecyclerView) r.findViewById(R.id.rvmarket);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        rvvv.setLayoutManager(mLayoutManager);
        marketAdaptor = new marketAdaptor(contracts, getActivity(),country);

                   rvvv.setAdapter(marketAdaptor);


//
        pb =(ProgressBar) r.findViewById(R.id.progressBar2);
        pb.setVisibility(View.INVISIBLE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) r.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new download().execute(contracts);

            }
        });
// run a background job and once complete
        // get data from fire base


   new download().execute(contracts);


        return r;
    }

    public  class download extends AsyncTask <List<dataList>,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pb.setVisibility(View.INVISIBLE);
            mSwipeRefreshLayout.setRefreshing(false);






        }

        @Override
        protected Void doInBackground(List<dataList>[] lists) {
            database.getReference("Market").child(country).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    contracts = new ArrayList<>();

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        try {
                            if (data.getValue(dataList.class) != null) {
                                dataList d = data.getValue(dataList.class);
                                contracts.add(d);
                            }
                        }catch (com.google.firebase.database.DatabaseException e){
                           continue;

                        }

                    }
                    marketAdaptor.notifyDataSetChanged();



                     marketAdaptor = new marketAdaptor(contracts, getActivity(),country);

                    rvvv.setAdapter(marketAdaptor);


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
        rvvv.setAdapter(null);
        super.onDestroyView();
    }

}