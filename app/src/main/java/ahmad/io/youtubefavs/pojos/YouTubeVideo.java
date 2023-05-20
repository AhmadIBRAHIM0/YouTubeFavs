package ahmad.io.youtubefavs.pojos;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The type You tube video.
 */
@Entity
public class YouTubeVideo {


    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "favorite")
    private int favorite;

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets favorite.
     *
     * @return the favorite
     */
    public int getFavorite() {
        return favorite;
    }

    /**
     * Sets favorite.
     *
     * @param favorite the favorite
     */
    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    /**
     * Instantiates a new You tube video.
     *
     * @param title       the title
     * @param description the description
     * @param url         the url
     * @param category    the category
     * @param favorite    the favorite
     */
    public YouTubeVideo(String title, String description, String url, String category, int favorite) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.category = category;
        this.favorite = favorite;
    }

    /**
     * Instantiates a new You tube video.
     */
    public YouTubeVideo() {
    }

    @NonNull
    @Override
    public String toString() {
        return "YouTubeVideo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", category='" + category + '\'' +
                ", favorite=" + favorite +
                '}';
    }
}
