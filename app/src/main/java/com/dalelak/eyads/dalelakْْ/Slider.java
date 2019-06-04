package com.dalelak.eyads.dalelakْْ;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



import com.dalelak.eyads.dalelakْْ.Adaptors.sliderAdaptort;
import java.util.ArrayList;

public class Slider extends AppCompatActivity {
private ViewPager mViewPager;
private ArrayList<String> links;
private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        links=new ArrayList<>();


        position = getIntent().getIntExtra("position",0);
     links = getIntent().getStringArrayListExtra("links");

         mViewPager = (ViewPager) findViewById(R.id.asd);



         sliderAdaptort adapterView = new sliderAdaptort(this,"x",links);





        mViewPager.setAdapter(adapterView);
        mViewPager.setCurrentItem(position);














    }
}
