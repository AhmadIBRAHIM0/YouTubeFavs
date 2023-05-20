package ahmad.io.youtubefavs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ahmad.io.youtubefavs.R;
import ahmad.io.youtubefavs.pojos.YouTubeVideo;

public class YouTubeVideoAdapter extends RecyclerView.Adapter<YouTubeVideoAdapter.YouTubeVideoViewHolder> {

    private final List<YouTubeVideo> youTubeVideos;

    public static class YouTubeVideoViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvDescription;

        public YouTubeVideoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
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
    }

    @Override
    public int getItemCount() {
        return youTubeVideos.size();
    }

}
