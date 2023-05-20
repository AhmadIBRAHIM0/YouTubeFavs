package ahmad.io.youtubefavs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Without AsyncTask
        List<YouTubeVideo> youTubeVideos = YouTubeVideoDatabase.getDb(context).youTubeVideoDao().list();

        YouTubeVideoAdapter adapter = new YouTubeVideoAdapter(youTubeVideos);
        rvYouTubeVideos.setAdapter(adapter);

        // With AsyncTask
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

    // class YouTubeVideoAsyncTask
    public class YouTubeVideoAsyncTask extends AsyncTask<Void, Void, List<YouTubeVideo>> {

        @Override
        protected List<YouTubeVideo> doInBackground(Void... voids) {
            return YouTubeVideoDatabase.getDb(context).youTubeVideoDao().list();
        }

        @Override
        protected void onPostExecute(List<YouTubeVideo> youTubeVideos) {

            YouTubeVideoAdapter adapter = new YouTubeVideoAdapter(youTubeVideos);
            rvYouTubeVideos.setAdapter(adapter);
        }
    }
}