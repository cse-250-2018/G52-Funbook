package com.example.preity;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;

public class VideoAdapter extends FirestoreRecyclerAdapter<VideoFile, VideoAdapter.VideoHolder>{

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public VideoAdapter(@NonNull FirestoreRecyclerOptions<VideoFile> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull VideoHolder holder, int position, @NonNull VideoFile model) {
        ExoPlayer player = new ExoPlayer.Builder(holder.itemView.getContext()).build();
        holder.playerView.setPlayer(player);
        // Build the media item.
        Uri videoUri =  Uri.parse(model.uri);
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        // Set the media item to be played.
        player.setMediaItem(mediaItem);
        // Prepare the player.
        player.prepare();
        // Start the playback.
        //player.play();

    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row,parent,false);

        return new VideoHolder(v);
    }

    class  VideoHolder extends RecyclerView.ViewHolder{
        StyledPlayerView playerView;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            playerView = itemView.findViewById(R.id.playerView);
        }
    }


}
