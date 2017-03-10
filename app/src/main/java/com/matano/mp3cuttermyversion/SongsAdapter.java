package com.matano.mp3cuttermyversion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matano.mp3cuttermyversion.model.Song;

import java.util.ArrayList;

/**
 * Created by matano on 6/3/17.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.ViewHolder>
{
    private ArrayList<Song> songsArrayList;

    final String Tag = SongsAdapter.class.getSimpleName();
    onSongClickedListener songClickedListener ;


    //When Adapter is created we hand it an ArrayList of Songs
    public SongsAdapter(ArrayList<Song> songsArrayList)
    {
        this.songsArrayList = songsArrayList;
    }

    public void setSongClickedListener(onSongClickedListener songClickedListener)
    {
        this.songClickedListener = songClickedListener;
    }

    @Override
    public SongsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SongsAdapter.ViewHolder holder, int position)
    {

        holder.songTitle.setText(songsArrayList.get(position).getTitle());
        holder.songArtist.setText(songsArrayList.get(position).getArtist());



    }

    @Override
    public int getItemCount()
    {
        return songsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView songTitle;
        TextView songArtist;

        public ViewHolder(final View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (songClickedListener != null)
                    {
                        songClickedListener.onSongClicked(v, getAdapterPosition());
                    }
                }
            });




            songTitle = (TextView) itemView.findViewById(R.id.songTitle_textView);
            songArtist = (TextView) itemView.findViewById(R.id.song_Artist_textView);

        }
    }

    public interface onSongClickedListener
    {
       void onSongClicked(View view, int position);
    }
}
