package dsa.upc.edu.listapp.github;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchListResponse {

    public String nextPageToken;
    public List<SearchResult> items;

    public static class SearchResult {
        public Id id;
        public Snippet snippet;
    }

    public static class Id {
        public String videoId;
    }

    public static class Snippet {
        public String title;
        public Thumbnails thumbnails;
    }

    public static class Thumbnails {
        @SerializedName("default")
        public Thumbnail defaultThumbnail;
    }

    public static class Thumbnail {
        public String url;
    }
}
