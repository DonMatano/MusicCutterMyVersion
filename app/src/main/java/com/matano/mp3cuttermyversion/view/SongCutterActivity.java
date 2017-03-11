package com.matano.mp3cuttermyversion.view;

import android.app.Activity;
import android.content.ContentUris;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.matano.mp3cuttermyversion.model.Song;
import com.matano.mp3cuttermyversion.R;

import java.io.IOException;

/**
 * Created by matano on 8/3/17.
 */

public class SongCutterActivity extends Activity implements MediaPlayer.OnPreparedListener,
        MediaController.MediaPlayerControl
{
    Song song;
    private final static String SONG_ID = "SONG_ID";
    private final static String SONG_TITLE = "SONG_TITLE";
    private final static String SONG_ARTIST = "SONG_ARTIST";
    String songTitle;
    String songArtist;
    int backPressedNumberOfTimes = 0;
    final String TAG = SongCutterActivity.class.getSimpleName();
    TextView songTitleTextView;
    TextView songArtistTextView;
    ImageView songThumb;
    MediaPlayer mediaPlayer;
    MediaController mediaController;
    Handler handler = new Handler();
    long musicId;

    public SongCutterActivity()
    {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        Log.d(TAG, "in onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.song_cutter_activity);

        songTitleTextView = (TextView) findViewById(R.id.song_cutter_song_title);
        songArtistTextView = (TextView) findViewById(R.id.song_cutter_artist_textview);

        if (savedInstanceState != null)
        {
            musicId = savedInstanceState.getLong(SONG_ID);
            songTitle = savedInstanceState.getString(SONG_TITLE);
            songArtist = savedInstanceState.getString(SONG_ARTIST);
        }
        else
        {
            musicId = getIntent().getLongExtra(SONG_ID, 0);
            songTitle = getIntent().getStringExtra(SONG_TITLE);
            songArtist = getIntent().getStringExtra(SONG_ARTIST);
        }

        songTitleTextView.setText(
                songTitle);

        songArtistTextView.setText(
                songArtist);

        musicId = getIntent().getLongExtra(SONG_ID, 0);
        playSong(musicId);


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstance)
    {

        savedInstance.putString(SONG_TITLE, songTitle);
        savedInstance.putString(SONG_ARTIST, songArtist);
        savedInstance.putLong(SONG_ID, musicId);

        super.onSaveInstanceState(savedInstance);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
       // setController();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

    }

    @Override
    protected void onStop()
    {
        mediaController.hide();
        mediaPlayer.release();
        mediaPlayer = null;
        super.onStop();
    }
    
    

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //the MediaController will hide after 3 seconds - tap the screen.
        mediaController.show(0);
        return false;
    }

    //MediaPlayer OnPreparedListener
    @Override
    public void onPrepared(MediaPlayer mp)
    {
        Log.d(TAG, "On Prepare Listener.");
        //set Controller
        mediaController = new MediaController(this);
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.songCutterConstraintLayout));


        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mediaController.setEnabled(true);
                mediaController.show(0);
            }
        });

    }





    //MediaControllerListeners
    @Override
    public void start()
    {
        mediaPlayer.start();

    }

    @Override
    public void pause()
    {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration()
    {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition()
    {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos)
    {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying()
    {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage()
    {
        return 0;
    }

    @Override
    public boolean canPause()
    {
        return true;
    }

    @Override
    public boolean canSeekBackward()
    {
        return true;
    }

    @Override
    public boolean canSeekForward()
    {
        return true;
    }

    @Override
    public int getAudioSessionId()
    {
        return 0;
    }

    private void playSong(long id)
    {
        Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                , id);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);

        try
        {
            mediaPlayer.setDataSource(getApplicationContext(), contentUri);
            mediaPlayer.prepare();
            //setController();
            mediaPlayer.start();
        }
        catch (IOException e)
        {
            Log.d(TAG, "Error! ", e);
        }


    }



    private void setController()
    {

    }

}