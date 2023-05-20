package ahmad.io.youtubefavs.adapter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ahmad.io.youtubefavs.R;
import ahmad.io.youtubefavs.pojos.YouTubeVideo;

public class YouTubeVideoAdapter extends RecyclerView.Adapter<YouTubeVideoAdapter.YouTubeVideoViewHolder> {

    private final List<YouTubeVideo> youTubeVideos;

    public static class YouTubeVideoViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvDescription, tvUrl;
        public Button btnWatch;

        public YouTubeVideoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvUrl = itemView.findViewById(R.id.tvUrl);
            btnWatch = itemView.findViewById(R.id.btnWatch);
        }
    }

    // Constructor that sets the list of YouTube videos
    public YouTubeVideoAdapter(List<YouTubeVideo> youTubeVideos) {
        this.youTubeVideos = youTubeVideos;
    }

    @NonNull
    @Override
    public YouTubeVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.youtube_video_item, parent, false);

        return new YouTubeVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YouTubeVideoViewHolder holder, int position) {
        // Get the YouTube video at the given position
        YouTubeVideo youTubeVideo = youTubeVideos.get(position);

        // Set the YouTube video information in the item view
        holder.tvTitle.setText(youTubeVideo.getTitle());
        holder.tvDescription.setText(youTubeVideo.getDescription());
        holder.tvUrl.setText(youTubeVideo.getUrl());
        holder.btnWatch.setOnClickListener(v -> {

            // Get the URL of the YouTube video
            String url = youTubeVideo.getUrl();

            // Create an intent to watch the YouTube video
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            // Start the activity
            try {
                v.getContext().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Display a toast message if the YouTube app is not installed
                Toast.makeText(v.getContext(), "The YouTube app is not installed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return youTubeVideos.size();
    }

}
