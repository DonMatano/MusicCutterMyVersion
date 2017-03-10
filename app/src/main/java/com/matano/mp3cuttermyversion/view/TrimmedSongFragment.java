package com.matano.mp3cuttermyversion.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by matano on 6/3/17.
 */

public class TrimmedSongFragment extends Fragment
{
    public TrimmedSongFragment()
    {
    }

    public static TrimmedSongFragment newInstance()
    {

        Bundle args = new Bundle();

        TrimmedSongFragment fragment = new TrimmedSongFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
