package ahmad.io.youtubefavs.pojos;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
}
