package com.matano.mp3cuttermyversion;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.matano.mp3cuttermyversion.model.Song;
import com.matano.mp3cuttermyversion.view.SongCutterActivity;
import com.matano.mp3cuttermyversion.view.SongListFragment;

public class MainActivity extends AppCompatActivity implements SongListFragment.songClickedListener,
    TabLayout.OnTabSelectedListener
{


    FragmentManager fragmentManager;
    TabLayout tabLayout;
    private ViewPager viewPager;
    final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "in onCreate");
        super.onCreate(savedInstanceState);

        askPermission();

        setContentView(R.layout.activity_main);


        //Add toolbar to Activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();

        //TODO set this toolbar visible
       // getSupportActionBar().hide();



       initialzeTabLayout();
    }

    @Override
    protected void onStart()
    {
        Log.d(TAG, "in onStart");
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        Log.d(TAG, "in onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        Log.d(TAG, "in onDestroy");
        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
        Log.d(TAG, "in onBackPressed");
        if (fragmentManager.getBackStackEntryCount() > 0)
        {
            fragmentManager.popBackStack();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause()
    {
        Log.d(TAG, "in onPause");
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        Log.d(TAG, "in onResume");
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState)
    {
        Log.d(TAG, "in onCreate");
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onRestart()
    {
        Log.d(TAG, "in onRestart");
        super.onRestart();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab)
    {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab)
    {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab)
    {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
            {
                //If request is cancelled  the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                else {
                    finishAffinity();
                }
            }
        }
    }

    public void askPermission()
    {
        //Check if app has permission to read external storage
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            //Request for permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);


        }
    }
     public void initialzeTabLayout()
     {
         tabLayout = (TabLayout) findViewById(R.id.tabLayout);

         //Adding the tabs using addTab() method

         tabLayout.addTab(tabLayout.newTab()
                 .setText(getText(R.string.song_list_tab_title)));

         tabLayout.addTab(tabLayout.newTab()
                 .setText(getText(R.string.trimmed_songs_tab_title)));

         tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

         //Adding onTabSelectedListener to swipe views
         tabLayout.addOnTabSelectedListener(this);

         viewPager = (ViewPager) findViewById(R.id.pager);

         Pager tabPagerAdapter = new Pager(getSupportFragmentManager(),
                 tabLayout.getTabCount(), this);

         //Adding adapter to pager
         viewPager.setAdapter(tabPagerAdapter);
         viewPager.addOnPageChangeListener(
                 new TabLayout.TabLayoutOnPageChangeListener(tabLayout)
         );
     }

     public void startSongCutterActivity(Song song)
     {
         Intent intentSongCutterActivity = new Intent(this, SongCutterActivity.class);
         intentSongCutterActivity.putExtra("SONG_ID", song.getId());
         intentSongCutterActivity.putExtra("SONG_TITLE", song.getTitle());
         intentSongCutterActivity.putExtra("SONG_ARTIST", song.getArtist());

         startActivity(intentSongCutterActivity);
     }

    @Override
    public void songClicked(Song song)
    {
        startSongCutterActivity(song);
   }
}
