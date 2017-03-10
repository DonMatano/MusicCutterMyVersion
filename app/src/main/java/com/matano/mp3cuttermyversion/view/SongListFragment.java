package com.matano.mp3cuttermyversion.view;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.matano.mp3cuttermyversion.model.Song;
import com.matano.mp3cuttermyversion.R;
import com.matano.mp3cuttermyversion.SongsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by matano on 6/3/17.
 */

public class SongListFragment extends Fragment implements SongsAdapter.onSongClickedListener
{
    RecyclerView songListRecyclerView;
    RecyclerView.LayoutManager songListRecyclerViewLayoutManager;
    SongsAdapter songsAdapter;
    ArrayList<Song> songsArrayList;
    //SongCutterActivity songCutterFragment;

    songClickedListener songClickedListener;
    private final String TAG = SongListFragment.class.getSimpleName();




    public SongListFragment()
    {
        
    }

    public static SongListFragment newInstance()
    {
        
        Bundle args = new Bundle();
        
        SongListFragment fragment = new SongListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Log.d(TAG, "In onCreateView");
        //Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.songlist_fragment, container, false);

        songListRecyclerView = (RecyclerView) rootview.findViewById(R.id.songlist_recyclerview);

        songListRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        songListRecyclerView.setLayoutManager(songListRecyclerViewLayoutManager);

        songsAdapter = new SongsAdapter(songsArrayList);
        songsAdapter.setSongClickedListener(this);
        songListRecyclerView.setAdapter(songsAdapter);

        return rootview;
    }

    @Override
    public void onPause()
    {
        Log.d(TAG, "In onPause");
        super.onPause();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        Log.d(TAG, "In Create");
        super.onCreate(savedInstanceState);
        songsArrayList = new ArrayList<>();

        getSongList();

        Collections.sort(songsArrayList, new Comparator<Song>()
        {
            @Override
            public int compare(Song o1, Song o2)
            {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
    }

    @Override
    public void onStart()
    {
        Log.d(TAG, "In onStart");
        super.onStart();
    }

    @Override
    public void onResume()
    {
        Log.d(TAG, "In onResume");
        super.onResume();
    }

    @Override
    public void onStop()
    {
        Log.d(TAG, "In onStop");
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        Log.d(TAG, "In onDestroy");
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context)
    {
        Log.d(TAG, "In onAttach");
        super.onAttach(context);
    }

    public void getSongList()
    {
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);


        if (cursor == null)
        {
            //query failed
            Toast.makeText(getContext(), "Failed to read storage", Toast.LENGTH_SHORT).show();
        }
        else if (!cursor.moveToFirst())
        {
            // no media found
            Toast.makeText(getContext(), "No audio found on the device", Toast.LENGTH_SHORT).show();
            cursor.close();
        }
        else
        {
            //get columns
            int titleColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Media.TITLE);
            int artistColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Media.ARTIST);
            int idColumn = cursor.getColumnIndex
                    (MediaStore.Audio.Media._ID);

            do
            {
                long thisId = cursor.getLong(idColumn);
                String thisTitle = cursor.getString(titleColumn);
                String thisArtist = cursor.getString(artistColumn);

                songsArrayList.add(new Song(thisId, thisTitle, thisArtist));
            }
            while (cursor.moveToNext());
            cursor.close();
        }


    }

    public void setSongClickedListener(SongListFragment.songClickedListener songClickedListener)
    {
        this.songClickedListener = songClickedListener;
    }

    public interface songClickedListener
    {
        void songClicked(Song song);
    }

    @Override
    public void onSongClicked(View view, int position)
    {
//        //start song cutter fragment
//           songCutterFragment = SongCutterActivity.
//                newInstance(songsArrayList.get(position));
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//        //Replace songListFragment with songCutterFragment and add to back stack
//        transaction.remove(this);
//        transaction.replace(R.id.activity_frame, songCutterFragment);
//        transaction.addToBackStack(null);
//
//        //Commit the transaction
//         transaction.commit();

        showSong(songsArrayList.get(position));

    }

    public void showSong(Song song)
    {
        songClickedListener.songClicked(song);
    }


























}
