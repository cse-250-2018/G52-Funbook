package com.example.preity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import org.w3c.dom.Text;

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
        holder.txtShowCaption.setText(model.getCaption());
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
        holder.download_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(model.uri));
                request.setDescription("download");
                request.setTitle(model.getCaption());
                // in order for this if to run, you must use the android 3.2 to compile your app
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, model.getCaption()+".mp4");

                // get download service and enqueue file
                DownloadManager manager = (DownloadManager) holder.itemView.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);
                return true;
            }
        });
    /*    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(model.uri));
                request.setDescription("download");
                request.setTitle(model.getCaption());
                // in order for this if to run, you must use the android 3.2 to compile your app
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, model.getCaption()+".mp4");

                // get download service and enqueue file
                DownloadManager manager = (DownloadManager) holder.itemView.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);
                return true;
            }
        });
        */

    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row,parent,false);

        return new VideoHolder(v);
    }

    class  VideoHolder extends RecyclerView.ViewHolder{
        StyledPlayerView playerView;
        TextView txtShowCaption;
        ImageView download_btn;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            playerView = itemView.findViewById(R.id.playerView);
            txtShowCaption = itemView.findViewById(R.id.txtShowCaption);

            download_btn = itemView.findViewById(R.id.download_btn);

        }


    }


}
