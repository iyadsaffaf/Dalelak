package com.dalelak.eyads.dalelakْْ;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by eyads on 1/28/2018.
 */

public class SpinnerAdaptorProvencies extends ArrayAdapter<String> {


    private Context context;
    private String[]  countries;
    public SpinnerAdaptorProvencies(Context context, int textViewResourceId, String[]  provences) {
        super(context, textViewResourceId,provences);
        this.context=context;
        this.countries=provences;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.spinner_ietm_provencies, parent,
                false);
        TextView countriesT = (TextView) row.findViewById(R.id.Spinner_countries_text);

        countriesT.setText(countries[position]);
        return row;
    }

    @Override
    public void clear() {
        super.clear();
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.spinner_ietm_provencies, parent,
                false);
        TextView countriesT = (TextView) row.findViewById(R.id.Spinner_countries_text);

        countriesT.setText(countries[position]);
        return row;
    }


}
