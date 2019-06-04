package com.dalelak.eyads.dalelakْْ;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by eyads on 1/28/2018.
 */

public class pageAdaptor extends FragmentStatePagerAdapter {
    private Context context;
    public pageAdaptor(FragmentManager fm, Context context) {

        super(fm);
        this.context=context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence k;


        switch (position) {

            case 0:
               k = context.getString(R.string.Market);
                return k;
            case 1:
                k = context.getString(R.string.Events);
                return k;
            case 2:
                k = context.getString(R.string.Guide);
                return k;
            case 3:
                k = context.getString(R.string.Questions);
                return k;
            default:
                return super.getPageTitle(position);

        }
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Market();
            case 1: return new events();
            case 2: return new guide();
            case 3: return new Info();
            default:        return null;

        }
    }
}
