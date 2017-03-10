package com.matano.mp3cuttermyversion;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.matano.mp3cuttermyversion.view.SongListFragment;
import com.matano.mp3cuttermyversion.view.TrimmedSongFragment;

/**
 * Created by matano on 6/3/17.
 */

public class Pager extends FragmentPagerAdapter
{
    //integer to count number of tabs
    int tabCount;
    Context context;

    //constructor

    public Pager(FragmentManager fm, int tabCount, Context context)
    {
        super(fm);
        this.tabCount = tabCount;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        //Return the current tabs
        switch (position)
        {
            case 0:
                SongListFragment fragment = SongListFragment.newInstance();
                fragment.setSongClickedListener((MainActivity)context);

                return fragment;

            case 1:
                return TrimmedSongFragment.newInstance();

            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return tabCount;
    }
}
