package ahmad.io.youtubefavs.activities;

import android.content.Context;
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

public class AddYouTubeActivity extends AppCompatActivity {

    private EditText titleEditText, descriptionEditText, urlEditText;
    private Spinner categorySpinner;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_youtube_video);

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
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnCancel = findViewById(R.id.btnCancel);

        createSpinner();

        btnAdd.setOnClickListener(v -> {

            // Get elements value
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String url = urlEditText.getText().toString();
            String category = categorySpinner.getSelectedItem().toString();

            if (title.isEmpty() || url.isEmpty() || description.isEmpty() || category.isEmpty()) {
                Toast.makeText(context, "The required fields cannot be empty!", Toast.LENGTH_SHORT).show();
            } else if (!URLUtil.isValidUrl(url)) {
                Toast.makeText(context, "The URL is not valid!", Toast.LENGTH_SHORT).show();
            } else {
                // Add the video to the database
                YouTubeVideoDatabase db = YouTubeVideoDatabase.getDb(context);
                YouTubeVideo youTube = new YouTubeVideo(title, description, url, category, 0);
                db.youTubeVideoDao().insert(youTube);

                // Show a success toast
                Toast.makeText(context, "The video is added successfully", Toast.LENGTH_SHORT).show();

                finish();
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
