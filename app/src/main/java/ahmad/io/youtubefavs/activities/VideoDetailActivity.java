package ahmad.io.youtubefavs.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ahmad.io.youtubefavs.R;
import ahmad.io.youtubefavs.data.YouTubeVideoDatabase;
import ahmad.io.youtubefavs.pojos.YouTubeVideo;

public class VideoDetailActivity extends AppCompatActivity {

    private Context context;
    private Long videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        setTitle("Video Details");

        // Retrieve the data from the intent extras
        Intent intent = getIntent();
        context = getApplicationContext();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String url = intent.getStringExtra("url");
        String category = intent.getStringExtra("category");
        this.videoId = intent.getLongExtra("videoId", 0);

        // Display the data in the appropriate views
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvUrl = findViewById(R.id.tvUrl);
        TextView tvCategory = findViewById(R.id.tvCategory);
        Button btnWatch = findViewById(R.id.btnWatch);
        ImageView ivFavorite = findViewById(R.id.ivFavorite);

        tvTitle.setText(title);
        tvDescription.setText(description);
        tvUrl.setText(url);
        tvCategory.setText(category);

        if (intent.getBooleanExtra("isFavorite", false)) {
            ivFavorite.setImageResource(R.drawable.ic_star);
        } else {
            ivFavorite.setImageResource(R.drawable.ic_star_border);
        }

        ivFavorite.setOnClickListener(v -> {
            // Toggle the favorite status
            boolean isFavorite = toggleFavoriteStatus();

            // Update the star icon
            int starIconRes = isFavorite ? R.drawable.ic_star : R.drawable.ic_star_border;
            ivFavorite.setImageResource(starIconRes);
        });

        btnWatch.setOnClickListener(v -> {

            // Create an intent to watch the YouTube video
            Intent watchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            // Start the activity
            try {
                context.startActivity(watchIntent);
            } catch (ActivityNotFoundException e) {
                // Display a toast message if the YouTube app is not installed
                Toast.makeText(context, "The YouTube app is not installed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.delete_youtube_menu) {

            deleteVideo();
            return true;

        } else if (item.getItemId() == R.id.edit_youtube_menu) {

            Intent intent = new Intent(context, UpdateYouTubeActivity.class);
            intent.putExtra("videoId", videoId);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Retrieve the updated data from the database
        YouTubeVideoDatabase db = YouTubeVideoDatabase.getDb(getApplicationContext());
        YouTubeVideo updatedVideo = db.youTubeVideoDao().find(videoId);

        if (updatedVideo != null) {
            // Update the views with the updated data
            TextView tvTitle = findViewById(R.id.tvTitle);
            TextView tvDescription = findViewById(R.id.tvDescription);
            TextView tvUrl = findViewById(R.id.tvUrl);
            TextView tvCategory = findViewById(R.id.tvCategory);

            tvTitle.setText(updatedVideo.getTitle());
            tvDescription.setText(updatedVideo.getDescription());
            tvUrl.setText(updatedVideo.getUrl());
            tvCategory.setText(updatedVideo.getCategory());
        }
    }

    private void deleteVideo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this video?");
        builder.setPositiveButton("Delete", (dialog, which) -> {
            // Delete the video from the database
            YouTubeVideoDatabase db = YouTubeVideoDatabase.getDb(getApplicationContext());
            YouTubeVideo video = db.youTubeVideoDao().find(videoId);
            db.youTubeVideoDao().delete(video);

            // Display a toast message
            Toast.makeText(context, "Video deleted successfully", Toast.LENGTH_SHORT).show();

            // Finish the activity
            finish();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // Cancel the deletion
            dialog.dismiss();
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean toggleFavoriteStatus() {
        // Retrieve the video from the database
        YouTubeVideoDatabase db = YouTubeVideoDatabase.getDb(getApplicationContext());
        YouTubeVideo video = db.youTubeVideoDao().find(videoId);
        String message;
        boolean isFavorite;

        if (video.getFavorite() == 0) {
            // The video is not a favorite, so make it a favorite
            video.setFavorite(1);
            message = "Video added to favorites";
            isFavorite = true;
        } else {
            // The video is a favorite, so remove it from favorites
            video.setFavorite(0);
            message = "Video removed from favorites";
            isFavorite = false;
        }

        // Update the video in the database
        db.youTubeVideoDao().update(video);

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        return isFavorite;
    }

}
