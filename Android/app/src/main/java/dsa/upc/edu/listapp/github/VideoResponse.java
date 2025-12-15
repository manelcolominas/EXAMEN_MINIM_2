package dsa.upc.edu.listapp.github;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResponse {
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public static class Item {
        private String id;
        private Snippet snippet;

        public String getId() {
            return id;
        }

        public Snippet getSnippet() {
            return snippet;
        }
    }

    public static class Snippet {
        private String title;
        private Thumbnails thumbnails;

        public String getTitle() {
            return title;
        }

        public Thumbnails getThumbnails() {
            return thumbnails;
        }
    }

    public static class Thumbnails {
        @SerializedName("default")
        private Thumbnail defaultThumbnail;

        public Thumbnail getDefault() {
            return defaultThumbnail;
        }
    }

    public static class Thumbnail {
        private String url;

        public String getUrl() {
            return url;
        }
    }
}
