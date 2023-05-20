package ahmad.io.youtubefavs.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ahmad.io.youtubefavs.pojos.YouTubeVideo;

@Dao
public interface YouTubeVideoDao {

    /**
     * Find YouTube video.
     *
     * @param id the id of the YouTube video
     * @return the YouTube video found
     */
    @Query("SELECT * FROM youtubevideo WHERE id = :id")
    public YouTubeVideo find(Long id);

    /**
     * List YouTube videos.
     *
     * @return the YouTube videos list
     */
    @Query("SELECT * FROM youtubevideo")
    public List<YouTubeVideo> list();

    /**
     * Add Youtube video.
     *
     * @param youTubeVideos the YouTube videos to add
     */
    @Insert
    public void add(YouTubeVideo... youTubeVideos);

    /**
     * Update Youtube video.
     *
     * @param youTubeVideos the YouTube videos to update
     */
    @Update
    public void update(YouTubeVideo... youTubeVideos);

    /**
     * Delete Youtube video.
     *
     * @param youTubeVideos the YouTube videos to delete
     */
    @Delete
    public void delete(YouTubeVideo... youTubeVideos);

}
