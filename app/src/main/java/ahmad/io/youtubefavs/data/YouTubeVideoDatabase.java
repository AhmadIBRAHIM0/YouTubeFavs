package ahmad.io.youtubefavs.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ahmad.io.youtubefavs.dao.YouTubeVideoDao;
import ahmad.io.youtubefavs.pojos.YouTubeVideo;

/**
 * The type You tube video database.
 */
@Database(entities = {YouTubeVideo.class}, version = 1)
public abstract class YouTubeVideoDatabase extends RoomDatabase {

    /**
     * The constant DATABASE_NAME.
     */
    private static final String DATABASE_NAME = "youtubevideo";

    /**
     * Gets db.
     *
     * @param context the context
     * @return the db
     */
    public static YouTubeVideoDatabase getDb(Context context) {
        return Room.databaseBuilder(context, YouTubeVideoDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries().build();
    }

    /**
     * YouTube video dao.
     *
     * @return the YouTube video dao
     */
    public abstract YouTubeVideoDao youTubeVideoDao();

}
