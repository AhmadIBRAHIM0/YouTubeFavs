package ahmad.io.youtubefavs;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ahmad.io.youtubefavs.activities.AddYouTubeActivity;
import ahmad.io.youtubefavs.adapter.YouTubeVideoAdapter;
import ahmad.io.youtubefavs.data.YouTubeVideoDatabase;
import ahmad.io.youtubefavs.pojos.YouTubeVideo;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvYouTubeVideos;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_main);

        context = getApplicationContext();

        rvYouTubeVideos = findViewById(R.id.rvYouTubeVideos);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvYouTubeVideos.setHasFixedSize(true);
        rvYouTubeVideos.setLayoutManager(layoutManager);

        ImageView btnFilter = findViewById(R.id.ivFilter);
        btnFilter.setOnClickListener(v -> {
            showFilterMenu(btnFilter);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        YouTubeVideoAsyncTask asyncTask = new YouTubeVideoAsyncTask();
        asyncTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add_youtube_menu) {

            Intent intent = new Intent(context, AddYouTubeActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class YouTubeVideoAsyncTask extends AsyncTask<Void, Void, List<YouTubeVideo>> {

        @Override
        protected List<YouTubeVideo> doInBackground(Void... voids) {
            return YouTubeVideoDatabase.getDb(context).youTubeVideoDao().list();
        }

        @Override
        protected void onPostExecute(List<YouTubeVideo> youTubeVideos) {
            YouTubeVideoAdapter adapter = new YouTubeVideoAdapter(youTubeVideos);
            rvYouTubeVideos.setAdapter(adapter);

            // Check if the video list is empty
            if (youTubeVideos.isEmpty()) {
                TextView tvNoVideos = findViewById(R.id.tvNoVideos);
                tvNoVideos.setVisibility(View.VISIBLE);
            } else {
                TextView tvNoVideos = findViewById(R.id.tvNoVideos);
                tvNoVideos.setVisibility(View.GONE);
            }
        }

    }

    private void showFilterMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_filter_main, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_all) {
                // Filter videos to show all items
                filterVideos(false);
            } else if (item.getItemId() == R.id.menu_favorites) {
                // Filter videos to show only favorite items
                filterVideos(true);
            }
            return true;
        });

        popupMenu.show();
    }

    private void filterVideos(boolean showFavoritesOnly) {
        List<YouTubeVideo> videos;
        if (showFavoritesOnly) {
            // Filter videos to show only favorite items
            videos = YouTubeVideoDatabase.getDb(context).youTubeVideoDao().listFavorites();
        } else {
            // Show all videos
            videos = YouTubeVideoDatabase.getDb(context).youTubeVideoDao().list();
        }

        YouTubeVideoAdapter adapter = new YouTubeVideoAdapter(videos);
        rvYouTubeVideos.setAdapter(adapter);
    }

}