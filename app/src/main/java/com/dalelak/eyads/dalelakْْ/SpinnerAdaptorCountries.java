package com.dalelak.eyads.dalelakْْ;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by eyads on 1/28/2018.
 */

public class SpinnerAdaptorCountries extends ArrayAdapter<String> {


    private Context context;
    private String[]  countries;
    private Integer[] images;
    private TextView textView;
   private ImageView imageView;

    public SpinnerAdaptorCountries(Context context, int textViewResourceId, String[]  countries,Integer[] images) {
        super(context, textViewResourceId,countries);
        this.context=context;
        this.countries=countries;
        this.images=images;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.spinner_ietm_countries, parent, false);
         imageView = (ImageView) row.findViewById(R.id.Spinner_countries_imageView);
        imageView.setImageResource(images[position]);
         textView = (TextView) row.findViewById(R.id.hjk);
        textView.setText(countries[position]);

        return row;
    }


    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.spinner_ietm_countries, parent,
                false);
         textView = (TextView) row.findViewById(R.id.hjk);
         imageView = (ImageView) row.findViewById(R.id.Spinner_countries_imageView);


        imageView.setImageResource(images[position]);
        textView.setText(countries[position]);
        return row;
    }


}
