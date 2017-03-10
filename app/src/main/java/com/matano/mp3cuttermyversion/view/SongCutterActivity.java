package com.matano.mp3cuttermyversion.view;

import android.app.Activity;
import android.content.ContentUris;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.matano.mp3cuttermyversion.model.Song;
import com.matano.mp3cuttermyversion.R;

import java.io.IOException;

/**
 * Created by matano on 8/3/17.
 */

public class SongCutterActivity extends Activity
{
    Song song;
    private final static String SONG_ID = "SONG_ID";
    private final static String SONG_TITLE = "SONG_TITLE";
    private final static String SONG_ARTIST = "SONG_ARTIST";
    final String TAG = SongCutterActivity.class.getSimpleName();
    TextView songTitleTextView;
    TextView songArtistTextView;
    ImageView songThumb;
    MediaPlayer mediaPlayer;
    long musicId;

    public SongCutterActivity(){}



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        Log.d(TAG, "in onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.song_cutter_activity);

        songTitleTextView = (TextView) findViewById(R.id.song_cutter_song_title);
        songArtistTextView = (TextView) findViewById(R.id.song_cutter_artist_textview);
        songThumb = (ImageView) findViewById(R.id.music_thumbnail_imageView);

        songTitleTextView.setText(
                getIntent().getStringExtra(SONG_TITLE));

        songArtistTextView.setText(
                getIntent().getStringExtra(SONG_ARTIST));

         musicId = getIntent().getLongExtra(SONG_ID, 0);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        playSong(musicId);
    }

    @Override
    protected void onStop()
    {
        mediaPlayer.release();
        mediaPlayer = null;
        super.onStop();
    }

    private void playSong(long id)
    {
        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                , id);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try
        {
            mediaPlayer.setDataSource(getApplicationContext(), contentUri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IOException e)
        {
            Log.d(TAG, "Error! ", e);
        }


    }


}
