package com.example.gezim.birthdayapp;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by Gezim on 5/10/2018.
 */

public class tabsPager extends FragmentStatePagerAdapter {

    String[] titles=new String[]{"Sot","Regjistro"};

    public tabsPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                BlankFragment blankFragment=new BlankFragment();
                return  blankFragment;


            case 1:
                Blank3Fragment blank3Fragment=new Blank3Fragment();
                return blank3Fragment;




        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
