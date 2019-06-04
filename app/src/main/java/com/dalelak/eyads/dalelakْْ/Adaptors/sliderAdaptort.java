package com.dalelak.eyads.dalelakْْ.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.dalelak.eyads.dalelakْْ.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by eyads on 2/8/2018.
 */

public class sliderAdaptort extends PagerAdapter {
   private Context c;
   private LayoutInflater layoutInflaters;
    private ImageView share;
    private ImageView imageView;

    private ArrayList<String> links;



    public sliderAdaptort(Context c,String x,ArrayList<String> links) {
        this.c = c;
        this.links =links;
    }

    @Override
    public int getCount() {
        return links.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;
    }


    @NonNull



    @Override
    public Object instantiateItem(@NonNull View container, final int position) {
        layoutInflaters = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);




        View view = layoutInflaters.inflate(R.layout.slidercostum, null);

         imageView = (ImageView) view.findViewById(R.id.imageView3);
        share = (ImageView) view.findViewById(R.id.imageView5);

  Picasso.with(c)
                .load(Uri.parse(links.get(position))).placeholder(R.drawable.whiteimage)
                .into(imageView);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable draw = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = draw.getBitmap();
                try {
                    File file = new File(c.getExternalCacheDir(),"logicchip.png");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    intent.setType("image/png");
                    c.startActivity(Intent.createChooser(intent, "Share image via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });



        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        // TODO Auto-generated method stub
        ((ViewPager) collection).removeView((View) view);
    }


}
