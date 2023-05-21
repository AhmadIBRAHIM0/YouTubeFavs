package ahmad.io.youtubefavs.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ahmad.io.youtubefavs.R;
import ahmad.io.youtubefavs.data.YouTubeVideoDatabase;
import ahmad.io.youtubefavs.pojos.YouTubeVideo;

public class UpdateYouTubeActivity extends AppCompatActivity {

    private EditText titleEditText, descriptionEditText, urlEditText;
    private Spinner categorySpinner;
    private Context context;
    private Long videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_youtube_video);

        setTitle("Update YouTube Video");

        // Get the context
        context = getApplicationContext();

        // Show the back in the actionbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Get the elements
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        urlEditText = findViewById(R.id.urlEditText);
        categorySpinner = findViewById(R.id.categorySpinner);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnCancel = findViewById(R.id.btnCancel);
        createSpinner();

        // Retrieve the video details from the intent extras
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("videoId")) {
            this.videoId = intent.getLongExtra("videoId", 0);
            if (videoId != 0) {
                // Get the video from the database using the video id
                YouTubeVideoDatabase db = YouTubeVideoDatabase.getDb(context);
                YouTubeVideo youTubeVideo = db.youTubeVideoDao().find(videoId);
                if (youTubeVideo != null) {
                    // Set the video details to the elements
                    titleEditText.setText(youTubeVideo.getTitle());
                    descriptionEditText.setText(youTubeVideo.getDescription());
                    urlEditText.setText(youTubeVideo.getUrl());
                    // Get the categories from the resources
                    String[] categories = getResources().getStringArray(R.array.categories);

                    // Convert the array to a list
                    List<String> categoriesList = new ArrayList<>(Arrays.asList(categories));

                    // Create the adapter
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            context,
                            android.R.layout.simple_spinner_item,
                            categoriesList
                    );
                    categorySpinner.setSelection(adapter.getPosition(youTubeVideo.getCategory()));
                }
            }
        } else {
            Toast.makeText(context, "Error retrieving the video details", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnUpdate.setOnClickListener(v -> {

            // Get elements value
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String url = urlEditText.getText().toString();
            String category = categorySpinner.getSelectedItem().toString();

            if (title.isEmpty() || url.isEmpty() || description.isEmpty() || category.isEmpty()) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else if (!URLUtil.isValidUrl(url)) {
                Toast.makeText(context, "Please enter a valid URL", Toast.LENGTH_SHORT).show();
            } else {
                // Create a YouTubeVideo object
                YouTubeVideo youTubeVideo = new YouTubeVideo(title, description, url, category, 0);
                youTubeVideo.setId(videoId);

                // Get the database instance
                YouTubeVideoDatabase db = YouTubeVideoDatabase.getDb(context);

                // Insert the YouTubeVideo object into the database
                db.youTubeVideoDao().update(youTubeVideo);

                // Display a toast message
                Toast.makeText(context, "YouTube video updated successfully", Toast.LENGTH_SHORT).show();

                // Finish the activity
                finish();

//                // To go back to the main activity (But i don't like this, so i prefer to go back to the previous activity)
//                Intent newIntent = new Intent(UpdateYouTubeActivity.this, MainActivity.class);
//                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear the activity stack
//                startActivity(newIntent);
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    private void createSpinner() {

        // Get the categories from the resources
        String[] categories = getResources().getStringArray(R.array.categories);

        // Convert the array to a list
        List<String> categoriesList = new ArrayList<>(Arrays.asList(categories));

        // Create the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                categoriesList
        );

        // Set the dropdown layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the spinner
        categorySpinner.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
